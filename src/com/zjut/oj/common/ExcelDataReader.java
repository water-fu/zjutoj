package com.zjut.oj.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.zjut.oj.util.ApplicationException;

public abstract class ExcelDataReader<T> implements DataReader<T> {
	private static final Log log = LogFactory.getLog(ExcelDataReader.class);

	public static final String DEFAULT_CHARSET = "GBK";

	private InputStream is;

	private POIFSFileSystem fs;

	private HSSFWorkbook wb;

	private HSSFSheet sheet;

	private int sheetAt;

	private int dataRow;

	private short totalCell;

	private int totalRow;

	private int cursor;

	private List<String> header;

	public void load() {
		if (is == null) {
			throw new NullPointerException("文件去哪里了?");
		}
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			throw new ApplicationException("文件读取出错");
		}
		sheet = wb.getSheetAt(sheetAt);
		totalRow = sheet.getLastRowNum();
		log.info("Total rows: " + totalRow);
		header = new ArrayList<String>();
		for (int i = 0; i <= dataRow; i++) {
			HSSFRow row = sheet.getRow(cursor++);
			totalCell = (short) row.getPhysicalNumberOfCells();
			String headers = "";
			for (short j = 0; j < totalCell; j++) {
				header.add(getStringCellValue(row.getCell(j)));
				headers += getStringCellValue(row.getCell(j)) + (j + 1 < totalCell ? ", " : "");
			}
			log.info("Headers: " + headers);
		}
		log.info("Total cells: " + totalCell);
	}

	public List<Object> getRow() {
		List<Object> list = new ArrayList<Object>();
		HSSFRow row = sheet.getRow(cursor++);
		for (short i = 0; i < totalCell; i++) {
			list.add(getCellFormatValue(row.getCell(i)));
		}
		return list;
	}

	public abstract Object[] next() throws Exception;

	public boolean validate(T t) {
		return true;
	}

	public boolean hasNext() {
		return !(cursor > totalRow);
	}

	public void release() {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		is = null;
		wb = null;
		sheet = null;
		fs = null;
		sheetAt = 0;
		dataRow = 0;
		totalCell = 0;
		totalRow = 0;
		cursor = 0;
	}

	/**
	 *
	 *
	 * @param cell
	 * @return
	 */
	protected Object getCellFormatValue(HSSFCell cell) {
		Object value = null;
		if (cell != null) {
			switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
				case HSSFCell.CELL_TYPE_FORMULA: {
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						value = cell.getDateCellValue();
					}
					else {
						DecimalFormat decimalFormat = new DecimalFormat("0");
						value = decimalFormat.format(cell.getNumericCellValue());
					}
					break;
				}
				case HSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				default:
					value = "";
			}
		} else {
			value = "";
		}
		return value;

	}

	/**
	 *
	 *
	 * @param cell
	 *            Excel
	 * @return String
	 */
	protected String getStringCellValue(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		String strCell = "";
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				strCell = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				strCell = String.valueOf(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			default:
				strCell = "";
				break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		return strCell;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public int getSheetAt() {
		return sheetAt;
	}

	public int getDataRow() {
		return dataRow;
	}

	public short getTotalCell() {
		return totalCell;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public int getTotalData() {
		return totalRow - dataRow;
	}

	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}

	public void setSheetAt(int sheetAt) {
		this.sheetAt = sheetAt;
	}

	public void setDataRow(int dataRow) {
		this.dataRow = dataRow;
	}

	public void setInputStream(File file) {
		try
		{
			this.is = new FileInputStream(file);
		}
		catch (FileNotFoundException e)
		{
		}
	}

	public Date stringToDate(String str, String format) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Date stringToDate(String str) {
		return stringToDate(str, "yyyy/MM/dd");
	}

	public String formatMath(String value, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(new Double(value));
	}

	public String formatMath(String value) {
		DecimalFormat df = new DecimalFormat( "#");
		return df.format(new Long(value));
	}

	public String formatMath(Object value, String format) {
		return formatMath(value.toString().trim(), format);
	}

	public String formatMath(Object value) {
		return formatMath(value, "#");
	}

	public short getShort(String source, String regex) throws NumberFormatException {
		String[] ss = source.split(regex);
		return Short.valueOf(formatMath(ss[0]));
	}

	public short getShort(String source) throws NumberFormatException {
		return getShort(source, "-");
	}

	public short getShort(Object source, String regex) throws NumberFormatException {
		return getShort(source.toString().trim(), regex);
	}

	public short getShort(Object source) throws NumberFormatException {
		return getShort(source, "-");
	}

	public List<String> getHeader() {
		return header;
	}
}
