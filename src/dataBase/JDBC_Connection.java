package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_Connection {
	static String drivername ="com.mysql.jdbc.Driver";//mysql���ݿ�����
	static String url="jdbc:mysql://localhost:3306/photo_album";//���ӵ����ݿ��ַ
	static String username="root";//�������ݿ��û���
	static String password="linwenjie";//�������ݿ���
	//���������ľ�̬�����

	static{
		try {
			Class.forName(drivername);//��������
			//System.out.println("���������ɹ���");
		} catch (ClassNotFoundException e) {
		//	System.out.println("��������ʧ�ܣ�");
			e.printStackTrace();
		}
	}

	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(url,username,password);//��������
			System.out.println("�������ݿ�ɹ���");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return conn;
	}

	public static void free(ResultSet rs,Connection conn ,Statement stmt){
		
			try {
				if(rs !=null)
					rs.close();//�رս����
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				
					try {
						if(conn != null)
							conn.close();//�ر�����
					} catch (SQLException e) {
						e.printStackTrace();
					}finally{
						try {
							if(stmt != null)
								stmt.close();//�ر�Statement����
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
			}
	}
	//����
public static void main(String[] args) {
	
	JDBC_Connection.getConnection();
}
}
