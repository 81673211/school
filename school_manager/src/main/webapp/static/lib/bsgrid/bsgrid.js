/**
 * 公共js函数
 */
/** BSGrid初始化 */
function bsGridInit(id, url) {
	var gridObj = $.fn.bsgrid.init(id, {
		url : url,//'${ctx}/tran/orderInfoCharge/ajaxQuery.do',
		autoLoad : true,
		pageSizeSelect : true,
		pageSizeForGrid : [ 10, 20, 30, 50 ],
		pageSize : 10,
		multiSort : false,//多列排序默认false
		stripeRows : true,//
		rowHoverColor : true,
		pageIncorrectTurnAlert : false,
		displayBlankRows : false
	});

	var buttonHtml = '<td style="text-align: left;">';
	buttonHtml += '<table><tr>';
	buttonHtml += '<td><input type="button" id="export_excel" value="导出" /></td>';
	buttonHtml += '</tr></table>';
	buttonHtml += '</td>';
	$('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
	$("#export_excel").hide();
	return gridObj;
}
/**没有导出按钮的初始化方法*/
	function bsGridInitNotExport(id, url) {
		var gridObj = $.fn.bsgrid.init(id, {
			url : url,//'${ctx}/tran/orderInfoCharge/ajaxQuery.do',
			autoLoad : true,
			pageSizeSelect : true,
			pageSizeForGrid : [ 10, 20, 30, 50 ],
			pageSize : 10,
			multiSort : false,//多列排序默认false
			stripeRows : true,//
			rowHoverColor : true,
			pageIncorrectTurnAlert : false,
			displayBlankRows : false
		});
	return gridObj;
	}

function bsGridInitMax(id, url) {
	var gridObj = $.fn.bsgrid.init(id, {
		url : url,//'${ctx}/tran/orderInfoCharge/ajaxQuery.do',
		autoLoad : false,
		pageSize : 100000,
		multiSort : false,//多列排序默认false
		stripeRows : true,//
		rowHoverColor : true,
		pageIncorrectTurnAlert : false,
		displayBlankRows : false
	});

	var buttonHtml = '<td style="text-align: left;">';
	buttonHtml += '<table><tr>';
	buttonHtml += '<td><input type="button" id="export_excel" value="导出" /></td>';
	buttonHtml += '</tr></table>';
	buttonHtml += '</td>';
	$('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
	return gridObj;
}

function bsGridInitNotAutoLoad(id, url) {
	var gridObj = $.fn.bsgrid.init(id, {
		url : url,//'${ctx}/tran/orderInfoCharge/ajaxQuery.do',
		autoLoad : false,
		pageSizeSelect : true,
		pageSizeForGrid : [ 10, 20, 30, 50 ],
		pageSize : 10,
		multiSort : false,//多列排序默认false
		stripeRows : true,//
		rowHoverColor : true,
		pageIncorrectTurnAlert : false,
		displayBlankRows : false
	});

	var buttonHtml = '<td style="text-align: left;">';
	buttonHtml += '<table><tr>';
	buttonHtml += '<td><input type="button" id="export_excel" value="导出" /></td>';
	buttonHtml += '</tr></table>';
	buttonHtml += '</td>';
	$('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
	$("#export_excel").hide();
	return gridObj;
}
function bsGridInitNotAutoLoadNotExport(id, url) {
	var gridObj = $.fn.bsgrid.init(id, {
		url : url,//'${ctx}/tran/orderInfoCharge/ajaxQuery.do',
		autoLoad : false,
		pageSizeSelect : true,
		pageSizeForGrid : [ 10, 20, 30, 50 ],
		pageSize : 10,
		multiSort : false,//多列排序默认false
		stripeRows : true,//
		rowHoverColor : true,
		pageIncorrectTurnAlert : false,
		displayBlankRows : false
	});

	return gridObj;
}

//BSGrid取消选中
function bsGridUnCheckedAll(gridObj){
	$("#"+gridObj.options.gridId+" thead tr").each(function (){
		$(this).find("th input[type=checkbox]").each(function(){
			 this.checked = false;
		 });
	});

	$("#"+gridObj.options.gridId+" tbody tr").each(function (){
		$(this).find("td input[type=checkbox]").each(function(){
			 this.checked = false;
		 });
	});
}

//BSGrid全部选中
function bsGridCheckedAll(gridObj){
	$("#"+gridObj.options.gridId+" thead tr").each(function (){
		$(this).find("th input[type=checkbox]").each(function(){
			 this.checked = true;
		 });
	});
	$("#"+gridObj.options.gridId+" tbody tr").each(function (){
		$(this).find("td input[type=checkbox]").each(function(){
			 this.checked = true;
		 });
	});
}

//BSGrid反选Reverse selection
function bsGridReChecked(gridObj){
	var c=0;
	var uc=0;
	var total=0;
	var headChecked =false;
	$("#"+gridObj.options.gridId+" tbody tr").each(function (){
		$(this).find("td input[type=checkbox]").each(function(){
			total++;
			 if(this.checked){
				 c++;
				 this.checked = false;
			 }else{
				 uc++;
				 this.checked = true;
			 }
		 });
	});

	if(total==uc){
		headChecked= true;
	}
	$("#"+gridObj.options.gridId+" thead tr").each(function (){
		$(this).find("th input[type=checkbox]").each(function(){
			this.checked = headChecked;
		 });
	});
}
function fmoney(s, n) {
	if(null==s){
		return "0.00";
	}
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var one = "";
	if(s.startWith("-")){
		s = s.replace("-","");
		one = "-"
	}

	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
	t = "";
	for (i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	return one+t.split("").reverse().join("") + "." + r;
}
/** BSGrid render处理，接受json对象和key值，
 * 如果对象不为空且在对象中能找到key对应的值返回该值，
 * 如果没有找到返回代码
 * @param jsonEl
 * @param jsonKey
 * @returns
 */
function codeToNameRender(jsonEl,jsonKey){
	try{
		if("undefined" != typeof jsonEl  && jsonEl !=null){
			 if("undefined" != typeof jsonEl[jsonKey] && "undefined" != typeof jsonEl[jsonKey]){
				 return jsonEl[jsonKey];
			 }
		 }
	}catch(e){
		throw new Error(10,"没有匹配："+e.message);
		return jsonKey;
	}
	return jsonKey;
}

/**
 * 建议使用该方法，传入的是字符串值，当json字符串为空的时候也可以处理，
 *  return codeToNameRender2('${industryInfoTypeJson}',record.industryCode);
 * */
function codeToNameRender2(jsonStr,jsonKey){
	try{
		if("undefined" != typeof jsonStr  && jsonStr !=null && jsonStr !=""){
			var jsonEl = eval('('+jsonStr+')');
			 if("undefined" != typeof jsonEl[jsonKey] && "undefined" != typeof jsonEl[jsonKey]){
				 return jsonEl[jsonKey];
			 }
		 }
	}catch(e){
		throw new Error(10,"没有匹配："+e.message);
		return jsonKey;
	}
	return jsonKey;
}

//Excel导出数据最大条数
var maxSize =10000;
/**Excel 导出按钮创建与绑定事件
* 后台Controller类必须要有个 export.do 请求方法用于文件输出
* @param gridObj  BSGrid对象
* @param formId   form查询条件Id,如果传入null或Id不存在将会使用BSGrid查询结果集带有的参数
*/
function excelExport(gridObj,formId){

	var buttonHtml = '<td style="text-align: left;">';
	buttonHtml += '<table><tr>';
	buttonHtml += '<td><img alt="导出数据" src="'+getCtx()+'/resources/images/xls.gif"  id="export_excel" title="导出数据" style="cursor: pointer;" /></td>';
	buttonHtml += '<td><iframe name="iframeXls" id="iframeXls" style="display: none"></iframe></td>';
	buttonHtml += '</tr></table>';
	buttonHtml += '</td>';
	$('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
	$("#export_excel").on("click",function(){
		//拼接BSGrid数据导出URL
		var url = gridObj.options.settings.url.substr(0,gridObj.options.settings.url.lastIndexOf("/"))+"/export.do?1=1&";
		//拼接排序字段
		url = url +"sortName="+ gridObj.options.sortName +"&";
		//排序方式
		url = url +"sortOrder="+ gridObj.options.sortOrder +"&";
		//请求参数拼接
		if(formId !=null && document.getElementById(formId) !=null && $("#"+formId).serialize() !=""){
			url = url + $("#"+formId).serialize();
		}else if(undefined != gridObj.options.otherParames && "undefined" != gridObj.options.otherParames){
			url = url+gridObj.options.otherParames;
		}
		//是否需要导出，用于数据判断
		var doExport =false;
		if(gridObj.options.totalRows>maxSize){
			doExport = confirm('查询条件查询结果总记录数已经超过最大可导出数据量条数,确认后只导出['+maxSize+']条数据，否则请取消后修改查询条件再导出！')
		}else{
			doExport = true;
		}
		if(doExport){
			var a = document.createElement("a");
		    a.setAttribute("href", url);
		    a.setAttribute("target", "iframeXls");
		    a.setAttribute("id", "openwin");
		    document.body.appendChild(a);
		    layer.msg("已发送数据导出请求,请稍后...", {
				icon : 7,
				time : 1000
			});
		    a.click();
		}
	});
}

