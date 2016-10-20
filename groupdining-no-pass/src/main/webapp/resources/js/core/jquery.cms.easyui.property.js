jQuery.extend(jQuery.fn.datagrid.defaults.editors, {    
        combotree: {    
            init: function(container, options){    
                var editor = jQuery('<input type="text">').appendTo(container);    
                editor.combotree(options);    
                return editor;    
            },    
            destroy: function(target){    
                jQuery(target).combotree('destroy');    
            },    
            getValue: function(target){    
                var temp = jQuery(target).combotree('getValues');    
                //alert(temp);    
                return temp.join(',');    
            },    
            setValue: function(target, value){    
                var temp = value.split(',');    
                //alert(temp);    
                jQuery(target).combotree('setValues', temp);    
            },    
            resize: function(target, width){    
                jQuery(target).combotree('resize', width);    
            }    
        },  
        textarea: {      
            init : function(container, options) {  
            var input = $(  
                    '<textarea class="datagrid-editable-input" style="resize:none;"></textarea>')  
                    .appendTo(container);  
                return input;  
            },  
            getValue : function(target) {  
                return $(target).val();  
            },  
            setValue : function(target, value) {  
                $(target).val(value);  
            },  
            resize : function(target, width) {  
                var input = $(target);  
                if ($.boxModel == true) {  
                    input.width(width - (input.outerWidth() - input.width()));  
                } else {  
                    input.width(width);  
                }  
            }  
        }      
    });