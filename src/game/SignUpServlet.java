package game;

import dataBase.SqlOption;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
public class SignUpServlet extends HttpServlet {
	
	public void init(){
		try{
			super.init();
		}catch(ServletException e){
			e.printStackTrace();
		}
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws 
		IOException,ServletException {
		
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws 
	IOException,ServletException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String url = request.getParameter("url");
		SqlOption option = new SqlOption();
		if( !option.checkUser(name)) {
			option.addUser(name, password, url) ;
			request.getRequestDispatcher("Room.jsp").include(request, response);
		}else {
			//用户名已存在
		}
	}
	public void destroy(){
		super.destroy();
	}
}
