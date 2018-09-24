<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file="/common.jsp"%>
<%@include file="/header.jsp"%>
<script src="${ROOT}/js/bus-common.js"></script>
<title>报告生成</title>
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
	<table class="easyui-datagrid" id="tableGrid" method='post' idField='id' pagination='true' sortName='id' sortOrder='desc' fitColumns="true" border='true' >   
	    <thead>   
	        <tr>   
	        	<th field="ck" checkbox="true"></th>
	            <th field="name" width="1">编码</th>    
	            <th field="description" width="1">名称</th>   
	            <th field="unitPrice" width="1" >价格</th>   
	            <th field="imageUrl" width="1" >链接</th>   
	            <th field="isNew" width="1" >是否最新</th>
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
		url : '${ROOT}/product/list',//列表加载地址
		queryParams : getQueryParams()
	});

}
//组合查询条件
function getQueryParams(){
	return $("#searchForm").serializeJson();
}
</script>

</html>