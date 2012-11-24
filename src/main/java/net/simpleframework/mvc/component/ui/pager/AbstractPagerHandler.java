package net.simpleframework.mvc.component.ui.pager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.simpleframework.common.Convert;
import net.simpleframework.common.ado.query.IDataQuery;
import net.simpleframework.mvc.component.ComponentHandlerEx;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class AbstractPagerHandler extends ComponentHandlerEx implements IPagerHandler {

	@Override
	public Map<String, Object> getFormParameters(final ComponentParameter cParameter) {
		final Map<String, Object> parameters = super.getFormParameters(cParameter);
		for (final String k : new String[] { PagerUtils.BEAN_ID_NAME, getBeanIdName(),
				(String) cParameter.getBeanProperty("pageNumberParameterName"),
				(String) cParameter.getBeanProperty("pageItemsParameterName") }) {
			parameters.put(k, cParameter.getParameter(k));
		}
		return parameters;
	}

	public IDataQuery<?> createDataObjectQuery(final ComponentParameter cParameter) {
		return null;
	}

	protected String getBeanIdName() {
		return PagerUtils.BEAN_ID;
	}

	protected static final String QUERY_CACHE = "__query_cache";

	protected IDataQuery<?> getDataObjectQuery(final ComponentParameter cParameter) {
		IDataQuery<?> dataObjectQuery = (IDataQuery<?>) cParameter.getRequestAttr(QUERY_CACHE);
		if (dataObjectQuery == null) {
			cParameter
					.setRequestAttr(QUERY_CACHE, dataObjectQuery = createDataObjectQuery(cParameter));
			if (dataObjectQuery != null) {
				dataObjectQuery.setFetchSize(((PagerBean) cParameter.componentBean).getPageItems());
			}
		}
		return dataObjectQuery;
	}

	@Override
	public int getCount(final ComponentParameter cParameter) {
		final IDataQuery<?> dataQuery = getDataObjectQuery(cParameter);
		if (dataQuery == null) {
			return 0;
		}
		doCount(cParameter, dataQuery);
		return dataQuery.getCount();
	}

	protected void doCount(final ComponentParameter cParameter, final IDataQuery<?> dataQuery) {
	}

	protected List<?> getData(final ComponentParameter cParameter, final int start) {
		final List<Object> data = new ArrayList<Object>();
		final IDataQuery<?> dataQuery = (IDataQuery<?>) cParameter.getRequestAttr(DATA_QUERY);
		if (dataQuery != null) {
			dataQuery.move(start - 1);
			Object object;
			int i = 0;
			while ((object = dataQuery.next()) != null) {
				data.add(object);
				if (++i == PagerUtils.getPageItems(cParameter)) {
					break;
				}
			}
		}
		return data;
	}

	@Override
	public String toPagerHTML(final ComponentParameter cParameter, final List<?> data) {
		return null;
	}

	protected static final String DATA_QUERY = "data_query";
	private static final String PROCESS_TIMES = "process_times";

	@Override
	public void process(final ComponentParameter cParameter, final int start) {
		final long l = System.currentTimeMillis();
		final IDataQuery<?> dataQuery = getDataObjectQuery(cParameter);
		cParameter.setRequestAttr(DATA_QUERY, dataQuery);
		cParameter.setRequestAttr(PAGER_LIST, getData(cParameter, start));
		cParameter.setSessionAttr(PROCESS_TIMES, System.currentTimeMillis() - l);
	}

	protected void wrapNavImage(final ComponentParameter cParameter, final StringBuilder sb) {
		final Object times = cParameter.getSessionAttr(PROCESS_TIMES);
		if (times != null) {
			sb.append("<span style=\"margin-left: 8px;\">( ");
			sb.append(times).append("ms )</span>");
			cParameter.removeSessionAttr(PROCESS_TIMES);
		}
		sb.insert(0, "<div class=\"nav0_image\">");
		sb.append("</div>");
	}

	public String getPagerUrl(final ComponentParameter cParameter,
			final EPagerPosition pagerPosition, final int pageItems, final Map<String, Integer> pageVar) {
		// final String lastUrl = (String) compParameter
		// .getSessionAttribute(IPageConstants.SESSION_LAST_URL);
		// if (StringUtils.hasText(lastUrl)) {
		// return WebUtils.addParameters(lastUrl,
		// IPagerHandle.Helper.getPageNumberParameter(cParameter,
		// pagerPosition, pageVar));
		// }
		final StringBuilder sb = new StringBuilder();
		sb.append("javascript:$Actions['").append(cParameter.getBeanProperty("name"));
		sb.append("'].doPager(this, '");
		sb.append(cParameter.getBeanProperty("pageItemsParameterName")).append("=");
		if (pagerPosition == EPagerPosition.pageItems) {
			sb.append("' + ").append("$F(this)").append(" + '");
		} else {
			sb.append(pageItems);
		}
		sb.append("&");
		sb.append(getPageNumberParameter(cParameter, pagerPosition, pageVar));
		sb.append("');");
		return sb.toString();
	}

	protected String getPageNumberParameter(final ComponentParameter cParameter,
			final EPagerPosition pagerPosition, final Map<String, Integer> pageVar) {
		final StringBuilder sb = new StringBuilder();
		sb.append(cParameter.getBeanProperty("pageNumberParameterName"));
		sb.append("=");
		final int pageNumber = Convert.toInt(pageVar.get("pageNumber"));
		final int currentPageNumber = Convert.toInt(pageVar.get("currentPageNumber"));
		final int pageCount = Convert.toInt(pageVar.get("pageCount"));
		if (pagerPosition == EPagerPosition.left2) {
			sb.append(1);
		} else if (pagerPosition == EPagerPosition.left) {
			sb.append(currentPageNumber > 1 ? (currentPageNumber - 1) : 1);
		} else if (pagerPosition == EPagerPosition.number) {
			sb.append(pageNumber);
		} else if (pagerPosition == EPagerPosition.right) {
			sb.append(currentPageNumber >= pageCount ? pageCount : currentPageNumber + 1);
		} else if (pagerPosition == EPagerPosition.right2) {
			sb.append(pageCount);
		} else if (pagerPosition == EPagerPosition.pageItems) {
			sb.append(1);
		}
		return sb.toString();
	}
}
