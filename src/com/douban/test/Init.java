/*********************************************************************
 * 
 *Description: this class is the main progress to get video info
 *
 *Author:      ningjiang@baicells.com
 *
 *Date:        created by 2017-01-23
 *  
 *********************************************************************/
package com.douban.test;

import java.io.File;
import java.util.ArrayList;


public class Init {
	

/*	public static void main(String[] args){
		
		System.setProperty("webdriver.firefox.marionette", "C:/ninja/software/geckodriver-v0.13.0-win64/geckodriver.exe");
		
		System.out.println("create firfox driver obj...................\r\n");

		WebDriver driver = new FirefoxDriver();
		
		String doubanURL = "https://movie.douban.com";
		
		String newLine = "\n";
		
		ArrayList<PlayBean> list = new ArrayList<>(); 
		
		
		ArrayList<String> videoName = JsonUtil.json2List(JsonUtil.file2String(new File("videoname.json")));
		
		videoName.add("赌神");
		videoName.add("无间道");
		videoName.add("澳门风云");
		videoName.add("搏击俱乐部");
		videoName.add("黑客帝国");
		videoName.add("闪灵");
		videoName.add("这个杀手不太冷");
		
		System.out.println("start to open douban URL...................\r\n");
		driver.get(doubanURL);
		
		System.out.println("start to find element ......................\r\n");
		
		
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
			
			infoBean.setDirector(split[0].split(":")[1]);
			infoBean.setEditor(split[1].split(":")[1]);
			infoBean.setActor(split[2].split(":")[1]);
			infoBean.setClassify(split[3].split(":")[1]);
			
			String area = split[4].split(":")[0];
			System.out.println("area: " + split[4].split(":")[0]);
			String name = "制片国家/地区";
			if (area.equals(name)) {
				System.out.println("equale");
				infoBean.setArea(split[4].split(":")[1]);
				infoBean.setLanguage(split[5].split(":")[1]);
				infoBean.setReleaseTime(split[6].split(":")[1]);
				infoBean.setDuration(split[7].split(":")[1]);
				infoBean.setNickname(split[8].split(":")[1]);
				infoBean.setIMDBlink(split[9].split(":")[1]);
			}else{
				infoBean.setArea(split[5].split(":")[1]);
				infoBean.setLanguage(split[6].split(":")[1]);
				infoBean.setReleaseTime(split[7].split(":")[1]);
				infoBean.setDuration(split[8].split(":")[1]);
				infoBean.setNickname(split[9].split(":")[1]);
				infoBean.setIMDBlink(split[10].split(":")[1]);
			}

			
			
			//find introduction
			WebElement introductionElem = driver.findElement(By.xpath(".//*[@id='link-report']/span[1]"));
			
			String introduction = introductionElem.getText();
			
		//	System.out.println("简介: " + introduction);
			
			infoBean.setIntroduction(introduction);
			
			System.out.println("\r\n=============================================\r\n");
			
			list.add(infoBean);
			
		}
		
		
		System.out.println(list.size());
		JsonUtil.write(list);
		
		driver.close();
		
		
	}*/
	
	public static void main(String[] args){
		String driverLocation = "C:/ninja/software/geckodriver-v0.13.0-win64/geckodriver.exe";
		
		
		Snatch.init(driverLocation);
		
		ArrayList<PlayBean> list = new ArrayList<>();
		ArrayList<String> videoName = JsonUtil.json2List(JsonUtil.file2String(new File("videoname.json")));
		
		String URL = "https://movie.douban.com";
		Snatch.openDriver(URL);
		
		list = Snatch.getInfo(videoName);
		JsonUtil.write(list);
		
		Snatch.closeDriver();
		
	}
}
