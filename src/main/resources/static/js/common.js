var rootpath = "/jdwywechat";
layui.use(['form','laydate'], function(){
	 var form = layui.form;
	$.fn.setForm = function(jsonValue) {  
	    var obj=this;  
	    $.each(jsonValue, function (name, ival) { 
	        var $oinput = obj.find("input[name=" + name + "]");   
	        if ($oinput.attr("type")== "radio" || $oinput.attr("type")== "checkbox"){  
	             $oinput.each(function(){  
	                 if(Object.prototype.toString.apply(ival) == '[object Array]'){//是复选框，并且是数组  
	                      for(var i=0;i<ival.length;i++){  
	                          if($(this).val()==ival[i])  
	                             $(this).attr("checked", "checked");  
	                      }  
	                     
	                 }else{  
	                     if($(this).val()==ival)  
	                        $(this).attr("checked", "checked"); 
	                     form.render('radio');
	                 }  
	             });  
	        }else if($oinput.attr("type")== "textarea"){//多行文本框  
	            obj.find("[name="+name+"]").html(ival);  
	        }else{  
	             obj.find("[name="+name+"]").val(ival);   
	        }  
	        var $oselect = obj.find("select[name=" + name + "]"); 
	        if($oselect.length==1){
	        	console.log(ival);
	        	$oselect.find("option[value='"+ival+"']").attr("selected","selected");
	        	form.render('select');
	        }
	       
	   });  
	}
	
	var laydate=layui.laydate;
	$.fn.setDate = function(fristdate, currentdate, weekdate) {
		$.ajax({
			url : rootpath + "/system/init.do",// 跳转到 action
			data : {
				stime : (new Date()).getTime()
			},
			type : "post",
			cache : false,
			async: false,
			dataType : "json",
			success : function(data) {
				if (fristdate != '') {
						var strs = new Array();
						strs = fristdate.split("|");
						for ( var i = 0; i < strs.length; i++) {
							laydate.render({
				      			elem: '#'+fristdate,
				      			value: data.fristdate,
				      			isInitValue: true
				      		});
						}
					}
					
				if(currentdate != ''){
					var strs = new Array();
					strs = currentdate.split("|");
					for ( var i = 0; i < strs.length; i++) {
					
					  	laydate.render({
			    	   		elem: '#'+currentdate,
			    	   		value: new Date(data.currentdate), 
			    	   	 	isInitValue: true
			    	   	});
					}
				}
				
				if(weekdate != ''){
					var strs = new Array();
					strs = currentdate.split("|");
					for ( var i = 0; i < strs.length; i++) {
					  	laydate.render({
			    	   		elem: '#'+weekdate,
			    	   		value: new Date(data.weekdate), 
			    	   	 	isInitValue: true
			    	   	});
					}
			}
			},
			error : function() {
				alert("网络操作请求失败");
			}
		 
	
	}); 
	} 
})
 
function GetArgsFromHref(sArgName) {
	var sHref = document.URL;
	var args = sHref.split("?");
	var retval = "";
	if (args[0] == sHref)/* 参数为空 */
	{
		return retval; /* 无需做任何处理 */
	}
	var str = args[1];
	args = str.split("&");
	for ( var i = 0; i < args.length; i++) {
		str = args[i];
		var arg = str.split("=");
		if (arg.length <= 1)
			continue;
		if (arg[0] == sArgName)
			retval = arg[1];
	}
	return retval;
}

function getform() {
	var d={};
    var t = $('form').serializeArray();
    $.each(t, function() {
      d[this.name] = this.value;
    });
 		return JSON.stringify(d);
  	}
