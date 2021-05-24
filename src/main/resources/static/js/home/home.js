$(function(){
    //默认首页
    toHtml('home/right');
})

function toHtml(obj){
    $('#content-wrapper').load('/page/'+obj);
}

//注销
function logout(){
    localStorage.clear();
    location.replace(root+"/");
}