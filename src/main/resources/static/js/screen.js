//获取宽度
function getWidth()
{
    var strWidth,clientWidth,bodyWidth;
    clientWidth = document.documentElement.clientWidth;
    bodyWidth = document.body.clientWidth;
    if(bodyWidth > clientWidth){
        strWidth = bodyWidth;
    } else {
        strWidth = clientWidth;
    }
    return strWidth;
}
//获取高度
function getHeight()
{
    var strHeight,clientHeight,bodyHeight;
    clientHeight = document.documentElement.clientHeight;
    bodyHeight = document.body.clientHeight;
    if(bodyHeight > clientHeight){
        strHeight = bodyHeight;
    } else {
        strHeight = clientHeight;
    }
    return strHeight;
}
// 锁屏
function showScreen()
{
    var Msg = document.getElementById('Message');
    var Bg = document.getElementById('Screen');
    Bg.style.width = getWidth()+'px';
    Bg.style.height = getHeight()+'px';
    document.getElementById('Message').style.left=0+"px"; // alert(screen.height/2-240);
    document.getElementById('Message').style.top=150+"px";
    Msg.style.display = 'block';
    Bg.style.display = 'block';
}
//解屏
function hideScreen()
{
    var Msg = document.getElementById('Message');
    var Bg = document.getElementById('Screen');
    Msg.style.display = 'none';
    Bg.style.display = 'none';
}