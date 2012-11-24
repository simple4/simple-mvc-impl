package net.simpleframework.mvc.component.ui.colorpalette;

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
public class ColorPaletteBean extends AbstractContainerBean {

	private EColorMode startMode;

	private String startHex = "CCCCCC";

	private String changeCallback;

	public ColorPaletteBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public ColorPaletteBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public EColorMode getStartMode() {
		return startMode == null ? EColorMode.h : startMode;
	}

	public void setStartMode(final EColorMode startMode) {
		this.startMode = startMode;
	}

	public String getStartHex() {
		return startHex;
	}

	public void setStartHex(final String startHex) {
		this.startHex = startHex;
	}

	public String getChangeCallback() {
		return changeCallback;
	}

	public void setChangeCallback(final String changeCallback) {
		this.changeCallback = changeCallback;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "changeCallback" };
	}
}
