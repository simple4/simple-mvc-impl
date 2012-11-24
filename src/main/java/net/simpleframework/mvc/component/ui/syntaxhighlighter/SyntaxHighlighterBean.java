package net.simpleframework.mvc.component.ui.syntaxhighlighter;

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
public class SyntaxHighlighterBean extends AbstractComponentBean {
	private ESyntaxHighlighterTheme shTheme;

	private String jsSelectedCallback; // editor

	public SyntaxHighlighterBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
		setHandleClass(DefaultSyntaxHighlighterHandler.class);
	}

	public SyntaxHighlighterBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public ESyntaxHighlighterTheme getShTheme() {
		return shTheme == null ? ESyntaxHighlighterTheme.shThemeDefault : shTheme;
	}

	public void setShTheme(final ESyntaxHighlighterTheme shTheme) {
		this.shTheme = shTheme;
	}

	public String getJsSelectedCallback() {
		return jsSelectedCallback;
	}

	public void setJsSelectedCallback(final String jsSelectedCallback) {
		this.jsSelectedCallback = jsSelectedCallback;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "jsSelectedCallback" };
	}
}
