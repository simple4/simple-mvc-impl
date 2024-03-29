/**
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
var MENU_DOCUMENT_CLICK = false;

$UI.Menu = Class.create({
	shown : [],
	
	initialize : function(container, options) {
		this.options = {
			zIndex : 900,
			pageOffset : 25,
			topOffset : -1,
			onBeforeShow : null,
			/* menuEvent = contextmenu, click */
			menuEvent : null
		};
		this.setOptions(options);

		if (container) {
			this.element = $(container);
			this.element.setDimensions(this.options);
			this.createMainList(this.options.menuItems);
		} else {
			this.element = this.createMenu(document.body, this.options.menuItems);
			this.bindEvent(this.options.selector);
		}

		if (!MENU_DOCUMENT_CLICK) {
			document.observe("click", function(e) {
				if (!e.isRightClick()) {
					this.hideAll(e);
				}
			}.bind(this));
			MENU_DOCUMENT_CLICK = true;
		}
	},

	destroy : function() {
		if (this.element) {
			this.element.remove();
		}
	},

	setOptions : function(options) {
		Object.extend(this.options, options || {});
	},

	_eventHandle : function(e) {
		if (e)
			e.stop();
		this.hideAll(e);
		this.show(e, this.element);
	},

	bindEvent : function(selector, ev) {
		if (!selector)
			return;
		$Elements(selector).invoke("observe", (ev || this.options.menuEvent),
				this._eventHandle.bind(this));
	},

	show : function(e, menu, pos) {
		if (menu.visible() || menu.select("li").length == 0) {
			return;
		}
		if (!pos) {
			menu.targetObject = e.currentTarget || e.target;
		}
		if (this.options.onBeforeShow) {
			if (this.options.onBeforeShow(menu, e) == false) {
				return;
			}
		}

		var bounds = this.position(e, menu, pos);
		menu.setStyle({
			left : bounds.left + "px",
			top : bounds.top + "px",
			zIndex : this.options.zIndex
		});

		menu.$show({
			effects : this.options.effects
		});
	},

	hide : function(menu) {
		menu.$hide({
			effects : this.options.effects
		});
	},

	hideAll : function(e) {
		this.shown.each(function(ele) {
			if (ele.mi) {
				ele.mi.removeClassName("active");
			}
			ele.hide();
		}.bind(this));
	},

	createMainList : function(items) {
		items.each(function(item) {
			var mi;
			if (item.separator) {
				mi = $UI.createLink(null, {
					className : "ms"
				});
			} else {
				mi = $UI.createLink(item.name, {
					className : item.disabled ? "mm disabled" : "mm",
					title : (item.desc || ""),
					href : item.url ? item.url : null
				});
				this.createItemFunction(mi);
				if (!item.url && item.onSelect) {
					mi.observe("click", function(e) {
						if (!mi.isItemDisabled()) {
							item.onSelect(mi, e);
						} else {
							Event.stop(e);
						}
					});
				}
			}
			var d;
			if (item.submenu) {
				d = this.createMenu(document.body, item.submenu);
			}
			mi.observe("mouseover", (function(e) {
				this.hideAll(e);
				if (d) {
					d.mi = mi;
					mi.addClassName("active");
					this.show(e, d);
				}
			}).bind(this));

			this.element.insert(mi);
		}.bind(this));

		this.element.insert(new Element("div", {
			style : "clear:both;"
		}));
	},

	createItemFunction : function(a) {
		a.isItemDisabled = function() {
			return a.hasClassName("disabled");
		};

		a.setItemDisabled = function(disabled) {
			if (disabled) {
				a.addClassName("disabled");
			} else {
				a.removeClassName("disabled");
			}
		};

		a.isItemChecked = function() {
			return a.up().hasClassName("checked");
		};

		a.setItemChecked = function(checked) {
			var w = a.up();
			w.removeClassName(checked ? "unchecked" : "checked");
			w.addClassName(checked ? "checked" : "unchecked");
		};

		a.setText = function(text) {
			a.update(text);
		};

		a.setItemVisible = function(visible) {
			var li = a.up("li");
			if (visible)
				li.show();
			else
				li.hide();
		};

		a.hideMenu = function() {
			var m = a.getMenu();
			while (m) {
				this.hide(m);
				m = m.getParentMenu();
			}
		}.bind(this);

		a.getMenu = function() {
			return a.up(".menu");
		};

		a.getSubMenu = function() {
			return a.down(".menu");
		};

		a.getTargetObject = function() {
			return a.getMenu().getTargetObject();
		}.bind(this);
	},

	createMenuFunction : function(menu) {
		menu.firstLevel = function() {
			return (menu.up() == document.body);
		};

		menu.getRootMenu = function() {
			var m = menu;
			while (!m.firstLevel()) {
				m = m.getParentMenu();
			}
			return m;
		};

		menu.getParentMenu = function() {
			return menu.up(".menu");
		};

		menu.getItems = function() {
			var results = [];
			menu.down("ul").immediateDescendants().each(function(li) {
				var a = li.down("a");
				if (a)
					results.push(a);
			});
			return results;
		};

		menu.getTargetObject = function() {
			return menu.getRootMenu().targetObject;
		};
	},

	createList : function(items) {
		var list = new Element("ul");
		items.each(function(item) {
			var li = new Element("li", {
				className : item.separator ? "separator" : ""
			});
			list.insert(li);
			if (item.separator) {
				li.insert("&nbsp;");
			} else {
				var a = $UI.createLink(item.name, {
					className : (item.disabled ? "disabled" : "")
							+ (item.submenu ? " submenu" : ""),
					title : (item.desc || ""),
					href : item.url ? item.url : null
				});
				a.observe("contextmenu", Event.stop);
				if (item.submenu) {
					this.createMenu(a, item.submenu);
				}

				this.createItemFunction(a);

				if (!item.url && item.onSelect) {
					a.observe("click", function(e) {
						if (!a.isItemDisabled()) {
							a.hideMenu();
							item.onSelect(a, e);
						} else if (!item.checkbox) {
							Event.stop(e);
						}
					});
				}

				var w = a;
				if (item.checkbox) {
					w = w.wrap({
						className : item.checked ? "checked" : "unchecked"
					});
					w.observe("click", function(e) {
						if (item.onCheck) {
							item.onCheck(a, e);
						} else {
							a.setItemChecked(!a.isItemChecked());
						}
						Event.stop(e);
					});
				} else if (item.icon) {
					w = w.wrap({
						className : "icon " + item.icon
					});
				}
				if (item.submenu) {
					w = w.wrap({
						className : "more"
					});
				}
				li.insert(w);
			}
		}.bind(this));
		return list;
	},

	createMenu : function(pa, items) {
		pa = $(pa);
		var menu = this.createList(items).wrap({
			className : "menu desktop",
			style : "display:none"
		}).observe("contextmenu", Event.stop);
		if (this.options.minWidth) {
			menu
					.setStyle(((Browser.IEVersion && Browser.IEVersion <= 6.0) ? "width:"
							: "min-width:")
							+ this.options.minWidth);
		}

		pa.insert(menu);
		this.createMenuFunction(menu);

		menu.getItems().each(function(a) {
			var m = a.getSubMenu();
			if (!m)
				return;
			a.observe("mouseover", function(e) {
				this.show(e, m, true);
			}.bind(this));
			a.observe("mouseout", function(e) {
				if (e.relatedTarget.descendantOf(a))
					return;
				this.hide(m);
			}.bind(this));
		}.bind(this));

		this.shown.push(menu);
		return menu;
	},

	position : function(e, menu, r) {
		var x, y;
		var o = r ? menu.up(".menu") : menu.targetObject;
		if (r) {
			var off = o.cumulativeOffset();
			var off2 = o.cumulativeScrollOffset();
			x = (off.left - off2.left) + o.getWidth();
		} else {
			if (this.options.menuEvent == "contextmenu") {
				var p = Event.pointer(e);
				x = p.x;
				y = p.y;
			} else {
				var off = o.cumulativeOffset();
				var off2 = o.cumulativeScrollOffset();
				x = off.left - off2.left;
				var h = o.getDimensions().height;
				y = (off.top - off2.top) + h + this.options.topOffset;
			}
		}

		var vpOff = document.viewport.getScrollOffsets();
		if (this.options.menuEvent != "contextmenu") {
			y += vpOff.top;
			x += vpOff.left;
		}
		var vpDim = document.viewport.getDimensions();
		var elDim = menu.getDimensions();
		var _left = (x + elDim.width + this.options.pageOffset) > vpDim.width ? (vpDim.width
				- elDim.width - this.options.pageOffset)
				: x;
		var _top = y ? ((y - vpOff.top + elDim.height) > vpDim.height
				&& (y - vpOff.top) > elDim.height ? (y - elDim.height) : y) : -2;
		if (r) {
			_left = (o.getWidth() - 10) - (x - _left);
		}

		return Object.clone(Object.extend(elDim, {
			left : _left,
			top : _top
		}));
	}
});

function __menu_actions_init(actionFunc, actionName) {
	if (actionName) {
		var act = $Actions[actionName];
		if (act && act.menu) {
			act.menu.destroy();
		}
	}
	actionFunc.bindEvent = function(selector) {
		actionFunc.menu.bindEvent(selector);
	};
}
