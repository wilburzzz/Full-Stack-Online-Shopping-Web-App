/**
 * js基类
 * 提供常用方法
 * */
$(function() {
	$("#cmsLogout").bind("click",cmsLogout);
});
/**
 * 根据id获取用户
 */
function getAdminById(id){
	var admin = new Object();
	$.ajax({
		type: "post",
		url: getContextPath()+'/admin/get',
		data:{id:id},
		dataType: "json",
		async:false,
		success: function(returnData){
			if(ajaxData(returnData)){
				admin = returnData;
			}
		}
	});
	return admin;
}
/**
 * 加载分页
 * @param pagerId
 */
function loadPage(tableId,pagerId){
	var pageSize = $(tableId).find("input:eq(0)").attr("data-pageSize");
	var pageIndex = $(tableId).find("input:eq(0)").attr("data-pageIndex");
	var pages = $(tableId).find("input:eq(0)").attr("data-pages");
	var recordTotal = $(tableId).find("input:eq(0)").attr("data-recordTotal");
	setPageInfo(pageSize,pageIndex,pages,recordTotal,pagerId);
}
/**
 * 获取分页信息
 * @returns {___anonymous5147_5149}
 */
function getPagerObj(tableId){
	var obj = new Object();
	obj.pageSize = $(tableId).find("input:eq(0)").attr("data-pageSize");
	obj.pageIndex = $(tableId).find("input:eq(0)").attr("data-pageIndex");
	obj.pages = $(tableId).find("input:eq(0)").attr("data-pages");
	obj.recordTotal = $(tableId).find("input:eq(0)").attr("data-recordTotal");
	return obj;
}

/**
 * 加载下拉框
 * @param str
 */
function loadSelect(str,returnData){
	$(str).empty();
	$(str).append("<option value=''>--请选择--</option>");
	$.each(returnData, function(i, item){
		$(str).append("<option value='"+item.constantValue+"'>"+item.constantName+"</option>"); 
	});
}

/**
 * 加载下拉框,不清空已有值
 * @param str
 */
function loadSelectItem(str,returnData){
	$.each(returnData, function(i, item){
		$(str).append("<option value='"+item.constantValue+"'>"+item.constantName+"</option>"); 
	});
}

/**
 * 根据类型查询常量 以list形式返回
 * @param type
 */
function getConstantByType(type){
	var obj = new Object();
	var data = {
			"constantType":type,
			"pageSize":1000,
			"pageIndex":1}
	$.ajax({
        type: "post",
        url: getContextPath()+"/constantConfig/getConstantConfigPageAjax",
        data:data,
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(returnData && returnData.rows && returnData.rows.length > 0 ){
        		obj =  returnData.rows;
        	}else return null;
        }
    });
	return obj;
}
/**
 * 
 * @param url
 * @param data
 * @param name
 */
function openPostWindowMutipleProperty(url, dataArray, name){     
    var tempForm = document.createElement("form");     
    tempForm.id="tempForm1";     
    tempForm.method="post";     
    tempForm.action=url;     
    tempForm.target=name;     
    for (var i = 0; i < dataArray.length; i++) {
    	var hideInput = document.createElement("input");     
        hideInput.type="hidden";     
        hideInput.name= dataArray[i].name; 
        hideInput.value= dataArray[i].value;   
        tempForm.appendChild(hideInput); 
	}     
	if (window.addEventListener) {
		tempForm.addEventListener("onsubmit", function() {openWindow(name);});
	} else if (window.attachEvent) {
		tempForm.attachEvent("onsubmit", function() {openWindow(name);});
	}
    document.body.appendChild(tempForm);     
    tempForm.submit();   
    document.body.removeChild(tempForm);   
}
/**
 * 获取freemark 局部刷新的表头隐藏的分页数据
 */
function getPageObj(pageInfo){
	var pageObj = new Object();
	pageObj.pageSize = $(pageInfo).attr("data-pageSize");
	pageObj.pageNumber = $(pageInfo).attr("data-pageIndex");
	pageObj.pages = $(pageInfo).attr("data-pages");
	pageObj.total = $(pageInfo).attr("data-recordTotal");
	return pageObj;
}
/**
 * 将 1,234 转换为 1234 
 * 即 去掉其中的逗号
 */
