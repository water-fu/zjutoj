package com.zjut.oj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 总体说明
 * 	文件解析器
 *
 * <p>具体说明</p>
 *
 * @author fushuijun
 * @version $Id: ConfigParse.java,v 0.1 2015-3-30 下午7:11:25 Exp $
 */
public class ConfigParse {

	/**
	 * 加载文件
	 *
	 * @param fileName
	 * @return
	 */
	private static InputStream loadFile(String fileName) {
		InputStream in = null;
		String filePath = "";
		try {
			File file = new File(fileName);
			filePath = file.getAbsolutePath();
			System.out.println("将从位置[" + filePath + "]加载数据库配置文件。");
			in = new FileInputStream(file);
		} catch (Exception e) {
			System.out.println("从位置[" + filePath + "]没有加载到数据库配置文件，将从类路径加载。");
			in = ConfigParse.class.getResourceAsStream("/" + fileName);
		}

		return in;
	}

	/**
	 * 加载properties文件
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static Properties loadConfig(String fileName) throws IOException{
		InputStream in = loadFile(fileName);

		Properties prop = new Properties();
		prop.load(in);

		return prop;

	}

	/**
	 * 获取给定参数获取属性值
	 *
	 * @param fileName
	 * @param configs
	 * @return
	 * @throws ApplicationException
	 */
	public static final Map<String, String> parseConfig(String fileName, String[] configs) throws ApplicationException{
		Properties prop = null;
		try {
			prop = loadConfig(fileName);
		} catch (IOException e) {
			throw new ApplicationException("加载配置文件异常");
		}

		Map<String, String> configMap = new HashMap<String, String>();
		for(String configStr : configs)
		{
			configMap.put(configStr, prop.getProperty(configStr));
		}

		return configMap;
	}

	/**
	 * 读取所有配置数据
	 *
	 * @param fileName
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public static final List<String> parseConfig(String fileName) throws ApplicationException {
		Properties prop = null;
		try {
			prop = loadConfig(fileName);
		} catch (IOException e) {
			throw new ApplicationException("加载配置文件异常");
		}

		List<String> store = new ArrayList<String>();

		Enumeration<String> en = (Enumeration<String>) prop.propertyNames();
		while (en.hasMoreElements()) {
			String key = en.nextElement();
			store.add(prop.getProperty(key));
		}

		return store;
	}
    
    
    
    /*------------------------------------无敌分割线------------------------------------------------------*/
	/**
	 * 解析TXT文件
	 *
	 * @param fileName
	 * @return
	 */
	public static List<String> parstTXTFile(String fileName) throws IOException {
		List<String> store = new ArrayList<String>();
		String encoding = "GBK";
		InputStream in = loadFile(fileName);
		// 读取流
		InputStreamReader reader = new InputStreamReader(in, encoding);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String txtLine = null;
		// 遍历txt文件
		while((txtLine = bufferedReader.readLine()) != null) {
			store.add(txtLine);
		}

		return store;
	}
}
