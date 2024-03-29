package net.simpleframework.mvc.component.ui.imageslide;

import net.simpleframework.common.xml.AbstractElementBean;
import net.simpleframework.common.xml.XmlElement;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class ImageItem extends AbstractElementBean {

	private String imageUrl;

	private String title;

	private String link;

	public ImageItem(final XmlElement xmlElement) {
		super(xmlElement);
	}

	public ImageItem(final String imageUrl, final String link, final String title) {
		super(null);
		this.imageUrl = imageUrl;
		this.link = link;
		this.title = title;
	}

	public ImageItem(final String imageUrl) {
		this(imageUrl, null, null);
	}

	public ImageItem() {
		this(null, null, null);
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public ImageItem setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public ImageItem setTitle(final String title) {
		this.title = title;
		return this;
	}

	public String getLink() {
		return link;
	}

	public ImageItem setLink(final String link) {
		this.link = link;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "imageUrl", "title", "link" };
	}
}
