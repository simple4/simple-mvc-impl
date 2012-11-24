<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.simpleframework.common.StringUtils"%>
<%@ page import="net.simpleframework.mvc.component.ui.pager.EPagerBarLayout"%>
<%@ page import="net.simpleframework.mvc.component.ui.pager.PagerUtils"%>
<%@ page import="net.simpleframework.mvc.component.ui.pager.IPagerHandler"%>
<%@ page import="net.simpleframework.common.web.HttpUtils"%>
<%@ page import="net.simpleframework.common.html.js.JavascriptUtils"%>
<%@ page import="net.simpleframework.mvc.component.ComponentRenderUtils"%>
<%@ page import="net.simpleframework.mvc.component.AbstractComponentBean"%>
<%@ page import="net.simpleframework.mvc.component.ComponentParameter"%>
<%@ page import="net.simpleframework.mvc.MVCUtils"%>
<%@ page import="net.simpleframework.mvc.UrlForward"%>
<%
	final ComponentParameter nComponentParameter = PagerUtils.get(request, response);
	final IPagerHandler hdl = (IPagerHandler) nComponentParameter.getComponentHandler();
	if (hdl == null) {
		return;
	}
	final int count = hdl.getCount(nComponentParameter);
	final int pageItems = PagerUtils.getPageItems(nComponentParameter);
	final int pageNumber = PagerUtils.getPageNumber(nComponentParameter);
	int start;
	if (pageNumber > 1) {
		start = (pageNumber - 1) * pageItems;
		if (start >= count) {
			start = 0;
			PagerUtils.resetPageNumber(nComponentParameter);
		}
	} else {
		start = 0;
	}
	HttpUtils.putParameter(request, "pager.offset", start);
	hdl.process(nComponentParameter, start);
	final EPagerBarLayout layout = (EPagerBarLayout) nComponentParameter
			.getBeanProperty("pagerBarLayout");
%>
<div class="pager"
  style="width:<%=StringUtils.text((String) nComponentParameter.getBeanProperty("width"), "100%")%>">
  <%
  	out.write(ComponentRenderUtils.genParameters(nComponentParameter));
  	final String title = (String) nComponentParameter.getBeanProperty("title");
  	final boolean top = count > 0
  			&& (layout == EPagerBarLayout.top || layout == EPagerBarLayout.both);
  	if (top || StringUtils.hasText(title)) {
  %>
  <div class="pager_block_top">
    <div class="pager_title"><%=StringUtils.blank(title)%></div>
    <%
    	if (top) {
    %><jsp:include page="pager_head.jsp" flush="true">
      <jsp:param value="<%=start%>" name="pager.offset" />
      <jsp:param value="<%=count%>" name="_count" />
      <jsp:param value="<%=pageItems%>" name="_pageItems" />
    </jsp:include><%
    	}
    %>
  </div>
  <%
  	}
  	String jspPath = (String) nComponentParameter.getBeanProperty("jspPath");
  	if (StringUtils.hasText(jspPath)) {
  		jspPath = HttpUtils.stripContextPath(request,
  				MVCUtils.doPageUrl(nComponentParameter, jspPath));
  %><jsp:include page="<%=jspPath%>" flush="true"></jsp:include><%
  	} else {
  		final String dataHtml = hdl.toPagerHTML(nComponentParameter,
  				PagerUtils.getPagerList(nComponentParameter));
  		if (StringUtils.hasText(dataHtml)) {
  			out.write(dataHtml);
  		}
  	}
  	if (count > 0 && (layout == EPagerBarLayout.bottom || layout == EPagerBarLayout.both)) {
  %><div class="pager_block_bottom">
    <jsp:include page="pager_head.jsp" flush="true">
      <jsp:param value="<%=start%>" name="pager.offset" />
      <jsp:param value="<%=count%>" name="_count" />
      <jsp:param value="<%=pageItems%>" name="_pageItems" />
    </jsp:include>
  </div><%
  	}
  %>
</div>
<%
	if (count == 0) {
%>
<div class="pager_no_result"><%=StringUtils.blank(nComponentParameter.getBeanProperty("noResultDesc"))%>
</div>
<%
	}
	String beanId = nComponentParameter.hashId();
%>
<script type="text/javascript">
  $ready(function() {
    var action = $Actions["<%=nComponentParameter.getBeanProperty("name")%>"];
    <%=ComponentRenderUtils.genJSON(nComponentParameter, "action")%>
    action.selector = 
      "<%=nComponentParameter.getBeanProperty("selector")%>";
    var ele = $("<%=AbstractComponentBean.FORM_PREFIX + beanId%>").up(".pager");
    action.pager = ele;
    action.hasData = <%=count > 0%>;
    action.beanId = "<%=beanId%>";
    
    ele.action = action;
    $call(window.pager_init_<%=beanId%>, action);
    
    (function(pa) {
      pa.refresh = function(params) {
        action(params);   
      };
    })(action);
    
    if (action.jsLoadedCallback) {
      action.jsLoadedCallback();
    } else {
      <%final String callback = (String) nComponentParameter.getBeanProperty("jsLoadedCallback");
			if (StringUtils.hasText(callback)) {
				out.write(JavascriptUtils.wrapWhenReady(callback));
			}%>
    }
  });
</script>