/**
 * Tianque.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.zjut.oj.reader;

import java.util.List;

import com.zjut.oj.common.ExcelDataReader;
import com.zjut.oj.util.ApplicationException;

/**
 * 总体说明
 * 	决赛用户导入读取类
 *
 * <p>具体说明</p>
 *
 * @author fushuijun
 * @version $Id: HustOjUserReader.java,v 0.1 2015-3-18 下午4:25:23 Exp $
 */
public class HustOjFinalUserReader extends ExcelDataReader<Object[]> {

	/**
	 * 用到的列
	 */
	@Override
	public short getUseCell() {
		return 3;
	}

	/**
	 * 获取下一行数据
	 */
	@Override
	public Object[] next() throws Exception {
		List<Object> data = getRow();
		if (!validate(data)) {
			throw new ApplicationException("存在空值");
		}
		Object[] oj = new Object[3];
		oj[0] = data.get(0);
		oj[1] = data.get(1);
		oj[2] = data.get(2);

		return oj;
	}

	private boolean validate(List<Object> data) {
		if("".equals(data.get(0).toString())) {
			return false;
		}
//		if("".equals(data.get(1).toString())) {
//			return false;
//		}
//		if("".equals(data.get(2).toString())) {
//			return false;
//		}

		return true;
	}

}
