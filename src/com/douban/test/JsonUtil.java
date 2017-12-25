/*********************************************************************
 * 
 *Description: this class is for Json tool used jackjson lib
 *
 *Author:      ningjiang@baicells.com
 *
 *Date:        created by 2017-01-23
 *  
 *********************************************************************/

package com.douban.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtil {
	
	private static ObjectMapper objectMapper = new ObjectMapper();  
     
    public static <T> String bean2Json(T bean) {  
        try {  
            return objectMapper.writeValueAsString(bean);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    public static String map2Json(Map map) {  
        try {  
            return objectMapper.writeValueAsString(map);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    public static String list2Json(List list) {  
        try {  
            return objectMapper.writeValueAsString(list);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    public static <T> T json2Bean(String json, Class<T> beanClass) {  
        try {  
            return objectMapper.readValue(json, beanClass);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
      
    public static Map json2Map(String json) {  
        try {  
              
            return (Map)objectMapper.readValue(json, Map.class);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
      
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
    	
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);    

    }   
    
    public static void write(List<PlayBean> list){
    	
    	JsonFactory jsonFactory = new JsonFactory();
    	
    	try {
    		JsonGenerator jsonGenerator  =jsonFactory.createGenerator(new File("videoinfo.json"), JsonEncoding.UTF8);

    		int count = list.size();
    		System.out.println("There has " + count + " videos!!!");

    		jsonGenerator.writeStartArray();
    		for(int i=0;i<count;i++){
    			jsonGenerator.writeStartObject();
    			
    			jsonGenerator.writeStringField(VideoInfo.VideoName, list.get(i).getVideoName());
    			jsonGenerator.writeStringField(VideoInfo.Score, list.get(i).getScore());
    			jsonGenerator.writeStringField(VideoInfo.Director,list.get(i).getDirector());
    			jsonGenerator.writeStringField(VideoInfo.Editor, list.get(i).getEditor());
    			jsonGenerator.writeStringField(VideoInfo.Actor, list.get(i).getActor()); 
    			jsonGenerator.writeStringField(VideoInfo.Classify, list.get(i).getClassify());
    			jsonGenerator.writeStringField(VideoInfo.Area, list.get(i).getArea());
    			jsonGenerator.writeStringField(VideoInfo.Language, list.get(i).getLanguage());
    			jsonGenerator.writeStringField(VideoInfo.ReleaseTime, list.get(i).getReleaseTime());
    			jsonGenerator.writeStringField(VideoInfo.Duration, list.get(i).getDuration());
    			jsonGenerator.writeStringField(VideoInfo.NickName, list.get(i).getNickname());
    			jsonGenerator.writeStringField(VideoInfo.IMDBLink, list.get(i).getIMDBlink());
    			jsonGenerator.writeStringField(VideoInfo.Introduction, list.get(i).getIntroduction());
    			
    			jsonGenerator.writeEndObject();

    		}
    		jsonGenerator.writeEndArray();
    				
			jsonGenerator.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("catch exception!!!");
			e.printStackTrace();
		}
    }
  
    
    public static String file2String(File file){
    	
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = br.readLine())!=null){
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    
    
    public static ArrayList<String> json2List(String json) {
    	ArrayList<String> result = new ArrayList<>();
    	try {
			ArrayList<LinkedHashMap<String, Object>> list = objectMapper.readValue(json, ArrayList.class);
			for(int i=0;i<list.size();i++){
				Map<String, Object> map = list.get(i);
				Set<String> set = map.keySet();
				Iterator<String> it = set.iterator();
				while(it.hasNext()){
					String key = it.next();
					result.add(map.get(key).toString());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	return result;
    }
    
    public static void main(String [] args){
//    	JsonUtil.read();
    	String output = JsonUtil.file2String(new File("videoname.json"));
  //  	System.out.println(output);
    	
    	ArrayList<String> list = JsonUtil.json2List(output);
    	
    	System.out.println("There has " + list.size() + " elements");
    	
    	Iterator<String> it = list.iterator();
    	
    	while(it.hasNext()){
    		System.out.println(it.next());
    	}
    	
    }
	
}