function formatCurrency(currency){
	 var textA = currency;
	 var arr = textA.split(',');
	 var fomatCurrency = '';
	 for (var i=0;i<arr.length;i++){
		 fomatCurrency = fomatCurrency + arr[i];
	 }
	 if(!fomatCurrency){
		 return 0;
	 }
	 return parseFloat(fomatCurrency);
}
/**兼容IE placeHolder*/
function loadPlaceHolder(){
	if( !('placeholder' in document.createElement('input'))){
		$('input[placeholder],textarea[placeholder]').each(function(){ 
			var that = $(this),   
			text= that.attr('placeholder');   
			if(that.val()===""){   
				that.val(text).addClass('placeholder');   
			}   
			that.focus(function(){   
				if(that.val()===text){   
				  that.val("").removeClass('placeholder');   
				}   
			}).blur(function(){
				if(that.val()===""){   
				  that.val(text).addClass('placeholder');   
				}   
			}).closest('form').submit(function(){
				if(that.val() === text){   
				  that.val('');   
				}   
			});   
		});   
	}  
}
/**
 * 行双击事件 不含checkbox
 */
function initTrClickNoCheck(trClass){
	/**行双击事件 不含checkbox*/
	$(trClass).bind("dblclick",function(){
		dbClickCallBack(this);
	});
}
/**
 * 用于单点时选中行首checkbox
 */
function checkBefore(obj){
	$(obj).parent().parent().siblings().find("input[name='subBox']").prop('checked', false);
	$(obj).parent().parent().find("input[name='subBox']").prop('checked', true);
}
/**
 * 行单击事件
 */
function initTrClick(trClass,ckName){
	if(!ckName)ckName = 'subBox';
	$("input[name='"+ckName+"']").bind("click",function(event){
		event.stopPropagation();
	});
	$(trClass).bind("click",function (){
		$(this).parent().find("input[name='"+ckName+"']").prop('checked', false);
		$(this).find("input[name='"+ckName+"']").prop('checked', true);
		trClickCallBack();
	});
}

/**
 * 重置表单
 */
function resetForm(){
	//重置表单
	$("input[type=reset]").trigger("click");
}

/**
 * 行双击事件
 */
function initDbClick(trClass,ckName){
	if(!ckName)ckName = 'subBox';
	$(trClass).bind("dblclick",function (){
		$(this).parent().find("input[name='"+ckName+"']").prop('checked', false);
		$(this).find("input[name='"+ckName+"']").prop('checked', true);
		dbClickCallBack(this);
	});
}
/**
 * 单击事件 回调函数 调用页需选择性重写
 */
function trClickCallBack(){}
/**
 * 双击事件 回调函数 调用页需重写
 */
function dbClickCallBack(){}
/**
 * 重置表单
 */
function Freset(formId){
	$(formId).find("input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	$(formId).find("select option").each(function(){
		if($(this).attr("selected")){
			$(this).removeAttr("selected");
		}
	});
}
/**
 * 显示定价
 */
function showFixPrice(obj){
	var top = $(obj).offset().top;
	var left = $(obj).offset().left;
	var url = getContextPath()+"/goods/fixGoodsPrice";
	if($("#fixGoodsPriceContainer").length == 0){
		$(".page-content").append("<div id='fixGoodsPriceContainer'></div>");
	}else{
		$("#fixGoodsPriceContainer").remove();
		showFixPrice(obj);
	}
	var data = {
			goodsId:$(obj).attr("data-goodsid")
	}
	url += "?id="+$(obj).attr("data-goodsid");
	$("#fixGoodsPriceContainer").load(url,data,function(){
		$("#fixGoodsPriceContainer").css("display","block");
		$(".show-box").slideDown("slow");
		$(".show-mask").fadeIn(100);
	});
}
/**
 * 退出系统
 */
function cmsLogout(){
	$.ajax({
        type: "post",
        url: getContextPath()+'/admin/logOut',
        data:{content:"退出系统"},
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		layer.msg("成功退出,期待您的下次光临！",5,1,function(){
        			window.location.href = getContextPath()+"/admin/loginView";
        		});
        	}
        }
    });
}
/**
 * 设置分页组件
 * @param pageSize
 * @param pageIndex
 * @param pageTotal
 * @param recordTotal
 */
