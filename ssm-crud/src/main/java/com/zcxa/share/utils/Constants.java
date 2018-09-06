package com.zcxa.share.utils;

public class Constants {
	/**
	 * 成功
	 */
	public static final String SUCCESS = "OK";

	/**
	 * 失败
	 */
	public static final String FAIL = "FAIL";

	/**
	 * 消息体标识
	 */
	public static final String MSG = "msg";

	/**
	 * 消息体数据
	 */
	public static final String DATA = "data";
	/**
	 * 消息状态：操作成功
	 */
	public static final String OPERATE_SUCCESS = "操作成功！";

	/**
	 * 消息状态：保存成功
	 */
	public static final String SAVE_SUCCESS_MSG = "保存成功！";

	/**
	 * 消息状态：修改成功
	 */
	public static final String UPDATE_SUCCESS_MSG = "修改成功！";

	/**
	 * 消息状态：删除成功
	 */
	public static final String DELETE_SUCCESS_MSG = "删除成功！";

	/**
	 * 消息状态：删除成功
	 */
	public static final String UPLOAD_SUCCESS_MSG = "上传成功";

	/**
	 * 消息状态：提交审核
	 */
	public static final String SAVE_ADUIT_MSG = "提交审核成功！";

	/**
	 * 消息状态：暂存
	 */
	public static final String SAVE_PAUSE_MSG = "暂存成功！";

	/**
	 * 消息状态：当前用户存在
	 */
	public static final String USER_EXIST = "当前用户已存在！";

	/**
	 * 系统操作
	 */
	public static final String SYS_OPERAT = "系统操作";

	/**
	 * 格式化(24小时制)<br>
	 * FORMAT_DateTime: 日期时间
	 */
	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 格式化(24小时制)<br>
	 * FORMAT_DateTime: 日期时间
	 */
	public static final String FORMAT_DATE = "yyyy-MM-dd";

	/**
	 * 集合分隔符: ,
	 */
	public static final String COMMA = ",";

	/**
	 * 新增辨识: -1
	 */
	public static final Long ISADD = -1L;

	/**
	 * 单项
	 */
	public static final String DX = "1";

	/**
	 * 多项
	 */
	public static final String DBX = "2";

	/**
	 * 时间
	 */
	public static final String TIME = "3";

	/**
	 * excel导出时,每个sheet最大行数
	 */
	public static final int EXCEL_PAGE_SIZE = 5000;

	/**
	 * 无数据/为0时显示标识符
	 */
	public static final String DASHED = "— —";

	/**
	 * 自定义表物理名称后缀
	 */
	public static final String EXTEND_TABLE_SUFFIX = "_extend";

	/**
	 * 自定义字段前缀
	 */
	public static final String EXTEND_COLUMN_PREFIX = "zdy_";
	
	/**
	 * 网站session绑定
	 */
	public static final String WEB_SESSION_CTSUSER = "web_session_ctsuser";
}
