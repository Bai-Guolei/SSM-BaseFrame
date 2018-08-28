/**
 * The Patch for jQuery EasyUI 1.5
 */

(function($){
	$.extend($.fn.datagrid.defaults, {
		loader: function(param, success, error){
			var opts = $(this).datagrid('options');
			if (!opts.pagerInited){
				opts.pagerInited = true;
				var data = $(this).datagrid('getData');
				if (!data.total){
					$(this).datagrid('getPager').pagination('refresh', {total:0});
				}
			}
			if (!opts.url) return false;
			$.ajax({
				type: opts.method,
				url: opts.url,
				data: param,
				dataType: 'json',
				success: function(data){
					//兼容PC端 ajax请求返回格式数据   {success:false,msg:'',data:{}}
					if((typeof data.success != 'undefined') && !data.success)
					{
						top.warn(data.msg);
					}
					else if(data.code == 401 || data.code == 500 )
					{
						return false;	
					}
					else
					{
						success(data);
					}
					//全局异常处理部分 end
				},
				error: function(){
					error.apply(this, arguments);
				}
			});
		}
	});
	var viewMethods = {
		updateRow: function(target, rowIndex, row){
			var opts = $.data(target, 'datagrid').options;
			var rowData = opts.finder.getRow(target, rowIndex);

			$.extend(rowData, row);
			var cs = _getRowStyle.call(this, rowIndex);
			var style = cs.s;
			var cls = 'datagrid-row ' + (rowIndex % 2 && opts.striped ? 'datagrid-row-alt ' : ' ') + cs.c;
			
			function _getRowStyle(rowIndex){
				var css = opts.rowStyler ? opts.rowStyler.call(target, rowIndex, rowData) : '';
				return this.getStyleValue(css);
			}
			function _update(frozen){
				var tr = opts.finder.getTr(target, rowIndex, 'body', (frozen?1:2));
				if (!tr.length){return;}
				var fields = $(target).datagrid('getColumnFields', frozen);
				var checked = tr.find('div.datagrid-cell-check input[type=checkbox]').is(':checked');
				tr.html(this.renderRow.call(this, target, fields, frozen, rowIndex, rowData));
				var cls12 = (tr.hasClass('datagrid-row-checked') ? ' datagrid-row-checked' : '') +
							(tr.hasClass('datagrid-row-selected') ? ' datagrid-row-selected' : '');
				tr.attr('style', style).attr('class', cls + cls12);
				if (checked){
					tr.find('div.datagrid-cell-check input[type=checkbox]')._propAttr('checked', true);
				}
			}
			
			_update.call(this, true);
			_update.call(this, false);
			$(target).datagrid('fixRowHeight', rowIndex);
		},

		insertRow: function(target, index, row){
			var state = $.data(target, 'datagrid');
			var opts = state.options;
			var dc = state.dc;
			var data = state.data;
			
			if (index == undefined || index == null) index = data.rows.length;
			if (index > data.rows.length) index = data.rows.length;
			
			function _incIndex(frozen){
				var serno = frozen?1:2;
				for(var i=data.rows.length-1; i>=index; i--){
					var tr = opts.finder.getTr(target, i, 'body', serno);
					tr.attr('datagrid-row-index', i+1);
					tr.attr('id', state.rowIdPrefix + '-' + serno + '-' + (i+1));
					if (frozen && opts.rownumbers){
						var rownumber = i+2;
						if (opts.pagination){
							rownumber += (opts.pageNumber-1)*opts.pageSize;
						}
						tr.find('div.datagrid-cell-rownumber').html(rownumber);
					}
					if (opts.striped){
						tr.removeClass('datagrid-row-alt').addClass((i+1)%2 ? 'datagrid-row-alt' : '');
					}
				}
			}
			
			function _insert(frozen){
				var serno = frozen?1:2;
				var fields = $(target).datagrid('getColumnFields', frozen);
				var rowId = state.rowIdPrefix + '-' + serno + '-' + index;
				var tr = '<tr id="' + rowId + '" class="datagrid-row" datagrid-row-index="' + index + '"></tr>';
				if (index >= data.rows.length){	// append new row
					if (data.rows.length){	// not empty
						opts.finder.getTr(target, '', 'last', serno).after(tr);
					} else {
						var cc = frozen ? dc.body1 : dc.body2;
						cc.html('<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>' + tr + '</tbody></table>');
					}
				} else {	// insert new row
					opts.finder.getTr(target, index+1, 'body', serno).before(tr);
				}
			}
			
			_incIndex.call(this, true);
			_incIndex.call(this, false);
			_insert.call(this, true);
			_insert.call(this, false);
			
			data.total += 1;
			data.rows.splice(index, 0, row);
			
			if (this.setEmptyMsg){
				this.setEmptyMsg(target);
			}
			this.refreshRow.call(this, target, index);
		}

	}
	$.extend($.fn.datagrid.defaults.view, viewMethods);
	$.extend($.fn.datalist.defaults.view, viewMethods);
})(jQuery);

