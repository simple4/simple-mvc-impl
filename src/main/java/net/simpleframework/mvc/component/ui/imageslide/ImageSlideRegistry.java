package net.simpleframework.mvc.component.ui.imageslide;

import java.util.Collection;
import java.util.Iterator;

import net.simpleframework.common.script.IScriptEval;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(ImageSlideRegistry.imageSlide)
@ComponentBean(ImageSlideBean.class)
@ComponentRender(ImageSlideRender.class)
@ComponentResourceProvider(ImageSlideResourceProvider.class)
public class ImageSlideRegistry extends AbstractComponentRegistry {
	public static final String imageSlide = "imageSlide";

	@Override
	public ImageSlideBean createComponentBean(final PageParameter pParameter, final Object data) {
		final ImageSlideBean imageSlide = (ImageSlideBean) super
				.createComponentBean(pParameter, data);
		if (data instanceof XmlElement) {
			final IScriptEval scriptEval = pParameter.getScriptEval();
			final Iterator<?> it = ((XmlElement) data).elementIterator("imageitem");
			final Collection<ImageItem> coll = imageSlide.getImageItems();
			while (it.hasNext()) {
				final XmlElement xmlElement = (XmlElement) it.next();
				final ImageItem imageItem = new ImageItem(xmlElement);
				imageItem.parseElement(scriptEval);
				coll.add(imageItem);
			}
		}
		return imageSlide;
	}
}
