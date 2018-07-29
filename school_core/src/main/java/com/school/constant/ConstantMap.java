package com.school.constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 *        
 * 类名称：ConstantMap    
 * 类描述：静态常用Map 
 * 创建人：wangcy    
 * 创建时间：2016年8月2日 下午4:06:19    
 * 最后修改人：wangcy    
 * 最后修改时间：2016年8月2日 下午4:06:19    
 * 修改备注：    
 * @version 1.0    
 *
 */
public class ConstantMap {
	
	/**
	 * 订单号类型：平台订单号
	 */
	public static final String ORDER_NO_TYPE_ORDER = "10";

	/**
	 * 订单号类型：支付订单号
	 */
	public static final String ORDER_NO_TYPE_PAY_ORDER = "11";

	/**
	 * 订单号类型：提现订单
	 */
	public static final String ORDER_NO_TYPE_SETT_WITHDRAW = "12";

	/**
	 * 订单号类型：委托代付申请
	 */
	public static final String ORDER_NO_TYPE_SETT_APPLY = "13";

	/**
	 * 订单号类型：直连委托
	 */
	public static final String ORDER_NO_TYPE_SETT_DIRECT = "14";
	
	/**
     * 订单号类型：手工上账订单号
     */
    public static final String ORDER_NO_TYPE_MANUAL_IN = "15";
	
	/**
	 * 申请机关
	 */
	public static final Map<Integer, String> applyOrgansMap = new HashMap<Integer, String>();
	{
		applyOrgansMap.put(1, "公安");
		applyOrgansMap.put(2, "检察机关");
		applyOrgansMap.put(3, "法院");
	}
	
	/**
	 * 辩护人调查取证类型
	 */
	public static final Map<Integer, String> evidenceTypeMap = new HashMap<Integer, String>();
	static{
		evidenceTypeMap.put(1, "公检法机关取证");
		evidenceTypeMap.put(2, "证人取证");
	}
	
	/**
	 * 客户类型
	 */
	public static final Map<Integer, String> custTypeMap = new LinkedHashMap<Integer, String>();
	static{
		custTypeMap.put(1, "银行");
		custTypeMap.put(2, "顾问单位");
		custTypeMap.put(3, "其他");
	}

	/**
	 * 案件状态
	 */
	public static final Map<Integer, String> caseStatusMap = new HashMap<Integer, String>();
	static {
		caseStatusMap.put(1, "立案");
		caseStatusMap.put(2, "侦查");
		caseStatusMap.put(3, "审查");
		caseStatusMap.put(4, "一审");
		caseStatusMap.put(5, "二审");
		caseStatusMap.put(6, "执行");
		caseStatusMap.put(7, "结案");
	}
	
	/**
	 * 案件的分享状态
	 */
	public static final Map<Integer, String> caseShareStatusMap = new HashMap<Integer, String>();
	static {
		caseShareStatusMap.put(0, "未分享");
		caseShareStatusMap.put(1, "已分享");
//		caseShareStatusMap.put(2, "本部门");
//		caseShareStatusMap.put(3, "本组");
	}
	
	/**
	 * 案件节点表名 保全
	 */
	public static final String FLOW_NODE_TABLENAME_1="case_flow_conservation";
	/**
	 * 案件节点表名 公告
	 */
	public static final String FLOW_NODE_TABLENAME_2="case_flow_notice";
	/**
	 * 案件节点表名 异议
	 */
	public static final String FLOW_NODE_TABLENAME_3="case_flow_opposition";
	/**
	 * 案件节点表名 撤诉
	 */
	public static final String FLOW_NODE_TABLENAME_4="case_flow_drop";
	/**
	 * 案件节点表名 调解
	 */
	public static final String FLOW_NODE_TABLENAME_5="case_flow_intervene";
	/**
	 * 案件节点表名 举证
	 */
	public static final String FLOW_NODE_TABLENAME_6="case_flow_proof";
	/**
	 * 案件节点表名 开庭
	 */
	public static final String FLOW_NODE_TABLENAME_7="case_flow_session";
	/**
	 * 案件节点表名 判决
	 */
	public static final String FLOW_NODE_TABLENAME_8="case_flow_judgment";
	/**
	 * 案件节点表名 缴费
	 */
	public static final String FLOW_NODE_TABLENAME_9="case_flow_payment";
	/**
	 * 案件节点表名 查封/冻结
	 */
	public static final String FLOW_NODE_TABLENAME_10="case_flow_frozen";
	/**
	 * 案件节点表名 处置财产
	 */
	public static final String FLOW_NODE_TABLENAME_11="case_flow_disposefortune";
	/**
	 * 案件节点表名 执行回款
	 */
	public static final String FLOW_NODE_TABLENAME_12="case_flow_receiptimplement";
	/**
	 * 案件节点表名 执行和解
	 */
	public static final String FLOW_NODE_TABLENAME_13="case_flow_compromise";
	/**
	 * 案件节点表名 终止本次执行
	 */
	public static final String FLOW_NODE_TABLENAME_14="case_flow_stopimplement";
	/**
	 * 案件节点表名 恢复执行
	 */
	public static final String FLOW_NODE_TABLENAME_15="case_flow_resumeimplement";
	/**
	 * 案件节点表名 执行终结
	 */
	public static final String FLOW_NODE_TABLENAME_16="case_flow_endimplement";
	/**
	 * 案件节点表名 申请回避
	 */
	public static final String FLOW_NODE_TABLENAME_17="case_flow_avoid";
	
