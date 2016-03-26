package com.zjut.oj.common;

public class SystemConstantType {

	/*
	 * 密码盐
	 */
	public static final String PASS_SALT = "zjutoj";

	public static final String USER_DETAILS = "USER_DETAILS";
	
	/*
	 * MESSAGE_TYPE类型定义
	 */

	//通知通告
	public static final int MESSAGE_TYPE_NOTICE = 1;

	//比赛简介
	public static final int MESSAGE_TYPE_MATCHINTRO = 2;

	//ACM简介
	public static final int MESSAGE_TYPE_ACMINTRO = 3;

	//最近赛事
	public static final int MESSAGE_TYPE_LASTMATCH = 4;

	//工大团队
	public static final int MESSAGE_TYPE_TEAM = 5;

	//竞赛成绩
	public static final int MESSAGE_TYPE_SCORE = 6;
	
	/*
	 * MESSAGE_ATTACH类型定义
	 */

	//图片
	public static final int MESSAGE_ATTACH_IMAGE = 1;

	//下载文件
	public static final int MESSAGE_ATTACH_FILE = 2;

	/*
	 * 是否显示
	 */
	//未审核
	public static final int IS_SHOW_NOT = 0;

	//通过
	public static final int IS_SHOW_YES = 1;

	/*
	 * 竞赛是否启用
	 */
	//启用
	public static final int CONTEST_STATUS_YES = 1;

	//禁用
	public static final int CONTEST_STATUS_NO = 0;

	/*
	 * 邮件发送状态
	 */
	//未发送
	public static final int SEND_STATE_WAIT = 0;

	//发送成功
	public static final int SEND_STATE_SUCC = 1;

	//发送失败
	public static final int SEND_STATE_ERROR = 2;
}
