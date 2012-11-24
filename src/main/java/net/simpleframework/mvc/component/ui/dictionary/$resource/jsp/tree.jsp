<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.simpleframework.mvc.component.ui.dictionary.DictionaryBean"%>
<%@ page import="net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryTreeBean"%>
<%@ page import="net.simpleframework.mvc.component.ui.dictionary.DictionaryUtils"%>
<%@ page import="net.simpleframework.mvc.component.ui.dictionary.DictionaryRender"%>
<%@ page import="net.simpleframework.mvc.component.ComponentParameter"%>
<%@ page import="net.simpleframework.mvc.component.ComponentRenderUtils"%>
<%
	final ComponentParameter nComponentParameter = DictionaryUtils.get(request, response);
	final DictionaryBean dictionaryBean = (DictionaryBean) nComponentParameter.componentBean;
	final String beanId = dictionaryBean.hashId();
	final String name = (String) nComponentParameter.getBeanProperty("name");
	final DictionaryTreeBean tree = (DictionaryTreeBean) dictionaryBean
			.getDictionaryTypeBean();
%>
<div class="dictionary" style="padding: 2px 0px 2px 4px;">
  <%=ComponentRenderUtils.genParameters(nComponentParameter)%>
  <div class="tree" id="tree<%=beanId%>"></div>
  <jsp:include page="okcancel_inc.jsp" flush="true"></jsp:include>
</div>
<script type="text/javascript">
  treeCheck<%=beanId%> = function(branch, checked, ev) {  
    if (!TafelTreeManager.ctrlOn(ev)) {
      branch._manageCheckThreeState(branch, checked);
      branch._adjustParentCheck();
    }
  };
      
  selected<%=beanId%> = function(branch, ev) {
    var selects = $tree_getSelects($Actions['<%=tree.getRef()%>'].tree, branch, ev);
    if (selects && selects.length > 0) {
      var act = $Actions['<%=name%>'];
      if (act.jsSelectCallback) {
        if (act.jsSelectCallback(selects))
          act.close();
      } else {
        <%=DictionaryRender.genSelectCallback(nComponentParameter, "selects")%>
      }
    }
  };
  
  $ready(function() {
    var t = $('tree<%=beanId%>');
    var w = $Actions['<%=name%>'].window;
    w.content.setStyle("overflow:hidden;");
    var s = function() {
      t.setStyle('height: ' + (w.getSize(true).height - 55) + 'px;');
    };
    s();
    w.observe("resize:ended", s);
  });
</script>
