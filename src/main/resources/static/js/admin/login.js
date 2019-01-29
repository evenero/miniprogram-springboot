/*刷新图片*/
function changeImg(id){
	var imgSrc = $("#"+id);
  var src = imgSrc.attr("src");
  imgSrc.attr("src", changeUrl(src));
};
/*为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳*/
function changeUrl(url) {
    var timestamp = (new Date()).valueOf();
    var index = url.indexOf("&",url);
    if (index > 0) {
        url = url.substring(index, url.indexOf(url, "&"));
    }
    if ((url.indexOf("&") >= 0)) {
        url = url + "&×tamp=" + timestamp;
    } else {
        url = url + "&timestamp=" + timestamp;
    }
    return url;
};
function toLogin(){
	var uuid = $("#uuid").val();
	var imgvalid = $("#code").val();
	var account = $("#userName").val();
	var password = $("#password").val();
	var hashdPassword = hex_md5(password);
	if(imgvalid.trim()==""){
		alert("请输入图形验证码！");
		return false;
	}else if(account.trim()==""){
		alert("请输入账号！");
		return false;
	}else if(password.trim()==""){
		alert("请输入密码！");
		return false;
	}else{
		$.post('/admin/login',{
			account:account,
			password:hashdPassword,
			uuid:uuid,
			imgvalid:imgvalid
		},function(msg){
			var data = eval("("+msg+")");
			if(data.ret_code==0){
				window.location.href = "/admin/index";
			}else{
				alert(data.ret_info);
			}
		});
	}
};