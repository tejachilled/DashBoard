package com.peer.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
	static Connection conn = null;
	static String url = "jdbc:mysql://localhost:3306/";
	static String dbName = "dashboard";
	static String driver = "com.mysql.jdbc.Driver";
	static String userName = "ravi";
	static String password = "teja";
	Database(){	}
	public static boolean validate(BeanClass student) throws SQLException{
		GetConnection();
		String sqlQuery = "SELECT PASSWRD FROM USER_TABLE WHERE USER_ID = ?";
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setString(1, student.getUsername());
		ResultSet rs = ps.executeQuery();
		String pwd ="";
		while(rs.next()){
			pwd = rs.getString("PASSWRD");			
		}
		System.out.println(student.getUsername()+" :login info: "+student.getPassword());
		System.out.println("DB pwd: "+pwd);

		conn.close();
		System.out.println("Disconnected from database");
		boolean flag = false;
		if(pwd.equals(student.getPassword())) flag = true;
		return flag;
	}
	public static BeanClass UserDataUploadtoDB(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		GetConnection();
		int count = getCount(student.getUsername());
		student.setCount(count);
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		System.out.println("todays date is : "+date);
		System.out.println("No of Submissions: "+count);
		if(count<=10){
			String sqlQuery = "INSERT INTO USER_ASSIGNMENT(USER_ID,IMAGEFILE,CONTENT,LINK,SUBMISSIONCOUNT,SUBMISSIONDATE,NOOFIMAGES,WORDCOUNT,CHARCOUNT) VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sqlQuery);
			ps.setString(1, student.getUsername());
			ps.setString(2, student.getImagefile());
			ps.setString(3, student.getContent());
			ps.setString(4, student.getLink());
			ps.setInt(5, count+1);
			ps.setTimestamp(6, date);
			ps.setInt(7, student.getImagesNumber());
			ps.setInt(8, student.getWordCount());
			ps.setInt(9, student.getCharCount());
			ps.executeUpdate();
			student.setSubmission_date(""+date);
			System.out.println("Inserted a record into DB");

		}
		conn.close();
		System.out.println("Disconnected from database");
		return student;
	}

	public static int getCount(String uid) throws SQLException{
		String sqlQuery = "SELECT SUBMISSIONCOUNT FROM USER_ASSIGNMENT WHERE USER_ID = ?";
		int count = 0;
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setString(1, uid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			count = rs.getInt("COUNT");
		}

		return count;
	}
	public static BeanClass RetrieveInfo(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		GetConnection();
		String sqlQuery = "SELECT * FROM user_assignment WHERE USER_ID = ?";
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setString(1, student.getUsername());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			student.setContent(rs.getString("CONTENT"));
			student.setCount(rs.getInt("SUBMISSIONCOUNT"));
			student.setImagefile(rs.getString("IMAGEFILE"));
			student.setLink(rs.getString("LINK"));
			student.setAssignment_name(rs.getString("assignment_name"));
			student.setGroup_id(rs.getString("group_id"));
			student.setSubmission_date(""+rs.getTimestamp("submissionDate"));
			student.setImagesNumber(rs.getInt("NOOFIMAGES"));
			student.setWordCount(rs.getInt("WORDCOUNT"));
			student.setCharCount(rs.getInt("CHARCOUNT"));
		}
		conn.close();
		return student;
	}
	public static void GetConnection(){
		System.out.println("-------- MySQL JDBC Connection Testing ------------");
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
			System.out.println("Connected to the database");

		} catch (Exception e) {
			System.out.println("NO CONNECTION =(");
		}

	}
	public static ArrayList<BeanClass> GetPeerInfo(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		GetConnection();
		String sqlQuery = "SELECT * FROM user_assignment WHERE USER_ID != ?";
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setString(1, student.getUsername());
		ResultSet rs = ps.executeQuery();
		ArrayList<BeanClass> alist = new ArrayList<BeanClass>();
		while(rs.next()){
			BeanClass peer = new BeanClass();
			peer.setUsername(rs.getString("USER_ID"));
			peer.setContent(rs.getString("CONTENT"));
			peer.setCount(rs.getInt("SUBMISSIONCOUNT"));
			peer.setImagefile(rs.getString("IMAGEFILE"));
			peer.setLink(rs.getString("LINK"));
			peer.setAssignment_name(rs.getString("assignment_name"));
			peer.setGroup_id(rs.getString("group_id"));
			peer.setSubmission_date(""+rs.getTimestamp("submissionDate"));
			peer.setImagesNumber(rs.getInt("NOOFIMAGES"));
			peer.setWordCount(rs.getInt("WORDCOUNT"));
			peer.setCharCount(rs.getInt("CHARCOUNT"));			
			alist.add(peer);
		}
		
		conn.close();
		return alist;
	}
	
	
}