(function($){
	var clickHandler = $.fn.treegrid.defaults.rowEvents.click;
	function clickEventHandler(e){
		var tt = $(e.target);
		var tr = tt.closest('tr.datagrid-row');
		if (!tr.length || !tr.parent().length){return;}
		var nodeId = tr.attr('node-id');
		var target = getTableTarget(tr);
		if (tt.hasClass('tree-hit')){
			clickHandler(e);
		} else if (tt.hasClass('tree-checkbox')){
			clickHandler(e);
		} else {
			var opts = $(target).datagrid('options');
			if (!tt.parent().hasClass('datagrid-cell-check') && !opts.singleSelect && e.shiftKey){
				var rows = $(target).treegrid('getChildren');
				var idx1 = $.easyui.indexOfArray(rows, opts.idField, opts.lastSelectedIndex);
				var idx2 = $.easyui.indexOfArray(rows, opts.idField, nodeId);
				var from = Math.min(Math.max(idx1,0), idx2);
				var to = Math.max(idx1, idx2);
				var row = rows[idx2];
				var td = tt.closest('td[field]',tr);
				if (td.length){
					var field = td.attr('field');
					opts.onClickCell.call(target, nodeId, field, row[field]);
				}
				$(target).treegrid('clearSelections');
				for(var i=from; i<=to; i++){
					$(target).treegrid('selectRow', rows[i][opts.idField]);
				}
				opts.onClickRow.call(target, row);
			} else {
				$.fn.datagrid.defaults.rowEvents.click(e);
			}
		}
	}
	function getTableTarget(t){
		return $(t).closest('div.datagrid-view').children('.datagrid-f')[0];
	}

	$.extend($.fn.treegrid.defaults.rowEvents, {
		click: clickEventHandler
	});

	$.extend($.fn.treegrid.defaults.view, {
		renderRow: function(target, fields, frozen, depth, row){
			var state = $.data(target, 'treegrid');
			var opts = state.options;
			
			var cc = [];
			if (frozen && opts.rownumbers){
				cc.push('<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">0</div></td>');
			}
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				var col = $(target).datagrid('getColumnOption', field);
				if (col){
					var css = col.styler ? (col.styler(row[field], row)||'') : '';
					var cs = this.getStyleValue(css);
					var cls = cs.c ? 'class="' + cs.c + '"' : '';
					var style = col.hidden ? 'style="display:none;' + cs.s + '"' : (cs.s ? 'style="' + cs.s + '"' : '');
					
					cc.push('<td field="' + field + '" ' + cls + ' ' + style + '>');
					
					var style = '';
					if (!col.checkbox){
						if (col.align){style += 'text-align:' + col.align + ';'}
						if (!opts.nowrap){
							style += 'white-space:normal;height:auto;';
						} else if (opts.autoRowHeight){
							style += 'height:auto;';
						}
					}
					
					cc.push('<div style="' + style + '" ');
					if (col.checkbox){
						cc.push('class="datagrid-cell-check ');
					} else {
						cc.push('class="datagrid-cell ' + col.cellClass);
					}
					cc.push('">');
					
					if (col.checkbox){
						if (row.checked){
							cc.push('<input type="checkbox" checked="checked"');
						} else {
							cc.push('<input type="checkbox"');
						}
						cc.push(' name="' + field + '" value="' + (row[field]!=undefined ? row[field] : '') + '">');
					} else {
						var val = null;
						if (col.formatter){
							val = col.formatter(row[field], row);
						} else {
							val = row[field];
//							val = row[field] || '&nbsp;';
						}
						if (field == opts.treeField){
							for(var j=0; j<depth; j++){
								cc.push('<span class="tree-indent"></span>');
							}
							if (row.state == 'closed'){
								cc.push('<span class="tree-hit tree-collapsed"></span>');
								cc.push('<span class="tree-icon tree-folder ' + (row.iconCls?row.iconCls:'') + '"></span>');
							} else {
								if (row.children && row.children.length){
									cc.push('<span class="tree-hit tree-expanded"></span>');
									cc.push('<span class="tree-icon tree-folder tree-folder-open ' + (row.iconCls?row.iconCls:'') + '"></span>');
								} else {
									cc.push('<span class="tree-indent"></span>');
									cc.push('<span class="tree-icon tree-file ' + (row.iconCls?row.iconCls:'') + '"></span>');
								}
							}
							if (this.hasCheckbox(target, row)){
								var flag = 0;
								var crow = $.easyui.getArrayItem(state.checkedRows, opts.idField, row[opts.idField]);
								if (crow){
									flag = crow.checkState == 'checked' ? 1 : 2;
									row.checkState = crow.checkState;
									row.checked = crow.checked;
									$.easyui.addArrayItem(state.checkedRows, opts.idField, row);
								} else {
									var prow = $.easyui.getArrayItem(state.checkedRows, opts.idField, row._parentId);
									if (prow && prow.checkState == 'checked' && opts.cascadeCheck){
										flag = 1;
										row.checked = true;
										$.easyui.addArrayItem(state.checkedRows, opts.idField, row);
									} else if (row.checked){
										$.easyui.addArrayItem(state.tmpIds, row[opts.idField]);
									}
									row.checkState = flag ? 'checked' : 'unchecked';
								}
								cc.push('<span class="tree-checkbox tree-checkbox' + flag + '"></span>');
							} else {
								row.checkState = undefined;
								row.checked = undefined;
							}
							cc.push('<span class="tree-title">' + val + '</span>');
						} else {
							cc.push(val);
						}
					}
					
					cc.push('</div>');
					cc.push('</td>');
				}
			}
			return cc.join('');
		}		
	});
})(jQuery);

