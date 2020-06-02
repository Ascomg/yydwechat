
function loadScript(url) {
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = url;
    document.body.appendChild(script);
}

$(function(){
	// 导航栏tab
	$(".foot_tab li").click(function(){
		$(this).find(".add_active").show().siblings(".rem_active").hide().parents("li").siblings("li").find(".add_active").hide().siblings(".rem_active").show();
		$(this).addClass("tab_active").siblings().removeClass("tab_active");
		var n=$(this).index();
		$(".member_max:eq("+n+")").show().siblings(".member_max").hide();
	})
	// 商品详情页
	
	function hisc(){
		if($(".detai_spebg").is(":visible")){
			document.documentElement.style.overflow = "hidden";
			console.log("显示")
		}else{
			document.documentElement.style.overflow = "scroll";
			console.log("隐藏")
		}
	}
	
	$(".detai_spebg").hide();
	$(".detailed_number").click(function(){
		$(".detai_spebg").show();
		hisc();
		//$(".go_shop").attr("onclick","shop();");	这样写在chrome是正常的，在手机端是不正常的
		$(".go_shop").click(function(){
			shop();
			hisc();
		});
		//$(".join_car").attr("onclick","shop_car();");
		$(".join_car").click(function(){
			shop_car();
			hisc();
		});
		
	})
	
	$(".go_shop").click(function(){
		$(".detai_spebg").show();
		hisc();
		$(".detai_submit").attr("buttype","shop");
		
	})
	
	$(".join_car").click(function(){
		$(".detai_spebg").show();
		hisc();
		$(".detai_submit").attr("buttype","cart");
	})
	
	
	$(".spe_remove").click(function(){
		$(this).parents(".detai_spebg").hide();
		/*$(".go_shop").unbind("click");//接触绑定，再添加新的绑定
		$(".join_car").unbind("click");*/
		hisc();
		/*$(".go_shop,.join_car").click(function(){
			$(".detai_spebg").show();
			$(".go_shop").click(function(){
				shop();
				hisc();
			});
			$(".join_car").click(function(){
				shop_car();
				hisc();
			});
		});*/
	})
	

	// 规格选择
	$(".detai_dl").on("click","dd",function(){
		// $(this).addClass("detai_active").siblings("dd").removeClass("detai_active");
		if($(this).hasClass("detai_active")){
			$(this).removeClass("detai_active").siblings("dd").removeClass("detai_active");
		}else{
			$(this).addClass("detai_active").siblings("dd").removeClass("detai_active");
		}
	})

	// 我的订单
	$(".order_all:not(:first)").hide();
	$(".order_tabul li").click(function(){
		$(this).addClass("order_tabactive").siblings("li").removeClass("order_tabactive");
		var i = $(this).index();
		$(".order_all:eq("+i+")").show().siblings(".order_all").hide();
	})
	// 消费记录
	$(".consume_list:not(:first)").hide();
	$(".consume_tabul li").click(function(){
		$(this).addClass("consume_active").siblings("li").removeClass("consume_active");
		var c = $(this).index();
		$(".consume_list:eq("+c+")").show().siblings(".consume_list").hide();
	})

	
	// 积分记录
	$(".scoring_list:not(:first)").hide();
	$(".scoring_tabul li").click(function(){
		$(this).addClass("scoring_active").siblings("li").removeClass("scoring_active");
		var s=$(this).index();
		$(".scoring_list:eq("+s+")").show().siblings(".scoring_list").hide();
	})
	// 订单详情
	$(".fill_psval").change(function(){
		if($("option:checked").attr("value")=="到店自取"){
			$(".fill_address").show();
		}else{
			$(".fill_address").hide();
		}
	})
	
	// 门店详情
	$(".stolist_ul li:eq(3)").nextAll("li").hide();
	$(".storelist_go a").click(function(){
		$(".stolist_ul li:eq(3)").nextAll("li").slideToggle();

		var stolicon = $(this).find("span").hasClass("icon-xiangxia");
		if(stolicon==true){
			$(this).html('收起<span class="iconfont icon-xiangxia"></span>')
			$(this).find("span").removeClass("icon-xiangxia").addClass("icon-xiangshang");
			
		}else{
			$(this).html('查看剩余门店<span class="iconfont icon-xiangxia"></span>')
			$(this).find("span").removeClass("icon-xiangshang").addClass("icon-xiangxia");

		}
	})
	// 收货地址
	$(".address_remove").click(function(){
		$(this).parents("li").remove();
	})
	// 拼团详情
	$(".coninfo_openlist li:eq(2)").nextAll("li").hide();
	$(".coninfo_all a").click(function(){
		$(".coninfo_openlist li:eq(2)").nextAll("li").slideToggle();

		var stolicon = $(this).find("span").hasClass("icon-xiangxia");
		if(stolicon==true){
			$(this).html('收起<span class="iconfont icon-xiangxia"></span>')
			$(this).find("span").removeClass("icon-xiangxia").addClass("icon-xiangshang");
			
		}else{
			$(this).html('查看更多<span class="iconfont icon-xiangxia"></span>')
			$(this).find("span").removeClass("icon-xiangshang").addClass("icon-xiangxia");

		}
	})
	// 抢购中
	
	$(".panic_per b").each(function(){
		var panic_val=$(this).attr("data-value");
		if(panic_val == 1){
			$(this).css("width","10%");
		}
		if(panic_val == 2){
			$(this).css("width","20%");
		}
		if(panic_val == 3){
			$(this).css("width","30%");
		}
		if(panic_val == 4){
			$(this).css("width","40%");
		}
		if(panic_val == 5){
			$(this).css("width","50%");
		}
		if(panic_val == 6){
			$(this).css("width","60%");
		}
		if(panic_val == 7){
			$(this).css("width","70%");
		}
		if(panic_val == 8){
			$(this).css("width","80%");
		}
		if(panic_val == 9){
			$(this).css("width","90%");
		}
		if(panic_val == 10){
			$(this).css("width","100%");
		}
	})
	// 拼团中
	var regiimg_size = $(".regi_imgul li").size();
	// console.log(regiimg_size);
	if(regiimg_size == 3){
		$(".regi_imgul").css({"width":"4.8rem"})
	}
	if(regiimg_size == 4){
		$(".regi_imgul").css({"width":"6.4rem"})
	}
	if(regiimg_size == 5){
		$(".regi_imgul").css({"width":"8.5rem"})
	}
	// 优惠券
	
	 $(".coupon_tkbg").on("click",function(){
			$(".coupon_tkbg").hide();
	 })
	 $(".coupon_tkbg").hide();
	
	
})