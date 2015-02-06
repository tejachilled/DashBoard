package com.peer.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class Database {
	private static Connection conn = null;
	private  static String url = null;
	private  static String dbName = null;
	private static String driver = null;
	private static String userName = null;
	private static String password = null;
	public static String student = null;
	public static String ta = null;
	public static String teacher = null;

	Database(){	}
	public static BeanClass validate(BeanClass student) throws SQLException{

		if(conn==null || conn.isClosed())GetConnection();
		String sqlQuery = "SELECT PASSWRD,ROLE FROM USER_TABLE WHERE USER_ID = ?";
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setString(1, student.getUsername());
		ResultSet rs = ps.executeQuery();
		String pwd =""; String role = "";
		while(rs.next()){
			pwd = rs.getString(1);	
			role = rs.getString(2);
		}
		System.out.println(student.getUsername()+" :login info: "+student.getPassword());
		System.out.println("DB pwd: "+pwd + " role: "+role);
		if(pwd.equals(student.getPassword())){
			student.setRole(role);
			if(role.equalsIgnoreCase(Database.student))
				student = RetrieveInfo(student);
			else if(role.equalsIgnoreCase(teacher)||role.equalsIgnoreCase(ta))
				student = TeacherInfo(student);
		}else{
			student = null;
		}
		ps.close();
		conn.close();
		System.out.println("Disconnected from database");

		return student;
	}
	private static BeanClass TeacherInfo(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		if(conn==null || conn.isClosed())GetConnection();
		ArrayList<String> al = new ArrayList<String>();
		String sqlQuery = "select  distinct group_id  from user_assignment group by group_id";
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery(sqlQuery);
			while(rs.next()){
				String key = rs.getString("group_id");
				System.out.println("key: "+key);
				al.add(key);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{		
			conn.close();
		}
		if(al.size()>0){
			student.setGroups(al);
		}
		
		return student;
	}
	public static BeanClass UserDataUploadtoDB(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		if(conn==null || conn.isClosed())GetConnection();
		int count = getCount(student.getUsername());
		student.setCount(count);
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		System.out.println("todays date is : "+date);
		System.out.println("No of Submissions: "+count);
		if(count<=50){
			String sqlQuery = "INSERT INTO USER_ASSIGNMENT(USER_ID,IMAGEFILE,CONTENT,LINK,SUBMISSIONCOUNT,SUBMISSIONDATE,NOOFIMAGES,WORDCOUNT,CHARCOUNT) VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sqlQuery);
			ps.setString(1, student.getUsername());
			ps.setString(2, student.getImagefile());
			ps.setString(3, student.getFullContext());
			ps.setString(4, student.getLink());
			ps.setInt(5, count+1);
			ps.setTimestamp(6, date);
			ps.setInt(7, student.getImagesNumber());
			ps.setInt(8, student.getWordCount());
			ps.setInt(9, student.getCharCount());
			ps.executeUpdate();
			student.setSubmission_date(""+date);
			System.out.println("Inserted a record into DB");
			ps.close();
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
			count = rs.getInt("SUBMISSIONCOUNT");
		}

		return count;
	}
	public static BeanClass RetrieveInfo(BeanClass student) throws SQLException {

		if(conn==null || conn.isClosed()) GetConnection();
		String sqlQuery = "SELECT * FROM user_assignment WHERE USER_ID = ?";
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setString(1, student.getUsername());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			student.setFullContext(rs.getString("CONTENT"));
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
		student = GetImageList(student);
		student = GetHTMLBody(student);
		ps.close();
		conn.close();
		return student;
	}
	
	public static BeanTeacher GetStudentsInfo(BeanTeacher teacher) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<BeanClass> al  = new ArrayList<BeanClass>();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("select user_id,assignment_id,group_id,assignment_name,imagefile,NoofImages,content,link, max(submissioncount),submissionDate,wordcount,charcount from user_assignment");
			sqlQuery.append(" where group_id= ? ");
			sqlQuery.append(" group by user_id ");
			BeanClass student;		
			System.out.println("GetStudentsInfo statement: "+sqlQuery.toString());
			PreparedStatement ps = conn.prepareStatement(sqlQuery.toString());
			ps.setString(1, teacher.getGroupid());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				student = new BeanClass();
				student.setUsername(rs.getString("USER_ID"));
				student.setFullContext(rs.getString("CONTENT"));
				student.setImagefile(rs.getString("IMAGEFILE"));
				student.setLink(rs.getString("LINK"));
				student.setAssignment_id(rs.getString("assignment_id"));
				student.setAssignment_name(rs.getString("assignment_name"));
				student.setGroup_id(rs.getString("group_id"));
				student.setSubmission_date(""+rs.getTimestamp("submissionDate"));
				student.setImagesNumber(rs.getInt("NOOFIMAGES"));
				student.setWordCount(rs.getInt("WORDCOUNT"));
				student.setCharCount(rs.getInt("CHARCOUNT"));	
				student  = GetImageList(student);
				student = GetHTMLBody(student);
				al.add(student);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(al.size()>0){
				teacher.setStudentsList(al);
			}
			conn.close();
		}
		
		return teacher;
	}

	public static void GetConnection(){
		System.out.println("-------- MySQL JDBC Connection Testing ------------");
		try {
			GetDBdetails();
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
			System.out.println("Connected to the database");

		} catch (Exception e) {
			System.out.println("NO CONNECTION =(");
		}

	}


	private static void GetDBdetails() {
		// TODO Auto-generated method stub
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("C:/Users/tejj/Desktop/PeerTool/PeerTool/WebContent/WEB-INF/constants.properties");
			if(input==null) System.out.println("nullllllllll values");
			// load a properties file
			prop.load(input);

			// get the property value and print it out
			url = prop.getProperty("url");
			dbName = prop.getProperty("dbName");
			driver = prop.getProperty("driver");
			userName= prop.getProperty("userName");
			password =prop.getProperty("password");
			student = prop.getProperty("password");
			ta= prop.getProperty("ta");
			teacher = prop.getProperty("teacher");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static ArrayList<BeanClass> GetPeerInfo(BeanClass student) throws SQLException {

		if(conn==null) GetConnection();
		String sqlQuery = "SELECT * FROM user_assignment WHERE USER_ID != ?";
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setString(1, student.getUsername());
		ResultSet rs = ps.executeQuery();
		ArrayList<BeanClass> alist = new ArrayList<BeanClass>();
		while(rs.next()){
			BeanClass peer = new BeanClass();
			peer.setUsername(rs.getString("USER_ID"));
			peer.setFullContext(rs.getString("CONTENT"));
			peer.setCount(rs.getInt("SUBMISSIONCOUNT"));
			peer.setImagefile(rs.getString("IMAGEFILE"));
			peer.setLink(rs.getString("LINK"));
			peer.setAssignment_id(rs.getString("assignment_id"));
			peer.setAssignment_name(rs.getString("assignment_name"));
			peer.setGroup_id(rs.getString("group_id"));
			peer.setSubmission_date(""+rs.getTimestamp("submissionDate"));
			peer.setImagesNumber(rs.getInt("NOOFIMAGES"));
			peer.setWordCount(rs.getInt("WORDCOUNT"));
			peer.setCharCount(rs.getInt("CHARCOUNT"));	
			peer  = GetImageList(peer);
			peer = GetHTMLBody(peer);
			alist.add(peer);
		}
		ps.close();
		conn.close();
		return alist;
	}
	
	
	
	private static BeanClass GetImageList(BeanClass student){
		String[] temp = student.getImagefile().split(",");
		ArrayList<String> imglist = new ArrayList<String>();
		for(int i=0;i<temp.length;i++){
			imglist.add(temp[i]);
		}
		student.setImages(imglist);

		return student;
	}
	public static BeanClass GetHTMLBody(BeanClass student) {
		StringBuilder sb = new StringBuilder();
		Document document = Jsoup.parse(student.getFullContext());
		Elements elements = document.body().select("*");
		for (Element element : elements) {
			sb.append( element.ownText());
		}
		student.setContent(sb.toString());
		return student;
	}
	
	

}
