/**
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
(function() {
	/**
	 * 覆盖系统的alert, confirm等函数
	 */
	window.$alert = function(msg, title) {
		var w = new UI.Window({
			minimize : false,
			maximize : false,
			resizable : false,
			height : 180,
			width : 420
		});

		var c = ("<div class='sy_alert_dialog'>"
				+ "<div class='top simple_toolbar'><div class='img'></div>"
				+ "<div class='txt wrap_text'>" + msg + "</div></div><div class='btn'></div></div>")
				.makeElement();
		c.down(".btn").insert(new Element("input", {
			type : "button",
			value : $MessageConst["Button.Ok"]
		}).observe("click", function(evn) {
			w.close();
		}));
		w.setHeader(title || $MessageConst["Alert.Title"]).setContent(c);
		w.center().show(true).activate();
	};

	window.$confirm = function(msg, okCallback, cancleCallback) {
		var w = new UI.Window({
			minimize : false,
			maximize : false,
			resizable : false,
			height : 180,
			width : 450
		});
		var c = ("<div class='sy_confirm_dialog'>"
				+ "<div class='top simple_toolbar'><div class='img'></div>"
				+ "<div class='txt wrap_text'>" + msg + "</div></div><div class='btn'></div></div>")
				.makeElement();
		c.down(".btn").insert(new Element("input", {
			type : "button",
			value : $MessageConst["Button.Ok"]
		}).observe("click", function(evn) {
			$call(okCallback);
			w.close();
		})).insert(new Element("input", {
			type : "button",
			style : "margin-left: 4px;",
			value : $MessageConst["Button.Cancel"]
		}).observe("click", function(evn) {
			$call(cancleCallback);
			w.close();
		}));
		w.setHeader($MessageConst["Confirm.Title"]).setContent(c);
		w.center().show(true).activate();
	};

	window.$error = function(err, alertType) {
		if (!WINDOW_UI_ENABLE || alertType) {
			alert(err.title);
		} else {
			if (typeof err == 'string') {
				alert(err);
				return;
			}
			if (!this.errWindows) {
				this.errWindows = {};
			}
			var _WIDTH = 600;
			var _HEIGHT = 175;
			var w;
			if (this.errWindows[err.hash]) {
				w = this.errWindows[err.hash];
			} else {
				w = this.errWindows[err.hash] = new UI.Window({
					minimize : false,
					maximize : false,
					width : _WIDTH,
					height : _HEIGHT
				});
				w.observe("hidden", function() {
					delete this.errWindows[err.hash];
				}.bind(this));
			}

			var detail = new Element("div");
			var c = new Element("div", {
				className : "sy_error_dialog"
			}).insert(new Element("div", {
				className : "simple_toolbar et wrap_text"
			}).update(err.title.convertHtmlLines())).insert(detail);

			var right = new Element("div", {
				style : "float: right;"
			}).insert(new Element("input", {
				type : "button",
				style : "margin-right: 6px;",
				value : $MessageConst["Button.More"]
			}).observe("click", function() {
				if (detail.down()) {
					detail.update("");
					this.value = $MessageConst["Button.More"];
					w.setSize(_WIDTH, _HEIGHT).adapt();
				} else {
					detail.update(new Element("div", {
						className : "simple_toolbar1 ta"
					}).insert(new Element("textarea", {
						readonly : "readonly"
					}).update(err.detail)));
					this.value = $MessageConst["Button.Less"];
					w.adapt();
				}
				w.center();
			})).insert(new Element("input", {
				type : "button",
				value : $MessageConst["Button.Cancel"]
			}).observe("click", function() {
				w.close();
			}));

			c.insert(new Element("div", {
				className : "eb"
			}).insert(right).insert(
					new Element("div", {
						style : "float: left"
					}).insert(new Element("a", {
						href : "#"
					}).update($MessageConst["Button.Location.Reload"]).observe("click",
							function() {
								location.reload();
							}))));

			w.setHeader($MessageConst["Error.Title"]).setContent(c);
			w.center().show(true).activate();
		}
	};

	$ready(function() {
		window.WINDOW_UI_ENABLE = window.UI && window.UI.Window;

		/**
		 * 覆盖alert的实现
		 */
		var $alert_bak = window.alert;
		window.alert = function(msg) {
			if (!WINDOW_UI_ENABLE)
				$alert_bak(msg);
			else
				$alert(msg);
		};
	});
})();

