/*载入页面加载图*/
function loading(){
	var width = $(document).width();
	var height = $(document).height();
	var dom = $("<div id='loading'>"+
			"<div class='overlay' style='position: absolute;top: 0px;left: 0px;z-index: 10001;background-color: #777;opacity: 0.1;-moz-opacity: 0.5;width: "+width+"px;height: "+height+"px;'></div>"+
			"<div id='loader' style='z-index: 10002;width: 40%; height: 32px;position: absolute;left: 30%;top: 45%;text-align: center;'>"+
			"<img src='/images/loading.gif' style='width:32px;height: 32px;'>"+
			"</div></div>");
	$(document.body).append(dom);
}
/*解除页面加载*/
function unloading(){
	var div = $("#loading");
	if(div.length > 0){
		$("#loading").remove();
	}else{ //遮罩层不存在，不处理
		/*alert("不存在");*/
		return false;
	}
}
/*跳转页面方法，带菊花图*/
function toPage(url){
	loading();
	setTimeout(function(){
		window.location.href=url;
	},200);
}





/*进页面加载方法*/
$(document).ready(function(){
	unloading();
});

/*页面每次加载，判断是否有缓存，有缓存就刷新*/
window.onpageshow = function(event){
    //event.persisted 判断浏览器是否有缓存, 有为true, 没有为false
    if (event.persisted) {
        window.location.reload();
    }
}