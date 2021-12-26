package com.alpha.common.util.lanxin.constant;

public class ApiRequestURL {
	/** 蓝信全局请求url */
	public static final String DOMAIN_URL = "https://lanxinplus.mwr.cn:10443/open/apigw";
	//public static final String DOMAIN_URL = "http://lanxin.mwr.gov.cn";
	//public static final String DOMAIN_URL = "https://lanxin.mwr.cn";

	/** auth2授权接口 */
	public static final String OAUTH2_TOKEN = DOMAIN_URL + "/sns/oauth2/access_token";
	/** auth2获取用户信息 */
	public static final String OAUTH2_USERINFO = DOMAIN_URL + "/sns/userinfo";

	/** 蓝信token获取接口 */
	public static final String TOKEN = DOMAIN_URL + "/cgi-bin/token";
	/** 蓝信消息接口_客服消息 */
	public static final String MESSAGE_CUSTOM = DOMAIN_URL + "/cgi-bin/message/custom/send";
	/** 蓝信消息接口_群发 */
	public static final String MESSAGE_MASS = DOMAIN_URL + "/cgi-bin/message/mass/send";
	/** 蓝信消息状态接口 */
	public static final String MESSAGE_STATUS = DOMAIN_URL + "/cgi-bin/message/control";
	/** 组织人员信息查询接口 */
	public static final String ORG_MEMBER_GET = DOMAIN_URL + "/cgi-bin/member/get";
	/** 获取组织人员信息 */
	public static final String ORG_MEMBER = DOMAIN_URL + "/cgi-bin/orgMember";
	/** 分级查询组织数据 */
	public static final String ORG_STRUCT_GET = DOMAIN_URL + "/cgi-bin/org/struct/parent/get";
	/** 查询分支下直属成员 */
	public static final String ORG_DIRECT_MEMBER_GET = DOMAIN_URL + "/cgi-bin/org/queryDirectMember";
	/** 蓝信自定义菜单接创建接口 */
	public static final String MENU_CREATE = DOMAIN_URL + "/cgi-bin/menu/create";
	/** 蓝信自定义菜单接获取接口 */
	public static final String MENU_GET = DOMAIN_URL + "/cgi-bin/menu/get";
	/** 蓝信自定义菜单接删除接口 */
	public static final String MENU_DELETE = DOMAIN_URL + "/cgi-bin/menu/delete";
	/** 文件上传地址 */
	public static final String FILE_UPLOAD = DOMAIN_URL + "/cgi-bin/media/upload";
	/** 文件下载地址 */
	public static final String FILE_DOWNLOAD = DOMAIN_URL + "/cgi-bin/media/get";
	/** 用户sso认证接口地址 */
	public static final String SSO_AUTH = DOMAIN_URL + "/cgi-bin/user/ssoauth";
	/** 蓝信企业号-部门管理-创建 */
	public static final String DEPARTMENT_CREATE = DOMAIN_URL + "/cgi-bin/department/create";
	/** 蓝信企业号-部门管理-更新*/
	public static final String DEPARTMENT_UPDATE = DOMAIN_URL + "/cgi-bin/department/update";
	/** 蓝信企业号-部门管理-删除 */
	public static final String DEPARTMENT_DELETE = DOMAIN_URL + "/cgi-bin/department/delete";
	/** 蓝信企业号-部门管理-列表查询 */
	public static final String DEPARTMENT_LIST_QUERY = DOMAIN_URL + "/cgi-bin/department/list";
}