function setPageInfo(pageSize,pageIndex,pageTotal,recordTotal,pagerId,styleObj){
	var backgroundColor = "";
	if(styleObj){
		backgroundColor = styleObj.backgroundColor;
	}
	var pageIdstr = pagerId;
	if(pageIdstr){
		pageIdstr = pageIdstr.substring(pageIdstr.indexOf("#")+1,pageIdstr.length);
	}
	$(pagerId).html('');
	var str = '<a href="javascript:void(0);" onclick="pageCallBackCk('+(pageIndex-1)+','+pageSize+','+pageTotal+',this)" data-pagerId='+pagerId+'>◀ </a>';
	if(pageIndex >= 10){
		var start = pageIndex - 4;
		var end = parseInt(pageIndex) + 4;
		if(end > pageTotal){
			end = pageTotal;
		}
		for (var i = start-1; i < end; i++) {
			if(i+1  == pageIndex){
				str += '<span style="'+backgroundColor+'" class="pageSpanClick">'+(i+1)+'</span>';
			}else{
				str += '<a href="#" onclick="pageCallBack('+(i+1)+','+pageSize+',this)" data-pagerId='+pagerId+'>'+(i+1)+'</a>';
			}
		}
	}else{
		for (var i = 0; i < pageTotal; i++) {
			if(i+1 == pageIndex){
				str += '<span style="'+backgroundColor+'">'+(i+1)+'</span>';
			}else if(i+1 > 9){
				str += '<a href="#">.999</a>';
				break;
			}else{
				str +='<a href="javascript:void(0);" onclick="pageCallBack('+(i+1)+','+pageSize+',this)" data-pagerId='+pagerId+'>'+(i+1)+'</a>';
			}
		}
	}
	str += '<a href="javascript:void(0);" onclick="pageCallBackCk('+(parseInt(pageIndex)+1)+','+pageSize+','+pageTotal+',this)" data-pagerId='+pagerId+'>▶</a>';
	str += '<input type="text" value="'+pageIndex+'" onkeydown="inpuKeyDown(this,'+pageSize+','+pageTotal+')" data-pagerId='+pagerId+' /><em>/'+pageTotal+'页</em>';
	str += '<select id="'+pageIdstr+'-select" onchange="selectChange('+(pageIndex)+','+pageTotal+',this)" class="form-control" style="width: 85px;display: inline;" data-pagerId='+pagerId+'>';
	str += '<option value="1">1</option>';
	str += '<option value="5" selected="selected">5</option>';
	str += '<option value="10">10</option>';
	str += '<option value="25">25</option>';
	str += '<option value="50">50</option>';
	str += '<option value="100">100</option>';
	str += '</select>';
	
	str += '共<em>'+recordTotal+'</em>条记录';
	$(pagerId).append(str);
	$("#"+pageIdstr+"-select option[value='"+pageSize+"']").attr("selected","selected");
}
/**
 * 切换事件
 */
function selectChange(pageIndex,pageTotal,obj){
	var pageSize = $(obj).val();
	if(!pageSize){
		return;
	}
	//切换时 置1
	pageIndex = 1;
	pageCallBack(pageIndex,pageSize,obj);
}

/***回车事件*/
function inpuKeyDown(obj,pageSize,pageTotal){
	var e = window.event || arguments.callee.caller.arguments[0];
    if (!e || e.keyCode != 13 ) {
    	return;
    }
    var pageIndex = $(obj).val();
    if(isNaN(pageIndex) || parseInt(pageIndex) > 10000 || parseInt(pageIndex) < 1){
    	layer.alert("请输入数字！");
    	return;
    }
    if(!pageTotal){
    	layer.alert("总页数不存在!");
    	return;
    }
    if(pageIndex > pageTotal){
    	layer.alert("页数最大为:"+pageTotal+".");
    	return;
    }
    pageCallBack(pageIndex,pageSize,obj);
}
/**
 * 上一页 下一页
 * @param pageIndex
 * @param pageSize
 * @param pageTotal
 */
function pageCallBackCk(pageIndex,pageSize,pageTotal,obj){
	if(pageIndex > pageTotal || pageIndex == 0){
		return;
	}
	pageCallBack(pageIndex,pageSize,obj);
}
/***空的回调函数 由调用页重写*/
function pageCallBack(pageIndex,pageSize,obj){}
/**
 * 获取当前登录用户
 */
function getCunrrentAdmin(){
	var admin = new Object();
	$.ajax({
        type: "post",
        url: getContextPath()+'/admin/getForAjax',
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		admin = returnData.object;
        	}
        }
    });
	return admin;
}
/**
 * timeStam 转化字符串
 * @param time
 * @returns {String}
 */
