var rootpath = "/wechat";
$(function() {
	var mid = GetArgsFromHref("menu_id");
/*	$.ajax({
		url : rootpath + "/system/menubtn.do",// 跳转到 action
		data : {
			parms : mid,
			stime : (new Date()).getTime()
		},
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data.status) {
				var la = null;
				var sa = null;
				for ( var i = 0; i < data.list.length; i++) {
					la = $("<a class='mini-button' style='margin-left:2px;margin-right:2px;' href='javascript:void(0)'></a>");
					$("#btnbar").append(la);
					sa = $("<span id='" + data.list[i].btn_id + "' class='mini-button-text  mini-button-icon " + data.list[i].btn_icon + "' onclick='" + data.list[i].btn_click + "'>"
							+ data.list[i].btn_label + "</span>");
					la.append(sa);
				}
			} else {
				alert(data.message);
			}
		},
		error : function() {
			mini.alert("网络操作请求失败");
		}
	});*/
});
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