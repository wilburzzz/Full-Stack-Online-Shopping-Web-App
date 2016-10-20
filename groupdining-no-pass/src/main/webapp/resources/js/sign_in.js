$(function(){
	$("#btnSign").bind("click",btnLogin);
	$("#btnSignUp").bind("click",btnSignUp);
})

function btnSignUp(){
	var url = getContextPath()+ '/toCustomerRegist';
	var userType = $("#userType").val();
	var data = {
		userType:userType
	}
	$("body").load(url,data);
}

function btnLogin(){
	var userName = $("#userName").val();
	var password = $("#password").val();
	var userType = $("#userType").val();
	if(!userName || !password){
		alert("用户名或者密码为空！")
		return;
	}
	var url = getContextPath()+'/admin/doLogin';
	var data = {
		userName:userName,password:password,userType:userType
	}
	/*$.ajax({
		type:"post",
		url: url,
        data:{userName:userName,password:password,userType:userType},
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		window.location.href = getContextPath()+ "/";
        	}
        }
	});*/
	$("body").load(url,data);
}