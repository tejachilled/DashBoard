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
	public static String RegisterUser(UserInfoBean userInfo) throws SQLException {
		// TODO Auto-generated method stub
		if(conn==null || conn.isClosed())GetConnection();
		String sqlQuery = "INSERT INTO user_table(user_id,uname,passwrd,email) values(?,?,?,?)";
		int count =0;
		if(CheckEmail(userInfo)){
			return "email";
		}else if(CheckUId(userInfo)){
			return "userid";
		} else{
			try {
				PreparedStatement ps = conn.prepareStatement(sqlQuery);
				ps.setString(1, userInfo.getUser_id());
				ps.setString(2, userInfo.getUname());
				ps.setString(3, userInfo.getPassword());
				ps.setString(4, userInfo.getEmailId());
				count = ps.executeUpdate();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				conn.close();
				System.out.println("New user registration successful!");
			}
		}
		return "success";
	}
	private static boolean CheckUId(UserInfoBean userInfo) throws SQLException {
		// TODO Auto-generated method stub
		if(conn==null || conn.isClosed())GetConnection();
		int count = 0;
		String sqlQuery = "select count(*) from user_table where user_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sqlQuery);
			ps.setString(1, userInfo.getUser_id());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("user id count: "+count);
		if(count ==0) return false;
		return true;
	}
	private static boolean CheckEmail(UserInfoBean userInfo) throws SQLException {
		// TODO Auto-generated method stub
		if(conn==null || conn.isClosed())GetConnection();
		int count = 0;
		String sqlQuery = "select count(*) cou from user_table where email = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sqlQuery);
			ps.setString(1, userInfo.getEmailId());
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt("cou");				
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("email id count: "+count);
		if(count ==0) return false;
		return true;
	}
	private static BeanClass TeacherInfo(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		if(conn==null || conn.isClosed())GetConnection();
		ArrayList<String> al = new ArrayList<String>();
		String sqlQuery = "select  distinct group_id  from user_table";
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
			String sqlQuery = "INSERT INTO USER_ASSIGNMENT(USER_ID,IMAGEFILE,CONTENT,LINK,SUBMISSIONCOUNT,SUBMISSIONDATE,NOOFIMAGES,WORDCOUNT,CHARCOUNT,group_id) VALUES(?,?,?,?,?,?,?,?,?,?)";
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
			ps.setString(10, student.getGroup_id());
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
		if(student.getImagefile()!=null){
			student = GetImageList(student);
			student = GetHTMLBody(student);
			student = GetMarks(student);
		}
		ps.close();
		conn.close();
		return student;
	}

	public static BeanTeacher GetStudentsInfo(BeanTeacher teacher) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, BeanClass> map = new HashMap<String, BeanClass>();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("select user_id,assignment_id,group_id,assignment_name,imagefile,NoofImages,content,link, max(submissioncount),submissionDate,wordcount,charcount from user_assignment");
			sqlQuery.append(" where group_id= ? ");
			sqlQuery.append(" group by user_id ");
			BeanClass student;		
			System.out.println("GetStudentsInfo statement: "+sqlQuery.toString());
			System.out.println("group id: "+teacher.getGroupid());
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
				student = GetMarks(student);
				map.put(student.getUsername(), student);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(map.size()>0){
				teacher.setStudentList(map);
			}
			conn.close();
		}

		return teacher;
	}

	public static BeanClass GetMarks(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		ArrayList<BeanMarks> list = new ArrayList<BeanMarks>();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			sb.append("select count,teacher_evaluation,analysis,design,vc,consistency,aesthetic,orginality,tot,user_id,peer_id from peer_table");
			sb.append(" where user_id = ?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, student.getUsername());
			ResultSet rs = ps.executeQuery();			
			while(rs.next()){
				BeanMarks marks = new BeanMarks();
				marks.setCount(rs.getInt("count"));				
				marks.setTeacher_evaluation(rs.getBoolean("teacher_evaluation"));
				System.out.println("student Id: "+student.getUsername());
				System.out.println("count: "+marks.getCount());
				System.out.println("teacher evaluation: "+marks.isTeacher_evaluation());
				marks.setAnalysis(rs.getInt("analysis"));
				marks.setDesign(rs.getInt("design"));
				marks.setVc(rs.getInt("vc"));
				marks.setConsistency(rs.getInt("consistency"));
				marks.setAesthetic(rs.getInt("aesthetic"));
				marks.setOrginality(rs.getInt("orginality"));
				marks.setTotal(rs.getInt("tot"));
				marks.setUserId(rs.getString("user_id"));
				marks.setPeerId(rs.getString("peer_id"));
				list.add(marks);
			}

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(list.size()>0) student.setMarks(list);
			ps.close();
		}
		return student;
	}

	public static void UploadMarks(String peerId, String reviewerId,
			BeanMarks peerMarks,String mode) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			if(CheckPeerTable(peerId,reviewerId)){
				sb.append("update peer_table  set analysis=?,vc=?,consistency=?,aesthetic=?,orginality=?,tot=?,design=? where user_id = ? and peer_id = ?");
				ps = conn.prepareStatement(sb.toString());
				ps.setInt(1, peerMarks.getAnalysis());
				ps.setInt(2, peerMarks.getVc());
				ps.setInt(3, peerMarks.getConsistency());
				ps.setInt(4, peerMarks.getAesthetic());
				ps.setInt(5, peerMarks.getOrginality());
				ps.setInt(6, peerMarks.getTotal());
				ps.setInt(7, peerMarks.getDesign());
				ps.setString(8, reviewerId);
				ps.setString(9, peerId);
				ps.executeUpdate();
				System.out.println("Marks updated to DB");
			}else{
				int count = getPeersCount(reviewerId);
				sb.append("insert into peer_table(user_id,peer_id,analysis,vc,consistency,aesthetic,orginality,tot,count,design,teacher_evaluation)");
				sb.append("values(?, ?, ?,?,?,?,?,?,?,?,?)");
				ps = conn.prepareStatement(sb.toString());			
				ps.setString(1, reviewerId);			
				ps.setString(2, peerId);
				ps.setInt(3, peerMarks.getAnalysis());
				ps.setInt(4, peerMarks.getVc());
				ps.setInt(5, peerMarks.getConsistency());
				ps.setInt(6, peerMarks.getAesthetic());
				ps.setInt(7, peerMarks.getOrginality());
				int tot = peerMarks.getAnalysis()+peerMarks.getVc()+peerMarks.getAesthetic()+peerMarks.getConsistency()+peerMarks.getDesign()+peerMarks.getOrginality();
				ps.setInt(8, tot);
				ps.setInt(9, count+1); // needs to be changed
				ps.setInt(10, peerMarks.getDesign());
				if(mode.equalsIgnoreCase("student"))
					ps.setBoolean(11, false);
				else
					ps.setBoolean(11, true);
				ps.execute();
				System.out.println("Marks Inserted to DB");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ps.close();
			conn.close();
		}

	}

	private static boolean CheckPeerTable(String peerId, String reviewerId) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			sb.append("select * from peer_table where user_id = ? and peer_id = ?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, reviewerId);			
			ps.setString(2, peerId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ps.close();
		}

		return false;
	}
	private static int getPeersCount(String peerId) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null; int count =0;
		StringBuffer sb = new StringBuffer();
		sb.append("select max(count) from peer_table  where user_id= ? ");
		ps = conn.prepareStatement(sb.toString());
		ps.setString(1, peerId);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			count = rs.getInt(1);
		}
		return count;
	}
	public static HashMap<String, BeanClass> GetPeerInfo(BeanClass student) throws SQLException {
		HashMap<String, BeanClass> map = new HashMap<String, BeanClass>();
		PreparedStatement ps = null;
		int count =0;
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			student = getSubmissionDate(student);
			StringBuffer sqlQuery = new StringBuffer();			
			sqlQuery.append("select user_id,assignment_id,group_id,assignment_name,imagefile,NoofImages,content,link,");
			sqlQuery.append("submissioncount,submissionDate,wordcount,charcount from user_assignment where  (user_id,submissionDate) in");
			sqlQuery.append("(select user_id,max(submissionDate) from user_assignment  group by user_id) and  submissionDate > ?");
			sqlQuery.append("order by submissionDate asc limit 2");
			ps = conn.prepareStatement(sqlQuery.toString());
			System.out.println("Get peer info submission date: "+student.getSubmission_date());
			ps.setString(1, student.getSubmission_date());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				BeanClass peer = new BeanClass();
				peer.setUsername(rs.getString("USER_ID"));
				peer.setFullContext(rs.getString("CONTENT"));
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
				peer = GetMarks(peer);
				//System.out.println(peer.getUsername());
				map.put(peer.getUsername(), peer);
				count++;
			}
			if(count <2){
				sqlQuery = new StringBuffer();			
				sqlQuery.append("select user_id,assignment_id,group_id,assignment_name,imagefile,NoofImages,content,link,");
				sqlQuery.append("submissioncount,submissionDate,wordcount,charcount from user_assignment where  (user_id,submissionDate) in");
				sqlQuery.append("(select user_id,max(submissionDate) from user_assignment  group by user_id) ");
				sqlQuery.append("order by submissionDate ");
				ps = conn.prepareStatement(sqlQuery.toString());
				rs = ps.executeQuery();
				while(rs.next() && count <2){
					BeanClass peer = new BeanClass();
					peer.setUsername(rs.getString("USER_ID"));
					peer.setFullContext(rs.getString("CONTENT"));
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
					peer = GetMarks(peer);
					map.put(peer.getUsername(), peer);
					count++;

				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ps.close();
			conn.close();
		}
		return map;
	}


	private static BeanClass getSubmissionDate(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select max(submissionDate) from user_assignment  where user_id = ?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, student.getUsername());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				System.out.println(" sub date: "+rs.getTimestamp(1));
				student.setSubmission_date("" +rs.getTimestamp(1));
			}
		}finally{
			ps.close();
		}
		return student;
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




}
