package com.zjut.oj.common;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.zjut.oj.entity.system.UserDetails;
import com.zjut.oj.util.ApplicationException;

@Aspect
public class ServiceExecAspect {
	
	private final static Log log = LogFactory.getLog(ServiceExecAspect.class);
	
	@Before("execution(public * com.zjut.oj.dao..*Impl.insert*(..)) &&  args(baseEntity,..)")
	public void beforeExecInsertInService(BaseEntity baseEntity) throws ApplicationException {
		log.debug("enter beforeExecInsertInService");
		UserDetails userDetails = SessionControl.getCurUserDetail(SysContext.getRequest());
		if(userDetails != null) {
			baseEntity.setUserInfoBean(userDetails.getUser());
		}
		baseEntity.setOperDate(new Date());
	}
	
	
	@Before("execution(public * com.zjut.oj.dao..*Impl.update*(..)) &&  args(baseEntity,..)")
	public void beforeExecUpdateInService(BaseEntity baseEntity) throws ApplicationException {
		log.debug("enter beforeExecUpdateInService");
		UserDetails userDetails = SessionControl.getCurUserDetail(SysContext.getRequest());
		if(userDetails != null) {
			baseEntity.setUserInfoBean(userDetails.getUser());
		}
		baseEntity.setOperDate(new Date());
	}
	
	@Before("execution(public * com.zjut.oj.common.DaoSupport.insert*(..)) &&  args(baseEntity,..)")
	public void beforeExecBaseInsertInService(BaseEntity baseEntity) throws ApplicationException {
		log.debug("beforeExecBaseInsertInService");
		UserDetails userDetails = SessionControl.getCurUserDetail(SysContext.getRequest());
		if(userDetails != null) {
			baseEntity.setUserInfoBean(userDetails.getUser());
		}
		baseEntity.setOperDate(new Date());
	}
	
	@Before("execution(public * com.zjut.oj.common.DaoSupport.update*(..)) &&  args(baseEntity,..)")
	public void beforeExecBaseUpdateInService(BaseEntity baseEntity) throws ApplicationException {
		log.debug("beforeExecBaseUpdateInService");
		UserDetails userDetails = SessionControl.getCurUserDetail(SysContext.getRequest());
		if(userDetails != null) {
			baseEntity.setUserInfoBean(userDetails.getUser());
		}
		baseEntity.setOperDate(new Date());
	}
	
//	@AfterThrowing(   
//	   pointcut="execution(public * com.zjut.oj.action..*Action.*(..))", 
//	   throwing="ex"
//	 )
//	public String doExceptionAction(JoinPoint jp, Throwable ex) {
//		String aa = jp.getSignature().getName();
//		System.out.println(aa);
//		ex.printStackTrace();
//		return "error";
//	}
}