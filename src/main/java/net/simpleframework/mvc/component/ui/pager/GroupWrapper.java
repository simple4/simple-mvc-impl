package net.simpleframework.mvc.component.ui.pager;

import java.io.Serializable;
import java.util.List;

import net.simpleframework.common.StringUtils;
import net.simpleframework.common.html.js.JavascriptUtils;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class GroupWrapper implements Serializable {
	private String name;

	private String description;

	public GroupWrapper(final String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getLeftTitle(final List<Object> data) {
		final StringBuilder sb = new StringBuilder();
		final String desc = getDescription();
		boolean title;
		if (title = StringUtils.hasText(desc)) {
			sb.append("<div title=\"").append(JavascriptUtils.escape(desc)).append("\">");
		}
		sb.append(getName());
		if (title) {
			sb.append("</div>");
		}
		return sb.toString();
	}

	public String getRightTitle(final List<Object> data) {
		final StringBuilder sb = new StringBuilder();
		sb.append("( ");
		sb.append(data.size()).append(" )");
		return sb.toString();
	}

	private static final long serialVersionUID = 1080241521644844455L;
}
