package net.simpleframework.mvc.component.ui.imageslide;

import java.util.ArrayList;
import java.util.Collection;

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
public class ImageSlideBean extends AbstractContainerBean {

	private Collection<ImageItem> imageItems;

	private int titleHeight = 35;

	private float titleOpacity = 0.6f;

	private float frequency = 4f;

	private boolean autoStart = true;

	private int start = 0;

	private boolean showPreAction = true, showNextAction = true;

	public ImageSlideBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public ImageSlideBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public Collection<ImageItem> getImageItems() {
		if (imageItems == null) {
			imageItems = new ArrayList<ImageItem>();
		}
		return imageItems;
	}

	public int getTitleHeight() {
		return titleHeight;
	}

	public ImageSlideBean setTitleHeight(final int titleHeight) {
		this.titleHeight = titleHeight;
		return this;
	}

	public float getTitleOpacity() {
		return titleOpacity;
	}

	public ImageSlideBean setTitleOpacity(final float titleOpacity) {
		this.titleOpacity = titleOpacity;
		return this;
	}

	public float getFrequency() {
		return frequency;
	}

	public ImageSlideBean setFrequency(final float frequency) {
		this.frequency = frequency;
		return this;
	}

	public boolean isAutoStart() {
		return autoStart;
	}

	public ImageSlideBean setAutoStart(final boolean autoStart) {
		this.autoStart = autoStart;
		return this;
	}

	public int getStart() {
		return start;
	}

	public ImageSlideBean setStart(final int start) {
		this.start = start;
		return this;
	}

	public boolean isShowPreAction() {
		return showPreAction;
	}

	public ImageSlideBean setShowPreAction(final boolean showPreAction) {
		this.showPreAction = showPreAction;
		return this;
	}

	public boolean isShowNextAction() {
		return showNextAction;
	}

	public ImageSlideBean setShowNextAction(final boolean showNextAction) {
		this.showNextAction = showNextAction;
		return this;
	}
}
