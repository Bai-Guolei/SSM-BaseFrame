<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file="/common.jsp"%>
<%@include file="/header.jsp"%>
<script src="${ROOT}/js/bus-common.js"></script>
<title></title>
</head>
<body>
<header>
<!-- 引入菜单 -->
<%@include file="/WEB-INF/jsp/menu.jsp" %>
<!-- 菜单编码 -->
<input type="hidden" id="menuCode" value="0">
</header>
<!-- 列表 -->
<div region='center' style="padding: 0 10px 0 10px;">
	<form action="" id="searchForm">
		<input type="hidden" name="keyWords" value="-1">
	</form>
	<table class="easyui-datagrid" id="tableGrid" method='post' pagination='true' sortName='id' sortOrder='desc' fitColumns="true" border='true' >   
	    <thead>   
	        <tr>   
	            <th field="title" width="1">主题</th>    
	            <th field="summary" width="1">摘要</th>   
	            <th field="summary" width="1">创建人</th>   
	            <th field="summary" width="1">创建时间</th>   
	        </tr>   
	    </thead>   
	</table>
</div>
</body>
<script type="text/javascript">
$(function(){
	initpage();
})
function initpage(){
	// 初始化表格
	$('#tableGrid').datagrid({
		url : '${ROOT}/report/list',//列表加载地址
		queryParams : getQueryParams()
	});

}
//组合查询条件
function getQueryParams(){
	return $("#searchForm").serializeJson();
}
</script>

</html>