<%@include file="/header.jsp"%>

<html>

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>文档在线预览</title>
	<link rel="stylesheet" href="${ROOT}/js/jquery/custom-scrollbar/jquery.mCustomScrollbar.min.css"/>
	<script src="${ROOT}/js/jquery/jquery.min.js"></script>
	<script src="${ROOT}/js/jquery/jquery-migrate.min.js"></script>
	<script src="${ROOT}/js/pdf.js"></script>
	<script src='${ROOT}/js/jquery/custom-scrollbar/jquery.mCustomScrollbar.concat.min.js'></script>
</head>

<style>
	.mCSB_inside>.mCSB_container{margin-right: 0}
	.mCSB_scrollTools{width: 10px}
	.mCS-light .mCSB_scrollTools .mCSB_draggerRail {background-color: rgba(255,255,255,.8); filter: "alpha(opacity=80)"; -ms-filter: "alpha(opacity=80)";}
	.mCS-light .mCSB_scrollTools .mCSB_dragger .mCSB_dragger_bar {background-color: rgba(0,0,0,.7); filter: "alpha(opacity=70)"; -ms-filter: "alpha(opacity=70)";}

	body {background: #c6c6c6 !important; margin: 0px;}
	.toolbar {position: absolute; left: 0px; right: 0px; padding: 5px; background-color: white;}
	.file-icon {position: relative; float: left; width: 32px;}
	.file-name {font-size: 16px; color: #909090; padding-top: 6px;}
	.loading {position: fixed; top: 10px; right: 40px; width: 24px; height: 24px;}
	.download {position: fixed; top: 10px; right: 10px; cursor: pointer; width: 24px;}
	.container {position: absolute; left: 0px; top: 45px; right: 0px; bottom: 5px; padding: 0px; margin: 0px;}
	#divContent {width: 800px; margin: 5px auto; padding: 10px; background-color: white; display: none;}
	.ppt-pager {background-color: #c6c6c6; height: 5px; width: 110%; margin-left: -10px;}

	.excel-sheet-title {font-size: 20px; font-weight: bold; padding: 10px 0px;}
	.excel-sheet-pager {width: 110%; margin-left: -10px; margin-top: 10px; background-color: #c6c6c6; height: 10px;}
	/* xls sheet页尾部
	table.t1 {border-bottom: solid 5px #c6c6c6}*/
	/* xls 行号 */
	table.t1 thead{display: none;}
	/* xls 列号 */
	.rownumber {display: none;}
	/*table.t1:after {content: "\\r\\n\\r\\n"}
</style>

<body>
<div class='toolbar'>
	<img class='file-icon'/>
	<div class='file-name'>&nbsp;${fileName}</div>

	<img class='loading' src='${ROOT}/image/loading-fashion.gif'/>
	<img class='download' src='${ROOT}/image/download.png' onclick='javascript: download();' title='下载附件'/>
</div>

<div class='container'>
	<c:if test="${true != isPDF}"><div id='divContent'></div></c:if>
	<c:if test="${true == isPDF}"><div id='divPDFCanvas' style='width: 800px; margin: 5px auto;'></div></c:if>
</div>
</body>

<script>
var pdfPagesIndex = 1;

/**
 * String类添加endsWith方法
 */
String.prototype.endsWith = function(val)
{
	return (this.match(val + '$') == val);
}

/**
 * jQuery初始化
 */
$(function()
{
	$('.file-icon').attr('src', getFileIcon('${fileName}'));
	$(".container").mCustomScrollbar({scrollInertia: 300, autoHideScrollbar: true, theme: "light"});

	<c:if test="${true == isPDF}">
	// 1. PDF
	PDFJS.workerSrc = '${ROOT}/js/pdf.worker.js';
	var loadingTask = PDFJS.getDocument('${ROOT}/third/mongodb/${id}');
	loadingTask.promise.then(function(pdf)
	{
		// 初始化canvas
		var pages = ''
		for (var page = 1; page <= pdf.numPages; page++)
		{
			pages += '<canvas id="canvasPDF-' + page + '"></canvas>';
		}
		$('#divPDFCanvas').html(pages);

		// 逐页渲染
		renderPDFPage(pdf);
	},
	function (reason)
	{
		// PDF loading error
		console.error(reason);
		alert('PDF文件预览错误：' + reason);
	});
	</c:if>

	<c:if test="${true != isPDF}">
	// 2. Office
	$.post('${ROOT}/third/mongodb/preview/office/${id}?fileName=${fileName}', function(rsp)
	{
		$('.loading').hide();
		$('#divContent').show().html(rsp);

		// Excel：调整显示宽度
		if ('${fileName}'.endsWith('.xls') || '${fileName}'.endsWith('.xlsx') || '${fileName}'.endsWith('.csv'))
		{
			$('#divContent').width('98%');
		}

		// Docx：移除Margin边距
		if ('${fileName}'.endsWith('.docx'))
		{
			$('#divContent>div').css('margin', '0px');
		}
	});
	</c:if>
});

// 渲染
function renderPDFPage(pdf)
{
	if (pdfPagesIndex <= pdf.numPages)
	{
		pdf.getPage(pdfPagesIndex).then(function(page)
		{
			console.log('Page ' + pdfPagesIndex + ' loaded');

			var viewport = page.getViewport(1.3);
			var canvas = $('#canvasPDF-' + pdfPagesIndex)[0];
			canvas.height = viewport.height;
			canvas.width = viewport.width;

			// Render PDF page into canvas context
			var renderTask = page.render({canvasContext: canvas.getContext('2d'), viewport: viewport});
			renderTask.then(function()
			{
				console.log('Page ' + pdfPagesIndex + ' rendered');
				$('.loading').hide();

				pdfPagesIndex++;
				renderPDFPage(pdf);
			});
		});
	}
}

/**
 * 下载附件
 */
function download()
{
	location.href = '${ROOT}/third/mongodb/${id}?fileName=${fileName}';
}

/**
 * 根据文件后缀名转换对应的图标css名称
 */
function getFileIcon(fileName)
{
	var image = 'unknown-file.png';
	if (fileName)
	{
		var file = fileName.toLowerCase();
		if (file.endsWith('.doc') || file.endsWith('.docx') || file.endsWith('.rtf'))
		{
			image = 'word.png';
		}
		else if (file.endsWith('.xls') || file.endsWith('.xlsx') || file.endsWith('.csv'))
		{
			image =  'excel.png';
		}
		else if (file.endsWith('.ppt') || file.endsWith('.pptx'))
		{
			image = 'ppt.png';
		}
		else if (file.endsWith('.pdf'))
		{
			image = 'pdf-red.png';
		}
	}

	return '${ROOT}/image/' + image;
}
</script>

</html>