package net.simpleframework.mvc.component.ui.chosen;

import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageDocument;
import net.simpleframework.mvc.component.AbstractComponentBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class ChosenBean extends AbstractComponentBean {
	/* 可查找 */
	private boolean enableSearch;

	public ChosenBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public ChosenBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public boolean isEnableSearch() {
		return enableSearch;
	}

	public ChosenBean setEnableSearch(final boolean enableSearch) {
		this.enableSearch = enableSearch;
		return this;
	}
}
