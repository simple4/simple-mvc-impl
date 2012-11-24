package net.simpleframework.mvc.component.ui.pager;

import static net.simpleframework.common.I18n.$m;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.simpleframework.common.Convert;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.web.HttpUtils;
import net.simpleframework.mvc.MVCUtils;
import net.simpleframework.mvc.PageDocument;
import net.simpleframework.mvc.PageRequestResponse;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class PagerUtils {
	public static final String BEAN_ID_NAME = "__pager_beanId_name";

	public static final String BEAN_ID = "pager_@bid";

	private static String beanId(final HttpServletRequest request) {
		final String beanIdName = request.getParameter(BEAN_ID_NAME);
		return StringUtils.hasText(beanIdName) ? beanIdName : BEAN_ID;
	}

	public static ComponentParameter get(final PageRequestResponse rRequest) {
		return ComponentParameter.get(rRequest, BEAN_ID);
	}

	public static ComponentParameter get(final HttpServletRequest request,
			final HttpServletResponse response) {
		return ComponentParameter.get(request, response, beanId(request));
	}

	public static List<?> getPagerList(final PageRequestResponse rRequest) {
		final List<?> l = (List<?>) rRequest.getRequestAttr(IPagerHandler.PAGER_LIST);
		return l != null ? l : Collections.EMPTY_LIST;
	}

	private static String attributesSessionKey(final ComponentParameter nComponentParameter) {
		return "attributes_" + nComponentParameter.hashId();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setPageAttributes(final ComponentParameter nComponentParameter,
			final String key, final Object value) {
		final String sk = attributesSessionKey(nComponentParameter);
		Map<String, Object> attributes = (Map) nComponentParameter.getSessionAttr(sk);
		if (attributes == null) {
			nComponentParameter.setSessionAttr(sk, attributes = new HashMap<String, Object>());
		}
		attributes.put(key, value);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int getPageItems(final ComponentParameter nComponentParameter) {
		final String pageItemsParameterName = (String) nComponentParameter
				.getBeanProperty("pageItemsParameterName");
		final String pageItems = nComponentParameter.getParameter(pageItemsParameterName);
		final int items;
		if (StringUtils.hasText(pageItems)) {
			items = Convert.toInt(pageItems);
		} else {
			final String sk = attributesSessionKey(nComponentParameter);
			final Map<String, Object> attri = (Map) nComponentParameter.getSessionAttr(sk);
			items = (attri == null ? 0 : Convert.toInt(attri.get(pageItemsParameterName)));
		}
		return items == 0 ? (Integer) nComponentParameter.getBeanProperty("pageItems") : items;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int getPageNumber(final ComponentParameter nComponentParameter) {
		final String pageNumberParameterName = (String) nComponentParameter
				.getBeanProperty("pageNumberParameterName");
		final String pageNumber = nComponentParameter.getParameter(pageNumberParameterName);
		if (StringUtils.hasText(pageNumber)) {
			return Convert.toInt(pageNumber);
		} else {
			final Map<String, Object> attri = (Map) nComponentParameter
					.getSessionAttr(attributesSessionKey(nComponentParameter));
			return attri != null ? Convert.toInt(attri.get(pageNumberParameterName)) : 0;
		}
	}

	public static void resetPageNumber(final ComponentParameter nComponentParameter) {
		final String pageNumberParameterName = (String) nComponentParameter
				.getBeanProperty("pageNumberParameterName");
		setPageAttributes(nComponentParameter, pageNumberParameterName, 0);
	}

	/***************************** utils for jsp ****************************/

	public static String nbar(final ComponentParameter cParameter,
			final EPagerPosition pagerPosition, final int pageItems, final Map<String, Integer> pageVar) {
		final StringBuilder sb = new StringBuilder();
		final String href = ((AbstractPagerHandler) cParameter.getComponentHandler()).getPagerUrl(
				cParameter, pagerPosition, pageItems, pageVar);
		if (StringUtils.hasText(href)) {
			sb.append("<a")
					.append(href.toLowerCase().startsWith("javascript:") ? " onclick" : " href")
					.append("=\"").append(href).append("\"");
			if (pagerPosition == EPagerPosition.left2) {
				sb.append(" title=\"#(pager_head.3)\" class=\"p2\"></a>");
			} else if (pagerPosition == EPagerPosition.left) {
				sb.append(" title=\"#(pager_head.4)\" class=\"p1\"></a>");
			} else if (pagerPosition == EPagerPosition.number) {
				final int pageNumber = Convert.toInt(pageVar.get("pageNumber"));
				final int currentPageNumber = Convert.toInt(pageVar.get("currentPageNumber"));
				if (pageNumber != currentPageNumber) {
					sb.append(" class=\"num\">");
				} else {
					sb.append(" class=\"current num\">");
				}
				sb.append(pageNumber).append("</a>");
			} else if (pagerPosition == EPagerPosition.right) {
				sb.append(" title=\"#(pager_head.5)\" class=\"n1\"></a>");
			} else if (pagerPosition == EPagerPosition.right2) {
				sb.append(" title=\"#(pager_head.6)\" class=\"n2\"></a>");
			}
		}
		return sb.toString();
	}

	public static String getPagerActions(final ComponentParameter cParameter, final int count,
			final int pageItems, final Map<String, Integer> pageVar) {
		final int itemCount = Convert.toInt(pageVar.get("itemCount"));
		final int firstItem = Convert.toInt(pageVar.get("firstItem"));
		final int lastItem = Convert.toInt(pageVar.get("lastItem"));

		final StringBuilder sb = new StringBuilder();
		final boolean showEditPageItems = (Boolean) cParameter.getBeanProperty("showEditPageItems");
		if (count > pageItems) {
			sb.append("<span>").append($m("pager_head.1", itemCount, firstItem + " - " + lastItem));
			sb.append("</span>");
			if (showEditPageItems) {
				sb.append("<span style=\"margin: 0px 2px;\">/</span>");
			}
		} else {
			sb.append("<span>");
			sb.append($m("pager_head.1", count, $m("pager_head.2")));
			sb.append("</span>");
			if (showEditPageItems) {
				sb.append("<span style=\"margin: 0px 2px;\">/</span>");
			}
		}
		if (showEditPageItems) {
			sb.append("<input type=\"text\" style=\"width: 28px;\" title=\"#(pager_head.0)\" ");
			sb.append("value=\"").append(pageItems).append("\" onkeydown=\"");
			sb.append("if ((event.which ? event.which : event.keyCode) == Event.KEY_RETURN)");
			sb.append("{ ");
			final String href = ((AbstractPagerHandler) cParameter.getComponentHandler()).getPagerUrl(
					cParameter, EPagerPosition.pageItems, pageItems, pageVar);
			if (href.toLowerCase().startsWith("javascript:")) {
				sb.append(href.substring(11));
			} else {
				final String pageItemsParameterName = (String) cParameter
						.getBeanProperty("pageItemsParameterName");
				sb.append("$Actions.loc('")
						.append(HttpUtils.addParameters(href, pageItemsParameterName + "="))
						.append("' + $F(this));");
			}
			sb.append(" }\" />");
		}
		String exportAction = (String) cParameter.getBeanProperty("exportAction");
		if (StringUtils.hasText(exportAction) && !"false".equals(exportAction)) {
			if (!exportAction.startsWith("$Actions")) {
				exportAction = "$Actions[" + exportAction + "]();";
			}
			sb.append("<span class=\"csv_icon\" onclick=\"").append(exportAction).append("\"></span>");
		}
		return sb.toString();
	}

	static String getXmlPathParameter(final ComponentParameter cParameter) {
		final String jspPath = (String) cParameter.getBeanProperty("jspPath");
		if (StringUtils.hasText(jspPath)) {
			final String xmlPath = MVCUtils.doPageUrl(cParameter,
					StringUtils.stripFilenameExtension(jspPath) + ".xml");
			if (new File(MVCUtils.getRealPath(xmlPath)).exists()) {
				return PageDocument.XMLPATH_PARAMETER + "=" + xmlPath;
			}
		}
		return null;
	}
}
