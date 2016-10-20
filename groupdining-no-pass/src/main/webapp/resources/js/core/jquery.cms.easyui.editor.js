    $.extend($.fn.datagrid.defaults.editors, {
        text: {
    		init: function(container, options){
    			var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container);
    			return input;
    		},
    		destroy: function(target){
    			$(target).remove();
    		},
    		getValue: function(target){
    			return $(target).val();
    		},
    		setValue: function(target, value){
    			$(target).val(value);
    		},
    		resize: function(target, width){
    			$(target)._outerWidth(width);
    		}
        }
    });