(function($){
	function constrain(left, top, width, height){
		var target = this;
		var state = $.data(target, 'window');
		var opts = state.options;
		if (!opts.constrain){return {};}
		if ($.isFunction(opts.constrain)){
			return opts.constrain.call(target, left, top, width, height);
		}
		var win = $(target).window('window');
		var parent = opts.inline ? win.parent() : $(window);
		if (left < 0){left = 0;}
		if (top < parent.scrollTop()){top = parent.scrollTop();}
		if (left + width > parent.width()){
			if (width == win.outerWidth()){	// moving
				left = parent.width() - width;
			} else {	// resizing
				width = parent.width() - left;
			}
		}
		if (top - parent.scrollTop() + height > parent.height()){
			if (height == win.outerHeight()){	// moving
				top = parent.height() - height + parent.scrollTop();
			} else {	// resizing
				height = parent.height() - top + parent.scrollTop();
			}
		}

		return {
			left:left,
			top:top,
			width:width,
			height:height
		};
	}
	function setDraggable(target){
		var state = $(target).data('window');
		$(target).window('window').draggable({
			onStopDrag: function(e){
				$.extend(e.data, constrain.call(target, e.data.left, e.data.top, e.data.width+0.1, e.data.height+0.1));
				$(target).window('move', e.data);
				state.pmask.remove();
				state.pmask = null;
				state.proxy.remove();
				state.proxy = null;
			}
		});

	}
	var plugin = $.fn.window;
	$.fn.window = function(options, param){
		if (typeof options == 'string'){
			return plugin.call(this, options, param);
		} else {
			return this.each(function(){
				plugin.call($(this), options, param);
				setDraggable(this);
			});
		}
	}
	$.fn.window.defaults = plugin.defaults;
	$.fn.window.methods = plugin.methods;
	$.fn.window.parseOptions = plugin.parseOptions;
	$.fn.window.getMaskSize = plugin.getMaskSize;
})(jQuery);

(function($){
	var plugin = $.fn.textbox;
	$.fn.textbox = function(options, param){
		if (typeof options == 'string'){
			return plugin.call(this, options, param);
		} else {
			return this.each(function(){
				plugin.call($(this), options, param);
				$(this).textbox('textbox').css('text-align', this.style.textAlign || '');
			});
		}
	}
	$.fn.textbox.defaults = plugin.defaults;
	$.fn.textbox.methods = plugin.methods;
	$.fn.textbox.parseOptions = plugin.parseOptions;
})(jQuery);

