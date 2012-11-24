package net.simpleframework.mvc.component.ui.htmleditor;

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
public class HtmlEditorBean extends AbstractContainerBean {
	private EEditorToolbar toolbar;

	private String textarea;

	private boolean toolbarCanCollapse = true;

	private boolean resizeEnabled;

	private boolean startupFocus = true;

	private EEditorLineMode enterMode, shiftEnterMode;

	private String htmlContent;

	private String jsLoadedCallback;

	public HtmlEditorBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public HtmlEditorBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public EEditorToolbar getToolbar() {
		return toolbar == null ? EEditorToolbar.Basic : toolbar;
	}

	public HtmlEditorBean setToolbar(final EEditorToolbar toolbar) {
		this.toolbar = toolbar;
		return this;
	}

	public String getTextarea() {
		return textarea;
	}

	public HtmlEditorBean setTextarea(final String textarea) {
		this.textarea = textarea;
		return this;
	}

	public boolean isToolbarCanCollapse() {
		return toolbarCanCollapse;
	}

	public HtmlEditorBean setToolbarCanCollapse(final boolean toolbarCanCollapse) {
		this.toolbarCanCollapse = toolbarCanCollapse;
		return this;
	}

	public boolean isResizeEnabled() {
		return resizeEnabled;
	}

	public HtmlEditorBean setResizeEnabled(final boolean resizeEnabled) {
		this.resizeEnabled = resizeEnabled;
		return this;
	}

	public boolean isStartupFocus() {
		return startupFocus;
	}

	public HtmlEditorBean setStartupFocus(final boolean startupFocus) {
		this.startupFocus = startupFocus;
		return this;
	}

	public EEditorLineMode getEnterMode() {
		return enterMode == null ? EEditorLineMode.p : enterMode;
	}

	public HtmlEditorBean setEnterMode(final EEditorLineMode enterMode) {
		this.enterMode = enterMode;
		return this;
	}

	public EEditorLineMode getShiftEnterMode() {
		return shiftEnterMode == null ? EEditorLineMode.br : shiftEnterMode;
	}

	public HtmlEditorBean setShiftEnterMode(final EEditorLineMode shiftEnterMode) {
		this.shiftEnterMode = shiftEnterMode;
		return this;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public HtmlEditorBean setHtmlContent(final String htmlContent) {
		this.htmlContent = htmlContent;
		return this;
	}

	public String getJsLoadedCallback() {
		return jsLoadedCallback;
	}

	public HtmlEditorBean setJsLoadedCallback(final String jsLoadedCallback) {
		this.jsLoadedCallback = jsLoadedCallback;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "htmlContent", "jsLoadedCallback" };
	}
}
