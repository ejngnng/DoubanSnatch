/*********************************************************************
 * 
 *Description: this class is for santch info from douban
 *
 *Author:      ningjiang@baicells.com
 *
 *Date:        created by 2017-01-23
 *  
 *********************************************************************/

package com.douban.test;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.douban.db.Info_DB;

public class SnatchMovies {
	
	private static String testURl = "https://movie.douban.com/tag/2015?start=0&type=T";

	private static String baseURL = "https://movie.douban.com/tag/";
	private static String tag_2016 = "2016";
	private static String tag_2015 = "2015";
	private static String tag_2014 = "2014";
	private static String tag_2013 = "2013";
	private static String tag_2012 = "2012";
	private static String tag_2011 = "2011";
	private static String tag_2010 = "2010";
	private static String tag_2009 = "2009";
	private static String tag_2008 = "2008";
	private static String tag_2007 = "2007";
	private static String tag_2006 = "2006";
	private static String tag_2005 = "2005";
	private static String tag_2004 = "2004";
	private static String tag_2003 = "2003";
	private static String tag_2002 = "2002";
	private static String tag_1999 = "1999";
	
	public static String getBaseURL() {
		return baseURL;
	}

	public static void setBaseURL(String baseURL) {
		SnatchMovies.baseURL = baseURL;
	}

	public static String getTag_2016() {
		return tag_2016;
	}

	public static void setTag_2016(String tag_2016) {
		SnatchMovies.tag_2016 = tag_2016;
	}

	public static String getTag_2015() {
		return tag_2015;
	}

	public static void setTag_2015(String tag_2015) {
		SnatchMovies.tag_2015 = tag_2015;
	}
	
	public static String constructURL(String tag, String page){
		
		String url = baseURL + tag + "?" + "start=" + page + "&type=T";
		System.out.println("url: " + url);
		return url;
	}
	
	public static String constructXpath(int i){
		
		String path = ".//*[@id='content']/div/div[1]/div[2]/table[" + String.valueOf(i) + "]/tbody/tr/td[1]/a/img";
		System.out.println("path: " + path);
		return path;
	}
	
	public static String getPageCount(WebDriver driver){
		
		String path = ".//*[@id='content']/div/div[1]/div[3]/a[10]";
		WebElement pageCountElem = driver.findElement(By.xpath(path));
		
		String count = pageCountElem.getText();
		System.out.println("pageCount: " + count);
		return count;
		
	}
	
	
	public static Map<String, String> getInfo(WebElement infoElem){
		
		String newLine = "\n";	
		String result = infoElem.getText();
		String[] split = result.split(newLine);
		
		Map<String, String> splitMap = new HashMap<>();
		
		for (int j = 0; j < split.length; j++) {
			if(split[j].contains(":") ){
			//	System.out.println("++++ " + split[j]);
				if(split[j].split(":")[0].equals("季数")){
					break;
				}
				splitMap.put(split[j].split(":")[0], split[j].split(":")[1]);
			}
		}
		
		return splitMap;
	}
	
