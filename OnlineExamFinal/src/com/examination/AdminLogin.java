package com.examination;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.connection.DatabaseConnection;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {//Can you explain why servlets extend HttpServlet? - 
	//Most of the web applications uses HTTP protocol. The user requests need to be received
	//and processed through the HTTP protocol. - Hence, the servlets should extend HttpServlet and override the doGet () 
	//and / or doPost () methods, depending on the data that is sent by GET or by POST.
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String uname = request.getParameter("uname");
			String pass = request.getParameter("upass");
			HttpSession hs = request.getSession();
			String tokens = UUID.randomUUID().toString();//UUID stands for Universally Unique Identifier
			//) method randomly generate the UUID
			Connection con = DatabaseConnection.getConnection();
			Statement st = con.createStatement();
			
				ResultSet resultset = st.executeQuery("select * from tbladmin where uname='" + uname + "' AND upass='" + pass + "'");
				if (resultset.next()) {
					hs.setAttribute("uname", uname);
					response.sendRedirect("addStudent.jsp?_tokens='" + tokens + "'");
				} else {
					response.sendRedirect("adminLogin.jsp");
				}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
