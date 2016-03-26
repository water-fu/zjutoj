package com.zjut.oj.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 文件上传下载工具类
 * @author Administrator
 *
 */
public class FileUtil {

	private static Log log = LogFactory.getLog(FileUtil.class);

	public static String fileUpdate(File file, String preFixed, String fileName, String url, String baseUrl) throws ApplicationException {
		try {
			StringBuffer sBuffer = new StringBuffer();

			File fileDir=new File(baseUrl + url);
			fileDir.mkdirs(); //建立文件夹

			String fixed = fileName.substring(fileName.indexOf("."));
			fileName = preFixed + "_" + new Date().getTime() + fixed;

			InputStream is = new FileInputStream(file);
			File outFile = new File(baseUrl + url, fileName);

			OutputStream os = new FileOutputStream(outFile);
			byte[] buffer = new byte[1024];
			int length = 0;
			while((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			sBuffer.append(url + "/" + fileName);

			return sBuffer.toString();
		} catch (Exception e) {
			log.debug(e);
			throw new ApplicationException("保存文件失败");
		}
	}

	public static void showImage(HttpServletResponse response, String url) throws ApplicationException {
		try
		{
			String imgFixed = url.substring(url.lastIndexOf(".") + 1);

			BufferedImage img = ImageIO.read(new File(url));
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/" + imgFixed);

			// 得到向客户端输出二进制数据的对象
			ServletOutputStream toClient = response.getOutputStream();
			ImageIO.write(img, imgFixed, toClient);
			toClient.close();
		}
		catch (Exception e)
		{
			throw new ApplicationException("显示图片异常");
		}
	}

	public static void downFile(HttpServletResponse response, String fileName, String url) throws ApplicationException {
		try{
			response.reset();
			response.setContentType("application/octet-stream;  charset=UTF-8");
			response.setHeader("Location", fileName);
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"),"ISO-8859-1")  ); // filename应该是编码后的(UTF-8)

//            response.setContentType("application/x-download");
//            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			ServletOutputStream out = response.getOutputStream();

			byte[] bytes = new byte[0xffff];
			InputStream is = new FileInputStream(new File(url));
			int b = 0;
			while ((b = is.read(bytes, 0, 0xffff)) > 0) {
				out.write(bytes, 0, b);
			}
			is.close();
			out.flush();

		} catch (Exception e) {
			throw new ApplicationException("导出文件异常");
		}
	}

	public static String readFileData(String url) throws ApplicationException {
		FileReader fileReader = null;
		try {
			File file = new File(url);
			fileReader = new FileReader(file);
			int len = (int) file.length();
			char[] chars = new char[len];
			fileReader.read(chars);
			String txt = String.valueOf(chars);
			return txt;
		} catch (Exception e) {
			throw new ApplicationException("读取文件数据异常");
		} finally {
			if(fileReader != null) {
				try {
					fileReader.close();
				} catch (Exception e2) {
					throw new ApplicationException("读取文件数据异常");
				}
			}
		}
	}

	public static void wirteFileData(String url, String data) throws ApplicationException {
		FileWriter fileWriter = null;
		try {
			File file = new File(url);
			fileWriter = new FileWriter(file);
			fileWriter.write(data);
		} catch (Exception e) {
			throw new ApplicationException("写入文件数据异常");
		} finally {
			if(fileWriter != null) {
				try {
					fileWriter.close();
				} catch (Exception e) {
					throw new ApplicationException("写入文件数据异常");
				}
			}
		}
	}
}
