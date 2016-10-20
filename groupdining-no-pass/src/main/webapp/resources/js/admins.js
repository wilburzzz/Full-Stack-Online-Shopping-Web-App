$(function(){
	/**初始化checkbox*/
	initCheckBox();
	$("#drop3").bind("click",btnLogOut);
	$("#btnDish").bind("click",initDishData);
	$("#btnMenu").bind("click",toMenu);
	$("#btnAddDish").bind("click",btnAddDish);
	$("#btnSaveDish").bind("click",btnSaveDish);
	$("#btnAddToMenu").bind("click",btnAddToMenu);
	//initMenuData();
	initDishData();
})
function btnAddToMenu(){
	var ids = getSelecteds();
	if(!ids){
		return;
	}
	var data = {
			
	}
	$.ajax({
        type: "post",
        url: getContextPath()+'/menu/add',
        data:data,
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		window.location.href = getContextPath()+"/";
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
	window.location.href = getContextPath()+"/menu/toMenuView";
}
function btnAddDish(){
	window.location.href = getContextPath()+"/dish/addView";
//	window.location.href = getContextPath()+"/templates/user.html";
	
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
						+'<td style="text-align: center;padding-left:0;">'
						+'<input name="subBox" type="checkbox" data-id="'+obj.id+'">'
						+'</td>'
						+'<td>'+i+'</td>'
						+'<td><img alt src="'+pic+'" style="width:50px;height:50px;"></img></td>'
						+'<td>'+obj.name+'</td>'
						+'<td>'+obj.price+'</td>'
						+'<td>'+obj.description+'</td>'
						+'<td>'
						+'<a href="'+getContextPath()+'/dish/toDishEdit?id='+obj.id+'"><i class="icon-pencil"></i></a>'
						+'<a  href="javascript:;" data-id="'+obj.id+'" class="delDish"><i class="icon-remove"></i></a>'
						+'</td>'
						+'</tr>';
				}
				$("#adminsTable").append(tpl);
			}
			$(".delDish").bind("click",delDish);
			
		}
	});
}

function delDish(){
	var id = $(this).attr("data-id");
	$.ajax({
        type: "post",
        url: getContextPath()+'/dish/del',
        data:{id:id},
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		window.location.href = window.location.href;
        	}
        }
    });
}