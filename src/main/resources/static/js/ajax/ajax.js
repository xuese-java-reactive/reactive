$(document).ajaxSend(function(event, jqxhr, settings) {
    if(localStorage.getItem("auth") != null){
        jqxhr.setRequestHeader('auth', localStorage.getItem("auth"))
    }
})
$(document).ajaxSuccess(function(event,xhr,options){
    let t = xhr.getResponseHeader('auth');
    console.log(t)
    if(t != null){
        localStorage.setItem("auth",t);
    }
});
