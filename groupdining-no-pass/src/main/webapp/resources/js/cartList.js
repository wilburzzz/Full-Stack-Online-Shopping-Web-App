$(function(){
	/**初始化checkbox*/
	initCheckBox();
	$("#drop3").bind("click",btnLogOut);
	$("#settleMeny").bind("click",settleMeny);
	initData();
})

function settleMeny(){
	var data ={
	   userId:$("#userId").val()
	}
	$.ajax({
        type: "post",
        url: getContextPath()+'/order/saveOrder',
        data:data,
        dataType: "json",
        async:false,
        success: function(returnData){
        	deal(returnData)
        	if(ajaxData(returnData)){
        		alert("Pay Sucess!");
        	}
        }
    });
}

function btnLogOut(){
	$.ajax({
        type: "post",
        url: getContextPath()+'/user/logOut',
        data:{},
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		window.location.href = getContextPath()+"/";
        	}
        }
    });
}

function initData(){
	var data = {
			userId:$("#userId").val()
	}
	$.ajax({
		type: "post",
		url: getContextPath()+'/menu/getListByCart',
		data:data,
		dataType: "json",
		async:false,
		success: function(returnData){
			$("#adminsTable").html("");
			//if(returnData && returnData.length > 0){
				var tpl = "";
//				for (var i = 0; i < data.length; i++) {
				if(returnData && returnData.length > 0){
					var data = returnData;
					for (var i = 0; i < data.length; i++) {
					var obj = data[i];
					var pic = getContextPath()+obj.pictrue;
					tpl += '<tr>'
						+'<td style="text-align: center;padding-left:0;">'
						+'<input name="subBox" type="checkbox" data-id="'+obj.id+'">'
						+'</td>'
						+'<td>'+i+'</td>'
						+'<td><img alt src="'+pic+'" style="width:50px;height:50px;"></img></td>'
						+'<td>'+obj.name+'</td>'
						+'<td>'+obj.price+'</td>'
						+'<td>'+obj.description+'</td>'
						+'<td>'
						//+'<a href="user.html"><i class="icon-pencil"></i></a>'
						+'<a  href="javascript:;" data-id="'+obj.id+'" class="delCartItem"><i class="icon-remove"></i></a>'
						+'</td>'
						+'</tr>';
				}
				$("#adminsTable").append(tpl);
			}
			//}
				$(".delCartItem").bind("click",delCartItem);
		}
	});
}
function delCartItem(){
	var menuId = $(this).attr("data-id");
	$.ajax({
        type: "post",
        url: getContextPath()+'/cart/delMenu',
        data:{menuId:menuId},
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		window.location.href = window.location.href;
        	}
        }
    });
}