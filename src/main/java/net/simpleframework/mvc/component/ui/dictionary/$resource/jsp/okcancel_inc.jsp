<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.simpleframework.mvc.component.ui.dictionary.DictionaryUtils"%>
<%@ page import="net.simpleframework.mvc.component.ui.dictionary.DictionaryRender"%>
<%@ page import="net.simpleframework.mvc.component.ComponentParameter"%>
<%@ page import="net.simpleframework.common.Convert"%>
<%@ page import="net.simpleframework.mvc.component.ui.dictionary.DictionaryBean"%>
<%@ page import="net.simpleframework.common.StringUtils"%>
<%
	final ComponentParameter nComponentParameter = DictionaryUtils.get(request, response);
	final DictionaryBean dictionaryBean = (DictionaryBean) nComponentParameter.componentBean;
	final String beanId = dictionaryBean.hashId();
	final String name = (String) nComponentParameter.getBeanProperty("name");
	final boolean multiple = (Boolean) nComponentParameter.getBeanProperty("multiple");
%>
<div class="okcancel clear_float">
  <div style="float: right">
    <span style="margin-right: 4px;"><%=DictionaryRender.getActions(nComponentParameter)%></span>
    <%
    	if (multiple) {
    %>
    <input type="button" class="button2" value="#(Button.Ok)" onclick="selected<%=beanId%>();" />
    <%
    	}
    %>
    <input type="button" value="#(Button.Cancel)" onclick="$Actions['<%=name%>'].close();" />
  </div>
  <%
  	if (Convert.toBool(nComponentParameter.getBeanProperty("showHelpTooltip"))) {
  %><div style="float: left">
    <img id="help<%=beanId%>"
      src="<%=dictionaryBean.getComponentRegistry().getPageResourceProvider()
						.getCssResourceHomePath(nComponentParameter)%>/images/help.png" />
  </div>
  <%
  	}
  %>
</div>
<script type="text/javascript">
  $ready(function() {
  	<%final String title = (String) nComponentParameter.getBeanProperty("title");
			if (StringUtils.hasText(title)) {
				out.append("$Actions['").append(name).append("'].window.setHeader('");
				out.append(title).append("');");
			}%>
  });
</script>
