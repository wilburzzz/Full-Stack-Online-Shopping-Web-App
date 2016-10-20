$(function(){
	/**初始化checkbox*/
	initCheckBox();
	initFormData($("#menuId").val());
	//initDishData($("#menuId").val());
	//initDishData();
})
function initDishData(dishData){
		$("#adminsTable").html("");
		var tpl = "";
		var data = dishData;
		var tpl = "";
		for (var i = 0; i < data.length; i++) {
			var obj = data[i];
			var pic = getContextPath()+obj.pictrue;
			tpl += '<tr>'
				+'<td>'+i+'</td>'
				+'<td><img alt src="'+(getContextPath()+obj.pictrue)+'" style="width:50px;height:50px;"></img></td>'
				+'<td>'+obj.name+'</td>'
				+'<td>'+obj.price+'</td>'
				+'<td>'+obj.description+'</td>'
				+'<td>'
				+'<a href="#myModal" role="button" data-toggle="modal"><i class="icon-remove"></i></a>'
				+'</td>'
				+'</tr>';
		}
		$("#adminsTable").append(tpl);
}
function initFormData(menuId){
	var data = {
		menuId : menuId
	}
	$.ajax({
        type: "post",
        url: getContextPath()+'/menu/get',
        data:data,
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(returnData){
        		var data = returnData.menuData;
        		$("#name").val(data.name);
        		$("#price").val(data.price);
        		$("#pictrue").val(data.pictrue);
        		$("#discount").val(data.discount);
        		$("#style").val(data.style);
        		$("#discription").text(data.discription);
        		initDishData(returnData.dishData);
        	}
        }
    });
	
}
function btnSaveMenuInfo(){
	$.ajax({
        type: "post",
        url: getContextPath()+'/menu/add',
        data:$("#tab").serialize(),
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		window.location.href = getContextPath()+"/menu/toMenuView";
        	}
        }
    });
}
function btnAddToMenu(menuId,dishId){
	var data = {
			dishId:dishId,
			menuId:menuId
	}
	$.ajax({
        type: "post",
        url: getContextPath()+'/menu/addDishToMenu',
        data:data,
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		window.location.href=window.location.href;
        	}
        }
    });
}

function btnSaveDish(){
	$.ajax({
        type: "post",
        url: getContextPath()+'/dish/add',
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
function toMenu(){
	window.location.href = getContextPath()+"/menu/toMenuEdit";
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
