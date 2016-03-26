package com.zjut.oj.action.common;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.DataReader;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.common.ReaderCommon;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.common.TableNameMapper;
import com.zjut.oj.util.ApplicationException;

@Controller
@Scope("prototype")
public class importExcelData extends ActionSupport implements ApplicationContextAware {

	private final Logger logger = LoggerFactory.getLogger(importExcelData.class);

	/**
	 * EXCEL导入公共类
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Spring容器对象
	 */
	private ApplicationContext ac;

	/**
	 * Excel文件名称
	 */
	private File excelFile;

	/**
	 * 需要导入的表名称
	 */
	private String tableName;

	/**
	 * 内容所在Excel所在sheet
	 */
	private int sheetAt;

	/**
	 * 内容从第几行开始读取
	 */
	private int dataRow;

	/**
	 * 初始化ApplicationContext对象
	 */
	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		this.ac = ac;
	}

	/**
	 * excel点数据导入公共方法
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("rawtypes")
	@LoginFilter(needLogin = true)
	public String importData(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		DataReader reader = null;
		int count = 0;
		try {
			//根据tableName获取影响的Reader类和Service类
			Class[] clazzs = TableNameMapper.getClazz(tableName);
			reader = ReaderCommon.initReader(clazzs[0], sheetAt, dataRow, excelFile);
			if (reader.getUseCell() != -1 && reader.getTotalCell() != reader.getUseCell()) {
				throw new ApplicationException("导入模版不正确");
			}
			String serviceName = clazzs[1].getSimpleName().substring(0, 1).toLowerCase()
					+ clazzs[1].getSimpleName().substring(1);
			ServiceSupport serviceSupport = (ServiceSupport) ac.getBean(serviceName);

			while (reader.hasNext()) {
				Object[] beans = (Object[]) reader.next();
				if (beans != null && beans.length != 0) {
					try {
						serviceSupport.importExcelDataSave(request, beans);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}

				count++;
			}
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				throw new ApplicationException("第" + reader.getCursor() + "行["
						+ e.getMessage().replace("\n", "") + "]", e);
			}
			throw new ApplicationException("导入失败,联系系统管理员", e);
		} finally {
			if (reader != null) {
				reader.release();
			}
		}
		return responseResult("import", count);
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public int getSheetAt() {
		return sheetAt;
	}

	public void setSheetAt(int sheetAt) {
		this.sheetAt = sheetAt;
	}

	public int getDataRow() {
		return dataRow;
	}

	public void setDataRow(int dataRow) {
		this.dataRow = dataRow;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
