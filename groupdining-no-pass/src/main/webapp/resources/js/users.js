$(function(){
	$("#drop3").bind("click",btnLogOut);
	initData();
})

function btnLogOut(){
	$.ajax({
        type: "post",
        url: getContextPath()+'/admin/logOut',
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
	
	}
	$.ajax({
        type: "post",
        url: getContextPath()+'/user/getPage',
        data:data,
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(returnData && returnData.rows && returnData.rows.length > 0){
        		var tpl = "";
        		var data = returnData.rows;
        		var tpl = "";
        		for (var i = 0; i < data.length; i++) {
					var obj = data[i];
					tpl += '<tr>'
						+'<td>1</td>'
				          +'<td>Mark</td>'
				          +'<td>Tompson</td>'
				          +'<td>the_mark7</td>'
				          +'<td>'
				          +'<a href="user.html"><i class="icon-pencil"></i></a>'
				              +'<a href="#myModal" role="button" data-toggle="modal"><i class="icon-remove"></i></a>'
				              +'</td>'
				        +'</tr>';
				}
        		$("#usersTable").html("");
        		$("#usersTable").append(tpl);
        	}else{
        		$("#usersTable").html("");
        	}
        }
    });
}