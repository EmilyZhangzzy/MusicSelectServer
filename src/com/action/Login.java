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

public class Login extends HttpServlet {
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://47.98.168.203:3306/User";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "AAAaaa111111!";
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//http://目标ip:8080/MusicSelectServer/login?uname=18762818600&upwd=111
		
		String name = req.getParameter("uname");
		String upwd = req.getParameter("upwd");
		String upwdDatabase = "";
		//result为-2表示操作不成功，为-1表示手机号不存在，为0表示要登陆的手机号存在但密码不正确，为1表示登陆成功
		int result  = -2;
		Connection connection=null;
		PreparedStatement ps=null;
		try{
			Class.forName(DATABASE_DRIVER);
			connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
			String sql = "select * from Account where phone = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet resultSet = (ResultSet) ps.executeQuery();
            if(resultSet.next()){
            	upwdDatabase = resultSet.getString("password");
                result = 0;
            }
            else{
            	result = -1;
            }
            if(upwdDatabase.equals(upwd))
            {
            	//如果账号密码匹配，result为1
            	 result = 1;
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
