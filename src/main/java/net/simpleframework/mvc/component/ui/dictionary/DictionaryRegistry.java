package net.simpleframework.mvc.component.ui.dictionary;

import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.AbstractDictionaryTypeBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryColorBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryFontBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryListBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionarySmileyBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryTreeBean;
import net.simpleframework.mvc.component.ui.window.WindowRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(DictionaryRegistry.dictionary)
@ComponentBean(DictionaryBean.class)
@ComponentRender(DictionaryRender.class)
@ComponentResourceProvider(DictionaryResourceProvider.class)
public class DictionaryRegistry extends WindowRegistry {
	public static final String dictionary = "dictionary";

	@Override
	public DictionaryBean createComponentBean(final PageParameter pParameter, final Object data) {
		final DictionaryBean dictionaryBean = (DictionaryBean) super.createComponentBean(pParameter,
				data);
		final ComponentParameter nComponentParameter = ComponentParameter.get(pParameter,
				dictionaryBean);
		if (data instanceof XmlElement) {
			final XmlElement component = (XmlElement) data;
			AbstractDictionaryTypeBean type = null;
			XmlElement xmlElement = component.element("tree");
			if (xmlElement != null) {
				type = new DictionaryTreeBean(dictionaryBean, xmlElement);
			} else if ((xmlElement = component.element("list")) != null) {
				type = new DictionaryListBean(dictionaryBean, xmlElement);
			} else if ((xmlElement = component.element("color")) != null) {
				type = new DictionaryColorBean(dictionaryBean, xmlElement);
			} else if ((xmlElement = component.element("font")) != null) {
				type = new DictionaryFontBean(dictionaryBean, xmlElement);
			} else if ((xmlElement = component.element("smiley")) != null) {
				type = new DictionarySmileyBean(dictionaryBean, xmlElement);
			}
			if (type != null) {
				type.parseElement(pParameter.getScriptEval());
				dictionaryBean.setDictionaryTypeBean(nComponentParameter, type);
			}
		}
		// 虚拟一个AjaxRequestBean
		createAjaxRequest(nComponentParameter);
		return dictionaryBean;
	}

	private AjaxRequestBean createAjaxRequest(final ComponentParameter cParameter) {
		final DictionaryBean dictionaryBean = (DictionaryBean) cParameter.componentBean;

		final String ajaxRequestName = "ajaxRequest_" + dictionaryBean.hashId();
		final AjaxRequestBean ajaxRequest = (AjaxRequestBean) cParameter
				.addComponentBean(ajaxRequestName, AjaxRequestBean.class).setShowLoading(false)
				.setParameters(DictionaryUtils.BEAN_ID + "=" + dictionaryBean.hashId())
				.setIncludeRequestData("pa").setHandleClass(DictionaryAction.class)
				.setAttr("$$dictionary", dictionaryBean);
		cParameter.addComponentBean(ajaxRequest);

		dictionaryBean.setContentRef(ajaxRequestName)
				.setContent(AbstractComponentRegistry.getLoadingContent())
				.setAttr("$$ajaxRequest", ajaxRequest);
		return ajaxRequest;
	}
}
