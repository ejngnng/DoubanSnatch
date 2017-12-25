/*********************************************************************
 * 
 *Description: this class is for mysql driver
 *
 *Author:      ningjiang@baicells.com
 *
 *Date:        created by 2017-01-23
 *  
 *********************************************************************/

package com.douban.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douban.test.ImgBean;
import com.douban.test.PlayBean;

public class Info_DB {
	
    private static String driver = "com.mysql.jdbc.Driver";

    private static String url = "jdbc:mysql://127.0.0.1:3306/info_db";

    private static String user = "root";
    
    private static String password = "root";
    
    public static Connection connectDB(){
             try {
                 Class.forName(driver);
                 //1.getConnection()方法，连接MySQL数据库！！
              Connection  conn = DriverManager.getConnection(url,user,password);
                 if(!conn.isClosed()){
                     System.out.println("Successed connecting to the Database!");
                 }else{
                	 System.out.println("connect failed");
                 }
                 return conn;
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
             return null;
    }
    
    public static void closeDB(Connection connection){
    	try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void insertDB(Connection conn, PlayBean playBean, int table){
    	String sql = null;
    	
    	switch (table) {
			case 1:
				sql = "insert into videoinfo16(videoName,score,director,editor,actor,classify,area,language,releaseTime,duration,nickNane,IMDBlink,introduction) values(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				break;
			case 2:
				sql = "insert into videoinfo15(videoName,score,director,editor,actor,classify,area,language,releaseTime,duration,nickNane,IMDBlink,introduction) values(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				break;
			case 3:
				sql = "insert into videoinfo14(videoName,score,director,editor,actor,classify,area,language,releaseTime,duration,nickNane,IMDBlink,introduction) values(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				break;
			case 4:
				sql = "insert into videoinfo13(videoName,score,director,editor,actor,classify,area,language,releaseTime,duration,nickNane,IMDBlink,introduction) values(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				break;
			case 5:
				sql = "insert into videoinfo12(videoName,score,director,editor,actor,classify,area,language,releaseTime,duration,nickNane,IMDBlink,introduction) values(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				break;
			case 6:
				sql = "insert into videoinfo11(videoName,score,director,editor,actor,classify,area,language,releaseTime,duration,nickNane,IMDBlink,introduction) values(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				break;
			
			default:
				break;
		}
    	
    	String videoName = playBean.getVideoName();
    	String score = playBean.getScore();
    	String director = playBean.getDirector();
    	String editor = playBean.getEditor();
    	String actor = playBean.getActor();
    	String classify = playBean.getClassify();
    	String area = playBean.getArea();
    	String language = playBean.getLanguage();
    	String releateTime = playBean.getReleaseTime();
    	String duration = playBean.getDuration();
    	String nickName = playBean.getNickname();
    	String IMDBLink = playBean.getIMDBlink();
    	String introduction = playBean.getIntroduction();

    	try {
        	PreparedStatement statement = conn.prepareStatement(sql);
        	
        	statement.setString(1, videoName);
        	statement.setString(2, score);
        	statement.setString(3, director);
        	statement.setString(4, editor);
        	statement.setString(5, actor);
        	statement.setString(6, classify);
        	statement.setString(7, area);
        	statement.setString(8, language);
        	statement.setString(9, releateTime);
        	statement.setString(10, duration);
        	statement.setString(11, nickName);
        	statement.setString(12, IMDBLink);
        	statement.setString(13, introduction);
        	
        	int i = statement.executeUpdate();
        	if(i == 1){
        		System.out.println("insert data Successed!!!");
        	}else{
        		System.out.println("insert data Failed!!!");
        	}
        	statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


    }
    
    public static void insertImg(Connection conn, ImgBean imgBean){
    	String sql = null;

		sql = "insert into imginfo1(videoName,imgLink) values(?,?) ";
    	
		String videoName = imgBean.getVideoName();
		String outerUrl = imgBean.getOuterUrl();

    	try {
        	PreparedStatement statement = conn.prepareStatement(sql);
        	
        	statement.setString(1, videoName);
        	statement.setString(2, outerUrl);
      
        	int i = statement.executeUpdate();
        	if(i == 1){
        		System.out.println("insert data Successed!!!");
        	}else{
        		System.out.println("insert data Failed!!!");
        	}
        	statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


    }
    
    public static void main(String[] args){
    	
    	Connection conn = Info_DB.connectDB();
    	PlayBean testBean = new PlayBean();
    	String test = "testing";
    	testBean.setVideoName(test);
    	testBean.setScore(test);
    	testBean.setDirector(test);
    	testBean.setEditor(test);
    	testBean.setActor(test);
    	testBean.setClassify(test);
    	testBean.setArea(test);
    	testBean.setLanguage(test);
    	testBean.setReleaseTime(test);
    	testBean.setDuration(test);
    	testBean.setNickname(test);
    	testBean.setIMDBlink(test);
    	testBean.setIntroduction(test);
    	
    //	Info_DB.insertDB(conn,testBean);
/*    	String name = "test";
    	String password = "12345";
    	String sql = "insert into user(name,password) values(?,?)";
    	try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			statement.setString(2, password);
			Boolean rs = statement.execute();
			if(rs){
				System.out.println("Successed");
			}else{
				System.out.println("Failed");
			}
			int i = statement.executeUpdate();
			System.out.println("result: " + i);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
    	
    	Info_DB.closeDB(conn);
    }
}
