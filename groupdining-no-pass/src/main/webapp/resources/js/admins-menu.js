$(function(){
	/**初始化checkbox*/
	initCheckBox();
	$("#drop3").bind("click",btnLogOut);
	$("#btnDish").bind("click",initDishData);
	$("#btnToMenu").bind("click",toMenu);
	$("#btnSaveDish").bind("click",btnSaveDish);
	$("#btnAddToMenu").bind("click",btnAddToMenu);
	$("#btnSaveMenuInfo").bind("click",btnSaveMenuInfo);
	$("#toMyDish").bind("click",toMyDish);
	initMenuData();
	initDishData();
})
function toMyDish (){
	window.location.href = getContextPath()+"/";
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

function initMenuData(){
	var data = {
	
	}
	$.ajax({
        type: "post",
        url: getContextPath()+'/menu/getPage',
        data:data,
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(returnData && returnData.rows && returnData.rows.length > 0){
        		$("#adminsMenuTable").html("");
        		var tpl = "";
        		var data = returnData.rows;
        		var tpl = "";
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
						+'<td>'+obj.style+'</td>'
						+'<td>'+obj.discount+'</td>'
						+'<td>'+obj.description+'</td>'
						+'<td>'
						+'<a href="'+getContextPath()+'/menu/toMenuEdit2?menuId='+obj.id+'"><i class="icon-pencil"></i></a>'
						+'<a  href="javascript:;" data-id="'+obj.id+'" class="delMenu"><i class="icon-remove"></i></a>'
						+'</td>'
						+'</tr>';
				}
        		$("#adminsMenuTable").append(tpl);
        	}
        	$(".delMenu").bind("click",delMenu);
        }
    });
}
function delMenu(){
	var id = $(this).attr("data-id");
	$.ajax({
        type: "post",
        url: getContextPath()+'/menu/del',
        data:{id:id},
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		window.location.href = window.location.href
        	}
        }
    });
}

function initDishData(){
	var data = {
			
	}
	$.ajax({
		type: "post",
		url: getContextPath()+'/dish/getPage',
		data:data,
		dataType: "json",
		async:false,
		success: function(returnData){
			
			if(returnData && returnData.rows && returnData.rows.length > 0){
				$("#adminsTable").html("");
				var tpl = "";
				var data = returnData.rows;
				var tpl = "";
				for (var i = 0; i < data.length; i++) {
					var obj = data[i];
					var pic = getContextPath()+obj.pictrue;
					tpl += '<tr>'
						+'<td>'+i+'</td>'
						+'<td><img alt src="'+pic+'" style="width:50px;height:50px;"></img></td>'
						+'<td>'+obj.name+'</td>'
						+'<td>'+obj.price+'</td>'
						+'<td>'+obj.description+'</td>'
						+'<td>'
						+' <button class="btn btn-primary btnAddtoMenu"  data-id="'+obj.id+'">Add To Menu</button>'
						+'</td>'
						+'</tr>';
				}
				$("#adminsTable").append(tpl);
			}
			$(".btnAddtoMenu").bind("click",btnAddtoMenu);
		}
	});
}
function btnAddtoMenu(){
	var dishId = $(this).attr("data-id");
	var ids = getSelected();
	if(!ids){
		alert("请选择一个菜单进行添加!")
		return;
	}
	btnAddToMenu(ids,dishId);
	
}