package net.simpleframework.mvc.component.base.jspinclude;

import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageDocument;
import net.simpleframework.mvc.component.AbstractContainerBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class PageIncludeBean extends AbstractContainerBean {
	private String pageUrl;

	public PageIncludeBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public PageIncludeBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public PageIncludeBean setPageUrl(final String pageUrl) {
		this.pageUrl = pageUrl;
		return this;
	}
}
