var rootpath = "/zrwechat";
function init(stype, fristdate, currentdate, spare) {
	$.ajax({
		url : rootpath + "/system/init.do",// 跳转到 action
		data : {
			
			stime : (new Date()).getTime()
		},
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (stype == 'date') {
				if (fristdate != '') {
					var strs = new Array();
					strs = fristdate.split("|");
					for ( var i = 0; i < strs.length; i++) {
					
						if(i==1){
							mini.get(strs[i]).setValue(data.fristdate);
						}else{
							mini.get(strs[i]).setValue(data.weekdate);
						}
						
					}
				}
				
				if(currentdate != ''){
					var strs = new Array();
					strs = currentdate.split("|");
					for ( var i = 0; i < strs.length; i++) {
						mini.get(strs[i]).setValue(data.currentdate);
					}
				}
			}
		},
		error : function() {
			mini.alert("网络操作请求失败");
		}
	});
}

function miniopen(vurl, vtitle, sid, action, width, height) {// 打开弹出窗，url地址，显示的标题，主键，打开方式:ａｄｄ，ｅｄｉｔ，ｖｉｅｗ
	if (isNull(width)) {
		width = 600;
	}
	if (isNull(height)) {
		height = 400;
	}
	mini.open({
		url : vurl,
		title : vtitle,
		width : width,
		height : height,
		onload : function() {
			var iframe = this.getIFrameEl();
			var data = {
				action : action,
				sid : sid
			};
			iframe.contentWindow.SetData(data);
		},
		ondestroy : function(action) {
			if (action != "close") {// 子窗口不是以cancel关闭的执行查询
				search_option(1);
			}
		}
	});
}
function miniopen1(vurl, vtitle, width, height) {
	if (isNull(width)) {
		width = 600;
	}
	if (isNull(height)) {
		height = 400;
	}
	mini.open({
		url : vurl,
		title : vtitle,
		width : width,
		height : height,
		ondestroy : function(action) {
			if (action != "close") {
				search_option(1);
			}
		}
	});
}

function cwinit(stype, fristdate, currentdate, spare) {
	$.ajax({
		url : rootpath + "/system/cwinit.do",// 跳转到 action
		data : {
			// parms : mid,
			stime : (new Date()).getTime()
		},
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (stype == 'date') {
				if (fristdate != '') {
					var strs = new Array();
					strs = fristdate.split("|");
					for ( var i = 0; i < strs.length; i++) {
						mini.get(strs[i]).setValue(data.fristdate);
					}
				}
				//spare值的格式(权限部门|显示成本(级别)|导出权限)
				if (spare != '') {
					var strs = new Array();
					strs = spare.split("|");
					mini.get(strs[0]).setValue(data.authdept);
					if(strs[1]!=''&&strs[1]!=null){
						mini.get(strs[1]).setValue(data.dqzq);
					}
					if(strs[2]!=''&&strs[2]!=null){
						mini.get(strs[2]).setValue(data.authprint);
					}
				}
				if(currentdate != ''){
					var strs = new Array();
					strs = currentdate.split("|");
					for ( var i = 0; i < strs.length; i++) {
						mini.get(strs[i]).setValue(data.currentdate);
					}
				}
			}
		},
		error : function() {
			mini.alert("网络操作请求失败");
		}
	});
}

// 根据id判断值是否为空
function isminiNull(key) {
	if (mini.get(key).getValue() == null || mini.get(key).getValue() == undefined || mini.get(key).getValue() == "") {
		return true;
	}
	return false;
}
// 判读是否为空
function isNull(key) {
	if (key == null || key == undefined || key == "") {
		return true;
	}
	return false;
}
// 设置form只读
function setFormReadOnly(form) {
	var fields = form.getFields();
	for ( var i = 0, l = fields.length; i < l; i++) {
		fields[i].setReadOnly(true);
	}
}
// 设置id集合只读
function setIdReadOnly(obj) {
	var array = obj.split(",");
	for ( var i = 0; i < array.length; i++) {
		mini.get(array[i]).setReadOnly(true);
	}
}
// 设置id不可编辑
function setIdDisable(obj) {
	var array = obj.split(",");
	for ( var i = 0; i < array.length; i++) {
		mini.get(array[i]).disable();
	}
}
// 设置id可编辑
function setIdEnable(obj) {
	var array = obj.split(",");
	for ( var i = 0; i < array.length; i++) {
		mini.get(array[i]).enable();
	}
}
//清空
function clearValue(obj) {
	var array = obj.split(",");
	for ( var i = 0; i < array.length; i++) {
		mini.get(array[i]).setValue("");
	}
}
function getMiniValue(key, defaultValue) {
	if (key == null || key == undefined || key == "") {
		return defaultValue;
	}
	return key;
}
// 这个还是有用的.用于转换Fri May 01 2015 00:00:00 GMT+0800格式的日期
function getMiniDate(key) {
	return mini.formatDate(new Date(key), "yyyy-MM-dd");
}
// 用于基础资料,当手动输入的时候,内码设置为0,显示值不变,一般用于查询条件中.在写的SQL中,可先判断内码是否为0,不为0,根据内码查找,为0,可以根据名字匹配.
// 注意id="keyid" name="keyid" textName="keyid_name" class="mini-buttonedit"
// onblur="onblurBaseField" onbuttonclick="onButtonEdit"
// 如果输出的只有数字,此时想把内码置零,根据显示值查询,比如品号.
function onblurBaseField(e) {
	if (isNaN(e.sender.value)) {
		e.sender.value = 0;
	}
}

function GetArgsFromHref(sArgName) {
	var sHref = document.URL;
	var args = sHref.split("?");
	var retval = "";
	if (args[0] == sHref)
	{
		return retval; 
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
