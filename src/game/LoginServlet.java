package game;

import dataBase.SqlOption;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
public class LoginServlet extends HttpServlet {
	
	public void init(){
		try{
			super.init();
		}catch(ServletException e){
			e.printStackTrace();
		}
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws 
		IOException,ServletException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		SqlOption check = new SqlOption();
		if(check.checkUser(name, password)) {
			request.getRequestDispatcher("Room.jsp").include(request, response);
		} else {
			
		}
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws 
	IOException,ServletException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		SqlOption check = new SqlOption();
		if(check.checkUser(name, password)) {
			request.getRequestDispatcher("Room.jsp").include(request, response);
		} else {
			
		}
	}
	
	public void destroy(){
		super.destroy();
	}
}
