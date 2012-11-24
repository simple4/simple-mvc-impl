/**
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
$UI.ValidateCode = Class.create({
	initialize : function(container, options) {
		this.options = {
			path : "",
			trigger : null,
			textWidth : 70
		};
		Object.extend(this.options, options || {});

		this.container = $(container);
		container.setDimensions(this.options);

		var tr = new Element("tr");
		this.container.update($UI.createTable());
		this.container.down("tbody").insert(tr);
		var td1 = new Element("td", {
			width : this.options.textWidth + "px;"
		});
		var td2 = new Element("td");
		tr.insert(td1).insert(td2);

		var t = new Element("input", {
			type : "text",
			style : "width:80%;"
		});
		var tn = this.options.textName;
		if (tn) {
			t.writeAttribute("id", tn);
			t.writeAttribute("name", tn);
		}
		td1.insert(t);
		var img = new Element("img", {
			src : this.options.path,
			className : "photo_icon"
		});
		td2.insert(img);
		td2.insert(new Element("div", {
			style : "padding-top: 3px;"
		}).insert($UI.createLink($MessageConst["ValidateCode.0"], null,
				function() {
					this.refresh();
				}.bind(this))));
	},

	refresh : function() {
		var e; 
		if (Browser.IE && window.onbeforeunload) {
			e = window.onbeforeunload;
			window.onbeforeunload = null;
			(function() {
				window.onbeforeunload = e;
			}).delay(2);
		}
		this.container.down("img").src = this.options.path.addParameter('tmp='
				+ (new Date().getTime().toString(36)));
		this.container.down("input").activate();
	}
});