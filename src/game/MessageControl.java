package game;

import game.GameServlet;
import com.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessageControl extends HttpServlet {
   
	/*
	 * stage为gamed的游戏进度
	 *  0:准备阶段
	 *	1: 守卫进行守卫
	 *	2: 狼人杀人
	 *	3 ：女巫主场
	 *	4：预言家预言
	 *	5：发言
	 *  6：投票
	 */
	private static final long serialVersionUID = -5662843742773158521L;
	public static  Map<String,User> userInfo = new HashMap<String,User>();//锟斤拷锟斤拷锟矫伙拷锟斤拷息
	public static int stage =0;//锟斤拷戏锟斤拷锟?
	public static int cnt = 0;//锟斤拷锟斤拷准锟斤拷锟斤拷锟斤拷
	private Iterator<User> iterator;
	private int[] info = {1,2,2,3,4,5,5,5};
	public static int[] vis = {0,1,2,1,1,3};
	/*
	 * 锟斤拷8锟斤拷锟斤拷色锟斤拷1锟斤拷锟斤拷锟斤拷2锟斤拷锟剿ｏ拷1女锟阶ｏ拷1预锟皆家ｏ拷3平锟斤拷
	 * 1 为锟斤拷锟斤拷
	 * 2为锟斤拷锟斤拷
	 * 3为女锟斤拷
	 * 4为预锟皆硷拷
	 * 5为平锟斤拷
	 */
	private String guarded = null; //被守卫的人
	private String killed  =  ""; // 被杀死的人
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); //时间格式
	private Integer num = 0; //投票计数
	public MessageControl() {
		super();
	}
	
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String message =new String (request.getParameter("message").getBytes("ISO8859_1"),"utf-8"); //聊天内容
		String userName =new String (request.getParameter("user").getBytes("ISO8859_1"),"utf-8");  //用户名
		String data = new String (request.getParameter("data").getBytes("ISO8859_1"),"utf-8") ; //游戏数据
		log("userName ="+userName +" message=" + message +" data:" + data);
		if ( killed.indexOf("userName") != -1 && !userInfo.get(userName).isAlive() ) {		
			message = userName + "(死亡) : " + message + "  [" + sdf.format(new Date()) + "] (此信息仅死亡的人可见)";
			for(User user : userInfo.values()) {
				if(!user.isAlive()) {
					GameServlet.send(user.getName(),message);
				}
			}
		} else {
			switch(stage){
			case 0 : {
				if(!message.equals("null")) {
					message = userName + " : " + message + "  [" + sdf.format(new Date()) + "]";
					GameServlet.send(message);
				} else {
					if("ready".equals(data)){
						cnt++;
						User user = userInfo.get(userName);
						user.setReady();
						userInfo.put(userName, user);
						log(userName+"准备了 ,共有 " + cnt + "人准备了");
						if( cnt == 8){
							info = getNewInfo(info);
							setInfo();
							cnt = 0;
							stage++;
							new Timer().schedule(new orderTask(), 5000);
						} else {
						  GameServlet.send("ready",userName+"','1");
						}
					}else {
						GameServlet.send("ready",userName+"','0");
						cnt--;
						User user = userInfo.get(userName);
						user.moveReady();
						userInfo.put(userName, user);
					}	
				}	
				break;
			}//end case 0
			case 1 : {
				this.guarded = data;
				log("被守卫的人是:" + data);
				stage++;
				GameServlet.send("order",null);
				break;
			}//end case 1
			case 2 : {
				if(data.equals("null") && ! message.equals("null")) {
					for(User user : userInfo.values()) {
						if( user.getInfo() == 2 && user.isAlive() ) {
						    message = userName + " : " + message + "  [" + sdf.format(new Date()) + "]";
							GameServlet.send(user.getName(), message);
						}
					}
					break;
				}
				if(killed == "") {
					killed = data;
					log ("狼人 "+ userName +" 选择杀" + data);
					if(vis[2] == 1)  {
						stage++;
//						if(killed.equals("null")) {
//							killed = userInfo.entrySet()
//						}
						User user1 = userInfo.get(data);
						user1.setDeadSatus();
						vis[user1.getInfo()]--;
						userInfo.put(data, user1);
						log("被杀人的人是： " + killed);
						GameServlet.send("order", killed);
						break;
					}
				}else if (data.equals(killed)) {
					stage++;
					log("统一杀："+data);
					if( ! data.equals(guarded)) {
						User user1 = userInfo.get(data);
						user1.setDeadSatus();
						vis[user1.getInfo()]--;
						userInfo.put(data, user1);
						log("死掉的人是"  + killed);
						GameServlet.send("order",killed);
					}else {
						killed = "";
						log ("没有人死掉");
						GameServlet.send("order", null);
					}				
				}else {
					killed  = "";
					log("狼人意见不同一，重新选过！");
					GameServlet.send("order","repeat");
				}
				break;
			}//end case 2
			case 3 : {
				User user;
				if(!data.equals("null")){
					if(data.equals(killed)) {
					    user = userInfo.get(killed);
						user.setAliveStatus();
						vis[user.getInfo()]++;
						userInfo.put(killed, user);
						log("女巫救玩家" + killed);
						killed = "";
					} else {
					    user = userInfo.get(data);
					    user.setDeadSatus();
					    vis[user.getInfo()]--;
					    log ("女巫毒死了  " + data);
					    if(killed != "")
					    killed +="," + data;
					    else
					    killed = data;
					}
				}
				stage++;
				GameServlet.send("order",null);
				break;
			}//end case 3
			case 4 : {
				if( !data.equals("null")) {
					User user = userInfo.get(data);
					killed = (killed == "") ? "null":killed;
					if(user.getInfo() == 2) {
						GameServlet.send("order","Y," + killed);
					}else {
						GameServlet.send("order","N," + killed);
					}
				}else {
					GameServlet.send("order","O," + killed);
				}
				if(isEnd()){
					stage=7;
					String result ="";
					for(User user1 : userInfo.values()) 
					result += user1.getName() + user1.getInfo() + " ";
					if(vis[2]!=0)
						result += "狼人一方获得胜利！";
					else
						result += "好人一方战胜了狼人！";
					GameServlet.send("order",result);
					new Timer().schedule(new cleanTask(),8000);
					new Timer().schedule(new orderTask(), 10000);
					break;
				}
				stage++;
				iterator = userInfo.values().iterator();
				User user = iterator.next();
				while( !user.isAlive()) {
					user = iterator.next();
				}
				new Timer().schedule(new talkTask(user.getName()), 4000);
				break;
			}// end case 4
			case 5 : {
				if( message.equals("null") ) {
					if(iterator.hasNext()) {
						User user = iterator.next();
						while(iterator.hasNext() &&!user.isAlive()) {
							user = iterator.next();
						}
						GameServlet.send("talk",user.getName());
					}else {
						stage++;
						GameServlet.send("order",null);
					}
				} else {
					message = userName + " : " + message + "  [" + sdf.format(new Date()) + "]";
					GameServlet.send(message);
				}
				break;
			}//end case 5
			case  6 : {
				String ms;
				if(data.endsWith("null")) {
					ms = "玩家 :" + userName +" 放弃投票 "  + " [" + sdf.format(new Date()) + "]";
				}else {
					ms = "玩家 :" + userName +" 投票给了 " + data + " [" + sdf.format(new Date()) + "]";
					User user = userInfo.get(data);
					user.addVote();
					userInfo.put(data,user);
				}
				GameServlet.send(ms);
				synchronized (num) {
					num++;
				}
				if(num >= numOfAlive()) {
					String dead =peopleOfDead();
					if(dead.indexOf("and") != -1) {
						GameServlet.send("order", dead);
						break;
					}
					User user = userInfo.get(dead);
					user.setDeadSatus();
					vis[user.getInfo()]--;
					userInfo.put(dead, user);
					if(isEnd()) {
						stage++;
						String result ="";
						for(User user1 : userInfo.values()) 
						result += user1.getName() + user1.getInfo() + " ";
						if(vis[2]!=0)
							result += "狼人一方获得胜利！";
						else
							result += "好人一方战胜了狼人！";
						GameServlet.send("order",result);
						new Timer().schedule(new cleanTask(),8000);
						new Timer().schedule(new orderTask(), 10000);
					}else {
						stage = 1;
						num = 0;
						killed="";
						GameServlet.send("order",dead);
					}
				}
				break;
			 }//end case 6
			default : break;
		   }// end switch
		 }//end else
	}//end service
	private int numOfAlive() {
		int i,num = 0;
		for(i = 1;i<6 ;i++) {
			num += vis[i];
		}
		return num;
	}
	private String peopleOfDead() {
		int max=0;
		String dead = "";
		for(User user : userInfo.values()) {
			if(user.isAlive()) {
				int num = user.getVote();
				if(max < num) {
					dead = user.getName();
					max = num;
				} else if( max == num) {
					dead +="and" + user.getName();
				}
			}
		}
		return dead;
	}
	private int[] getNewInfo(int[] a) {
		int i,j = 0,temp;
		for(i = 0;i < 8; i++){
			j += (int) (Math.random()*100);
			j = j%8 ;
			temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
		return a;
	}
	private void setInfo() {
		int i = 0;
		for(String name : GameServlet.connections.keySet()){
			User user = new User(name);
			user.setInfo(info[i]);
			log("user ["+ user.getName() + "] :" + user.getInfo());
			userInfo.put(name,user);
			String data = name +" And " + (new Integer(info[i++]).toString());
			GameServlet.send("showRole"+i,data);
			//new Timer().schedule(new sendRole(data), (i-1)*50);	
		}
	}
	public void clean() {
		this.stage = 0;
		this.cnt = 0;
		this.killed = "";
		this.guarded = null;
		this.num = 0;
		for(User user :userInfo.values()) {
			user.reset();
			userInfo.put(user.getName(), user);
		}
		vis[1]=vis[3]=vis[4]=1;
		vis[2]=2;
		vis[5]=3;
	}
	public static void clear() {
		stage = 0;
		cnt = 0 ;
		vis[1]=vis[3]=vis[4]=1;
		vis[2]=2;
		vis[5]=3;
	}
	private boolean isEnd() {
		if( (vis[1]+vis[3]+vis[4]+vis[5]) == 0 || vis[2] == 0)
			return true;
		else
			return false;
	}
	public static  void addUserInfo(String name) {
		User user = new User(name);
		synchronized (userInfo) {
			userInfo.put(name, user);
			//userInfo.notify();
		}
	}
	public  static void deleteUserInfo(String name) {
		synchronized (userInfo) {
			if(userInfo.get(name).getReady() ==1)
				cnt--;
			if(stage != 0) {
				vis[userInfo.get(name).getInfo()]--;
			}	
			userInfo.remove(name);
			//userInfo.notify();
		}	
	}
	private class orderTask extends TimerTask {
		public void run() {
			GameServlet.send("order",null);
		}
	}
	private class cleanTask extends TimerTask {
		public void  run() {
			clean();
		}
	}
	private class talkTask extends TimerTask {
		String name;
		public talkTask (String name) {
			this.name = name;
		}
		public void run() {
			GameServlet.send("talk",name);
		}
	}
}
