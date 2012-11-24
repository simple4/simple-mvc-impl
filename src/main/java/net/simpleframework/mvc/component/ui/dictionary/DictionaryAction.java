package net.simpleframework.mvc.component.ui.dictionary;

import net.simpleframework.common.NotImplementedException;
import net.simpleframework.mvc.IForward;
import net.simpleframework.mvc.UrlForward;
import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentUtils;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;
import net.simpleframework.mvc.component.base.ajaxrequest.DefaultAjaxRequestHandler;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.AbstractDictionaryTypeBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryColorBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryFontBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryListBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionarySmileyBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryTreeBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class DictionaryAction extends DefaultAjaxRequestHandler {

	@Override
	public Object getBeanProperty(final ComponentParameter cParameter, final String beanProperty) {
		final AjaxRequestBean ajaxRequest = (AjaxRequestBean) cParameter.componentBean;

		final ComponentParameter nComponentParameter = ComponentParameter.get(cParameter,
				(AbstractComponentBean) ajaxRequest.getAttr("$$dictionary"));
		if ("selector".equals(beanProperty)) {
			return nComponentParameter.getBeanProperty("selector");
		}
		return super.getBeanProperty(cParameter, beanProperty);
	}

	@Override
	public IForward ajaxProcess(final ComponentParameter cParameter) {
		final ComponentParameter nComponentParameter = DictionaryUtils.get(cParameter);
		final DictionaryBean dictionaryBean = (DictionaryBean) nComponentParameter.componentBean;
		if (dictionaryBean == null) {
			return null;
		}
		final AbstractDictionaryTypeBean dictionaryType = dictionaryBean.getDictionaryTypeBean();
		String jsp = null;
		if (dictionaryType instanceof DictionaryTreeBean) {
			jsp = "tree.jsp";
		} else if (dictionaryType instanceof DictionaryListBean) {
			jsp = "list.jsp";
		} else if (dictionaryType instanceof DictionaryColorBean) {
			jsp = "color.jsp";
		} else if (dictionaryType instanceof DictionaryFontBean) {
			jsp = "font.jsp";
		} else if (dictionaryType instanceof DictionarySmileyBean) {
			jsp = "smiley.jsp";
		}
		if (jsp == null) {
			throw NotImplementedException.of(getClass(), "ajaxProcess");
		}
		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentUtils.getResourceHomePath(DictionaryBean.class)).append("/jsp/")
				.append(jsp).append("?").append(DictionaryUtils.BEAN_ID).append("=")
				.append(dictionaryBean.hashId());
		return new UrlForward(sb.toString());
	}
}
