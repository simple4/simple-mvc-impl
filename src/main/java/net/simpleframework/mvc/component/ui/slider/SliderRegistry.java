package net.simpleframework.mvc.component.ui.slider;

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
@ComponentName(SliderRegistry.slider)
@ComponentBean(SliderBean.class)
@ComponentRender(SliderRender.class)
@ComponentResourceProvider(SliderResourceProvider.class)
public class SliderRegistry extends AbstractComponentRegistry {
	public static final String slider = "slider";

}
