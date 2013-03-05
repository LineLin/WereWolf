package com;
import javax.servlet.http.*;
import org.apache.catalina.comet.*;
import java.util.*;
import javax.servlet.ServletException;
import java.io.*;
public class Demo extends HttpServlet {
	protected  void doGet(HttpServletRequest request,HttpServletResponse response)throws 
	IOException,ServletException {
		PrintWriter writer = response.getWriter();
		writer.println("<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">");
		writer.println("<html><head><script type=\"text/javascript\">var comet = window.parent.Game;</script></head><body>");
		writer.println("<script type=\"text/javascript\">");
		writer.println("var comet = window.parent.Game;");
		writer.println("</script>");
		writer.flush();
		writer.print("<script type=\"text/javascript\">");
		writer.println("comet.showMessage();"); //这里写调用的函数。
		writer.print("</script>");
		writer.flush();
	}
	public void destroy() {
		super.destroy();
	}
	public void init()  {
		try {
			super.init();
		}catch (ServletException e){
			e.printStackTrace();
		}
	}
}