/**
 * 给$UI添加一些常用函数
 */
Object.extend($UI, {
	/**
	 * 获取弹出窗口的偏移量
	 */
	getPopupOffsets : function(popup) {
		var e = document.getEvent();
		var trigger;
		if (!e || !(trigger = Event.element(e)))
			return [ 0, 0 ];

		if (window.$Target)
			trigger = $Target(trigger);

		var tl = trigger.cumulativeOffset();
		var valueT = tl.top, valueL = tl.left;

		valueT = valueT + trigger.getDimensions().height;

		tl = trigger.cumulativeScrollOffset();
		valueT -= tl.top, valueL -= tl.left;

		var bodyDelta = 35;
		var vpDim = document.viewport.getDimensions();
		var bodyWidth = vpDim.width - bodyDelta;
		var bodyHeight = vpDim.height - bodyDelta;

		var popupWidth, popupHeight;
		if (Object.isElement(popup)) {
			popup = $(popup);
			popupWidth = popup.getWidth();
			popupHeight = popup.getHeight();
		} else {
			popupWidth = popup.width;
			popupHeight = popup.height;
		}

		if (valueL + popupWidth > bodyWidth) {
			var d = bodyWidth - popupWidth;
			valueL = d < 0 ? 2 : d;
		}
		if (valueT + popupHeight > bodyHeight) {
			var d = bodyHeight - popupHeight;
			valueT = d < 0 ? 2 : d;
		}

		var vpOff = document.viewport.getScrollOffsets();
		valueT += vpOff.top;
		valueL += vpOff.left;
		return [ valueL, valueT ];
	},

	/**
	 * 通过图标使指定目标可折叠
	 */
	doImageToggle : function(image, target, options) {
		if (!(image = $(image)))
			return;
		if (!(target = $(target))) {
			target = image.up().next();
		}
		var src = image.src;
		if (!src)
			return;

		image.setStyle("cursor:pointer;border:0px");
		options = Object.extend({
			cookie : true,
			open : true,
			onShow : Prototype.emptyFunction,
			onHide : Prototype.emptyFunction
		}, options);

		var p = src.lastIndexOf("/") + 1;
		var path = src.substring(0, p);
		var file = src.substring(p);
		var doImage = function() {
			if (target.visible()) {
				if (!file.startsWith("p_")) {
					file = "p_" + file;
				}
			} else {
				if (file.startsWith("p_")) {
					file = file.substring(2);
				}
			}
			image.src = path + file;
			if (options.cookie) {
				document.setCookie("toggle_" + image.identify(), target.visible());
			}
		};
		if (options.cookie) {
			var s = document.getCookie("toggle_" + image.identify());
			if (s == "true") {
				target.show();
			} else if (s == "false") {
				target.hide();
			} else {
				if (options.open) {
					target.show();
				} else {
					target.hide();
				}
			}
		}
		image.observe("click", function() {
			if (target.visible()) {
				target.hide();
				doImage();
				if (options.onHide)
					options.onHide();
			} else {
				target.show();
				doImage();
				if (options.onShow)
					options.onShow();
			}
		});
		doImage();
	},
	
	/**
	 * 加入背景文字
	 */
	addBackgroundTitle : function(txt, TITLE) {
		txt = $(txt);
		if (!txt)
			return;
		if ("placeholder" in txt) {
			txt.setAttribute("placeholder", TITLE);
			return;
		}
		var c = txt.getStyle("color");
		var f = function(ev) {
			if ($F(txt) == "") {
				txt.setValue(TITLE);
			}
			if ($F(txt) == TITLE) {
				txt.setStyle("color: #ddd;");
			}
		};
		txt.observe("focus", function(ev) {
			if ($F(txt) == TITLE) {
				txt.setValue("");
				txt.setStyle("color:" + c);
			}
		});
		txt.observe("blur", f);
		f();
	},

	addReturnEvent : function(txt, func) {
		txt.observe("keypress", function(ev) {
			if (((ev.which) ? ev.which : ev.keyCode) != Event.KEY_RETURN) {
				return;
			}
			if (func)
				func(ev);
		});
	},

	/**
	 * 使容器支持拖拽
	 */
	enableDragDrop : function(container, hoverclass, callback) {
		if (!Object.isArray(container)) {
			container = $(container);
			if (!container)
				return;
			container = container.select(".drag_image");
		}
		callback = callback || {};
		container.each(function(a) {
			new Draggable(a, {
				revert : true,
				scroll : callback.scroll || window,
				onStart : function() {
					a.addClassName("drag_tooltip");
					// backup drops
					a.drops = Droppables.drops.clone();
					Droppables.drops = Droppables.drops.reject(function(d) {
						return d.hoverclass != hoverclass;
					});
					if (callback.onStart)
						callback.onStart(a);
				},
				onEnd : function() {
					a.removeClassName("drag_tooltip");
					a.update("");
					Droppables.drops = a.drops;
					if (callback.onEnd)
						callback.onEnd(a);
				}
			});
		});
	},

	shakeMsg : function(o, msg) {
		o.innerHTML = msg;
		o.$shake({
			afterFinish : function() {
				o.innerHTML = "";
			}.delay(5)
		});
	}
});

