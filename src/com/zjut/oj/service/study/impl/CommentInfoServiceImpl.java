package com.zjut.oj.service.study.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.entity.study.CommentInfoBean;
import com.zjut.oj.service.study.CommentInfoService;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@Service("commentInfoService")
@Transactional
public class CommentInfoServiceImpl extends ServiceSupport implements CommentInfoService
{
	@Override
    public int insert(CommentInfoBean commentInfoBean) throws ApplicationException{
    	return commentInfoDao.insert(commentInfoBean);
    }
    
    @Override
    public void delete(CommentInfoBean commentInfoBean) throws ApplicationException{
    	commentInfoDao.delete(commentInfoBean);
    }
    
    @Override
    public void update(CommentInfoBean commentInfoBean) throws ApplicationException{
    	commentInfoDao.update(commentInfoBean);
    }
    
    @Override
    public CommentInfoBean findById(Integer id) throws ApplicationException{
    	return commentInfoDao.findById(id);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<CommentInfoBean> queryForList(CommentInfoBean commentInfoBean) throws ApplicationException {
    	QueryHelper queryHelper = new QueryHelper(CommentInfoBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<CommentInfoBean> list = this.commentInfoDao.queryForList(queryHelper);
		return list;
    }
    
    @Override
	public PageQueryResult queryForPage(CommentInfoBean commentInfoBean,
			PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(CommentInfoBean.class, "a");
		queryHelper.addCondition(commentInfoBean.getProblemInfoBean().getProblemId() != 0, "problemId = ?", commentInfoBean.getProblemInfoBean().getProblemId());
		queryHelper.addCondition("parentId is null");
		pageQueryResult = this.commentInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}

	@Override
	public PageQueryResult queryChildForPage(CommentInfoBean commentInfoBean,
			PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(CommentInfoBean.class, "a");
		queryHelper.addCondition("parentId = ?", commentInfoBean.getCommentId());
		pageQueryResult = this.commentInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}
}