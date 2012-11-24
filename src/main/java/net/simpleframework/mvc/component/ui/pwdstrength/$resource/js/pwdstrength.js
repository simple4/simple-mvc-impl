/**
 * @author 陈侃(cknet@126.com, 13910090885)
 */
$UI.PwdStrength = Class.create( {
	initialize : function(container, options) {
		this.options = {
			pwdInput : null
		};
		Object.extend(this.options, options || {});

		container = $(container);
		container.setDimensions(this.options);

		container.insert($UI.createTable( {
			className : "pwdstrength"
		}));

		this.s = new Element("div", {
			className : "pwds"
		});
		this.m = new Element("td", {
			className : "pwdm"
		}).update($MessageConst["PwdStrength.1"]);
		var tr = new Element("tr");
		tr.insert(new Element("td", {
			width : "100px"
		}).insert(new Element("div", {
			className : "pwdc"
		}).insert(this.s))).insert(this.m);
		container.down("tbody").insert(tr);

		if (this.options.pwdInput) {
			var input = $(this.options.pwdInput);
			if (input) {
				input.observe("keyup", function() {
					this.update($F(input));
				}.bind(this));
			}
		}
	},

	update : function(passwd) {
		var score = 0;
		for ( var i = 0; i < passwd.length; i++) {
			var c = passwd.charAt(i);
			if (c >= 'a' && c <= 'z')
				score += 2;
			if (c >= '0' && c <= '9')
				score += 3;
			if (c >= 'A' && c <= 'Z')
				score += 4;
			if (c.match(/[!,@#$%^&*?_~]/))
				score += 5;
		}

		var width = score * 2.2;
		if (width > 98)
			width = 98;
		this.s.$style("width:" + width + "px", {
			effects : this.options.effects
		});
		if (score <= 0) {
			this.m.update($MessageConst["PwdStrength.1"]);
		} else {
			var i;
			if (score < 4)
				i = 0;
			else if (score <= 10)
				i = 1;
			else if (score <= 15)
				i = 2;
			else if (score <= 20)
				i = 3;
			else if (score <= 25)
				i = 4;
			else if (score <= 30)
				i = 5;
			else if (score <= 40)
				i = 6;
			else
				i = 7;
			this.m.update($MessageConst["PwdStrength.0"][i]);
		}
	}
});