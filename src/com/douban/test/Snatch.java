/*********************************************************************
 * 
 *Description: this class is for selenium firfox driver
 *
 *Author:      ningjiang@baicells.com
 *
 *Date:        created by 2017-01-23
 *  
 *********************************************************************/
package com.douban.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Snatch {
	
	private static WebDriver driver = null;
	
	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		Snatch.driver = driver;
	}

	private static String doubanURL = "https://movie.douban.com";
	
	private static String newLine = "\n";
	
	public static void init(String driverLocation){
		
		System.setProperty("webdriver.firefox.marionette", driverLocation);
		driver = new FirefoxDriver();
		
	}
	
	public static void restartDriver(){
		driver.close();
		System.out.println("closed firfox and sleep");
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("start new firfox");
		driver = new FirefoxDriver();
	}
	
	public static void openDriver(String URL){
	//	driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get(URL);

	}
	
	public static void closeDriver(){
		driver.close();
	}
	
	public static ArrayList<PlayBean> getInfo(ArrayList<String> videoName){

		
		ArrayList<PlayBean> list = new ArrayList<>();
		
		for(int i=0;i< videoName.size();i++){
			
			WebElement Search_txt = driver.findElement(By.name("search_text"));
			
			WebElement Search_key = driver.findElement(By.className("inp-btn"));
			
			Search_txt.sendKeys(videoName.get(i));
			
			Search_key.click();
			
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			// find top one from the result of research
			WebElement topPic = driver.findElement(By.xpath(".//*[@id='content']/div/div[1]/div[2]/table[1]/tbody/tr/td[1]/a/img"));
			topPic.click();
			
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			WebElement areaElem = driver.findElement(By.xpath(".//*[@id='info']"));

			System.out.println("\r\n=============================================\r\n");
			System.out.println("电影: " + videoName.get(i));
			String result = areaElem.getText();
			System.out.println(result);
			
			PlayBean infoBean = new PlayBean();
			
			String[] split = result.split(newLine);
			infoBean.setVideoName(videoName.get(i));
			
			
			Map<String, String> splitMap = new HashMap<>();
			
//			for(int i1=0;i1<split.length;i1++){
//				System.out.println("********* split " + i1 + "  :" + split[i1]);
//			}
			
			for (int j = 0; j < split.length; j++) {
				if(split[j].contains(":") ){
//					System.out.println("++++ " + split[j]);
					if(split[j].split(":")[0].equals("季数")){
						break;
					}
//					if(!(split[j].split(":")[1].isEmpty())){
						splitMap.put(split[j].split(":")[0], split[j].split(":")[1]);
//					}
				}
			}

			Iterator<String> it = splitMap.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				//1
				if(key.equals(DoubanInfo.DoubanDirector)){
					infoBean.setDirector(splitMap.get(key));
				}
				//2
				if(key.equals(DoubanInfo.DoubanEditor)){
					infoBean.setEditor(splitMap.get(key));;
				}
				//3
				if(key.equals(DoubanInfo.DoubanActor)){
					infoBean.setActor(splitMap.get(key));
				}
				//4
				if(key.equals(DoubanInfo.DoubanClassify)){
					infoBean.setClassify(splitMap.get(key));
				}
				//5
				if(key.equals(DoubanInfo.DoubanArea)){
					infoBean.setArea(splitMap.get(key));
				}
				//6
				if(key.equals(DoubanInfo.DoubanLanguage)){
					infoBean.setLanguage(splitMap.get(key));
				}
				//7
				if(key.equals(DoubanInfo.DoubanReleaseTime)){
					infoBean.setReleaseTime(splitMap.get(key));
				}
				//8
				if(key.equals(DoubanInfo.DoubanDuration)){
					infoBean.setDuration(splitMap.get(key));
				}
				//9
				if(key.equals(DoubanInfo.DoubanNickName)){
					infoBean.setNickname(splitMap.get(key));
				}
				//10
				if(key.equals(DoubanInfo.DoubanIMDBLink)){
					infoBean.setIMDBlink(splitMap.get(key));
				}
				
			}
			
			//find introduction
			WebElement introductionElem = driver.findElement(By.xpath(".//*[@id='link-report']/span[1]"));
			
			String introduction = introductionElem.getText();
			
			infoBean.setIntroduction(introduction);
			
			//find score
			WebElement scoreElem = driver.findElement(By.xpath(".//*[@id='interest_sectl']/div[1]/div[2]/strong"));
			
			String score = scoreElem.getText();
			
			infoBean.setScore(score);
			
			System.out.println("\r\n=============================================\r\n");
			
			list.add(infoBean);
			
		}
		
		return list;
	}
	
}