function timeStamp2String(time){
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
}
/**
 * 格式化时间 
 * dateStr 2014-05-10 13:25:50
 * var  ddate = new Date('2014-05-10 13:25:50').getTime();
 * var  ddate = new Date('2014/05/10 13:25:50').getTime(); 这么写的时候两个浏览器都没有问题
 */
function formDate(dateStr){
	if(!dateStr){
		return;
	}
	return new Date((dateStr).replace(new RegExp("-","gm"),"/")).getTime();
}
/**
 * 用于测试
 * @param data
 */
function deal(data){
	return alert(JSON.stringify(data));
}
/**
 * 上传文件
 */
function uploadFile(obj){
	var top = $(obj).offset().top;
	var left = $(obj).offset().left;
	var url = getContextPath()+"/yqFile/uploadFile";
	if($("#uploadFileContainer").length == 0){
		$(".page-content").append("<div id='uploadFileContainer'></div>");
		$("#uploadFileContainer").load(url,function(){
			$("#uploadFileContainer").css("display","block");
			$(".show-box").slideDown("slow");
			$(".show-mask").fadeIn(100);
		});
	}else{
		$("#uploadFileContainer").css("display","block");
		$(".show-box").slideDown("slow");
		$(".show-mask").fadeIn(100);
	}
}
/**
 * 文件上传 回调函数
 * @param idStr
 * @param nameStr
 */
function callBackFile(ckIdStr,ckNameStr){
	//alert("文件上传回调函数!--callBackFile(ckIdStr,ckNameStr){//ToDo...}");
}
/**
 * 学校选择 回调函数
 * @param idStr
 * @param nameStr
 */
function callBack(ckIdStr,ckNameStr){
	//alert("学校选择回调函数!--callBackFile(ckIdStr,ckNameStr){//ToDo...}");
}
/**
 * 保存附件
 */
function saveAttatch(objectId){
	var flag = false;
	if(objectId == null || objectId == '' || typeof(objectId) == "undefined"){
		return;
	}
	var imageStr = ''; 
	$(".base-div").find("img").each(function(){
		var id = $(this).attr("data-id");
		if( !id || id == ''){
			imageStr += $(this).attr("src") +",";  
		}
	});
	if(!imageStr && imageStr == '' || $.trim(imageStr) == null || $.trim(imageStr) == ''){
		return;
	}
	imageStr = imageStr.substring(0,imageStr.length-1);
	$.ajax({
        type: "post",
        url: getContextPath()+'/yqFile/save',
        data: {objectId:objectId,remark:imageStr},
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		layer.msg("附件保存成功！",1,1);
        		flag = true;
        	}
        }
    });
	return flag;
}
/**
 * 删除附件
 */
function deleteAttatch(obj){
	var id = $(obj).prev().attr("data-id");
	if(!id || typeof(id) == "undefined"){
		return;
	}
	$.ajax({
		type:"post",
		url: getContextPath()+"/yqFile/deleteById",
        data: {ids:id},
        dataType: "json",
        async:false,
        success: function(returnData){
        	if(ajaxData(returnData)){
        		//移除
        		$(obj).parent().remove();
        		//移除物理文件
        		//Todo
        	}
        }
	});
}
/**
 * 单张上传-新
 * @param obj
 */
function uploadSingle(obj){
	var top = $(obj).offset().top;
	var left = $(obj).offset().left;
	var url = getContextPath()+"/yqFile/uploadSingle";
	if($("#uploadSingleContainer").length == 0){
		$(".page-content").append("<div id='uploadSingleContainer' class='singleContainer'></div>");
		$("#uploadSingleContainer").load(url,function(){
			$("#uploadSingleContainer").css("display","block");
			$(".demo").css("top",top-100);
			$(".demo").css("left","50%");
			setHtmlObjSingle(obj);
		});
	}else{
		$("#uploadSingleContainer").css("display","block");
		//$(".demo").css("top",top-100);
		//$(".demo").css("left","50%");
		setHtmlObjSingle(obj);
	}
}

/**
 * 单张上传
 */
function uploadSingle(obj){
	var top = $(obj).offset().top;
	var left = $(obj).offset().left;
	var url = getContextPath()+"/yqFile/uploadSingle";
	if($("#uploadSingleContainer").length == 0){
		$(".page-content").append("<div id='uploadSingleContainer' class='singleContainer'></div>");
		$("#uploadSingleContainer").load(url,function(){
			$("#uploadSingleContainer").css("display","block");
			$(".demo").css("top",top-100);
			$(".demo").css("left","50%");
			setHtmlObjSingle(obj);
		});
	}else{
		$("#uploadSingleContainer").css("display","block");
		//$(".demo").css("top",top-100);
		//$(".demo").css("left","50%");
		setHtmlObjSingle(obj);
	}
}
/**
 * 图片选择 多张
 */
