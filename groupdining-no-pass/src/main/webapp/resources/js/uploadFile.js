$(function(){
	
})

function uploadSucced(msg){
	alert("上传成功！地址："+msg)
	$("#pictrue").val(msg);
	$("#dishImage").attr("src",getContextPath()+msg);
//	$("#firstUploadSucceed").html(msg);
//	$("#firstUploadSucceedMsg").show(msg);
//	$("#firstUploadFailedMsg").hide(msg);
}
function uploadFailed(msg){
	alert("上传失败！")
//	$("#firstUploadSucceed").html(msg);
//	$("#firstUploadSucceedMsg").hide(msg);
//	$("#firstUploadFailedMsg").show(msg);
}