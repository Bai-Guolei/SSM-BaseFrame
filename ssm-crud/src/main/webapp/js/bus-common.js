$(function() {
	var $list = $("#thelist");
	var $btn = $("#ctlBtn");
	var state = 'pending'; // 上传文件初始化
	var uploader = WebUploader.create({
	 	swf : $AppContext + '/js/webuploader/Uploader.swf',
	 	server : $AppContext + '/base/file/upload',
		pick : '#picker',
		resize : false
	});
	uploader.on('fileQueued', function(file) {
		$list.append('<div id="' + file.id + '" class="item">'
				+ '<h4 class="info">' + file.name + '</h4>'
				+ '<p class="state">等待上传...</p>' + '</div>');
	});
 
	uploader.on('uploadProgress',
		function(file, percentage) {
			var $li = $('#' + file.id), $percent = $li
					.find('.progress .progress-bar');
 
						// 避免重复创建
			if (!$percent.length) {
				$percent = $(
						'<div class="progress progress-striped active">'
								+ '<div class="progress-bar" role="progressbar" style="width: 0%">'
								+ '</div>' + '</div>')
						.appendTo($li).find('.progress-bar');
						}
 
						$li.find('p.state').text('上传中');
 
						$percent.css('width', percentage * 100 + '%');
		});
		uploader.on('uploadSuccess', function(file) {
		$('#' + file.id).find('p.state').text('已上传');
	});
 
	uploader.on('uploadError', function(file) {
		$('#' + file.id).find('p.state').text('上传出错');
	});
 
	uploader.on('uploadComplete', function(file) {
		$('#' + file.id).find('.progress').fadeOut();
	});
	$btn.on('click', function() {
		if (state === 'uploading') {
			uploader.stop();
		} else {
			uploader.upload();
		}
	});
 
});


/**
 * 将表单序列化为JSON
 */
(function($){  
     $.fn.serializeJson=function(){  
         var serializeObj = {};  
         var array = this.serializeArray();  
         $(array).each(function(){
        	 var val = this.value;
        	 var input = $("input[name='" + this.name + "']"); 
        	 if(input.hasClass("fd-decimal4") || input.hasClass("fd-decimal2") || input.hasClass("fd-decimal"))
        	 {
        		val = val.replaceAll(',', ''); 
        	 }
             if(serializeObj[this.name]){  
                 if($.isArray(serializeObj[this.name])){  
                     serializeObj[this.name].push(val);  
                 }else{  
                     serializeObj[this.name] = [serializeObj[this.name], val];  
                 }  
             }else{  
                 serializeObj[this.name] = val;   
             }  
         });  
         return serializeObj;  
     }; 
     
     $.fn.serializeToString = function(){
    	 var serializeObj = {};  
         var array = this.serializeArray();  
         $(array).each(function(){  
        	 var val = this.value;
        	 var input = $("input[name='" + this.name + "']"); 
        	 if(input.hasClass("fd-decimal4") || input.hasClass("fd-decimal2") || input.hasClass("fd-decimal"))
        	 {
        		val = val.replaceAll(/,/g, ''); 
        	 }
             if(serializeObj[this.name]){  
                 if($.isArray(serializeObj[this.name])){  
                     serializeObj[this.name].push(val);  
                 }else{  
                     serializeObj[this.name] = [serializeObj[this.name], val];  
                 }  
             }else{  
                 serializeObj[this.name] = val;   
             }  
         });
         var serializeString = "";
         for(var o in serializeObj)
         {
        	 var val = serializeObj[o];
        	 if($.isArray(val))
        	 {
        		 val = val.join(",");
        	 }
        	 serializeString += (o + "=" + val + "&");
         }
         return encodeURI(serializeString);
     };
     
     $.extend($.validator.methods, { 
    	 min: function(value, element, param){
        	value = value.replaceAll(",", "");
        	return this.optional( element ) || value >= param;
    	 },
    	 max: function(value, element, param){
         	value = value.replaceAll(",", "");
         	return this.optional( element ) || value <= param;
     	 }
     });
})(jQuery);