(function($){
	function setValue(target, value){
		var state = $.data(target, 'numberbox');
		var opts = state.options;
		opts.value = parseFloat(value);
		var value = opts.parser.call(target, value);
		var text = opts.formatter.call(target, value);
		opts.value = value;
		$(target).textbox('setText', text).textbox('setValue', value);
		text = opts.formatter.call(target, $(target).textbox('getValue'));
		$(target).textbox('setText', text);
	}
	$.extend($.fn.numberbox.defaults, {
		inputEvents: {
			keypress:function(e){
				var target = e.data.target;
				var opts = $(target).numberbox('options');
				return opts.filter.call(target, e);
			},
			blur:function(e){
				$(e.data.target).numberbox('fix');
			},
			keydown: function(e){
				if (e.keyCode == 13){
					$(e.data.target).numberbox('fix');
				}
			}
		},
		parser: function(s){
			s = s + '';
			var opts = $(this).numberbox('options');
			if (opts.prefix) s = $.trim(s.replace(new RegExp('\\'+$.trim(opts.prefix),'g'), ''));
			if (opts.suffix) s = $.trim(s.replace(new RegExp('\\'+$.trim(opts.suffix),'g'), ''));
			if (parseFloat(s) != opts.value){
				if (opts.groupSeparator) s = $.trim(s.replace(new RegExp('\\'+opts.groupSeparator,'g'), ''));
				if (opts.decimalSeparator) s = $.trim(s.replace(new RegExp('\\'+opts.decimalSeparator,'g'), '.'));
				s = s.replace(/\s/g,'');
			}
			var val = parseFloat(s).toFixed(opts.precision);
			if (isNaN(val)) {
				val = '';
			} else if (typeof(opts.min) == 'number' && val < opts.min) {
				val = opts.min.toFixed(opts.precision);
			} else if (typeof(opts.max) == 'number' && val > opts.max) {
				val = opts.max.toFixed(opts.precision);
			}
			return val;
		}
	});
	$.extend($.fn.numberbox.methods, {
		fix: function(jq){
			return jq.each(function(){
				var opts = $(this).numberbox('options');
				opts.value = null;
				var value = opts.parser.call(this, $(this).numberbox('getText'));
				$(this).numberbox('setValue', value);
			});
		},
		setValue: function(jq, value){
			return jq.each(function(){
				setValue(this, value);
			});
		}
	});
})(jQuery);

(function($){
	$.extend($.fn.combo.methods, {
		setText: function(jq, text){
			return jq.each(function(){
				var target = this;
				var state = $.data(target, 'combo');
				var oldText = $(target).textbox('getText');
				if (oldText != text){
					$(target).textbox('setText', text);
				}
				state.previousText = text;
			});
		}
	});
})(jQuery);

