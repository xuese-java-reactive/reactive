$(function(){
    //默认首页
    toHtml('/page/home/right');
})

function toHtml(obj){
    $('#content-wrapper').load(obj);
}

//注销
function logout(){
    localStorage.clear();
    location.replace(root+"/");
}