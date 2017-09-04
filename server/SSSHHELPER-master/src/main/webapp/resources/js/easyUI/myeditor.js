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

/*长文本输入*/
$.extend($.fn.datagrid.defaults.editors, {
	textarea: {   
	 init: function(container, options){   
	             var input = $('<textarea class="datagrid-editable-input" rows='+options.rows+'  maxlength='+options.maxlength+'></textarea>').appendTo(container);   
	             return input;   
	          },   
	 getValue: function(target){   
	             return $(target).val();   
	         },   
	 setValue: function(target, value){   
	             $(target).val(value);   
	        },   
	 resize: function(target, width){   
	 
	             var input = $(target);   
	             if ($.boxModel == true){   
	                 input.width(width - (input.outerWidth() - input.width()));   
	             } else {   
	                 input.width(width);   
	             }   
	         }   
	     }   
}); 
