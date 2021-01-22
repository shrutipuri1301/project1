package com.examination;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.connection.DatabaseConnection;
//import com.sun.crypto.provider.RSACipher;

/**
 * Servlet implementation class examination
 */
@WebServlet("/Examination")
public class Examination extends HttpServlet {
	private static final long serialVersionUID = 1L;//this variable is automatic provide by servlet

	protected void doPost(HttpServletRequest request, HttpServletResponse response)//we specifie the 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			 //statements that may cause an exception
			int hitAnswer = 0;//define
			int submitAnswer = 0;
			int id = 0;
			HttpSession session = request.getSession();//return the current session related  with this request
			int studentId = (Integer) session.getAttribute("uid");
			ResultSet resultSet = DatabaseConnection.getResultFromSqlQuery("select * from questions where subjectTopic='"+session.getAttribute("selectTopic")+"'");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());//for timing we use timestamp
			currenttimemillis()which return the current time in miliseconds
			while (resultSet.next()) {
				id = resultSet.getInt(1);//we get id value  from the database
				submitAnswer = DatabaseConnection.insertUpdateFromSqlQuery(
						"insert into tblStudentResult(studentId,question_id,hitAnswer,examdate,subjectTopic) values('" + studentId + "','"
								+ id + "','" + request.getParameter(resultSet.getString(1)) + "','"+timestamp+"','"+session.getAttribute("selectTopic")+"')");
			}
			if (submitAnswer > 0) {
				response.sendRedirect("thanksSubmitingExam.jsp");
			} else {
				response.sendRedirect("examTest.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
