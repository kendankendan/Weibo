package org.shiro.demo.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文本切割工具类
 * @author Christy
 *
 */
public class SplitParamsUtil {

	
	/**
	 * 根据文本切割函数
	 * @param text 文本
	 * @param special 切割的字符
	 * @return
	 */
	public static String[] splitWithSpecial(String text,String special){
		String[] arrs = text.split(special);
		return arrs;
	}
	
	/**
	 * 切割参数
	 * @param params 参数文本
	 * @param splitword 类似&的分隔符
	 * @param equalword xx=xx 分隔符
	 * @return
	 */
	public static Map<String, String> splitParams(String params,String splitword,String equalword){
		
		Map<String , String > resultMap = new HashMap<String, String>();
		try{
			String[] arrs = splitWithSpecial(params, splitword);
			for(String item : arrs){
				String[] realparams = item.split(equalword);
				resultMap.put(realparams[0], realparams[1]);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("参数请按照正确格式输入");
		}
		return resultMap;
	}
}
