package net.simpleframework.mvc.component.ui.colorpalette;

import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentHtmlRenderEx;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(ColorPaletteRegistry.colorpalette)
@ComponentBean(ColorPaletteBean.class)
@ComponentRender(ColorPaletteRender.class)
@ComponentResourceProvider(ColorPaletteResourceProvider.class)
public class ColorPaletteRegistry extends AbstractComponentRegistry {
	public static final String colorpalette = "colorpalette";

	@Override
	public AbstractComponentBean createComponentBean(final PageParameter pParameter,
			final Object data) {
		final ColorPaletteBean colorPalette = (ColorPaletteBean) super.createComponentBean(
				pParameter, data);
		ComponentHtmlRenderEx.createAjaxRequest(ComponentParameter.get(pParameter, colorPalette));
		return colorPalette;
	}
}
