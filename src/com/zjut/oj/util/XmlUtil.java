package com.zjut.oj.util;

import java.io.File;
import java.io.InputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * XML解析和转换工具类
 * @author Administrator
 *
 */
public class XmlUtil {

	/**
	 * 把XML片段转化为JAVABEAN
	 * @param str
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String str, Class<T> clazz) {
		XStream xStream = new XStream(new DomDriver());
		xStream.processAnnotations(clazz);
		T obj = (T) xStream.fromXML(str);
		return obj;
	}

	/**
	 * 把XML片段转化为JAVABEAN
	 * @param iStream
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(InputStream iStream, Class<T> clazz) {
		XStream xStream = new XStream(new DomDriver());
		xStream.processAnnotations(clazz);
		T obj = (T) xStream.fromXML(iStream);
		return obj;
	}

	/**
	 * 把XML片段转化为JAVABEAN
	 * @param file
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(File file, Class<T> clazz) {
		XStream xStream = new XStream(new DomDriver());
		xStream.processAnnotations(clazz);
		T obj = (T) xStream.fromXML(file);
		return obj;
	}

	/**
	 * 把JAVABEAN转化为XML
	 * @param obj
	 * @return
	 */
	public static String toXml(Object obj) {
		XStream xStream = new XStream(new DomDriver("utf-8"));
		xStream.processAnnotations(obj.getClass());
		return xStream.toXML(obj);
	}
}
