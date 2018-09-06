<html>
<head></head>
<body>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">Report</a>
    </div>
    <div>
        <ul class="nav navbar-nav navbar-left">
            <li class="actice"><a href="${ROOT }/">首页</a></li>
            <li><a href="${ROOT }/report/listView">报告生成</a></li>
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
</body>
<script type="text/javascript">
$(function(){
	var menuCode = $("#menuCode").val();
	$(".navbar-nav li").removeClass("active");
	$(".navbar-nav li:eq("+menuCode+")").addClass("active");
})
</script>
</html>
