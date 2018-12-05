$(function() {
        $("#login_btn").click(function(){
                $.ajax({
                        type : 'POST',
                        url : "/singleDoLogin",
                        data: {loginid:$("#user-name").val(),password:$("#password").val()},
                        dataType : 'json',
                        success : function(res){
                                console.log("请求后的返回",res);
                                if(res.success == true){
                                    location.href = "main.html";
                                }
                        },
                        error : function(XMLHttpRequest, textStatus, errorThrown) {
                        
                        }
                });
        })
});