function uploadMultiple(obj){
	var top = $(obj).offset().top;
	var left = $(obj).offset().left;
	var url = getContextPath()+"/yqFile/uploadMultiple";
	if($("#uploadContainer").length == 0){
		$("body").append("<div id='uploadContainer'></div>");
		$("#uploadContainer").load(url,function(){
			$("#uploadContainer").modal("show");
			$(".demo").css("top",top-100);
			$(".demo").css("left","50%");
			setHtmlObj(obj);
		});
	}else{
		$("#uploadContainer").modal("show");
		$(".demo").css("top",top-100);
		$(".demo").css("left","50%");
		setHtmlObj(obj);
	}
}
/**
 * 选择学校(单选)
 */
function selectCollege(){
	var url = getContextPath()+"/college/selectColleges";
	if($("#collegeContainer").length == 0){
		$("body").append("<div id='collegeContainer'></div>");
		$("#collegeContainer").load(url,function(){
			initBaseArea("radio");
			$("#colleList ul li").remove();
			$("#chooseCollegeDiv").modal("show");
		});
	}else{
		$("#collegeContainer").remove();
		selectCollege();
	}
}
/**
 * 选择学校(多选)
 */
function selectColleges(){
	var url = getContextPath()+"/college/selectColleges";
	if($("#collegeContainerMul").length == 0){
		$("body").append("<div id='collegeContainerMul'></div>");
		$("#collegeContainerMul").load(url,function(){
			initBaseArea("checkBox");
			$("#colleList ul li").remove();
			$("#chooseCollegeDiv").modal("show");
		});
	}else{
		$("#collegeContainerMul").remove();
		selectColleges();
	}
}
/**
 * 引入外部样式文件
 */
function includeLinkStyle(url) {
	var link = document.createElement("link");
	link.rel = "stylesheet";
	link.type = "text/css";
	link.href = url;
	document.getElementsByTagName("head")[0].appendChild(link);
}
/**
 * 格式化Node
 */
function formatNode(nodes,para){
	for (var i = 0; i < nodes.length; i++) {
			delete nodes[i].target;
			var b = new Object();
			b.para = para;
			nodes[i].attributes = b;
		if(nodes[i].children && nodes[i].children[0]){
			nodes[i].children = temp(nodes[i].children,para);
		}
	}
	return nodes;
}
/**
 * 递归
 * @param nodes
 * @returns
 */
function temp(nodes,para){
	for (var i = 0; i < nodes.length; i++) {
		delete nodes[i].target;
		var b = new Object();
		b.para = para;
		nodes[i].attributes = b;
		if(nodes[i].children && nodes[i].children[0]){
			temp(nodes[i].children,para);
		}
	}
	return nodes;
}
/**
 * 初始化分页
 * 列表页面展现时 需要有一个buttonSearch()方法
 */
function initPage(){
	/**上一页*/
	$("#prePage").bind("click",buttonPrePage);
	/**下一页*/
	$("#nextPage").bind("click",buttonNextPage);
	/**切换页码*/
	$("#pageSize").bind("change",changePage);
	/**页码点击时触发*/
	$(".pageSpanClick").bind("click",pageSpanClick);	
	/**输入页码*/
	$("#pageInput").bind("keydown",doKeydown);
}
/**
 * 初始化分页
 * 列表页面展现时 需要有一个buttonSearch()方法
 */
function initPageX(index){
	/**上一页*/
	$("#prePage"+index).bind("click",function(){
		buttonPrePageX(index);
	});
	/**下一页*/
	$("#nextPage"+index).bind("click",function(){
		buttonNextPageX(index);
	});
	/**切换页码*/
	$("#pageSize"+index).bind("change",function(){
		changePageX(index);
	});
	/**页码点击时触发*/
	$(".pageSpanClick"+index).bind("click",function(){
		pageSpanClickX(index,this);
	});	
	/**输入页码*/
	$("#pageInput"+index).bind("keydown",function(){
		doKeydownX(index,this);
	});
}

/**
 * 获取项目的根路径
 * 
 * @returns
 * @author Julian
 * @since 2.3.1
 */
