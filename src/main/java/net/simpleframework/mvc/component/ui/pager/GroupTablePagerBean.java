package net.simpleframework.mvc.component.ui.pager;

import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageDocument;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class GroupTablePagerBean extends TablePagerBean {
	private String groupColumn;

	public GroupTablePagerBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public GroupTablePagerBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public String getGroupColumn() {
		return groupColumn;
	}

	public GroupTablePagerBean setGroupColumn(final String groupColumn) {
		this.groupColumn = groupColumn;
		return this;
	}
}
