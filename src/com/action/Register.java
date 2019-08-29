package com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://47.98.168.203:3306/User";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "AAAaaa111111!";
    
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//http://Ŀ��ip:8080/MusicSelectServer/register?uname=18762818600&upwd=111
		
		String name = req.getParameter("uname");
		String upwd = req.getParameter("upwd");
		//resultΪ-1��ʾ�������ɹ���Ϊ0��ʾҪע����ֻ����Ѿ����ڣ�Ϊ1��ʾע��ɹ�
		int result  = -1;
		Connection connection=null;
		PreparedStatement ps=null;
		try{
			//�������ݿ�����
			Class.forName(DATABASE_DRIVER);
			//�������ݿ�
			connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
			//��ѯҪע����ֻ����Ƿ����
			String sql = "select * from Account where phone = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet resultSet = (ResultSet) ps.executeQuery();
            if(resultSet.next()){
                result = 0;
            }
            else{
            	sql = "INSERT INTO Account(phone,password) VALUES (?,?)";
            	ps = connection.prepareStatement(sql);
            	ps.setString(1, name);
            	ps.setString(2, upwd);
            	result = ps.executeUpdate();
            }
			
            if (ps!=null) {
                ps.close();
            }
            if (connection!=null) {
                connection.close();
            }
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch(SQLException e){
            	e.printStackTrace();
		}
		PrintWriter out = resp.getWriter();
		out.print(result);
		out.flush();
	}

}