(function($){
	function setValues(target, values, remainText){
		var opts = $.data(target, 'combobox').options;
		var panel = $(target).combo('panel');
		
		if (!$.isArray(values)){
			values = values.split(opts.separator);
		}
		if (!opts.multiple){
			values = values.length ? [values[0]] : [''];
		}

		// unselect the old rows
		var oldValues = $(target).combo('getValues');
		if (panel.is(':visible')){
			panel.find('.combobox-item-selected').each(function(){
				var row = opts.finder.getRow(target, $(this));
				if (row){
					if ($.easyui.indexOfArray(oldValues, row[opts.valueField]) == -1){
						$(this).removeClass('combobox-item-selected');
					}
				}
			});
		}
		$.map(oldValues, function(v){
			if ($.easyui.indexOfArray(values, v) == -1){
				var el = opts.finder.getEl(target, v);
				if (el.hasClass('combobox-item-selected')){
					el.removeClass('combobox-item-selected');
					opts.onUnselect.call(target, opts.finder.getRow(target, v));
				}
			}
		});

		var theRow = null;
		var vv = [], ss = [];
		for(var i=0; i<values.length; i++){
			var v = values[i];
			var s = v;
			var row = opts.finder.getRow(target, v);
			if (row){
				s = row[opts.textField];
				theRow = row;
				var el = opts.finder.getEl(target, v);
				if (!el.hasClass('combobox-item-selected')){
					el.addClass('combobox-item-selected');
					opts.onSelect.call(target, row);
				}
			}
			vv.push(v);
			ss.push(s);
		}

		if (!remainText){
			$(target).combo('setText', ss.join(opts.separator));
		}
		if (opts.showItemIcon){
			var tb = $(target).combobox('textbox');
			tb.removeClass('textbox-bgicon ' + opts.textboxIconCls);
			if (theRow && theRow.iconCls){
				tb.addClass('textbox-bgicon ' + theRow.iconCls);
				opts.textboxIconCls = theRow.iconCls;
			}
		}
		$(target).combo('setValues', vv);
		panel.triggerHandler('scroll');	// trigger the group sticking
	}
	function doQuery(target, q){
		var state = $.data(target, 'combobox');
		var opts = state.options;

		var highlightItem = $();
		var qq = opts.multiple ? q.split(opts.separator) : [q];
		if (opts.mode == 'remote'){
			_setValues(qq);
			request(target, null, {q:q}, true);
		} else {
			var panel = $(target).combo('panel');
			panel.find('.combobox-item-hover').removeClass('combobox-item-hover');
			panel.find('.combobox-item,.combobox-group').hide();
			var data = state.data;
			var vv = [];
			$.map(qq, function(q){
				q = $.trim(q);
				var value = q;
				var group = undefined;
				for(var i=0; i<data.length; i++){
					var row = data[i];
					if (opts.filter.call(target, q, row)){
						var v = row[opts.valueField];
						var s = row[opts.textField];
						var g = row[opts.groupField];
						var item = opts.finder.getEl(target, v).show();
						if (s.toLowerCase() == q.toLowerCase()){
							value = v;
							if (opts.reversed){
								highlightItem = item;
							} else {
								select(target, v, true);
							}
						}
						if (opts.groupField && group != g){
							opts.finder.getGroupEl(target, g).show();
							group = g;
						}
					}
				}
				vv.push(value);
			});
			_setValues(vv);
		}
		function _setValues(vv){
			if (opts.reversed){
				highlightItem.addClass('combobox-item-hover');
			} else {
				setValues(target, opts.multiple ? (q?vv:[]) : vv, true);
			}
		}
	}

	var enter = $.fn.combobox.defaults.keyHandler.enter;
	$.extend($.fn.combobox.defaults, {
		reversed: false,
		inputEvents: $.extend({}, $.fn.combo.defaults.inputEvents, {
			blur: function(e){
				var target = e.data.target;
				var opts = $(target).combobox('options');
				if (opts.reversed){
					$(target).combobox('setValues', $(target).combobox('getValues'));
				} else if (opts.limitToList){
					enter.call(target);
				}
			}
		}),
		onShowPanel: function(){
			setValues(this, $(this).combobox('getValues'), true);
		}
	});
	var query = $.fn.combobox.defaults.keyHandler.query;
	$.extend($.fn.combobox.defaults.keyHandler, {
		query: function(q,e){
			var opts = $(this).combobox('options');
			if (opts.reversed && opts.mode == 'local'){
				doQuery(this, q);
			} else {
				query.call(this, q, e);
			}
		}
	});
	$.extend($.fn.combobox.methods, {
		setValues: function(jq, values){
			return jq.each(function(){
				setValues(this, values);
			});
		},
		setValue: function(jq, value){
			return jq.each(function(){
				setValues(this, $.isArray(value)?value:[value]);
			});
		}
	});
})(jQuery);

