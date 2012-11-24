package net.simpleframework.mvc.component.ui.window;

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
public class WindowBean extends AbstractComponentBean {
	private String contentRef;

	private int top, left, width = 400, height = 300, minWidth = 200, minHeight = 100, maxWidth,
			maxHeight;

	private boolean destroyOnClose = true;

	private boolean resizable = true, draggable = true, minimize, maximize, modal = true;

	private boolean showCenter = true;

	private boolean popup = false;

	private String url;

	private String title;

	private String content;

	private String contentStyle = "padding: 4px;";

	private boolean singleWindow = true;

	private String jsShownCallback, jsHiddenCallback;

	public WindowBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
		setRunImmediately(false);
	}

	public WindowBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public String getContentRef() {
		return contentRef;
	}

	public WindowBean setContentRef(final String contentRef) {
		this.contentRef = contentRef;
		return this;
	}

	public boolean isPopup() {
		return popup;
	}

	public WindowBean setPopup(final boolean popup) {
		this.popup = popup;
		return this;
	}

	public int getTop() {
		return top;
	}

	public WindowBean setTop(final int top) {
		this.top = top;
		return this;
	}

	public int getLeft() {
		return left;
	}

	public WindowBean setLeft(final int left) {
		this.left = left;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public WindowBean setWidth(final int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public WindowBean setHeight(final int height) {
		this.height = height;
		return this;
	}

	public int getMinWidth() {
		return minWidth;
	}

	public WindowBean setMinWidth(final int minWidth) {
		this.minWidth = minWidth;
		return this;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public WindowBean setMinHeight(final int minHeight) {
		this.minHeight = minHeight;
		return this;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public WindowBean setMaxWidth(final int maxWidth) {
		this.maxWidth = maxWidth;
		return this;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public WindowBean setMaxHeight(final int maxHeight) {
		this.maxHeight = maxHeight;
		return this;
	}

	public boolean isResizable() {
		return resizable;
	}

	public WindowBean setResizable(final boolean resizable) {
		this.resizable = resizable;
		return this;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public WindowBean setDraggable(final boolean draggable) {
		this.draggable = draggable;
		return this;
	}

	public boolean isDestroyOnClose() {
		return destroyOnClose;
	}

	public WindowBean setDestroyOnClose(final boolean destroyOnClose) {
		this.destroyOnClose = destroyOnClose;
		return this;
	}

	public boolean isMinimize() {
		return minimize;
	}

	public WindowBean setMinimize(final boolean minimize) {
		this.minimize = minimize;
		return this;
	}

	public boolean isMaximize() {
		return maximize;
	}

	public WindowBean setMaximize(final boolean maximize) {
		this.maximize = maximize;
		return this;
	}

	public boolean isModal() {
		return modal;
	}

	public WindowBean setModal(final boolean modal) {
		this.modal = modal;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public WindowBean setUrl(final String url) {
		this.url = url;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public WindowBean setTitle(final String title) {
		this.title = title;
		return this;
	}

	public String getContent() {
		return content;
	}

	public WindowBean setContent(final String content) {
		this.content = content;
		return this;
	}

	public String getContentStyle() {
		return contentStyle;
	}

	public WindowBean setContentStyle(final String contentStyle) {
		this.contentStyle = contentStyle;
		return this;
	}

	public boolean isShowCenter() {
		return showCenter;
	}

	public WindowBean setShowCenter(final boolean showCenter) {
		this.showCenter = showCenter;
		return this;
	}

	public boolean isSingleWindow() {
		return singleWindow;
	}

	public WindowBean setSingleWindow(final boolean singleWindow) {
		this.singleWindow = singleWindow;
		return this;
	}

	public String getJsShownCallback() {
		return jsShownCallback;
	}

	public WindowBean setJsShownCallback(final String jsShownCallback) {
		this.jsShownCallback = jsShownCallback;
		return this;
	}

	public String getJsHiddenCallback() {
		return jsHiddenCallback;
	}

	public WindowBean setJsHiddenCallback(final String jsHiddenCallback) {
		this.jsHiddenCallback = jsHiddenCallback;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "jsShownCallback", "jsHiddenCallback", "content" };
	}
}
