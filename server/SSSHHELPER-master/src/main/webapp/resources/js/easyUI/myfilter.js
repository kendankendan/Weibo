
/*@author lks
 *@date 2015-06-28
 *@addr xiamen
 * 
 * 
 */
$.extend($.fn.datagrid.defaults.view,{
    onAfterRender:function(target){
        var dc = $.data(target,'datagrid').dc;
        if(dc.header2.find('[filter="true"]').length == 0){
            var header = dc.header1; //固定列表头
            var header2 = dc.header2; // 常规列表头
            var filterRow = $('<tr></tr>');
            var opts = $.data(target,'datagrid').options;
            var columns = opts.columns;
            var frozenColumns = opts.frozenColumns;

            $.each(frozenColumns[0],function(){
                if(!this.checkbox){
                    var w = header.find('[field="'+this.field+'"] > div').width();

                    filterRow.append('<td><input style="width:'+w+'px"/></td>');
                }
                else{
                    header.find('.datagrid-header-check').parent().attr('rowspan',2);
                   
                }
            });
            header.find('tbody').append(filterRow);
            filterRow = $('<tr filter="true"></tr>');

            $.each(columns[0],function(){
                var w = header2.find('[field="'+this.field+'"] > div').width()*0.8;
                if(this.hfilter){
                    var a = $('<input field="'+this.field+'" class="easyui-combobox" style="width:'+w+'px" />');
                    filterRow.append($('<td></td>').append(a));
                    a.data('options',this.hfilter);
                }else{
                   // filterRow.append('<td id='+this.field+'><input style="width:'+w+'px"/></td>');
                	filterRow.append('<td class=datagrid-filter-c id='+this.field+'>'+
                			'<input class=datagird-editable-input datagrid-filter style="width:'+w+'px"/>'+
                			'<a class="datagrid-filter-btn icon-filter">&nbsp;</a>'+
                			'</td>');
                }
            });

            header2.find('tbody').append(filterRow);

//            var dgData = $(target).datagrid('getData').rows;
//
//            header2.find('td[id]').each(function(){
//                //var opts = $(this).data('options');
//                var field = $(this).attr('id');
//                alert(field);
////                $.extend(opts.options,{
////                    onSelect:function(item){
////                        var d = _.filter(dgData,function(row){
////                            return row[field].indexOf(item[opts.options.textField]) > -1;
////                        });
////
////                        $(target).datagrid('loadData',d);
////                    }
////                });
////
////                $(this)[opts.type](opts.options);
//            });
        }
    }
});


function createFilterButton(container, operators){
	if (!operators){return null;}
	
	var btn = $('<a class="datagrid-filter-btn">&nbsp;</a>').addClass(opts.filterBtnIconCls);
	if (opts.filterBtnPosition == 'right'){
		btn.appendTo(container);
	} else {
		btn.prependTo(container);
	}
	var menu = $('<div></div>').appendTo('body');
	menu.menu({
		alignTo:btn,
		onClick:function(item){
			var btn = $(this).menu('options').alignTo;
			var td = btn.closest('td[field]');
			var field = td.attr('field');
			var input = td.find('input.datagrid-filter');
			var value = input[0].filter.getValue(input);
			
			addFilterRule(target, {
				field: field,
				op: item.name,
				value: value
			});
			
			doFilter(target);
		}
	});
	$.each(['nofilter'].concat(operators), function(index,item){
		var op = opts.operators[item];
		if (op){
			menu.menu('appendItem', {
				text: op.text,
				name: item
			});
		}
	});
	btn.bind('click', {menu:menu}, function(e){
		$(e.data.menu).menu('show');
		return false;
	});
	return menu;
}