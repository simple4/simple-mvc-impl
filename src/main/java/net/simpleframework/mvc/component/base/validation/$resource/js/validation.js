/**
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
Validation = Class.create();

Validation.insertAfter = function(ele, message) {
	ele = $(ele);
	if (!ele)
		return;
	if (!ele.insertAfter) {
		ele.insertAfter = new Element("div", {
			className : "validation_insertAfter"
		}).update(message);
		ele.insert({
			after : ele.insertAfter
		});
	} else {
		ele.insertAfter.update(message);
	}
};

Validation.clearInsert = function(ele) {
	$Elements(ele).each(function(e) {
		e = $(e);
		if (!e)
			return;
		if (e.insertAfter) {
			e.insertAfter.remove();
			e.insertAfter = null;
		}
	});
};

Validation.format = function(str, args) {
	args = $A(args || []);
	var result = str.replace(/%a/, args);
	args.each(function(i) {
		result = result.replace(/%s/, i);
	});
	return result;
};

Validation.isEmpty = function(v) {
	return ((v == null) || (v.length == 0));
};

Validation.isNumber = function(v) {
	return (!isNaN(v) && !/^\s+$/.test(v));
};

Validation.great_less_than = function(v, args, great) {
	if (args.length > 0) {
		var arr = $$(args[0]);
		var t = arr.length > 0 ? $F(arr[0]) : args[0];
		if (t == "" || v == "")
			return true;
		t = parseFloat(t);
		if (!Validation.isNumber(t))
			return false;
		v = parseFloat(v);
		if (!Validation.isNumber(v))
			return false;
		return args[1] ? (great ? t <= v : t >= v) : (great ? t < v : t > v);
	} else {
		return false;
	}
};

Validation.methods = {
	'required' : function(v, args) {
		return !Validation.isEmpty(v);
	},
	'email' : function(v, args) {
		return Validation.isEmpty(v)
				|| /\w{1,}[@][\w\-]{1,}([.]([\w\-]{1,})){1,3}$/.test(v);
	},
	'equals' : function(v, args) {
		if (args.length > 0) {
			var ele = $(args[0]);
			return ele ? $F(ele) == v : args[0] == v;
		} else {
			return false;
		}
	},
	'less_than' : function(v, args) {
		return Validation.great_less_than(v, args);
	},
	'great_than' : function(v, args) {
		return Validation.great_less_than(v, args, true);
	},
	'date' : function(v, args) {
		if (Validation.isEmpty(v))
			return true;
		args[1] = new Date().format(args[0]);
		return (args.length > 0 && Date.isValid(v, args[0]));
	},
	'file' : function(v, args) {
		return Validation.isEmpty(v) || args.any(function(name) {
			return new RegExp('\\.' + name + '$', 'i').test(v);
		});
	},
	'mobile_phone' : function(v, args) {
		args[0] = v.length;
		return Validation.isEmpty(v) || /(^0?[1][385][0-9]{9}$)/.test(v);
	},
	'phone' : function(v, args) {
		args[0] = v.length;
		return Validation.isEmpty(v)
				|| /^((0[1-9]{3})?(0[12][0-9])?[-])?\d{6,8}$/.test(v);
	},
	'number' : function(v, args) {
		return Validation.isEmpty(v) || Validation.isNumber(v);
	},
	'digits' : function(v, args) {
		return Validation.isEmpty(v) || !/[^\d]/.test(v);
	},
	'alpha' : function(v, args) {
		return Validation.isEmpty(v) || /^[a-zA-Z]+$/.test(v);
	},
	'alphanum' : function(v, args) {
		return Validation.isEmpty(v) || !/\W/.test(v);
	},
	'url' : function(v, args) {
		return Validation.isEmpty(v)
				|| /^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i
						.test(v);
	},
	'min_value' : function(v, args) {
		return Validation.isEmpty(v)
				|| (args.length > 0 && parseFloat(v) >= parseFloat(args[0]));
	},
	'max_value' : function(v, args) {
		return Validation.isEmpty(v)
				|| (args.length > 0 && parseFloat(v) <= parseFloat(args[0]));
	},
	'min_length' : function(v, args) {
		return Validation.isEmpty(v)
				|| (args.length > 0 && (args[1] = v.length) >= parseInt(args[0]));
	},
	'max_length' : function(v, args) {
		return Validation.isEmpty(v)
				|| (args.length > 0 && (args[1] = v.length) <= parseInt(args[0]));
	},
	'int_range' : function(v, args) {
		return Validation.isEmpty(v)
				|| (args.length > 1 && parseInt(v) >= parseInt(args[0]) && parseInt(v) <= parseInt(args[1]));
	},
	'float_range' : function(v, args) {
		return Validation.isEmpty(v)
				|| (args.length > 1 && parseFloat(v) >= parseFloat(args[0]) && parseFloat(v) <= parseFloat(args[1]));
	},
	'length_range' : function(v, args) {
		return Validation.isEmpty(v)
				|| (args.length > 1 && (args[2] = v.length) >= parseInt(args[0]) && args[2] <= parseInt(args[1]));
	},
	'chinese' : function(v, args) {
		return Validation.isEmpty(v) || /^[\u4e00-\u9fa5]+$/.test(v);
	},
	'ip' : function(v, args) {
		return Validation.isEmpty(v)
				|| /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/
						.test(v);
	}
};

Validation.prototype = {
	argsDelimiter : ',',

	initialize : function(trigger, validators, options) {
		this.validators = validators || [];

		trigger = $$(trigger);
		if (!trigger)
			return;
		$A(trigger).each(function(t) {
			if (!t._onclick) {
				t._onclick = t.onclick;
			}
			if (!t.observes) {
				var registry = Element.retrieve(t, 'prototype_event_registry');
				if (registry) {
					t.observes = registry.get('click');
					Event.stopObserving(t, 'click');
				}
			}
			t.onclick = (function(ev) {
				ev = ev ? ev : event;
				if (!this.validate()) {
					Event.stop(ev);
					return;
				}
				if (t._onclick)
					t._onclick(ev);
				if (t.observes) {
					for ( var i = 0; i < t.observes.length; i++) {
						t.observes[i](ev);
					}
				}
			}).bind(this);
		}.bind(this));

		this.options = Object.extend({
			warnType : 'alert' // 'alert', 'insertAfter'
		}, options);
	},

	add : function(v) {
		if (!v.method)
			return;
		if (!Object.isString(v.selector))
			return;
		this.validators.push(v);
	},

	getMethod : function(name) {
		return Validation.methods[name] ? Validation.methods[name]
				: Prototype.emptyFunction;
	},

	validate : function() {
		return !this.validators.find((function(validator) {
			var args = validator['args'] ? validator['args']
					.split(this.argsDelimiter) : [];
			var method = validator['method'];

			return $$(validator.selector).find(
				(function(ele) {
					if (ele.disabled == true) {
						return;
					}
					var fv;
					if (ele.tagName == "TEXTAREA" && ele.htmlEditor) {
						var htmlEditor = ele.htmlEditor;
						fv = ele.htmlEditor.getData();
						if (!fv.toLowerCase().include("src"))
							fv = fv.stripTags();
						fv = fv.replace(/&nbsp;/g, '').strip();
						ele = ele.next();
						ele.htmlEditor = htmlEditor;
					} else {
						if (!ele.$visible()) {
							return;
						}
						fv = $F(ele);
					}
					if (!this.getMethod(method)(fv, args)) {
						var message = Validation.format(validator['message']
								|| Validation_TT[method], args);
						ele.$highlight("#FFEEEE");
						var wt = validator['warnType'] || this.options.warnType;
						if (wt == 'alert') {
							alert(message);
							ele.title = message;
						} else if (wt == 'insertAfter') {
							Validation.insertAfter(ele, message);
							ele.insertAfter.$shake();
						}
						if (ele.htmlEditor) {
							ele.htmlEditor.focus();
						} else {
							ele.activate();
						}
						return true;
					} else {
						// ele.removeClassName("validation_highlight");
						ele.title = '';
						Validation.clearInsert(ele);
					}
				}).bind(this));
		}).bind(this));
	}
};