	public static void setMainInfo(PlayBean playBean, WebElement infoElem){
		
		Map<String, String> infoMap = getInfo(infoElem);
		
		Iterator<String> it = infoMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			//1
			if(key.equals(DoubanInfo.DoubanDirector)){
				playBean.setDirector(infoMap.get(key));
			}
			//2
			if(key.equals(DoubanInfo.DoubanEditor)){
				playBean.setEditor(infoMap.get(key));;
			}
			//3
			if(key.equals(DoubanInfo.DoubanActor)){
				playBean.setActor(infoMap.get(key));
			}
			//4
			if(key.equals(DoubanInfo.DoubanClassify)){
				playBean.setClassify(infoMap.get(key));
			}
			//5
			if(key.equals(DoubanInfo.DoubanArea)){
				playBean.setArea(infoMap.get(key));
			}
			//6
			if(key.equals(DoubanInfo.DoubanLanguage)){
				playBean.setLanguage(infoMap.get(key));
			}
			//7
			if(key.equals(DoubanInfo.DoubanReleaseTime)){
				playBean.setReleaseTime(infoMap.get(key));
			}
			//8
			if(key.equals(DoubanInfo.DoubanDuration)){
				playBean.setDuration(infoMap.get(key));
			}
			//9
			if(key.equals(DoubanInfo.DoubanNickName)){
				playBean.setNickname(infoMap.get(key));
			}
			//10
			if(key.equals(DoubanInfo.DoubanIMDBLink)){
				playBean.setIMDBlink(infoMap.get(key));
			}
			
		}
		//return playBean;
	}
	
	public static PlayBean getOne(WebDriver driver, int i){
		
		String imgPath = constructXpath(i);
		try{
		//	driver.navigate().refresh();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			WebElement img = driver.findElement(By.xpath(imgPath));
			img.click();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("find the img element failed!");
			System.out.println("current url is: " + driver.getCurrentUrl());
			return null;
		}
		
		try{
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Time out!!");
			return null;
		}
		
		PlayBean infoBean = new PlayBean();
		
		//find video name
		try{
			String videoName = driver.findElement(By.xpath(".//*[@id='content']/h1/span[1]")).getText();
			System.out.println(videoName);
			infoBean.setVideoName(videoName);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("get video name field!!!");
			return null;
		}
		//find main info
		try{
			WebElement infoElem = driver.findElement(By.xpath(".//*[@id='info']"));
			SnatchMovies.setMainInfo(infoBean, infoElem);
		}catch (Exception e) {
			// TODO: handle exception
			String currentUrl = driver.getCurrentUrl();
			driver.get(currentUrl);
			System.out.println("get main info failed");
			return null;
		}
	
		
		//find introduction
		try{
			WebElement introductionElem = driver.findElement(By.xpath(".//*[@id='link-report']/span[1]"));	
			String introduction = introductionElem.getText();
			infoBean.setIntroduction(introduction);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("get introduction failed");
			infoBean.setIntroduction(null);
			//return null;
		}
		//find score
		try{
			WebElement scoreElem = driver.findElement(By.xpath(".//*[@id='interest_sectl']/div[1]/div[2]/strong"));
			String score = scoreElem.getText();
			infoBean.setScore(score);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("get video score failed!!!");
			infoBean.setScore(null);
		//	return null;
		}
/*		
		Connection conn = Info_DB.connectDB();
		Info_DB.insertDB(conn,infoBean);
		Info_DB.closeDB(conn);
*/		
		return infoBean;
	}
	
	public static void getOnePage(WebDriver driver,String url,int table){

		for(int i=1;i<20;i++){
			
			Snatch.openDriver(url);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("delay.....");
				continue;
			}
		//	driver.navigate().refresh();
			PlayBean playBean = getOne(Snatch.getDriver(), i);
			if(playBean == null){
				System.out.println("get this video field!!!");
				Snatch.restartDriver();
				continue;
				
			}else{
				System.out.println("get this video successed!!!");
				Connection conn = Info_DB.connectDB();
				Info_DB.insertDB(conn, playBean, table);
				Info_DB.closeDB(conn);
			}
	//		Snatch.openDriver(fuckURL.getFuckURL());
	//		driver.navigate().refresh();
	//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
	}
	
	public static void main(String[] args){
		
		String driverLocation = "C:/ninja/software/geckodriver-v0.13.0-win64/geckodriver.exe";
		
		Snatch.init(driverLocation);
		
/*		//get 2016 year
		String testurl_16 = SnatchMovies.constructURL(tag_2016, "0");
		
		Snatch.openDriver(testurl_16);
	
		String pagetCount_16 = SnatchMovies.getPageCount(Snatch.getDriver());
		
		for(long i=264;i< Integer.valueOf(pagetCount_16).intValue() - 1;i++ ){
			
			Snatch.getDriver().navigate().refresh();
			try{
				Snatch.openDriver(fuckURL.getFuckURL());
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("redirect url failed");
				
			}finally {
				Snatch.restartDriver();
			}
			
			String url_16 = SnatchMovies.constructURL(tag_2016, String.valueOf(i*20));
			Snatch.openDriver(url_16);
			
			getOnePage(Snatch.getDriver(), url_16,1);

		}
		
		
		//get 2015 year
		String testurl_15 = SnatchMovies.constructURL(tag_2015, "0");
		
		Snatch.openDriver(testurl_15);
	
		String pagetCount_15 = SnatchMovies.getPageCount(Snatch.getDriver());
		
		for(long i=0;i< Integer.valueOf(pagetCount_15).intValue() - 1;i++ ){
			
			Snatch.getDriver().navigate().refresh();
			try{
				Snatch.openDriver(fuckURL.getFuckURL());
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("redirect url failed");
				
			}finally {
				Snatch.restartDriver();
			}
			
			String url_15 = SnatchMovies.constructURL(tag_2015, String.valueOf(i*20));
			Snatch.openDriver(url_15);
			
			getOnePage(Snatch.getDriver(), url_15,2);

		}
		
		//get 2014 year
		String testurl_14 = SnatchMovies.constructURL(tag_2014, "0");
		
		Snatch.openDriver(testurl_14);
	
		String pagetCount_14 = SnatchMovies.getPageCount(Snatch.getDriver());
		
		for(long i=0;i< Integer.valueOf(pagetCount_14).intValue() - 1;i++ ){
			
			Snatch.getDriver().navigate().refresh();
			try{
				Snatch.openDriver(fuckURL.getFuckURL());
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("redirect url failed");
				
			}finally {
				Snatch.restartDriver();
			}
			
			String url_14 = SnatchMovies.constructURL(tag_2014, String.valueOf(i*20));
			Snatch.openDriver(url_14);
			
			getOnePage(Snatch.getDriver(), url_14,3);

		}
		
		//get 2013 year
		String testurl_13 = SnatchMovies.constructURL(tag_2013, "0");
		
		Snatch.openDriver(testurl_13);
	
		String pagetCount_13 = SnatchMovies.getPageCount(Snatch.getDriver());
		
		for(long i=0;i< Integer.valueOf(pagetCount_13).intValue() - 1;i++ ){
			
			Snatch.getDriver().navigate().refresh();
			try{
				Snatch.openDriver(fuckURL.getFuckURL());
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("redirect url failed");
				
			}finally {
				Snatch.restartDriver();
			}
			
			String url_13 = SnatchMovies.constructURL(tag_2013, String.valueOf(i*20));
			Snatch.openDriver(url_13);
			
			getOnePage(Snatch.getDriver(), url_13,4);

		}
		
		
		//get 2012 year
		String testurl_12 = SnatchMovies.constructURL(tag_2012, "0");
		
		Snatch.openDriver(testurl_12);
	
		String pagetCount_12 = SnatchMovies.getPageCount(Snatch.getDriver());
		
		for(long i=0;i< Integer.valueOf(pagetCount_12).intValue() - 1;i++ ){
			
			Snatch.getDriver().navigate().refresh();
			try{
				Snatch.openDriver(fuckURL.getFuckURL());
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("redirect url failed");
				
			}finally {
				Snatch.restartDriver();
			}
			
			String url_12 = SnatchMovies.constructURL(tag_2012, String.valueOf(i*20));
			Snatch.openDriver(url_12);
			
			getOnePage(Snatch.getDriver(), url_12,5);

		}
		
		//get 2011 year
		String testurl_11 = SnatchMovies.constructURL(tag_2011, "0");
		
		Snatch.openDriver(testurl_11);
	
		String pagetCount_11 = SnatchMovies.getPageCount(Snatch.getDriver());
		
		for(long i=0;i< Integer.valueOf(pagetCount_11).intValue() - 1;i++ ){
			
			Snatch.getDriver().navigate().refresh();
			try{
				Snatch.openDriver(fuckURL.getFuckURL());
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("redirect url failed");
				
			}finally {
				Snatch.restartDriver();
			}
			
			String url_11 = SnatchMovies.constructURL(tag_2011, String.valueOf(i*20));
			Snatch.openDriver(url_11);
			
			getOnePage(Snatch.getDriver(), url_11,6);

		}*/
		//get from 1990 to 2010
		for(int i=2004;i<2011;i++){
			//get 2011 year
			String testurl = SnatchMovies.constructURL(String.valueOf(i), "0");
			
			Snatch.openDriver(testurl);
		
			String pagetCount = SnatchMovies.getPageCount(Snatch.getDriver());
			
			for(int j=0;j< Integer.valueOf(pagetCount).intValue() - 1;j++ ){
				
				Snatch.getDriver().navigate().refresh();
			/*	try{
					Snatch.openDriver(fuckURL.getFuckURL());
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println("redirect url failed");
					
				}finally {
					Snatch.restartDriver();
				}
				*/
				String url = SnatchMovies.constructURL(String.valueOf(i), String.valueOf(j*20));
				Snatch.openDriver(url);
				
				getOnePage(Snatch.getDriver(), url,1);

			}
		}
		
		Snatch.closeDriver();
		
	}
}
