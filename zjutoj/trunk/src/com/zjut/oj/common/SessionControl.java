package com.zjut.oj.common;

import javax.servlet.http.HttpServletRequest;

import com.zjut.oj.entity.system.UserDetails;
import com.zjut.oj.entity.system.UserInfoBean;

public class SessionControl
{

    /**
     * 根据把值赋给session相应名称的属性
     */
    public static void setAttribute(HttpServletRequest request,
                                    String strName,
                                    Object oValue)
    {
        setAttribute(request,
                strName,
                oValue,
                true);// 默认情况下是从session取，以兼容原有代码
    }

    /**
     * 根据把值赋给session或者attribute相应名称的属性,提供为原来将变量放入session向attribute的平滑过度
     */
    public static void setAttribute(HttpServletRequest request,
                                    String strName,
                                    Object oValue,
                                    boolean fromSession)
    {
        removeAttribute(request,
                strName,
                fromSession);
        if(fromSession)
        {
            request.getSession()
                    .setAttribute(strName,
                            oValue);
        }
        else
        {
            request.setAttribute(strName,
                    oValue);
        }
    }

    /**
     * 从session中相应名称的属性删除
     */
    public static void removeAttribute(HttpServletRequest request,
                                       String strName)
    {
        removeAttribute(request,
                strName,
                true);
    }

    /**
     * *从session或attribute中相应名称的属性删除
     */
    public static void removeAttribute(HttpServletRequest request,
                                       String strName,
                                       boolean fromSession)
    {
        if(fromSession)
        {
            request.getSession()
                    .removeAttribute(strName);
        }
        else
        {
            request.removeAttribute(strName);
        }
    }

    /**
     * 根据名称得到session中相应属性值
     */
    public static Object getAttribute(HttpServletRequest request,
                                      String strName)
    {
        return getAttribute(request,
                strName,
                true);
    }

    public static Object getAttribute(HttpServletRequest request,
                                      String strName,
                                      boolean fromSession)
    {
        if(fromSession)
        {
            if(request.getSession() == null) {
                return null;
            }
            return request.getSession()
                    .getAttribute(strName);
        }
        else
        {
            return request.getAttribute(strName);
        }
    }

    public static UserDetails getCurUserDetail(HttpServletRequest request) {
        return (UserDetails) getAttribute(request, SystemConstantType.USER_DETAILS,
                true);
    }

    public static UserInfoBean getOperInfo(HttpServletRequest request) {
        return getCurUserDetail(request).getUser();
    }

    public static long getOpId(HttpServletRequest request)
    {
        return getOperInfo(request).getUserId();
    }

}
