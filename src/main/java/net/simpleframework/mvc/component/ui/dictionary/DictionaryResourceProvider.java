package net.simpleframework.mvc.component.ui.dictionary;

import net.simpleframework.common.coll.ArrayUtils;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.IComponentRegistry;
import net.simpleframework.mvc.component.IComponentResourceProvider.AbstractComponentResourceProvider;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.AbstractDictionaryTypeBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryListBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryTreeBean;
import net.simpleframework.mvc.component.ui.listbox.ListboxRegistry;
import net.simpleframework.mvc.component.ui.tooltip.TooltipRegistry;
import net.simpleframework.mvc.component.ui.tree.TreeRegistry;
import net.simpleframework.mvc.component.ui.window.WindowRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class DictionaryResourceProvider extends AbstractComponentResourceProvider {

	public DictionaryResourceProvider(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String[] getDependentComponents(final PageParameter pParameter) {
		String[] arr = new String[] { WindowRegistry.window };
		boolean tree = false, list = false, tooltip = false;
		for (final AbstractComponentBean componentBean : pParameter.getComponentBeans().values()) {
			if (componentBean instanceof DictionaryBean) {
				final DictionaryBean dict = (DictionaryBean) componentBean;
				if (!tooltip) {
					tooltip = dict.isShowHelpTooltip();
				}
				final AbstractDictionaryTypeBean dictType = dict.getDictionaryTypeBean();
				if (dictType instanceof DictionaryTreeBean) {
					tree = true;
				} else if (dictType instanceof DictionaryListBean) {
					list = true;
				}
			}
		}
		if (tree) {
			arr = ArrayUtils.add(arr, TreeRegistry.tree);
		}
		if (list) {
			arr = ArrayUtils.add(arr, ListboxRegistry.listbox);
		}
		if (tooltip) {
			arr = ArrayUtils.add(arr, TooltipRegistry.tooltip);
		}
		return arr;
	}

	@Override
	public String[] getCssPath(final PageParameter pParameter) {
		return new String[] { getCssResourceHomePath(pParameter, DictionaryBean.class)
				+ "/dictionary.css" };
	}
}
