<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.simpleframework.mvc.component.ui.dictionary.DictionaryUtils"%>
<%@ page import="net.simpleframework.mvc.component.ui.dictionary.DictionaryRender"%>
<%@ page import="net.simpleframework.mvc.component.ComponentParameter"%>
<%
	final ComponentParameter nComponentParameter = DictionaryUtils.get(request, response);
	final String beanId = nComponentParameter.hashId();
%>
<div id="fontEditor<%=beanId%>" style="margin-bottom: 6px;"></div>
<jsp:include page="okcancel_inc.jsp" flush="true"></jsp:include>
<script type="text/javascript">
  $Actions.callSafely('_dict_fontEditor', null, function(action) {
    action.container = $("fontEditor<%=beanId%>");
  });
  <%=DictionaryRender.fontSelected(nComponentParameter)%>
</script>
