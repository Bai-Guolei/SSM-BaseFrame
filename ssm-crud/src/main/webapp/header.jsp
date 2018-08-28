<!DOCTYPE HTML>

<%
	// TODO 权限检查【忽略标志 - jsp参数】
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">Report</a>
    </div>
    <div>
        <ul class="nav navbar-nav navbar-left">
            <li class="active"><a href="#">首页</a></li>
            <li><a href="#">报告生成</a></li>
            <li><a href="#">风险预警</a></li>
            <li><a href="#">期限查询</a></li>
            <li><a href="#">智能助手</a></li>
            <li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
                   	 系统设置
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="#">菜单配置</a></li>
                    <li><a href="#">权限管理</a></li>
                </ul>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right"> 
            <li><a href="#"><span class="glyphicon glyphicon-user"></span> 注册</a></li> 
            <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> 登录</a></li> 
        </ul>
    </div>
    </div>
</nav>
<script type="text/javascript">
$(function(){
	
})


</script>
