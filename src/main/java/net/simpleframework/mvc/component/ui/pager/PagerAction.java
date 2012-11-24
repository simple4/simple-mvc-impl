package net.simpleframework.mvc.component.ui.pager;

import net.simpleframework.common.Convert;
import net.simpleframework.common.StringUtils;
import net.simpleframework.mvc.IForward;
import net.simpleframework.mvc.UrlForward;
import net.simpleframework.mvc.component.ComponentHtmlRenderEx;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentUtils;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class PagerAction extends ComponentHtmlRenderEx.RefreshAction {

	@Override
	public IForward ajaxProcess(final ComponentParameter cParameter) {
		final ComponentParameter nComponentParameter = PagerUtils.get(cParameter);
		final String pageNumberParameterName = (String) nComponentParameter
				.getBeanProperty("pageNumberParameterName");
		final int pageNumber = Math.max(
				Convert.toInt(nComponentParameter.getParameter(pageNumberParameterName)), 1);
		PagerUtils.setPageAttributes(nComponentParameter, pageNumberParameterName, pageNumber);
		final String pageItemsParameterName = (String) nComponentParameter
				.getBeanProperty("pageItemsParameterName");
		final int pageItems = Convert.toInt(nComponentParameter.getParameter(pageItemsParameterName));
		if (pageItems > 0) {
			PagerUtils.setPageAttributes(nComponentParameter, pageItemsParameterName, pageItems);
		}
		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentUtils.getResourceHomePath(PagerBean.class)).append("/jsp/pager.jsp");
		final String xpParameter = PagerUtils.getXmlPathParameter(nComponentParameter);
		if (StringUtils.hasText(xpParameter)) {
			sb.append("?").append(xpParameter);
		}
		return new UrlForward(sb.toString());
	}
}
