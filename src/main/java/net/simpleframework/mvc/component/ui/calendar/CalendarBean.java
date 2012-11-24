package net.simpleframework.mvc.component.ui.calendar;

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
public class CalendarBean extends AbstractContainerBean {

	private String inputField;

	private String dateFormat = "yyyy-MM-dd";

	private boolean showTime;

	private String closeCallback;

	public CalendarBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public CalendarBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public String getInputField() {
		return inputField;
	}

	public CalendarBean setInputField(final String inputField) {
		this.inputField = inputField;
		return this;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public CalendarBean setDateFormat(final String dateFormat) {
		this.dateFormat = dateFormat;
		return this;
	}

	public boolean isShowTime() {
		return showTime;
	}

	public CalendarBean setShowTime(final boolean showTime) {
		this.showTime = showTime;
		return this;
	}

	public String getCloseCallback() {
		return closeCallback;
	}

	public CalendarBean setCloseCallback(final String closeCallback) {
		this.closeCallback = closeCallback;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "closeCallback" };
	}
}
