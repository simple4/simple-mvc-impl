<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.simpleframework.mvc.component.ui.swfupload.SwfUploadUtils"%>
<%@ page import="net.simpleframework.mvc.component.ComponentRenderUtils"%>
<%@ page import="net.simpleframework.mvc.component.ComponentParameter"%>
<%
	final ComponentParameter nComponentParameter = SwfUploadUtils.get(
			request, response);
	final String uploadName = (String) nComponentParameter
			.getBeanProperty("name");
	final String beanId = nComponentParameter.hashId();
%>
<div class="swfupload">
  <%=ComponentRenderUtils.genParameters(nComponentParameter)%>
  <div class="btns">
    <span id="placeholder_<%=beanId%>"></span><a type="button"
      class="simple_btn simple_btn_all" id="upload_<%=beanId%>"
      onclick="upload_action_<%=beanId%>();">#(swfupload.0)</a>
  </div>
  <div id="message_<%=beanId%>" class="message"></div>
  <div id="fileQueue_<%=beanId%>" class="queue"></div>
</div>
<script type="text/javascript">
<%=SwfUploadUtils.genJavascript(nComponentParameter)%>
  function upload_action_<%=beanId%>() {
    var paramsObject = $Actions["<%=uploadName%>"].paramsObject;
    if (!paramsObject) {
      paramsObject = new Object(); 
    }
    paramsObject["<%=SwfUploadUtils.BEAN_ID%>"] = "<%=beanId%>";
		$Actions["<%=uploadName%>_upload"](Object.toQueryString(paramsObject));
	}
</script>