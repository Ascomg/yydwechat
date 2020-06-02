layui.define(['element', 'layer', 'util', 'pagesize', 'form'], function (exports) {
    	var $ = layui.jquery;
    	var element = layui.element;
    	var layer = layui.layer;
	    var util = layui.util;
	    var form = layui.form;
	    
    showMenu();
	showFans();
	//获取公司名称，门店和当前登录人
	// $.ajax({
	// 	url : "../login/getData.do",// 跳转到 action
	// 	data : {
	// 		stime : (new Date()).getTime()
	// 	},
	// 	type : "post",
	// 	cache : false,
	// 	dataType : "json",
	// 	success : function(data) {
	// 		 document.getElementById('ht-user-name').innerHTML = '<i class="fa fa-user fa-fw"></i>'+data.userinfo;
	// 	}
	// });
	//
	function showFans() {
		$.ajax({
			url: "menu/getCount",// 跳转到 action
			data: {
				stime: (new Date()).getTime()
			},
			type: "get",
			cache: false,
			dataType: "json",
			success: function (data) {
				if (data.status) {
					var fansNew = echarts.init(document.getElementById('fansNew'));
					var option = {
						backgroundColor: '#FFFFFF',//背景色
						title: {
							text: '关注人数',
							textStyle: {
								fontSize: 14,
								fontStyle: 'normal',
								fontWeight: 'bold',
								fontFamily: 'sans-serif'
							},
						},
						tooltip: {
							show: true
						},
						grid: {       //直角坐标系内绘图网格
							left: '3%',
							right: '6%',
							bottom: '3%',
							containLabel: true
						},
						xAxis: [
							{
								type: 'category',
								axisLabel: {
									interval: 0
								},
								data: data.fansDate
							}
						],
						yAxis: [
							{
								type: 'value'
							}
						],
						series: [
							{
								"name": "人数",
								"type": "bar",
								"data": data.fansNew
							}
						]
					};
					fansNew.setOption(option);//最近七天关注



				} else {
					layer.msg(data.message);
				}
			},
			error: function () {
				layer.closeAll('loading');
				layer.msg("网络操作请求失败!");
			}
		});

	}

	// $.ajax({
	// 	url : "../system/getInfo.do",// 跳转到 action
	// 	type : "get",
	// 	cache : false,
	// 	dataType : "json",
	// 	success : function(data) {
	// 		var str='<div class="ht-box" style="width:20%;background-image:url(../resources/images/fansNew.png);float:left;"><p>'+data.fansNew+'</p><p>今日关注</p></div>'
	// 		+'<div class="ht-box" style="width:20%;background-image:url(../resources/images/masterNew.png);margin-right:30px;float:left"><p>'+data.masterNew+'</p><p>今日注册</p></div>'
    //         +'<div class="ht-box" style="width:20%;margin-right:30px;background-image:url(../resources/images/fansCount.png);float:left"><p>'+data.fansCount+'</p> <p>粉丝总数</p></div>'
    //         +'<div class="ht-box" style="width:20%;margin-right:30px;background-image:url(../resources/images/masterCount.png);float:left" ><p>'+data.masterCount+'</p><p>会员总数</p></div>';
	// 		 $("#nonediv").before(str);
	// 	}
	// });
	//
	function showMenu(){
		$.ajax({
			url : "menu/menu",//
			data : {

				stime : (new Date()).getTime()
			},
			type : "get",
			cache : false,
			dataType : "json",
			success : function(data) {
				var li="";
				for(var i in data){
					li+=' <li class="layui-nav-item "><a href="javascript:;">'+data[i].menuSname+'</a>';
		                if(data[i].menuList.length>0){
		                	li+=' <dl class="layui-nav-child">';
		                	var child=data[i].menuList;
		                	for(var j in child ){
		                		li+=' <dd><a href="javascript:;" data-url="menu/showmenu"  data-id='+child[j].menuId+'>'+child[j].menuSname+'</a></dd>';
		                	}
		                	li+="</dl>";
		                }
		            	li+='</li>';
				}
				$("#leftmenu").append(li);
				element.init("nav","leftnav");
			}
		});

	}




    //个性化设置开关
    $('#individuation').click(function (e) {
        e.stopPropagation();    //阻止事件冒泡
        $('.individuation').removeClass('layui-hide').toggleClass('bounceInRight').toggleClass('flipOutY');
    });
    $('.individuation').click(function (e) {
        e.stopPropagation();    //阻止事件冒泡
    })
    $('.layui-body').click(function () {
        $('.individuation').removeClass('bounceInRight').addClass('flipOutY');
    });
    
    
    /**
    * 注册tab右键菜单点击事件
    */
    $(".rightmenu li").click(function () {
        if ($(this).attr("data-type") == "closethis") {		//关闭当前
            var tabid = $("li[class='layui-this']").attr('lay-id');// 获取当前激活的选项卡ID
            tabDelete(tabid);
        } else if ($(this).attr("data-type") == "closeall") {  //关闭所有
            var tabtitle = $(".layui-tab-title li");
            var ids = new Array();
            $.each(tabtitle, function (i) {
                ids[i] = $(this).attr("lay-id");
            })
            tabDeleteAll(ids);
        }else if($(this).attr("data-type") == "closeother"){	//关闭其它
        	var tabid = $("li[class='layui-this']").attr('lay-id');
        	var tabtitles = $(".layui-tab-title li");
            var tableids = new Array();
            $.each(tabtitles, function (j) {
            	if($(this).attr("lay-id") != tabid){
            		tableids[j] = $(this).attr("lay-id");
            	}
            })
            tabDeleteOther(tableids);
        }
        
        $('.rightmenu').hide();
        $(".layui-tab-content").css("top","35px");
    })
    
    tabDelete = function (id) {
		if(id != 0){
    		element.tabDelete("tab", id);
    	}
	};
	tabDeleteAll = function (ids) {
	    $.each(ids, function (i, item) {
	    	if(item != 0){
	    		element.tabDelete("tab", item);
	    		element.tabChange("tab", 0);
	    	}
	    })
	};
	tabDeleteOther = function (ids) {
	    $.each(ids, function (i, item) {
	    	if(item != 0){
	    		element.tabDelete("tab", item);
	    	}
	    })
	};
    //
    //监听左侧导航点击
    element.on('nav(leftnav)', function (elem) {

        var url = $(elem).attr('data-url');   //页面url
        var id = $(elem).attr('data-id');     //tab唯一Id
        var title = $(elem).text();
        //layer.msg(id);
         //菜单名称
        if (title == "首页") {
            element.tabChange('tab', 0);
            return;
        }
        if (url == undefined) return;

        var tabTitleDiv = $('.layui-tab[lay-filter=\'tab\']').children('.layui-tab-title');
        var exist = tabTitleDiv.find('li[lay-id=' + id + ']');
        if (exist.length > 0) {
            //切换到指定索引的卡片
            element.tabChange('tab', id);
        } else {
            var index = layer.load(1);
            //由于Ajax调用本地静态页面存在跨域问题，这里用iframe
            setTimeout(function () {
                //模拟菜单加载
                layer.close(index);
                element.tabAdd('tab', { title: title, content: '<iframe src="' + url +'?menu_id='+id+ '" style="width:100%;height:100%;border:none;outline:none;"></iframe>', id: id });
                //切换到指定索引的卡片
                element.tabChange('tab', id);
            }, 500);
        }
    });
    
    //监听tab选项卡
    element.on("tab(tab)",function(data){
    	CustomRightClick();
    });
    
    

    //监听侧边导航开关
    form.on('switch(sidenav)', function (data) {
        if (data.elem.checked) {
            showSideNav();
        } else {
            hideSideNav();
        }
    });

    //收起侧边导航点击事件
    $('.layui-side-hide').click(function () {
        hideSideNav();
        $('input[lay-filter=sidenav]').siblings('.layui-form-switch').removeClass('layui-form-onswitch');
        $('input[lay-filter=sidenav]').prop("checked", false);
    });

    //鼠标靠左展开侧边导航
    $(document).mousemove(function (e) {
        if (e.pageX == 0) {
            showSideNav();
            $('input[lay-filter=sidenav]').siblings('.layui-form-switch').addClass('layui-form-onswitch');
            $('input[lay-filter=sidenav]').prop("checked", true);
        }
    });
    
    
    //皮肤切换
    $('.skin').click(function () {
        var skin = $(this).attr("data-skin");
        $('body').removeClass();
        $('body').addClass(skin);
    });

    var ishide = false;
    //隐藏侧边导航
    function hideSideNav() {
        if (!ishide) {
            $('.layui-side').animate({ left: '-200px' });
            $('.layui-side-hide').animate({ left: '-200px' });
            $('.layui-body').animate({ left: '0px' });
            $('.layui-footer').animate({ left: '0px' });
            var tishi = layer.msg('鼠标靠左自动显示菜单', { time: 1500 });
            layer.style(tishi, {
                top: 'auto',
                bottom: '50px'
            });
            ishide = true;
        }
    }
    //显示侧边导航
    function showSideNav() {
    
        if (ishide) {
            $('.layui-side').animate({ left: '0px' });
            $('.layui-side-hide').animate({ left: '0px' });
            $('.layui-body').animate({ left: '200px' });
            $('.layui-footer').animate({ left: '200px' });
            ishide = false;
        }
    }
    
    function CustomRightClick () {
        //屏蔽右键
        $('.layui-tab-title li').on('contextmenu', function () {
            return false;
        })
        $('.layui-tab-title').click(function () {
            $('.rightmenu').hide();
        });
        $('.layui-tab-title li').on('contextmenu', function (e) {
            var popupmenu = $(".rightmenu");
            var c = $(this).position().left-5;
            var d = $(".layui-tab-title").height()+5;
            popupmenu.css({left: c, top: d}).show();
            return false;
        });
    }
    
   
    exports('main', {});
});
