package com.zjut.oj.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XMLDecodeUtil extends DefaultHandler
{
    public static final int LOCAL_FILE = 1;
    public static final int HTTP_VALUE = 2;

    /**
     * 解析返回数据
     */
    private List<Map<String, Object>> list = null;

    /**
     * 返回List中存放的Key、Value
     */
    private HashMap<String, Object> map = null;

    /**
     * 当前正在解析的tag名称
     */
    private String currentTag;

    /**
     * 存放具体数据的父节点
     */
    private String nodeName;

    /**
     * 存放文件名称
     */
    private String fileName;

    /**
     * 解析文件类型
     */
    private int fileType;

    /**
     * 当前节点是否是数据
     */
    private boolean isData = false;

    /**
     * 是否是开始节点
     */
    private boolean isStartNode = false;

    /**
     * 构造方法
     * @param nodeName  存放数据的父节点
     */
    public XMLDecodeUtil(String nodeName)
    {
        this.nodeName = nodeName;
    }

    /**
     * 文档读取开始
     */
    public void startDocument() throws SAXException
    {
        list = new ArrayList<Map<String,Object>>();
    }

    /**
     * 文档读取结束
     */
    public void endDocument() throws SAXException
    {
    }

    /**
     * 节点读取开始
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if(qName.equals(nodeName) && isData == false)
        {
            map = new HashMap<String, Object>();
            isData = true;
        }
        isStartNode = true;
        currentTag = qName;
    }

    /**
     * 节点读取结束
     */
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if(qName.equals(nodeName) && isData == true)
        {
            list.add(map);
            isData = false;
        }
        isStartNode = false;
        super.endElement(uri, localName, qName);
    }

    /**
     * 读取数据
     */
    @SuppressWarnings("rawtypes")
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        if(isData)
        {
            String currentValue = new String(ch, start, length).trim();
            if(!"".equals(currentValue) && !"\n".equals(currentValue))
            {
                map.put(currentTag, currentValue);
            }else if(!currentTag.equals(nodeName) && isStartNode) {
                XMLDecodeUtil xmlDecodeUtil = new XMLDecodeUtil(currentTag);
                List list = xmlDecodeUtil.XMLDecode(this.fileName, this.fileType);
                map.put(currentTag, list);
            }
        }
    }

    private List<Map<String, Object>> getList()
    {
        return this.list;
    }

    /**
     *
     * @param value
     * @param type   1:表示文件名   2：标识XML格式字符串
     * @return
     */
    public List<Map<String, Object>> XMLDecode(String value, int type)
    {
        this.fileName = value;
        this.fileType = type;
        InputStream mStream = null;
        if(type == LOCAL_FILE)
        {
            String filePath = "";
            try
            {
                File file = new File(value);
                filePath = file.getAbsolutePath();
                System.out.println("将从位置[" + filePath + "]加载数据库配置文件。");
                mStream = new FileInputStream(file);
            }
            catch (IOException e)
            {
                System.out.println("从位置[" + filePath + "]没有加载到数据库配置文件，将从类路径加载。");
                mStream = XMLDecodeUtil.class.getResourceAsStream("/" + value);
            }
        }
        else if(type == HTTP_VALUE)
        {
            mStream = new ByteArrayInputStream(
                    value.getBytes());
        }
        try
        {
            if(mStream == null)
            {
                System.out.println("输入流异常");
                return null;
            }

            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();

            XMLReader xmlReader = parser.getXMLReader();
            xmlReader.setContentHandler(this);

            xmlReader.parse(new InputSource(mStream));
        }
        catch (Exception e)
        {
            System.out.println("解析XML异常：" + e);
        }
        finally
        {
            if(mStream != null)
            {
                try
                {
                    mStream.close();
                }
                catch (IOException e)
                {
                    System.out.println("关闭文件流异常:" + e);
                }
            }
        }

        return this.getList();
    }

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        XMLDecodeUtil xmlDecodeUtil = new XMLDecodeUtil("FireCompanyInfo");
        List list = xmlDecodeUtil.XMLDecode("test.xml", 1);
        System.out.println(list.size());
    }
}