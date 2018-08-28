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
