
layui.define(['element', 'layer', 'form'], function (exports) {
	
	login();
	
    var form = layui.form;
    var $ = layui.jquery;
    //自定义验证
    form.verify({
        userpwd: [/^[\S]{4,12}$/, '密码必须4到12位'],
        userid: function (value) {
            if (value.length <= 0 || value.length > 10) {
                return "账号必须1到10位";
            }
            var reg = /^[a-zA-Z0-9]*$/;
            if (!reg.test(value)) {
                return "账号只能为英文或数字";
            }
        },
        randcode: function (value) {
            if (value.length < 4) {
                return '请输入4位验证码';
            }
        }
    });

    form.on('submit(login)', function (data) {

    	$.ajax({
    		url : "login/login",// 跳转到 action
    		data : {
    			parms :JSON.stringify(data.field),
    			stime : (new Date()).getTime()
    		},
    		type : "post",
    		cache : false,
    		dataType : "json",
    		success : function(data) {
    			if (data.status) {
    				layer.msg('登陆成功，正在跳转......', { icon: 6 });
    	            layer.closeAll('page');
    				location.href = "main.html";
    			} else {
    				layer.msg(data.message, { icon: 5 });
    				
    			}
    		},
    		error : function() {
    		
    			alert("网络操作请求失败");
    		}
    	});
  
        return false;
    });
	
    //检测键盘按下
    $('body').keydown(function (e) {
        if (e.keyCode == 13) {  //Enter键
            if ($('#layer-login').length <= 0) {
                login();
            } else {
                $('button[lay-filter=login]').click();
            }
        }
    });
    
    $(document).ready(function() {
    	$("#scode").attr("src", "../checkcode?" + Math.random());
    });

    $('.enter').on('click', login);
    
    //$('body').on('click', login);

    function login() {
        var loginHtml = ''; //静态页面只能拼接，这里可以用iFrame或者Ajax请求分部视图。html文件夹下有login.html

        loginHtml += '<form class="layui-form"  >';
        loginHtml += '<div class="layui-form-item">';
        loginHtml += '<label class="layui-form-label">账号</label>';
        loginHtml += '<div class="layui-input-inline pm-login-input">';
        loginHtml += '<input type="text" name="userid" lay-verify="userid" placeholder="请输入账号" value="" autocomplete="off" class="layui-input">';
        loginHtml += '</div>';
        loginHtml += '</div>';
        loginHtml += '<div class="layui-form-item">';
        loginHtml += '<label class="layui-form-label">密码</label>';
        loginHtml += '<div class="layui-input-inline pm-login-input">';
        loginHtml += '<input type="password" name="userpwd" lay-verify="userpwd" placeholder="请输入密码" value="" autocomplete="off" class="layui-input">';
        loginHtml += '</div>';
        loginHtml += '</div>';
        loginHtml += '<div class="layui-form-item">';
        loginHtml += '<label class="layui-form-label">验证码</label>';
        loginHtml += '<div class="layui-input-inline pm-login-input">';
        loginHtml += '<input type="text" name="randcode" lay-verify="randcode" placeholder="请输入验证码" value="" autocomplete="off" width="100px" class="layui-input" style="width: 60%;">';
        loginHtml += '<span id="yzm"><a class="fl sx" href="javascript:void(0)" onclick="t_rancode();return false;"><img id="scode" src="resources/images/yzm.png" style="margin-top: -38px;float:right;height: 38px;"/></a></span>';
        loginHtml += '</div>';
        loginHtml += '</div>';
        loginHtml += '<div class="layui-form-item" style="margin-top:25px;margin-bottom:0;">';
        loginHtml += '<div class="layui-input-block">';
        loginHtml += ' <button class="layui-btn" style="width:230px;" lay-submit="" lay-filter="login" >立即登录</button>';
        loginHtml += ' </div>';
        loginHtml += ' </div>';
        loginHtml += '</form>';

        layer.open({
            id: 'layer-login',
            type: 1,
            title: false,
            shade: 0.4,
            shadeClose: true,
            area: ['480px', '270px'],
            closeBtn: 0,
            anim: 1,
            skin: 'pm-layer-login',
            content: loginHtml
        });
        layui.form.render('checkbox');
        var $ = layui.jquery;
        $("#scode").attr("src", "../checkcode?" + Math.random());
    }

    exports('index', {});
});