(function($){
	function setValues(target, values, remainText){
		var state = $.data(target, 'combogrid');
		var opts = state.options;
		var grid = state.grid;
		
		var oldValues = $(target).combo('getValues');
		var cOpts = $(target).combo('options');
		var onChange = cOpts.onChange;
		cOpts.onChange = function(){};	// prevent from triggering onChange event
		var gOpts = grid.datagrid('options');
		var onSelect = gOpts.onSelect;
		var onUnselectAll = gOpts.onUnselectAll;
		gOpts.onSelect = gOpts.onUnselectAll = function(){};
		
		if (!$.isArray(values)){
			values = values.split(opts.separator);
		}
		if (!opts.multiple){
			values = values.length ? [values[0]] : [''];
		}
		var vv = $.map(values, function(value){return String(value);});
		vv = $.grep(vv, function(v, index){
			return index === $.inArray(v, vv);
		});

		var selectedRows = $.grep(grid.datagrid('getSelections'), function(row, index){
			return $.inArray(String(row[opts.idField]), vv) >= 0;
		});
		grid.datagrid('clearSelections');
		grid.data('datagrid').selectedRows = selectedRows;

		var ss = [];
		opts.unselectedValues = [];
		$.map(vv, function(v){
			var index = grid.datagrid('getRowIndex', v);
			if (index >= 0){
				grid.datagrid('selectRow', index);
			} else {
				opts.unselectedValues.push(v);
			}
			ss.push(findText(v, grid.datagrid('getRows')) ||
					findText(v, selectedRows) ||
					findText(v, opts.mappingRows) ||
					v
			);
		});

		$(target).combo('setValues', oldValues);
		cOpts.onChange = onChange;	// restore to trigger onChange event
		gOpts.onSelect = onSelect;
		gOpts.onUnselectAll = onUnselectAll;
		
		if (!remainText){
			var s = ss.join(opts.separator);
			if ($(target).combo('getText') != s){
				$(target).combo('setText', s);
			}
		}
		$(target).combo('setValues', values);
		
		function findText(value, a){
			var item = $.easyui.getArrayItem(a, opts.idField, value);
			return item ? item[opts.textField] : undefined;
		}
	}
	
	/**
	 * do the query action
	 */
	function doQuery(target, q){
		var state = $.data(target, 'combogrid');
		var opts = state.options;
		var grid = state.grid;
		state.remainText = true;

		var qq = opts.multiple ? q.split(opts.separator) : [q];
		qq = $.grep(qq, function(q){return $.trim(q)!='';});
		if (opts.mode == 'remote'){
			_setValues(qq);
			grid.datagrid('load', $.extend({}, opts.queryParams, {q:q}));
		} else {
			grid.datagrid('highlightRow', -1);
			var rows = grid.datagrid('getRows');
			var vv = [];
			$.map(qq, function(q){
				q = $.trim(q);
				var value = q;
				_addRowValue(opts.mappingRows, q);
				_addRowValue(grid.datagrid('getSelections'), q);
				var index = _addRowValue(rows, q);
				if (index >= 0){
					if (opts.reversed){
						grid.datagrid('highlightRow', index);
					}
				} else {
					$.map(rows, function(row, i){
						if (opts.filter.call(target, q, row)){
							grid.datagrid('highlightRow', i);
						}
					});
				}
			});
			_setValues(vv);
		}
		function _addRowValue(rows, q){
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				if ((row[opts.textField]||'').toLowerCase() == q.toLowerCase()){
					vv.push(row[opts.idField]);
					return i;
				}
			}
			return -1;
		}
		function _setValues(vv){
			if (!opts.reversed){
				setValues(target, vv, true);
			}
		}
	}
	
	function doEnter(target){
		var state = $.data(target, 'combogrid');
		var opts = state.options;
		var grid = state.grid;
		var tr = opts.finder.getTr(grid[0], null, 'highlight');
		state.remainText = false;
		if (tr.length){
			var index = parseInt(tr.attr('datagrid-row-index'));
			if (opts.multiple){
				if (tr.hasClass('datagrid-row-selected')){
					grid.datagrid('unselectRow', index);
				} else {
					grid.datagrid('selectRow', index);
				}
			} else {
				grid.datagrid('selectRow', index);
			}
		}
		var vv = [];
		$.map(grid.datagrid('getSelections'), function(row){
			vv.push(row[opts.idField]);
		});
		$.map(opts.unselectedValues, function(v){
			if ($.easyui.indexOfArray(opts.mappingRows, opts.idField, v) >= 0){
				$.easyui.addArrayItem(vv, v);
			}
		});
		$(target).combogrid('setValues', vv);
		if (!opts.multiple){
			$(target).combogrid('hidePanel');
		}
	}

	$.extend($.fn.combogrid.defaults, {
		reversed: false,
		inputEvents: $.extend({}, $.fn.combo.defaults.inputEvents, {
			blur: function(e){
				var target = e.data.target;
				var opts = $(target).combogrid('options');
				if (opts.reversed){
					$(target).combogrid('setValues', $(target).combogrid('getValues'));
				}
			}
		}),
	});
	
	var query = $.fn.combogrid.defaults.keyHandler.query;
	$.extend($.fn.combogrid.defaults.keyHandler, {
		enter: function(e){doEnter(this)},
		query: function(q, e){
			var opts = $(this).combogrid('options');
			if (opts.mode == 'local'){
				doQuery(this, q);
			} else {
				query.call(this, q, e);
			}
		}
	});
})(jQuery);

