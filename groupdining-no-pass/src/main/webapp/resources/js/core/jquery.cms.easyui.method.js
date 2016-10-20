/**
 * 扩展jQuery easyui datagrid增加动态改变列编辑的类型  
 * 使用方式：    
 * 为password字段添加一个editor
 * 
 * $("#gridId").datagrid('addEditor', {    
        field : 'password',    
        editor : {    
            type : 'validatebox',    
            options : {    
                required : true    
            }    
        }    
	});
 */
$.extend($.fn.datagrid.methods, {    
    addEditor : function(jq, param) {    
        if (param instanceof Array) {    
            $.each(param, function(index, item) {    
                var e = $(jq).datagrid('getColumnOption', item.field);    
                e.editor = item.editor;    
            });    
        } else {    
            var e = $(jq).datagrid('getColumnOption', param.field);    
            e.editor = param.editor;    
        }    
    },    
    removeEditor : function(jq, param) {    
        if (param instanceof Array) {    
            $.each(param, function(index, item) {    
                var e = $(jq).datagrid('getColumnOption', item);    
                e.editor = {};    
            });    
        } else {    
            var e = $(jq).datagrid('getColumnOption', param);    
            e.editor = {};    
        }    
    }    
});