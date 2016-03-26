package com.zjut.oj.common;

import java.util.List;

import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

public interface IDaoSupport<T>
{
    T findById(Integer id) throws ApplicationException;

    @SuppressWarnings("rawtypes")
    List queryForList(QueryHelper queryHelper) throws ApplicationException;

    PageQueryResult queryForPage(PageQueryResult pageQueryResult, QueryHelper queryHelper) throws ApplicationException;

    int insert(T t) throws ApplicationException;

    void update(T t) throws ApplicationException;

    void delete(T t) throws ApplicationException;
}