Object.extend($UI, {
	createLink : function(text, options, click) {
		options = Object.extend({
			href : (Browser.IEVersion && Browser.IEVersion <= 6.0) ? "###"
					: "javascript:void(0);"
		}, options || {});
		if (Browser.IEVersion && Browser.IEVersion <= 7.0) {
			options = Object.extend(options, {
				hideFocus : true
			});
		}
		var a = new Element("a", options);
		if (text)
			a.update(text);
		if (click)
			a.observe("click", click);
		return a;
	},

	createTable : function(options) {
		options = Object.extend({
			width : "100%",
			cellpadding : "0",
			cellspacing : "0"
		}, options || {});
		return new Element("table", options).insert(new Element("tbody"));
	},

	createSplitbar : function(bar, left, callback) {
		if (!(bar = $(bar)) || !(left = $(left))) {
			return;
		}
		var p, w, ow = left.getWidth();
		bar.observe("dblclick", function(evt) {
			left.setStyle("width: " + ow + "px");
		});
		var mousemove = function(evt) {
			if (!p)
				return;
			var nw = w - p.x + evt.pointer().x;
			if (nw < 50 || nw > (document.viewport.getWidth() - 50))
				return;
			left.setStyle("width: " + nw + "px");
			$call(callback, nw);
			bar.fire("size:splitbar");
			Event.stop(evt);
		};
		var mouseup = function(evt) {
			if (!p)
				return;
			$(document.body).fixOnSelectStart(true);
			document.stopObserving("mousemove", mousemove);
			document.stopObserving("mouseup", mouseup);
			p = null;
		};
		bar.observe("mousedown", function(evt) {
			$(document.body).fixOnSelectStart();
			p = evt.pointer();
			w = left.getWidth();
			document.observe("mousemove", mousemove);
			document.observe("mouseup", mouseup);
		});
	},

	createButtonInputField : function(textId, bFunc) {
		var element = new Element("div", {
			className : "text text_button"
		});

		element.textInput = new Element("input", {
			type : "text"
		});
		if (textId) {
			element.textInput.id = element.textInput.name = textId;
		}
		element.textButton = new Element("div", {
			className : "sbtn"
		}).observe("click", function(ev) {
			if (bFunc)
				bFunc(ev);
		});
		return element.insert(new Element("div", {
			className : "d1"
		}).insert(new Element("div", {
			className : "d2"
		}).insert(element.textInput))).insert(element.textButton);
	},

	createFileInputField : function() {
		var tb = this.createButtonInputField();
		var f = new Element("input", {
			type : "file",
			hidefocus : "hidefocus",
			size : "1"
		}).observe("change", function(e) {
			tb.textInput.value = f.value;
		});
		
		tb.textInput.observe("change", function(e) {
			if (tb.textInput.value == '') {
				var up = f.up();
				new Element("form").insert(f).reset();
				up.insert(f);
			}
		});

		var ele = new Element("div", {
			className : "text_file_button"
		}).insert(tb).insert(f);
		ele.file = f;
		ele.textButton = tb;
		return ele;
	},

	createSearchInputField : function(sFunc, aFunc, inputText, inputWidth,
			buttonText) {
		if (!inputText)
			inputText = $MessageConst["Text.Search"];
		if (!buttonText)
			buttonText = $MessageConst["Button.Search"];
		var sp = new Element("SPAN", {
			className : "sech_pane"
		});
		var t1 = new Element("input", {
			className : "txt",
			type : "text"
		});
		this.addBackgroundTitle(t1, inputText);
		if ((inputWidth = parseInt(inputWidth)) > 0) {
			t1.setStyle("width:" + inputWidth + "px");
		}
		
		var clickCallback;
		this.addReturnEvent(t1, clickCallback = function(ev) {
			var v = $F(t1);
			if (v == "" || v == inputText) {
				return;
			}
			if (sFunc)
				sFunc(sp, ev);
		});
		
		var b1 = new Element("SPAN", {
			className : "br"
		}).observe("click", clickCallback).update(buttonText);
		var b2 = new Element("SPAN", {
			className : "ar"
		}).observe("click", function(ev) {
			if (aFunc)
				aFunc(sp, ev);
		});
		return sp.insert(t1).insert(new Element("SPAN", {
			className : "btn"
		}).insert(b1).insert(b2));
	},

	createMultiSelectInputField : function(textId, bFunc) {
		var r = new Element("div", {
			className : "multi_dselect"
		});	
		var hidden = new Element("input", {
			type : "hidden"
		});
		if (textId) {
			hidden.id = hidden.name = textId;
		}
		
		r.insert(hidden).insert(r.textButton = new Element("SPAN", {
			className : "sbtn"
		}).observe("click", function(ev) {
			if (bFunc)
				bFunc(r, ev);
		}));
		
		var updateHidden = function() {	
			hidden.value = r.select(".item").inject([], function(ret, o) {
				ret.push(o.id);
				return ret;
			}).join(";");
		};
		
		r.insertItem = function(id, txt) {
			if (r.select(".item").find(function(o) {
		    return o.id == id;
			})) {
				return;
			}
			var item;
			r.insert(item = new Element("SPAN", {
				className : "item",
				id : id
			}).update(txt).insert(new Element("SPAN", {
				className : "del"
			}).observe("click", function(ev) {
				item.remove();
				updateHidden();
			})));
			updateHidden();
		};
		return r;
	}
});

Object.extend($UI, {
	hackCheckbox : function(selector) {
		$Elements(selector).each(
				function(c) {
					c.select("input[type=checkbox]").invoke("observe", "click",
							function(evn) {
								evn.stopPropagation();
							});
				});
	},

	fixMaxWidth : function(selector, maxWidth) {
		$Elements(selector).each(function(c) {
			c.select("img, table").each(function(ele) {
				if (ele.getWidth() > maxWidth) {
					ele.wrap(new Element("DIV", {
						style : "width: " + maxWidth + "px; overflow-x:auto;"
					}));
				}
			});
		});
	}
});
