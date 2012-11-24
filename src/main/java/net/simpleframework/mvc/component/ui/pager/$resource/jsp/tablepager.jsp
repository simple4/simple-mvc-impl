<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.simpleframework.mvc.component.ComponentParameter"%>
<%@ page import="net.simpleframework.mvc.component.ui.pager.PagerUtils"%>
<%@ page import="net.simpleframework.mvc.component.ui.pager.TablePagerHTML"%>
<%@ page import="net.simpleframework.mvc.component.ui.pager.GroupTablePagerBean"%>
<%@ page import="net.simpleframework.common.StringUtils"%>
<%@ page import="net.simpleframework.mvc.component.AbstractComponentBean"%>
<%
	final ComponentParameter nComponentParameter = PagerUtils.get(request, response);
	out.write(TablePagerHTML.renderTable(nComponentParameter));

	AbstractComponentBean componentBean = nComponentParameter.componentBean;
	final StringBuilder sb = new StringBuilder();
	final String jsRowClick = (String) nComponentParameter.getBeanProperty("jsRowClick");
	final String jsRowDblclick = (String) nComponentParameter.getBeanProperty("jsRowDblclick");
	final boolean b1 = StringUtils.hasText(jsRowClick);
	final boolean b2 = StringUtils.hasText(jsRowDblclick);
	if (b1 || b2) {
		sb.append("action.pager.select(\".titem\").invoke(\"observe\", \"");
		sb.append(b1 ? "click" : "dblclick").append("\", function(evn) {");
		sb.append("(function click(item) {");
		sb.append(b1 ? jsRowClick : jsRowDblclick).append("})(this);");
		sb.append("});");
	}
	if (componentBean instanceof GroupTablePagerBean) {
		sb.append("action.pager.select(\".toggle>img\").each(function(img) {");
		sb.append("$UI.doImageToggle(img, img.up(\".group_t\").next());");
		sb.append("});");
	}
	final String detailField = (String) nComponentParameter.getBeanProperty("detailField");
	if (StringUtils.hasText(detailField)) {
		sb.append("action.pager.select(\".plus>img\").each(function(img) {");
		sb.append("$UI.doImageToggle(img, img.up(\".titem\").next(), { cookie : false, open : false });");
		sb.append("});");
	}
%>
<script type="text/javascript">
  var pager_init_<%=componentBean.hashId()%> = function(action) {
    $table_pager_addMethods(action);
    
    <%=sb.toString()%>
  };
</script>