function getContextPath() {
	var strFullPath = window.document.location.href; // 获取当前网址
	var strPath = window.document.location.pathname; // 获取主机地址之后的目录
	var pos = strFullPath.indexOf(strPath);
	var prePath = strFullPath.substring(0, pos); // 获取主机地址与端口
	var postPath = "";
	// 获取带"/"的项目名
	// 处理单独以项目名称访问的情况
	if(strPath.substr(1).indexOf('/') > 0){
		postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
	}else
		postPath = strPath; 
	return (prePath + postPath);
}

/**
 * 获取选中的ID
 * 注 此方法在jquery.cms.core.js中也存在 不过为局部方法
 * @autor yxg
 * @param
 * @return
 */
function getCheckedIds(){
    var ids = '';
    $('input[name="subBox"]').each(function(i,el){
      if($(el).prop("checked")){
          ids += $.trim($(el).attr('data-id'))+',';
      }
    });
    if(ids != ''){
        ids = ids.substr(0,ids.length-1);  //去除最后一个逗号
    }
    return ids;
 }
/**
 * 获取本页所有checkboxid 
 * @returns {String}
 */
function getAllIds(){
	var ids = '';
    $('input[name="subBox"]').each(function(i,el){
         ids += $.trim($(el).attr('data-id'))+',';
    });
    if(ids != ''){
        ids = ids.substr(0,ids.length-1);  //去除最后一个逗号
    }
    return ids;
}

/**
 * 用于checkbox全选全不选的函数
 * @autor yxg
 * 注意prop和attr的区别 
 * 在jquery1.6后加入了prop jquery之前版本用来存储属性值而非改变选中状态
 */
var $check_box_click = function() {
	if($(this).prop("checked") == undefined){
		alert("Todo");
	}
	if ($(this).prop("checked")) {
		$(this).addClass("checked");
	    $('input[name="ids[]"]').prop('checked', $(this).hasClass('checked'));
	} else {
		$(this).removeClass("checked");
		$('input[name="ids[]"]').prop('checked', $(this).hasClass('checked'));
	}
} 
/**
 * 用于checkbox全选全不选的函数2
 */
function initCheckBox(str){
	if(!str) str = "subBox";
	$('#checkAll').change(function () {
		$('input[name='+str+']').prop('checked', $(this).prop('checked'));
	});

	$('input[name='+str+']').change(function () {
		$("#checkAll").prop("checked", $('input[name='+str+']').length == $('input[name='+str+']:checked').length);
	});
}
/**
 * 用于checkbox全选全不选的函数3 
 */
function initCheckBoxX(pCheckAllId,chilCheckName){
	if(!chilCheckName) chilCheckName = "subBox";
	$(pCheckAllId).change(function () {
		$('input[name='+chilCheckName+']').prop('checked', $(this).prop('checked'));
	});
	
	$('input[name='+chilCheckName+']').change(function () {
		$(pCheckAllId).prop("checked", $('input[name='+chilCheckName+']').length == $('input[name='+chilCheckName+']:checked').length);
	});
}
/**
 * 自定义弹框
 * @param message
 */
function FnAlert(message){
	$.fn.alert(message);
}

/**
 * 统一处理返回值
 * @param data
 */
function ajaxData(data){
	return $.ajaxData(data);
}
/**
 * 统一处理页面跳转
 * @param url
 */
function ajaxLoad(url,data){
	$.ajaxLoad(url,data);
}
/**
 * 获取选中的单个 用于编辑
 */
function getEdit(str){
	if(!str) str = "subBox";
	var ids = $.ajaxCheckSingle(str);
	return ids;
}
/**
 * 获取选中的单个 用于删除
 */
function getSelected(str){
	if(!str) str = "subBox";
	var ids = $.ajaxCheckSingle(str);
	return ids;
}
/**
 * 获取选中的多个
 */
function getSelecteds(str){
	if(!str) str = "subBox";
	var ids = $.ajaxCheckMultiple(str);
	return ids;
}
/**
 * 手动验证form
 */
function checkValid(formId){
	return $(formId).validate().form();
}

/**
 * 切换页
 */
function changePage(){
	var pageSize = $("#pageSize").val();
	var nowPage = $("#pageInput").val();
	buttonSearch(pageSize,nowPage);
}
/**
 * 切换页 X
 */
