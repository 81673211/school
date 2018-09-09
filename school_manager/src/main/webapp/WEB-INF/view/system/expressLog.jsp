 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <title>日志列表</title>
    <script type="text/javascript">
      var vm_expressLogManage=avalon.define({
          $id:'expressLogManage',
          data:${listData},
//           expressLogStatusMap:${expressLogStatusMap},
          expressTypes:${expressTypes},
          inputStatus:"${searchParams.status}",
          inputText:"${searchParams.keyword}",
          methods:{
              query:'func',//搜索事件
              detail:'func',//查看
              detailUrl:"${detailUrl}"//详情url
          }
      });
    </script>
</head>

<body :controller="expressLogManage">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 系统管理 <span class="c-gray en">&gt;</span> 日志列表 <a class="btn btn-success radius r"
            style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="page-container">
        <div class="bk-gray pd-20 clearfix">
<!--             <div class="revi-staus f-l"> -->
<!--                 <span class="f-l lh-30">状态：</span> -->
<!--                 <span class="select-box radius f-l" style="width: 120px;"> -->
<!--                  <select class="select" size="1" name="status" :duplex="@inputStatus"> -->
<!--                  	<option value="">全部</option> -->
<!--                     <option :for="(k,v) in @expressLogStatusMap" :attr="{value:k}">{{v}}</option>  -->
<!--                  </select> -->
<!--              </span> -->
<!--             </div> -->
            <div class="tran-serch f-l">
                <input type="text" name="keywordSearch" id="" placeholder="快递单号" style="width:200px" class="input-text" :duplex="@inputText">
                <button name="" id="" class="btn btn-primary radius" type="button" :click="@methods.query"><i class="Hui-iconfont"></i> 搜索</button>
            </div>
        </div>
        <div class="mt-20">
            <table class="table table-bexpressLog table-bexpressLoged table-hover table-bg table-sort" id="reviwtable">
                <thead>
                    <tr class="text-c">
                        <th width="25">编号</th>
                        <th width="100">快递单号</th>
                        <th width="30">快递类型</th>
                        <th width="100">操作</th>
                        <th width="100">快递状态</th>
                        <th width="100">操作时间</th>
                        <th width="100">操作人</th>
                        <th width="50">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="text-c" :for="index,el in @data">
                        <td>{{index+1}}</td>
                        <td>{{el.code}}</td>
                        <td>{{@expressTypes[el.expressType]}}</td>
                        <td>{{el.action}}</td>
                        <td>{{el.status}}</td>
                        <td>{{el.operateTime|date('yyyy-MM-dd HH:mm:ss')}}</td>
                        <td>{{el.operatorName}}</td>
                        <td class="but_xq">
                        	<btn:hasUrlPerm link="${detailUrl}">
	                           	<a class="btn btn-primary-outline size-S radius" :click="@methods.detail(el.id,@methods.detailUrl)">详情</a>
                            </btn:hasUrlPerm>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!--分页-->
		<div class="pag-box withdarwal-pag" id="page">
			<form method="post" id="searchForm"
				action="${ctx}/expressLog/list.do">
				<input name="search_status" id="testForm_state"
					type="hidden"  :attr="{value:@inputStatus}" />
				<input name="search_type" id="testForm_type" type="hidden"
					:attr="{value:@inputType}" />
				<input name="search_keyword" id="testForm_text" type="hidden"
					:attr="{value:@inputText}" />
			</form>
			<page:createPager pageSize="${pageSize}" totalPage="${pageCount}"
					totalCount="${totalCount}" curPage="${pageNo}" formId="searchForm" />
		</div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
    <script type="text/javascript" src="${ctx}/static/model/system/expressLog.js"></script>
</body>
</html>