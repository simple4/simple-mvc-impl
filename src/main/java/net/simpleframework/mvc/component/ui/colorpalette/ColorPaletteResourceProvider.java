package net.simpleframework.mvc.component.ui.colorpalette;

import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.IComponentRegistry;
import net.simpleframework.mvc.component.IComponentResourceProvider.AbstractComponentResourceProvider;
import net.simpleframework.mvc.component.ui.slider.SliderRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class ColorPaletteResourceProvider extends AbstractComponentResourceProvider {

	public ColorPaletteResourceProvider(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String[] getDependentComponents(final PageParameter pParameter) {
		return new String[] { SliderRegistry.slider };
	}

	@Override
	public String[] getCssPath(final PageParameter pParameter) {
		return new String[] { getCssResourceHomePath(pParameter) + "/colorpalette.css" };
	}

	@Override
	public String[] getJavascriptPath(final PageParameter pParameter) {
		final String rPath = getResourceHomePath();
		return new String[] { rPath + "/js/colorutils.js", rPath + "/js/colorpalette.js" };
	}
}
