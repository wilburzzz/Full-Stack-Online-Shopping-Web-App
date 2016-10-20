$(function(){
	$("#btnSignUpAdmin").bind("click",btnSignUpUser);
})

function btnSignUpUser(){
	var name = $("#name").val();
	var password = $("#password").val();
	var confirmPassword = $("#confirmPassword").val();
	var email = $("#email").val();
	var style = $("#style").val();
	
	if(!name || !password){
		alert("用户名或者密码为空！")
		return;
	}
	if(!confirmPassword || !email){
		alert("确认密码或邮箱为空!")
		return;
	}
	if(password != confirmPassword){
		alert("确认密码有误!");
		return;
	}
	if(password.length < 6){
		alert("长度不得小于6！")
		return;
	}
	var url = getContextPath()+'/registRestaurant';
	var data = {
		name:name,password:password,email:email,style:style
	}
	$.ajax({
		type:"post",
		url: url,
        data:data,
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		var url = getContextPath()+ "/";
        		$("body").load(url);
        	}
        }
	});
}