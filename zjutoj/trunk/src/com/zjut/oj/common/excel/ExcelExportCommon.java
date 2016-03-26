package com.zjut.oj.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.zjut.oj.util.ApplicationException;

/**
 * 
 * 总体说明
 * 	Excel生成文件和下载公共类
 *
 * <p>具体说明</p>
 *
 * @author fushuijun
 * @version $Id: ExcelExportCommon.java,v 0.1 2015-3-18 下午3:05:09 Exp $
 */
public class ExcelExportCommon {
	/**
	 * 导出
	 * 
	 * @param columnList
	 * @param rowList
	 * @param path
	 */
	public static void export(List<String> columnList, List<List<String>> rowList, String path, String fileName) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		wb.setSheetName(0, "sheet1", HSSFWorkbook.ENCODING_UTF_16);

		for (int i = 0; i < columnList.size(); i++) {
			sheet.setColumnWidth((short) i, (short) 7000);
		}
		HSSFFont fontNormal = wb.createFont();
		fontNormal.setFontName("宋体");
		fontNormal.setBoldweight((short) 100);
		fontNormal.setFontHeight((short) 200);
		fontNormal.setColor(HSSFColor.BLUE.index);
		HSSFFont fontCantNull = wb.createFont();
		fontCantNull.setFontName("宋体");
		fontCantNull.setBoldweight((short) 100);
		fontCantNull.setFontHeight((short) 200);
		fontCantNull.setColor(HSSFColor.RED.index);
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style1.setWrapText(false);
		style1.setFont(fontNormal);
		style1.setBottomBorderColor(HSSFColor.RED.index);
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);

		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setWrapText(false);
		style2.setFont(fontCantNull);
		style2.setBottomBorderColor(HSSFColor.RED.index);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);

		HSSFCellStyle style3 = wb.createCellStyle();
		style3.setWrapText(false);
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 500);

		for (int i = 0; i < columnList.size(); i++) {
			HSSFCell cell = row.createCell((short) i);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			String[] tempStr = columnList.get(i).split("\\|");
			StringBuffer value = new StringBuffer();
			if ("0".equals(tempStr[0])) {
				value.append(tempStr[1]);
				cell.setCellStyle(style1);
			} else {
				value.append("*").append(tempStr[1]);
				cell.setCellStyle(style2);
			}
			cell.setCellValue(value.toString());
		}
		for (int r = 0; r < rowList.size(); r++) {
			List<String> rowData = rowList.get(r);
			HSSFRow rowCloumn = sheet.createRow(r + 1);
			for (int j = 0; j < columnList.size(); j++) {
				HSSFCell Cloumn = rowCloumn.createCell((short) j);
				Cloumn.setEncoding(HSSFCell.ENCODING_UTF_16);
				Cloumn.setCellValue(rowData.get(j).toString());
				Cloumn.setCellStyle(style3);
			}
		}
		FileOutputStream os = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			os = new FileOutputStream(path + "/" + fileName);
			wb.write(os);
		} catch (Exception e) {
			throw new ApplicationException("生成Excel错误", e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 * @param filePath
	 */
	public static void downLoad(HttpServletRequest request, HttpServletResponse response,
			String filePath, String fileName) {
		InputStream is = null;
		ServletOutputStream out = null;
		try {
			response.reset();
			response.setContentType("application/octet-stream;  charset=UTF-8");
			response.setHeader("Location", fileName);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1")); // filename应该是编码后�?UTF-8)

			out = response.getOutputStream();

			byte[] bytes = new byte[0xffff];
			is = new FileInputStream(new File(filePath + "/" + fileName));
			int b = 0;
			while ((b = is.read(bytes, 0, 0xffff)) > 0) {
				out.write(bytes, 0, b);
			}
			out.flush();
			
		} catch (Exception e) {
			throw new ApplicationException("下载Excel数据异常", e);
		} finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
