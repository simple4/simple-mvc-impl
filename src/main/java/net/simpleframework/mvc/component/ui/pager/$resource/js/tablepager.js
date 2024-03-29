/**
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
function $table_pager_addMethods(pa) {
	if (!pa.sort) {
		pa.sort = function(col) {
			var sort = $F(pa.hiddenInput("sort"));
			var col2 = $F(pa.hiddenInput("sort_col"));

			if (col != col2)
				sort = "";
			if (sort == "up")
				sort = "down";
			else if (sort == "down")
				sort = "";
			else
				sort = "up";
			pa("sort=" + sort + "&sort_col=" + col);
		};

		pa.checkAll = function(cb) {
			pa.pager.select("input[type='checkbox']").each(function(c) {
				if (cb != c) {
					c.checked = cb.checked;
					if (c.clickFunc) {
						try {
							c.clickFunc();
						} catch (e) {
						}
					}
				}
			});
		};

		pa.checkId = function(actionFunc) {
			var ids = "";
			pa.pager.select("input[type='checkbox']").each(function(c) {
				if (c.value && c.value != "on" && c.checked) {
					ids += ";" + c.value;
				}
			});
			if (ids.length > 0) {
				if (actionFunc)
					actionFunc(ids.substring(1));
			} else {
				alert($MessageConst["Error.delete2"]);
			}
		};

		pa.move = function(up, moveAction, o) {
			var row = pa.row(o);
			var row2 = up ? row.previous() : row.next();
			if (row2) {
				var rowId = pa.rowId(row);
				var rowId2 = pa.rowId(row2);
				if (!rowId2)
					return;
				if (Object.isString(moveAction)) {
					moveAction = $Actions[moveAction];
				}
				moveAction.selector = pa.selector;
				moveAction("up=" + up + "&rowId=" + rowId + "&rowId2=" + rowId2);
			}
		};

		pa.move2 = function(up, moveAction, o) {
			var row = pa.row(o);
			
			var rowId = pa.rowId(row), rowId2;
			var firstRow = pa.pager.down("#firstRow");
			var lastRow = pa.pager.down("#lastRow");
			if (firstRow && lastRow) {
				rowId2 = $F(row2);
			} else {
				var items = pa.pager.select(".titem");
				rowId2 = pa.rowId(up ? items.first() : items.last());
			}
			
			if (rowId != rowId2) {
				if (Object.isString(moveAction)) {
					moveAction = $Actions[moveAction];
				}
				moveAction.selector = pa.selector;
				moveAction("up=" + up + "&rowId=" + rowId + "&rowId2=" + rowId2);
			}
		};

		pa.row = function(o) {
			if (!o) {
				return pa.currentRow;
			}
			return o.hasClassName("titem") ? o : $Target(o).up(".titem");
		};

		pa.rowId = function(o) {
			return (o = pa.row(o)) ? o.readAttribute("rowId") : null;
		};

		pa.rowData = function(i, o) {
			return pa.row(o).select("td")[i].innerHTML.stripTags().strip();
		};
		
		pa.editRow = function(params, o) {
			var row = pa.row(o);
			if (row.editing) {
				pa.uneditRow(o);
				return;
			}
			var act = $Actions["ajaxTablePagerRowEdit"];
			act.selector = pa.selector;
			act.jsCompleteCallback = function(req, responseText, json) {
				var columns = row.select("td");
				var i = -1;
				row.up(".tablepager").select(".thead td").each(function(col2) {
					i++;
					var columnName = col2.getAttribute("columnName");
					if (!columnName || !json[columnName]) 
						return;
					var lbl = columns[i].down(".lbl");
					lbl.$bak_innerHTML = lbl.innerHTML;
					lbl.innerHTML = json[columnName];
				});
				row.editing = true;
			};
			act(('rowId=' + pa.rowId(o)).addParameter(params));
		};

		pa.uneditRow = function(o) {
			var row = pa.row(o);
			row.select("td").each(function(col) {
				var lbl = col.down(".lbl");
				if (lbl && lbl.$bak_innerHTML) {
					lbl.innerHTML = lbl.$bak_innerHTML;
				}
			});
			row.editing = undefined;
		};
		
		pa.bindMenu = function(menuAction) {
			$Actions.callSafely(menuAction, null, function(act) {
				act.bindEvent(pa.pager.select(".m"));
				return true;
			});
			$Actions.callSafely(menuAction + "2", null, function(act) {
				act.bindEvent(pa.pager.select(".m2"));
				return true;
			});
		};
		
		pa.checkArr = function(c) {
			return c.select(".tbody .titem").inject([],
					function(results, item) {
						var o = item.down("input[type=checkbox]");
						if (o) {
							if (o.checked) {
								results.push(item);
							}
						} else if (item.hasClassName("titem_selected")) {
							results.push(item);
						}
						return results;
					});
		};
		
		pa.exportFile = function(params) {
			var ep = $Actions["tablePagerExportWin"];
			ep.selector = pa.selector;
			ep(params);
		};

		pa.hiddenInput = function(id) {
			var form = pa.pager.down(".parameters");
			var o = form.down("#" + id);
			if (!o) {
				o = new Element("input", {
					"type" : "hidden",
					"name" : id,
					"id" : id
				});
				form.insert(o);
			}
			return o;
		};

		pa.setHeight = function(height) {
			if (!height) {
				return;
			}
			pa.pager.down(".tbody").setStyle(
					"height: " + (pa.hasData ? parseInt(height) : 0) + "px");
			pa.hiddenInput("tbl_tbody_height").setValue(height);
			pa._fixScrollWidth();
		};
		
		// 当tbody出现滚动条时，thead需要加上滚动条的宽度
		pa._fixScrollWidth = function() {
			var tbody = pa.pager.down(".tbody");
			var thead = pa.pager.down(".thead");
			var w = pa.hasData && (tbody.scrollHeight - 1) > tbody.clientHeight ? 17 : 0;
			var tbl = thead.down("table");
			if (w != parseInt(tbl.getStyle("padding-right"))) {
				var padding = "padding-right: " + w + "px;";
				tbl.setStyle(padding);
				var tfilter = thead.next(".tfilter");
				if (tfilter) {
					tfilter.down("table").setStyle(padding);
				}
				pa.hiddenInput("tbl_thead_margin_right").setValue(w);
			}
		};
	}

	var tablepager = pa.pager.down(".tablepager");
	var thead = tablepager.down(".thead");
	var tbody = tablepager.down(".tbody");

	pa.bindMenu("ml_" + pa.beanId + "_Menu");
	
	// tooltip
	var tt = $Actions["tablePagerHeadTooltip"];
	if (tt) {
		tt.createTip(thead.select(".box"));
	}

	// filter
	var tfilter = thead.next(".tfilter");
	if (tfilter) {
		var title = $MessageConst["TablePager.0"];
		tfilter.select("input").each(function(txt) {
			$UI.addBackgroundTitle(txt, title);
			$UI.addReturnEvent(txt, function(ev) {
				var v = $F(txt);
				if (v != "" && v != title) {
					var act = $Actions["ajaxTablePagerColumnFilter"];
					act.jsCompleteCallback = function(req, responseText, json) {
						pa(json["filter"]);
					};
					act(txt.readAttribute("params") + "&v=" + v);
				}
			});
		});
	}

	// scroll head
	var scrollHead = tablepager.readAttribute("scrollHead");
	var scroll;
	if ((scrollHead == "true") && (scroll = thead.getOffsetParent())) {
		// 需要父元素设置 overflow: auto; position: relative;
		scroll._scrollTop = thead.offsetTop;
		if (!scroll.thead) {
			scroll.observe("scroll", function() {
				var delta = scroll.scrollTop - scroll._scrollTop - 1;
				scroll.thead.style.top = Math.max(delta, 0) + "px";
			});
		}
		scroll.scrollTop = 0;
		scroll.thead = thead;
	}

	// 当设置tbody的宽度时
	tbody.observe("scroll", function() {
		thead.scrollLeft = tbody.scrollLeft;
		if (tfilter) {
			tfilter.scrollLeft = tbody.scrollLeft;
		}
	});

	pa._fixScrollWidth();

	// highlight
	var items = tablepager.select(".titem");
	items.invoke("observe", "click", function(evn) {
		items.each(function(item) {
			if (item.hasClassName("titem_selected")) {
				item.removeClassName("titem_selected");
			}
		});
		this.addClassName("titem_selected");
	});
	tablepager.select(".cb input[type=checkbox]").invoke("observe", "click",
			function(evn) {
				var b = this.checked;
				var item = this.up(".titem");
				if (item) {
					item[b ? "addClassName" : "removeClassName"]("titem_highlight");
				} else {
					items.each(function(i) {
						i[b ? "addClassName" : "removeClassName"]("titem_highlight");
					});
				}
				evn.stopPropagation();
			});

	// fix column height
//	var boxColl = thead.select(".box");
//	var maxHeight = 0, minHeight = Number.MAX_VALUE;
//	boxColl.each(function(box) {
//		var nHeight = parseInt(box.getStyle("height"));
//		if (nHeight > maxHeight) 
//			maxHeight = nHeight;
//		if (nHeight < minHeight) 
//			minHeight = nHeight;
//	});
//	if (maxHeight != minHeight) {
//		boxColl.each(function(box) {
//			box.setStyle({ "height" : maxHeight + "px" });
//		});
//	}
	
	// resize
	var i = 0;
	thead.select("td").each(function(td) {
		var columnIndex = i++;
		var sep = td.down(".sep");
		if (!sep)
			return;
		var col = td.previous();
		if (col.readAttribute("resize") != 'true')
			return;

		var p, w;
		sep.setStyle("cursor: e-resize;");
		var getw = function() {
			return Browser.WebKit ? col.getWidth() : col.measure("width");
		};
		var dow = function(width) {
			if (!width)
				return;
			width = parseInt(width);
			if (width < Math.min(40, parseInt(col.readAttribute("owidth"))))
				return;
			col.setStyle("width: " + width + "px");

			if (tfilter) {
				var col2 = tfilter.select("td")[columnIndex - 1];
				if (col2)
					col2.setStyle("width: " + width + "px");
			}

			items.each(function(titem) {
				var col2 = titem.select(".itr>td")[columnIndex - 1];
				if (col2)
					col2.setStyle("width: " + width + "px");
			});
		};
		var mousemove = function(evt) {
			if (!p)
				return;
			dow(w - p.x + evt.pointer().x);
			Event.stop(evt);
		};
		var mouseup = function(evt) {
			if (!p)
				return;
			var body = $(document.body);
			body.setStyle("cursor: default;");
			body.fixOnSelectStart(true);
			document.stopObserving("mousemove", mousemove);
			document.stopObserving("mouseup", mouseup);
			p = null;
		};
		sep.observe("mousedown", function(evt) {
			var body = $(document.body);
			body.fixOnSelectStart();
			body.setStyle("cursor: e-resize;");
			p = evt.pointer();
			w = getw();
			document.observe("mousemove", mousemove);
			document.observe("mouseup", mouseup);
			var owidth = col.readAttribute("owidth");
			if (!owidth) {
				col.writeAttribute("owidth", w);
			}
		});
		sep.observe("dblclick", function(evt) {
			dow(col.readAttribute("owidth"));
		});
	});
}

var TablePagerUtils = {
	filterDelete : function(obj, params) {
		var act = $Actions["ajaxTablePagerFilterDelete"];
		act.selector = obj.up(".pager").down(".parameters");
		act(params);
	},
	
	filterWindow : function(obj, params) {
		var act = $Actions["tablePagerColumnFilterPage"];
		act.selector = obj.up(".pager").down(".parameters");
		$Actions["tablePagerColumnFilterWindow"](params);
	}
};

var TableRowDraggable = {
	init : function(containerId, hoverclass, callback) {
		callback = callback || {};
		$UI.enableDragDrop(containerId, hoverclass, {
			scroll : callback.scroll,
			onStart : function(a) {
				a.innerHTML = $MessageConst["Table.Draggable.0"]
						+ TableRowDraggable.checked(a).length
						+ $MessageConst["Table.Draggable.1"];
				if (callback.onStart)
					callback.onStart(a);
			},
			onEnd : function(a) {
				if (callback.onEnd)
					callback.onEnd(a);
			}
		});
	},

	checked : function(a) {
		var arr = [];
		a.up(".tablepager").select(".titem input").each(function(c) {
			if (c.value && c.value != "on" && c.checked) {
				arr.push(c.value);
			}
		});
		if (arr.length == 0) {
			arr.push(a.up(".titem").down("input").value);
		}
		return arr;
	}
};