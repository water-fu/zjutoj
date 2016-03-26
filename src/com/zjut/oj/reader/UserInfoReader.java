package com.zjut.oj.reader;

import java.util.List;

import com.zjut.oj.common.ExcelDataReader;
import com.zjut.oj.entity.system.UserInfoBean;
import com.zjut.oj.util.ApplicationException;

public class UserInfoReader extends ExcelDataReader<Object[]>
{

    @Override
    public short getUseCell()
    {
        return 2;
    }

    @Override
    public Object[] next() throws Exception
    {
        List<Object> data = getRow();
        if (!validate(data)) {
            throw new ApplicationException("存在空值");
        }
        Object[] oj = new Object[1];
        UserInfoBean bean = new UserInfoBean();
        bean.setLoginSign(data.get(1).toString());

        oj[0] = bean;
        return oj;
    }

    public boolean validate(List<Object> data) {
        for (int i = 0; i < data.size(); i++) {
            if ("".equals(data.get(i))) {
                return false;
            }
        }
        return true;
    }
}