(function($){
	function clear(target){
		$('input,select,textarea', target).each(function(){
			if ($(this).hasClass('textbox-value')){return;}
			var t = this.type, tag = this.tagName.toLowerCase();
			if (t == 'text' || t == 'hidden' || t == 'password' || tag == 'textarea'){
				this.value = '';
			} else if (t == 'file'){
				var file = $(this);
				if (!file.hasClass('textbox-value')){
					var newfile = file.clone().val('');
					newfile.insertAfter(file);
					if (file.data('validatebox')){
						file.validatebox('destroy');
						newfile.validatebox();
					} else {
						file.remove();
					}
				}
			} else if (t == 'checkbox' || t == 'radio'){
				this.checked = false;
			} else if (tag == 'select'){
				this.selectedIndex = -1;
			}
			
		});
		
		var tmp = $();
		var form = $(target);
		var opts = $.data(target, 'form').options;
		for(var i=0; i<opts.fieldTypes.length; i++){
			var type = opts.fieldTypes[i];
			var field = form.find('.'+type+'-f').not(tmp);
			if (field.length && field[type]){
				field[type]('clear');
				tmp = tmp.add(field);
			}
		}
		form.form('validate');
	}

	$.extend($.fn.form.methods, {
		clear: function(jq){
			return jq.each(function(){
				clear(this);
			});
		}
	});
})(jQuery);

(function($){
	function bindEvents(){
		$(document).unbind('.messager').bind('keydown.messager', function(e){
			if (e.keyCode == 27){	//ESC
				$('body').children('div.messager-window').children('div.messager-body').each(function(){
					$(this).dialog('close');
				});
			} else if (e.keyCode == 9){	//TAB
				var win = $('body').children('div.messager-window');
				if (!win.length){return}
				var buttons = win.find('.messager-input,.messager-button .l-btn');
				for(var i=0; i<buttons.length; i++){
					if ($(buttons[i]).is(':focus')){
						$(buttons[i>=buttons.length-1?0:i+1]).focus();
						return false;
					}
				}
			} else if (e.keyCode == 13){	// ENTER
				var input = $(e.target).closest('input.messager-input');
				if (input.length){
					var dlg = input.closest('.messager-body');
					closeDialog(dlg, input.val());
				}
			}
		});
	}

	function unbindEvents(){
		$(document).unbind('.messager');
	}

	function createDialog(options){
		bindEvents();
		var dlg = $('<div class="messager-body"></div>').appendTo('body');
		dlg.dialog($.extend({}, options, {
			noheader: (options.title?false:true),
			onClose: function(){
				unbindEvents();
				if (options.onClose){
					options.onClose.call(this);
				}
				dlg.dialog('destroy');
			}
		}));
		var win = dlg.dialog('dialog').addClass('messager-window');
		win.find('.dialog-button').addClass('messager-button').find('a:first').focus();
		return dlg;
	}

	$.extend($.messager, {
		progress: function(options){
			var methods = {
				bar: function(){	// get the progress bar object
					return $('body>div.messager-window').find('div.messager-p-bar');
				},
				close: function(){	// close the progress window
					var dlg = $('body>div.messager-window>div.messager-body:has(div.messager-progress)');
					if (dlg.length){
						dlg.dialog('close');
					}
				}
			};
			
			if (typeof options == 'string'){
				var method = methods[options];
				return method();
			}

			options = options || {};
			var opts = $.extend({}, {
				title: '',
				minHeight: 0,
				content: undefined,
				msg: '',	// The message box body text
				text: undefined,	// The text to display in the progress bar
				interval: 300	// The length of time in milliseconds between each progress update
			}, options);

			var dlg = createDialog($.extend({}, $.messager.defaults, {
				content: '<div class="messager-progress"><div class="messager-p-msg">' + opts.msg + '</div><div class="messager-p-bar"></div></div>',
				closable: false,
				doSize: false
			}, opts, {
				onClose: function(){
					if (this.timer){
						clearInterval(this.timer);
					}
					if (options.onClose){
						options.onClose.call(this);
					} else {
						$.messager.defaults.onClose.call(this);
					}
				}				
			}));
			var bar = dlg.find('div.messager-p-bar');
			bar.progressbar({
				text: opts.text
			});
			dlg.dialog('resize');
			
			if (opts.interval){
				dlg[0].timer = setInterval(function(){
					var v = bar.progressbar('getValue');
					v += 10;
					if (v > 100) v = 0;
					bar.progressbar('setValue', v);
				}, opts.interval);
			}
			return dlg;
		}		
	});
})(jQuery);
