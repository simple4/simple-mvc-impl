package net.simpleframework.mvc.component.ui.listbox;

import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.IComponentRegistry;
import net.simpleframework.mvc.component.IComponentResourceProvider.AbstractComponentResourceProvider;
import net.simpleframework.mvc.component.ui.tooltip.TooltipRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class ListboxResourceProvider extends AbstractComponentResourceProvider {

	public ListboxResourceProvider(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String[] getDependentComponents(final PageParameter pParameter) {
		boolean tooltip = false;
		for (final AbstractComponentBean componentBean : pParameter.getComponentBeans().values()) {
			if (componentBean instanceof ListboxBean) {
				tooltip = ((ListboxBean) componentBean).isTooltip();
				if (tooltip) {
					break;
				}
			}
		}
		if (tooltip) {
			return new String[] { TooltipRegistry.tooltip };
		} else {
			return null;
		}
	}

	@Override
	public String[] getCssPath(final PageParameter pParameter) {
		return new String[] { getCssResourceHomePath(pParameter) + "/listbox.css" };
	}

	@Override
	public String[] getJavascriptPath(final PageParameter pParameter) {
		return new String[] { getResourceHomePath() + "/js/listbox.js" };
	}
}
