package net.simpleframework.mvc.component.ui.imageslide;

import java.util.Collection;

import net.simpleframework.common.Convert;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.html.js.JavascriptUtils;
import net.simpleframework.mvc.component.AbstractComponentRender.ComponentJavascriptRender;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRenderUtils;
import net.simpleframework.mvc.component.IComponentRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class ImageSlideRender extends ComponentJavascriptRender {

	public ImageSlideRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final ImageSlideBean imageSlide = (ImageSlideBean) cParameter.componentBean;
		Collection<ImageItem> items;
		final IImageSlideHandler handle = (IImageSlideHandler) cParameter.getComponentHandler();
		if (handle != null) {
			items = handle.getImageItems(cParameter);
		} else {
			items = imageSlide.getImageItems();
		}
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentRenderUtils.initContainerVar(cParameter));
		sb.append(actionFunc).append(".imageSlide = new $UI.ImageSlide(")
				.append(ComponentRenderUtils.VAR_CONTAINER).append(", [");
		if (items != null) {
			int i = 0;
			for (final ImageItem item : items) {
				if (i++ > 0) {
					sb.append(",");
				}
				sb.append("{");
				final String link = item.getLink();
				if (StringUtils.hasText(link)) {
					sb.append("link: \"").append(cParameter.wrapContextPath(link)).append("\",");
				}
				sb.append("imageUrl: \"").append(cParameter.wrapContextPath(item.getImageUrl()))
						.append("\",");
				sb.append("title: \"").append(JavascriptUtils.escape(item.getTitle())).append("\"");
				sb.append("}");
			}
		}
		sb.append("], {");

		final int titleHeight = Convert.toInt(cParameter.getBeanProperty("titleHeight"));
		if (titleHeight > 0) {
			sb.append("titleHeight: ").append(titleHeight).append(",");
		}
		sb.append("titleOpacity: ").append(cParameter.getBeanProperty("titleOpacity")).append(",");
		sb.append("frequency: ").append(cParameter.getBeanProperty("frequency")).append(",");
		sb.append("showNextAction: ").append(cParameter.getBeanProperty("showNextAction"))
				.append(",");
		sb.append("showPreAction: ").append(cParameter.getBeanProperty("showPreAction")).append(",");
		sb.append("autoStart: ").append(cParameter.getBeanProperty("autoStart")).append(",");
		sb.append("start: ").append(cParameter.getBeanProperty("start")).append(",");
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "height"));
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "width"));
		sb.append("effects: Browser.effects && ").append(cParameter.getBeanProperty("effects"));
		sb.append("});");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}
}
