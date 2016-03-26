package com.zjut.oj.common;

import java.io.File;

import com.zjut.oj.util.ApplicationException;

public class ReaderCommon
{
    @ SuppressWarnings ( {"rawtypes"} )
    public static DataReader initReader(Class readerClass, int sheetAt, int dataRow, File file)
    {
        DataReader reader = getReader(readerClass);
        reader.setSheetAt(sheetAt);
        reader.setDataRow(dataRow);
        reader.setInputStream(file);
        reader.load();

        return reader;
    }

    @ SuppressWarnings ( {"rawtypes"} )
    private static DataReader getReader(Class clazz)
    {
        try
        {
            return (DataReader) clazz.newInstance();
        }
        catch (Exception e)
        {
            throw new ApplicationException(clazz.getSimpleName() + "导入类初始化失败");
        }
    }
}
