package dataBase;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import dataBase.JDBC_Connection;

public class SqlOption {
	static Connection conn = JDBC_Connection.getConnection();
	static ResultSet rs;
	static Statement stm;
	static String sql;
	public static void addUser(String user,String password,String url) {
		sql = "insert into User (name,password,url) values ('" + user +"','" +password +"','" +
				url + "');";
		try{
			stm = conn.createStatement();
			stm.executeUpdate(sql);
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBC_Connection.free(rs, conn, stm);
		}
	}
	public static boolean checkUser(String user) {
		sql = "select * from User where name='"+user +"';";
		int i=0;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next())
				i++;
		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			JDBC_Connection.free(rs, conn, stm);
		}
		if(i!=0) 
			return true;
		else
			return false;
	}
	public static boolean checkUser(String user,String password) {
		sql = "select * from User where name ='" + user +"'and password='" + password + "';";
		int i=0;
		try{
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next())
				i++;
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBC_Connection.free(rs, conn, stm);
		}
		if(i != 0)
			return true;
		else 
			return false;
	}
}
