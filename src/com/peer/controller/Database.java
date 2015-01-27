package com.peer.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ImportResource("/WEB-INF/spring-dispatcher-servlet.xml")
public class Database {
	private static Connection conn = null;
	@Value("${admin}")
	private String role;
	@Value("${url}")
	private  String url;
	@Value("${dbName}")
	private  String dbName;
	@Value("${driver}")
	private  String driver;
	@Value("${userName}")
	private  String userName;
	@Value("${password}")
	private  String password;
	static Database db;
	Database(){	}
	public static BeanClass validate(BeanClass student) throws SQLException{
		Database db = new Database();
		if(conn==null)db.GetConnection();
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
			student = RetrieveInfo(student);
		}

		conn.close();
		System.out.println("Disconnected from database");

		return student;
	}
	public static BeanClass UserDataUploadtoDB(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		db = new Database();
		db.GetConnection();
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
		// TODO Auto-generated method stub
		db = new Database();
		if(conn==null) db.GetConnection();
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
		conn.close();
		return student;
	}

	public void GetConnection(){
		System.out.println("-------- MySQL JDBC Connection Testing ------------");
		try {
			System.out.println("DB role: "+role);
			System.out.println("dbName: "+dbName);
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
			System.out.println("Connected to the database");

		} catch (Exception e) {
			System.out.println("NO CONNECTION =(");
		}

	}


	public static ArrayList<BeanClass> GetPeerInfo(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		db = new Database();
		if(conn==null) db.GetConnection();
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
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

}
