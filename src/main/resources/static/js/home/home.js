$(function(){
})


//默认首页
//toHtml('/page/home/index');
function toHtml(obj){
    $('#contents').load(obj);
}

//注销
function logout(){
    localStorage.clear();
    location.replace(root+"/");
}