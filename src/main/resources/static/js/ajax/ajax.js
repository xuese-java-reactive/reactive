$(document).ajaxSend(function(event, jqxhr, settings) {
  jqxhr.setRequestHeader('auth', localStorage.getItem("auth"))
})
$(document).ajaxSuccess(function(event,xhr,options){
    let t = xhr.getResponseHeader('auth');
    if(t != null){
        localStorage.setItem("auth",t);
    }
});

//$(document).ajaxError(function(event,xhr,options,exc){
//    if(xhr.status == 'undefined'){
//        return;
//    }
//});

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};