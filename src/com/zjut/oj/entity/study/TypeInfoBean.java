package com.zjut.oj.entity.study;

import java.io.Serializable;
import com.zjut.oj.common.BaseEntity;

public class TypeInfoBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int typeId ;

    private java.lang.String typeName ;

    public void setTypeId(int typeId){
        this.typeId = typeId ;
    }

    public int getTypeId(){
        return this.typeId ;
    }

    public void setTypeName(java.lang.String typeName){
        this.typeName = typeName ;
    }

    public java.lang.String getTypeName(){
        return this.typeName ;
    }
}