package game;

import game.MessageControl;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;

/**
 * 
 * @author Aries Zhao
 * 
 */
public class GameServlet extends HttpServlet implements CometProcessor {

	private static final long serialVersionUID = -3667180332947986301L;

	// <�û�,������>
	protected static Map<String, HttpServletResponse> connections = new HashMap<String, HttpServletResponse>();

	// ��Ϣ�����߳�
	protected static MessageSender messageSender = null;
	

	public void init() throws ServletException {
		// ������Ϣ�����߳�
		messageSender = new MessageSender();
		Thread messageSenderThread = new Thread(messageSender, "MessageSender["
				+ getServletContext().getContextPath() + "]");
		messageSenderThread.setDaemon(true);
		messageSenderThread.start();
	}

	public void destroy() {
		connections.clear();
		messageSender.stop();
		messageSender = null;
	}

	public void event(CometEvent event) throws IOException, ServletException {
		HttpServletRequest request = event.getHttpServletRequest();
		HttpServletResponse response = event.getHttpServletResponse();
		response.setCharacterEncoding("utf-8");
		// �û���
		String name =new String (request.getParameter("user").getBytes("ISO8859_1"),"utf-8");
		log(name);
		if (event.getEventType() == CometEvent.EventType.BEGIN) {
			// Http���ӿ��г�ʱ
			event.setTimeout(Integer.MAX_VALUE);
			log("Begin for session: " + request.getSession(true).getId());

			//  Iframe ������
			PrintWriter writer = response.getWriter();
			writer.println("<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">");
			writer.println("<html><head><script type=\"text/javascript\">var comet = window.parent.Game;</script></head><body>");
			writer.println("<script type=\"text/javascript\">");
			writer.println("var comet = window.parent.Game;");
			writer.println("</script>");
			writer.flush();

			// for chrome ���⡣��
			if (request.getHeader("User-Agent").contains("KHTML")) {
				for (int i = 0; i < 100; i++) {
					writer.print("<input type=\"hidden\" name=\"none\" value=\"none\">");
				}
				writer.flush();
			}
			// ��ӭ��Ϣ
			writer.print("<script type=\"text/javascript\" charset=\"utf-8\">");
			writer.println("comet.showMessage('Hello " + name + ", Welcome!');");
			writer.print("</script>");
			writer.flush();
			
			//���������û������û���¼
			if (!connections.containsKey(name)) {
				messageSender.login(name);
			}
			// �����Ѿ���½���û���Ϣ
			for (String user : connections.keySet()) {
				if (!user.equals(name)) {
					log("ԭ����ң� " +user);
					writer.print("<script type=\"text/javascript\">");
					writer.println("comet.newUser('" + user + "');");
					writer.print("</script>");
					if(MessageControl.userInfo.get(user).getReady() == 1) {
						writer.print("<script type=\"text/javascript\">");
						writer.println("comet.readyStatus('" + user +"', '1');" );
						writer.print("</script>");
						writer.flush();
					}			
				}
			}
			writer.flush();

			synchronized (connections) {
				connections.put(name, response);
				MessageControl.addUserInfo(name);
			}
		} else if (event.getEventType() == CometEvent.EventType.ERROR) {
			log("Error for session: " + request.getSession(true).getId());
			synchronized (connections) {
				connections.remove(name);
				MessageControl.deleteUserInfo(name);
				if(connections.size() == 0) {
					MessageControl.clear();
				}
			}
			event.close();
		} else if (event.getEventType() == CometEvent.EventType.END) {
			log("End for session: " + request.getSession(true).getId());
			messageSender.logout(name);
			synchronized (connections) {
				connections.remove(name);
				MessageControl.deleteUserInfo(name);
				if(connections.size() == 0) {
					MessageControl.clear();
				}
			}
			PrintWriter writer = response.getWriter();
			writer.println("</body></html>");
			event.close();
		} else if (event.getEventType() == CometEvent.EventType.READ) {
			InputStream is = request.getInputStream();
			byte[] buf = new byte[512];
			do {
				int n = is.read(buf); 
				if (n > 0) {
					log("Read " + n + " bytes: " + new String(buf, 0, n)+ " for session: "+ request.getSession(true).getId());
				} else if (n < 0) {
					return;
				}
			} while (is.available() > 0);
		}
	}

	// ������Ϣ��������
	public static void send(String message) {
		messageSender.send("*", message);
	}

	// ������˷�����Ϣ
	public static void send(String name, String message) {
		messageSender.send(name, message);
	}

	public class MessageSender implements Runnable {

		protected boolean running = true;
		protected Map<String, String> messages = new HashMap<String, String>();

		public MessageSender() {		
		}

		public void stop() {
			running = false;
		}

		// ���û���½
		public void login(String name) {
			synchronized (messages) {
				messages.put("Login", name);
				messages.notify();
			}
		}

		// �û�����
		public void logout(String name) {
			synchronized (messages) {
				messages.put("Logout", name);
				messages.notify();
			}
		}

		// ������Ϣ
		public void send(String user, String message) {
			synchronized (messages) {
				messages.put(user, message);
				messages.notify();
			}
		}
		
		public void run() {
			while (running) {
				if (messages.size() == 0) {
					try {
						synchronized (messages) {
							messages.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				synchronized (connections) {
					synchronized (messages) {
						for (Entry<String, String> message : messages.entrySet()) {
							if ("Login".equals(message.getKey())) {// ���û���½
								log(message.getValue() + " Login");
								for (HttpServletResponse response : connections.values()){
									if(response != connections.get(message.getValue())) {
										try {
											PrintWriter writer = response.getWriter();
											writer.print("<script type=\"text/javascript\">");
											writer.println("comet.newUser('"+ message.getValue() + "');");
											writer.print("</script>");
											writer.flush();
										} catch (IOException e) {
											log("IOExeption execute command", e);
										}
									}	
								}
							} else if ("Logout".equals(message.getKey())) {// �û��˳�
								log(message.getValue() + " Logout");
								for (HttpServletResponse response : connections.values()) {
									try {
										PrintWriter writer = response.getWriter();
										writer.print("<script type=\"text/javascript\">");
										writer.println("comet.showMessage('�û���"+ message.getValue() + "�뿪��Ϸ�ˣ�');");
										writer.println("comet.moveUser('"+ message.getValue() + "');");
										writer.print("</script>");
										writer.flush();
									} catch (IOException e) {
										log("IOExeption execute command", e);
									}
								}
							} else if ("order".equals(message.getKey())) {
								log("Send the game stage : "+ MessageControl.stage + " to everyone.");
									for (HttpServletResponse response : connections.values()) {
										try {
											PrintWriter writer = response.getWriter();
											writer.print("<script type=\"text/javascript\">");
											writer.println("comet.order('" + MessageControl.stage +"', '" + message.getValue() + "');" );
											writer.print("</script>");
											writer.flush();
										}catch(IOException e){
											log("the round" + MessageControl.stage +"have some errors");
										}
									}
							}else if("ready".equals(message.getKey())) {
								for (HttpServletResponse response : connections.values()) {
									try {
										PrintWriter writer = response.getWriter();
										writer.print("<script type=\"text/javascript\">");
										writer.println("comet.readyStatus('" + message.getValue() + "');" );
										writer.print("</script>");
										writer.flush();
									}catch(IOException e){
										log("the round" + MessageControl.stage +"have some errors");
									}
								}
							}else if(message.getKey().indexOf("showRole") != -1){
								int i=message.getValue().indexOf(" And ");
								String user=message.getValue().substring(0, i);
								String role=message.getValue().substring(i+5,message.getValue().length());
								log("send  Role :" + role + "to user :" +user);
								HttpServletResponse response =connections.get(user);
								try{
									PrintWriter writer = response.getWriter();
									writer.print("<script type=\"text/javascript\">");
									writer.println("comet.showRole('" + role +"');");
									writer.print("</script>");
									writer.flush();
								}catch(IOException e){
									log("the round for send role has some error");
								}
							}else if("talk".equals(message.getKey())) { //����
								log("Now is turn " + message.getValue() + " to talking");
								HttpServletResponse response = connections.get(message.getValue());
								try{
									PrintWriter writer = response.getWriter();
									writer.print("<script type=\"text/javascript\">");
									writer.println("comet.talk();");
									writer.print("</script>");
									writer.flush();
								}catch (IOException e) {
									e.printStackTrace();
								}
							}else if ("*".equals(message.getKey())) {// ����
								log("Send message: " + message.getValue()+ " to everyone.");
								for (HttpServletResponse response : connections.values()) {
									try {
										PrintWriter writer = response.getWriter();
										writer.print("<script type=\"text/javascript\">");
										writer.println("comet.showMessage('"+ message.getValue() + "');");
										writer.print("</script>");
										writer.flush();
									} catch (IOException e) {
										log("IOExeption execute command", e);
									}
								}
							} else {// �����˷���
								try {
									HttpServletResponse response = connections.get(message.getKey());
									PrintWriter writer = response.getWriter();
									writer.print("<script type=\"text/javascript\">");
									writer.println("comet.showMessage('"+ message.getValue() + "');");
									writer.print("</script>");
									writer.flush();
								} catch (IOException e) {
									log("IOExeption sending message", e);
								}
							}
							// ����Ϣ������ɾ����Ϣ
						}
						messages.clear();
					}
				}
			}
		}
	}
}