function changePageX(index){
	var pageSize = $("#pageSize"+index).val();
	var nowPage = $("#pageInput"+index).val();
	autoSearch(index,pageSize,nowPage);
	
}
/**
 * 上一页
 */
function buttonPrePage(){
	var nowPage = $("#pageInput").val();
	if(nowPage == 1){
		return;
	}
	var pageSize = $("#pageSize").val();
	var prePageIndex = parseInt(nowPage) - 1;
	buttonSearch(pageSize,prePageIndex);
}
/**
 * 上一页 X
 */
function buttonPrePageX(index){
	var nowPage = $("#pageInput"+index).val();
	if(nowPage == 1){
		return;
	}
	var pageSize = $("#pageSize"+index).val();
	var prePageIndex = parseInt(nowPage) - 1;
	autoSearch(index,pageSize,prePageIndex);
}
/**
 * 最多实现5个查询调用
 * @param index
 * @param pageSize
 * @param pageIndex
 */
function autoSearch(index,pageSize,pageIndex){
	if(index == 1){
		buttonSearch1(pageSize,pageIndex);
	}else if(index == 2){
		buttonSearch2(pageSize,pageIndex);
	}else if(index == 3){
		buttonSearch3(pageSize,pageIndex);
	}else if(index == 4){
		buttonSearch4(pageSize,pageIndex);
	}else if(index == 5){
		buttonSearch5(pageSize,pageIndex);
	}else
		buttonSearch(pageSize,pageIndex);
}
/**
 * 下一页
 */
function buttonNextPage(){
	var nowPage = $("#pageInput").val();
	var pages = $("#pages").text();
	if(!nowPage || !pages){
		return;
	}
	//显示的页标签中，是否是最后一个选中 且如果
	//var lastTag = $(".list-page a:last").prev().prev().prev().is('span');
	//如果当前页是总页数 返回
	if(parseInt(nowPage) == parseInt(pages)){	
		return;
	}
	if(parseInt(nowPage) >= parseInt(pages)){
		nowPage = 0;
	}
	var pageSize = $("#pageSize").val();
	var nextPageIndex = parseInt(nowPage) + 1;
	buttonSearch(pageSize,nextPageIndex);
}
/**
 * 下一页 X
 */
function buttonNextPageX(index){
	var nowPage = $("#pageInput"+index).val();
	var pages = $("#pages"+index).text();
	//显示的页标签中，是否是最后一个选中 且如果
	//var lastTag = $(".list-page a:last").prev().prev().prev().is('span');
	//如果当前页是总页数 返回
	if(nowPage == pages){	
		return;
	}
	var pageSize = $("#pageSize"+index).val();
	var nextPageIndex = parseInt(nowPage) + 1;
	autoSearch(index,pageSize,nextPageIndex);
}
/**
 * 页码点击时 触发
 */
function pageSpanClick(){
	var pageSize = $("#pageSize").val();
	var pageIndex = $(this).text();
	pageIndex = parseInt(pageIndex);
	var lastTag = $(this).is('span');
	//点击的span为当前页 返回
	if(lastTag){
		return;
	}
	buttonSearch(pageSize,pageIndex);
}
/**
 * 页码点击时 触发 X
 */
function pageSpanClickX(index,obj){
	var pageSize = $("#pageSize"+index).val();
	var pageIndex = $(obj).text();
	pageIndex = parseInt(pageIndex);
	var lastTag = $(obj).is('span');
	//点击的span为当前页 返回
	if(lastTag){
		return;
	}
	autoSearch(index,pageSize,pageIndex);
}
/**
 * 回车事件
 * */
function doKeydown(){
	var e = window.event || arguments.callee.caller.arguments[0];
    if (!e || e.keyCode != 13 ) {
    	return;
    }
    var pageIndex = $(this).val();
    var pages = $("#pages").text();
    if(parseInt(pageIndex) <= parseInt(pages)){
    	var pageSize = $("#pageSize").val();
    	buttonSearch(pageSize,pageIndex);
    }
}
/**
 * 回车事件 X
 * */
function doKeydownX(index,obj){
	var e = window.event || arguments.callee.caller.arguments[0];
	if (!e || e.keyCode != 13 ) {
		return;
	}
	var pageIndex = $(obj).val();
	var pages = $("#pages"+index).text();
	if(parseInt(pageIndex) <= parseInt(pages)){
		var pageSize = $("#pageSize"+index).val();
		autoSearch(index,pageSize,pageIndex);
	}
}