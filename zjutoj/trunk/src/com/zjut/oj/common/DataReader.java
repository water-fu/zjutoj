package com.zjut.oj.common;

import java.io.File;
import java.util.List;

public interface DataReader<T>
{
    public void load();
    
    public Object[] next()  throws Exception;
    
    public boolean validate(T t);
    
    public boolean hasNext();
    
    public void release();
    
    public void setInputStream(File file);
    
    public void setSheetAt(int sheetAt);
    
    public void setDataRow(int dataRow);
    
    public int getTotalRow();
    
    public int getTotalData();
    
    public short getTotalCell();
    
    public short getUseCell();
    
    public int getCursor();
    
    public List<String> getHeader();
}
