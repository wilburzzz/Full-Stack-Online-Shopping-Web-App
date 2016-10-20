$(function (){
	initFormData($("#dishId").val());
	$("#btnModifyDish").bind("click",btnModifyDish);
})

function initFormData(dishId){
	$.ajax({
        type: "post",
        url: getContextPath()+'/dish/get',
        data:{id:dishId},
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(returnData){
        		$("#name").val(returnData.name);
        		$("#price").val(returnData.price);
        		$("#pictrue").val(returnData.pictrue);
        		$("#discription").text(returnData.discription);
        	}
        }
    });
	
}

function btnModifyDish(){
	$.ajax({
        type: "post",
        url: getContextPath()+'/dish/modify',
        data:$("#tab").serialize(),
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		window.location.href = getContextPath()+"/";
        	}
        }
    });
}