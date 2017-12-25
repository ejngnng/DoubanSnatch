package com.douban.test;

import java.sql.Connection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.douban.db.Info_DB;

public class SnatchImg {
	
	//video name xpath
	public static String constructXpath(int i){
		String path = ".//*[@id='content']/div/div[1]/div[2]/table[" + String.valueOf(i) + "]/tbody/tr/td[2]/div/a";
		System.out.println("name path: " + path);
		return path;
	}
	
	//local page location
	public static String contructLocalURL(String tag,String page){
		
	//	String baseURL = "file:///C:/ninja/Project/video/material/pages/";
//		String baseURL = "file:///C:/ninja/Project/video/material/2013/";
//		String url = baseURL + tag + "%253F" + "start=" + page;
		
//		String baseURL = "file:///C:/ninja/Project/video/material/jilupian/";
//		String url = baseURL + tag + "%253F" + "start=" + page;	

		String baseURL = "file:///C:/ninja/Project/video/material/dianshiju/";
		String url = baseURL + tag + "%253F" + "start=" + page;
		
		//file:///C:/ninja/Project/video/material/pages/1993%253Fstart=160
		System.out.println("localURL: " + url);
		
		return url;
	}
	
	//get one img and name from one page
	public static ImgBean getOne(WebDriver driver, int i){
		
		ImgBean imgBean = new ImgBean();
		
		String imgPath = SnatchMovies.constructXpath(i);
		String namePath = SnatchImg.constructXpath(i);
		
		//find img
		try {
			WebElement img = driver.findElement(By.xpath(imgPath));
			String imgLink = img.getAttribute("src");
			imgBean.setOuterUrl(imgLink);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("get img link failed!!!");
			return null;
		}
		
		
		//find name
		try {
			WebElement name = driver.findElement(By.xpath(namePath));
			String imgName = name.getText();
			imgBean.setVideoName(imgName);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("get imgName failed!!!");
			return null;
		}
		
		return imgBean;
	}

	//get one page
	public static void getOnePage(WebDriver driver,String url){
		
		Snatch.openDriver(url);
		
		for(int i=1;i<20;i++){
			

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("delay.....");
				continue;
			}
			ImgBean imgBean = getOne(Snatch.getDriver(), i);
			if(imgBean == null){
				System.out.println("get this img field!!!");
				Snatch.restartDriver();
				continue;
				
			}else{
				System.out.println("get this img successed!!!");
				Connection conn = Info_DB.connectDB();
				Info_DB.insertImg(conn, imgBean);
				Info_DB.closeDB(conn);
			}
		}
	}
	
	public static void main(String[] args) {
		
		String driverLocation = "C:/ninja/software/geckodriver-v0.13.0-win64/geckodriver.exe";
		
			
		Snatch.init(driverLocation);
		
/*		for(int i=2014;i<2016;i++){
			//get 2011 year
			String testurl = contructLocalURL(String.valueOf(i), "0");
			
			Snatch.openDriver(testurl);
		
			String pagetCount = SnatchMovies.getPageCount(Snatch.getDriver());
			
			for(int j=0;j< Integer.valueOf(pagetCount).intValue() - 1;j++ ){
				
				Snatch.getDriver().navigate().refresh();

				String url = contructLocalURL(String.valueOf(i), String.valueOf(j*20));
				
				Snatch.openDriver(url);
				System.out.println("url: " + url);
				getOnePage(Snatch.getDriver(), url);

			}
		}*/
		
		
		String testurl = contructLocalURL("电视剧", "0");
		
		Snatch.openDriver(testurl);
	
		String pagetCount = SnatchMovies.getPageCount(Snatch.getDriver());
		
		for(int j=0;j< Integer.valueOf(pagetCount).intValue() - 1;j++ ){
			
			Snatch.getDriver().navigate().refresh();

			String url = contructLocalURL("电视剧", String.valueOf(j*20));
			
			Snatch.openDriver(url);
			System.out.println("url: " + url);
			getOnePage(Snatch.getDriver(), url);

		}
		
		
		Snatch.closeDriver();
		
		System.out.println("=============end of snatch===============");

	}

}