	/**
	 * 案件节点表名map
	 */
	public static final Map<Integer, String> flowNodeTableNameMap=new HashMap<Integer, String>();
	static{
		flowNodeTableNameMap.put(1, FLOW_NODE_TABLENAME_1);
		flowNodeTableNameMap.put(2, FLOW_NODE_TABLENAME_2);
		flowNodeTableNameMap.put(3, FLOW_NODE_TABLENAME_3);
		flowNodeTableNameMap.put(4, FLOW_NODE_TABLENAME_4);
		flowNodeTableNameMap.put(5, FLOW_NODE_TABLENAME_5);
		flowNodeTableNameMap.put(6, FLOW_NODE_TABLENAME_6);
		flowNodeTableNameMap.put(7, FLOW_NODE_TABLENAME_7);
		flowNodeTableNameMap.put(8, FLOW_NODE_TABLENAME_8);
		flowNodeTableNameMap.put(9, FLOW_NODE_TABLENAME_9);
		flowNodeTableNameMap.put(10, FLOW_NODE_TABLENAME_10);
		flowNodeTableNameMap.put(11, FLOW_NODE_TABLENAME_11);
		flowNodeTableNameMap.put(12, FLOW_NODE_TABLENAME_12);
		flowNodeTableNameMap.put(13, FLOW_NODE_TABLENAME_13);
		flowNodeTableNameMap.put(14, FLOW_NODE_TABLENAME_14);
		flowNodeTableNameMap.put(15, FLOW_NODE_TABLENAME_15);
		flowNodeTableNameMap.put(16, FLOW_NODE_TABLENAME_16);
		flowNodeTableNameMap.put(17, FLOW_NODE_TABLENAME_17);
	}
	
	/**
	 * 文书管理-统一文书存储路径
	 */
	public static final String DOC_UNITY_PATH = "/unity_doc";
	/**
	 * 文书管理-自定义文书存储路径
	 */
	public static final String DOC_CUSTOM_PATH = "/custom_doc";
	/**
	 * 文书管理-案件文书存储路径
	 */
	public static final String DOC_CASE_PATH = "/case_doc";
	/**
	 * 企业logo存储路径
	 */
	public static final String COMPANY_LOGO_PATH = "/logo";
	/**
	 * 案件文书存放文件路径
	 */
	public static final String CASE_CASE_FILE_PATH="/caseCaseFile";
	/**
	 * 商品图片存放文件路径
	 */
	public static final String GOOD_INFO_IMG_PATH="/good/goodInfo/";
	
	/**
	 * 调解书文件路径
	 */
	public static final String INTERVENE_FILE_PATH="/interveneFile";
	/**
	 * 举证文件文件路径
	 */
	public static final String PROOF_FILE_PATH="/proofFile";
	/**
	 * 执行和解->和解协议文件路径
	 */
	public static final String COMPROMISE_FILE_PATH="/compromise";
	/**
	 * 终止本次执行->裁定内容文件路径
	 */
	public static final String STOPIMPLEMENT_FILE_PATH="/stopimplement";
	/**
	 * 执行终结->裁定内容文件路径
	 */
	public static final String ENDIMPLEMENT_FILE_PATH="/endimplement";
	/**
	 * 处置财产->抵押物文件路径
	 */
	public static final String GUARANTY_FILE_PATH="/guaranty";
	/**
	 * 案件导入excel路径
	 */
	public static final String CASE_EXCEL_PATH="caseExcel";
	/**
	 * 案件
	 */
	public static final String CASE_PATH = "/case";
	/**
	 * 刑事案件->辩护人
	 */
	public static final String CASE_CRIMINAL_COUNSEL = "/criminal/counsel";
	/**
	 * 刑事案件->一审
	 */
	public static final String CASE_CRIMINAL_FIRST = "/criminal/first";
	/**
	 * 刑事案件->二审
	 */
	public static final String CASE_CRIMINAL_SECOND = "/criminal/second";
	/**
	 * 刑事案件->再审
	 */
	public static final String CASE_CRIMINAL_RETRAIL = "/criminal/retrial";
}
