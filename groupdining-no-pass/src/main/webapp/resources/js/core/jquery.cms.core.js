(function($){
	/**返回数据统一处理*/
	$.ajaxData = function(data) {
		if(data.success) return true;
		else if(data.message){
			$.fn.alert(data.message);
		}else return true;
	}
	$.fn.alert = function(message) {
		/*if ($("#myModalx").length > 0){ 
			$("#myModalx").remove();
		}
		var str = "";
		/////////////////////////
		str = '<div class="dialog modal fade in" id="myModalx" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="display: block;"'+
				'aria-hidden="false">'+
				'<div class="modal-dialog" style=" width: 30%;">'+
					'<div class="modal-content" style=" min-height: 69px;">'+
						'<div class="modal-header">'+
							'<button class="close" type="button" data-dismiss="modal" aria-label="Close">×</button>'+
							'<h4 class="modal-title">系统提示！</h4>'+
						'</div>'+
						'<div class="modal-body" style=" min-height: 50px;">'+
							'<div>'+message+'</div>'+
						'</div>'+
						'<div class="modal-footer">'+
							'<button onclick="buttonDelete()" class="btn btn-default" type="button">关闭</button>'+
						'</div>'+
					'</div>'+
				'</div>'+
			'</div>'+
			'<script>'+
				'function buttonDelete(){'+
					'$("#myModalx").remove();'+
				'}'+
			'</script>';
		////////////////////////
		$('body').append(str);
		$("#myModalx").modal('show');*/
		//layer.alert(message);
		//Minu.alert({msg:message});
		alert(message);
	}
	/**页面提交*/
	$.ajaxLoad = function(url,data) {
		if(!url)return;
		$("#right").load(url,data);
	}
	/**单条选择核查 一般用于详情查找*/
	$.ajaxCheckSingle = function(str) {
		var ids = getCheckedId(str);
	    if(!ids || ids.split(",").length > 1){
	    	//alert('请选择一条需要操作的记录!');
	    	$.fn.alert('请选择一条需要操作的记录!');
	        return null;
	    }else return ids;
	    function getCheckedId(str){
	        var ids = '';
	        $("input[name="+str+"]").each(function(i,el){
	          if($(el).prop("checked")){
	              ids += $.trim($(el).attr('data-id'))+',';
	          }
	        });
	        if(ids != ''){
	            ids = ids.substr(0,ids.length-1);  //去除最后一个逗号
	        }
	        return ids;
	     }
	}
	/**多条选择核查 一般用于批量删除*/
	$.ajaxCheckMultiple = function(str) {
		var ids = getCheckedId(str);
		if(!ids){
			//alert('请选择一条需要操作的记录!');
			$.fn.alert('请选择一条需要操作的记录!');
			return null;
		}else return ids;
		function getCheckedId(str){
			var ids = '';
			$("input[name="+str+"]").each(function(i,el){
				if($(el).prop("checked")){
					ids += $.trim($(el).attr('data-id'))+',';
				}
			});
			if(ids != ''){
				ids = ids.substr(0,ids.length-1);  //去除最后一个逗号
			}
			return ids;
		}
	}
	/**确认操作 一般用于删除时候的确认提醒*/
	$.fn.confirmOperator = function(opts) {
		var settings = $.extend({
			msg:"该操作不可逆，确定进行该操作吗？",
			eventName:"click"
		},opts||{});
		$(this).on(settings.eventName,function(event){
			if(!confirm(settings.msg)) {
				event.preventDefault();
			}
		});
	}
})(jQuery)