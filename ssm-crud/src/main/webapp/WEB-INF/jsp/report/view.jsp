<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file="/common.jsp"%>
<%@include file="/header.jsp"%>
<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="${ROOT}/js/webuploader/webuploader.css">
<!--引入JS-->
<script type="text/javascript" src="${ROOT}/js/webuploader/webuploader.js"></script>
<script src="${ROOT}/js/bus-common.js"></script>
<title>Insert title here</title>
</head>
<body>
<header>
<!-- 引入菜单 -->
<%@include file="/WEB-INF/jsp/menu.jsp" %>
<!-- 菜单编码 -->
<input type="hidden" id="menuCode" value="1">
</header>
<form action="" id="addForm">
	<table class="form-table" border="0" cellpadding="0" cellspacing="0">
		<col width="15%"/>
		<col width="35%"/>
		<col width="15%"/>
		<col width="35%"/>
		<tr>
			<th>标题</th>
			<td><input type="text" maxlength="50" name="title"></td>
			<th>摘要</th>
			<td><input type="text" maxlength="100" name="summary"></td>
		</tr>
		<tr>
			<th>备注</th>
			<td colspan="3">
				<textarea rows="3" name="remark"></textarea>
			</td>
		</tr>
		<tr>
			<th>附件</th>
			<td colspan="3">
				<div id="uploader" class="wu-example">
					<!--用来存放文件信息-->
					<div id="thelist" class="uploader-list"></div>
					<div class="btns">
						<div id="picker">选择文件</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
</form>
<div class="btn-bar">
	<button class="btn primary" onclick="save()">保存</button>
	<button class="btn default" onclick="goBack()">取消</button>
</div>
</body>
<script type="text/javascript">
$(function(){
	//上传组件初始化
	var uploader = WebUploader.create({
	 	swf : $AppContext + '/js/webuploader/Uploader.swf',
	 	server : $AppContext + '/base/file/uploadfile',
	 	pick : "#picker",
	 	formData : {},
	 	auto : true,
	 	fileSingleSizeLimit : 30 * 1024 * 1024, // 验证单个文件大小是否超出限制, 超出则不允许加入队列。
	 	duplicate : true, // 不去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
	 	disableGlobalDnd : true
	 	// 是否禁掉整个页面的拖拽功能，如果不禁用，图片拖进来的时候会默认被浏览器打开。
	});

	console.log(uploader);	
	//当有文件被添加进队列的时候
	uploader.on( 'fileQueued', function( file ) {
	     $("#thelist").append( '<div id="' + file.id + '" class="item">' +
	         '<h4 class="info">' + file.name + '</h4>' +
	         '<h4 class="info">' + file.size + '</h4>' +
	         '<p class="state">等待上传...</p>' +
	     '</div>' );
	});
	//上传成功刷新数据
	uploader.on( 'uploadSuccess', function( file ) {
	    $( '#'+file.id ).find('p.state').text('已上传');
	});
	
	uploader.on( 'uploadError', function( file ) {
	    $( '#'+file.id ).find('p.state').text('上传出错');
	});
	
	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').fadeOut();
	});
	
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
	    var $li = $( '#'+file.id ),
	        $percent = $li.find('.progress .progress-bar');

	    // 避免重复创建
	    if ( !$percent.length ) {
	        $percent = $('<div class="progress progress-striped active">' +
	          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
	          '</div>' +
	        '</div>').appendTo( $li ).find('.progress-bar');
	    }

	    $li.find('p.state').text('上传中');

	    $percent.css( 'width', percentage * 100 + '%' );
	});
})
/**
 * 表单保存方法
 */
function save(){
	//1.表单校验
	
	//2.获取表单数据
	 var dataArr = $("#addForm").serializeArray();
	//3.post提交表单
	$.post('${ROOT}/report/-1', dataArr, function(data){
		if (data.success) {
			alert("保存成功");
        } else {
            error(data.msg);
        }
	});
	
}


</script>
</html>