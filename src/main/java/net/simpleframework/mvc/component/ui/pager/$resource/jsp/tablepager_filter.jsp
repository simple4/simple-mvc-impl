<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.simpleframework.mvc.component.ComponentParameter"%>
<%@ page import="net.simpleframework.mvc.component.ui.pager.PagerUtils"%>
<%@ page import="net.simpleframework.mvc.component.ui.pager.TablePagerColumn"%>
<%@ page import="net.simpleframework.mvc.component.ui.pager.TablePagerUtils"%>
<%@ page import="net.simpleframework.common.ado.EFilterRelation"%>
<%@ page import="java.util.Date"%>
<%
	final ComponentParameter nComponentParameter = PagerUtils.get(
			request, response);
	final TablePagerColumn col = TablePagerUtils
			.getSelectedColumn(nComponentParameter);
	if (col == null) {
%>
<div class="simple_toolbar3 f2" style="text-align: center;">#(tablepager_filter.1)</div>
<%
	return;
	}
	boolean isDate = Date.class.isAssignableFrom(col.propertyClass());
%>
<div class="tablepager_filter simple_window_tcb">
  <div class="c">
    <input type="hidden" name="<%=TablePagerUtils.PARAM_FILTER_CUR_COL%>"
      value="<%=col.getColumnName()%>">
    <table style="width: 100%;">
      <tr>
        <td width="80"><select style="padding: 2px; width: 95%;" name="tp_filter_r1"
          id="tp_filter_r1">
            <%=TablePagerUtils.toOptionHTML(col)%>
        </select></td>
        <td>
          <%
          	if (isDate) {
          		out.write("<div id='tp_filter_date1'></div>");
          	} else {
          		out.write("<input type='text' name='tp_filter_v1' id='tp_filter_v1' />");
          	}
          %>
        </td>
      </tr>
      <tr>
        <td></td>
        <td><input type="radio" name="tp_filter_op" id="tp_filter_op0" value="none"
          onclick="tp_filter_click()" checked /><label for="tp_filter_op0">#(tablepager_filter.0)</label>
          <input type="radio" name="tp_filter_op" id="tp_filter_op1" value="and"
          onclick="tp_filter_click()" /><label for="tp_filter_op1">#(tablepager_filter.2)</label> <input
          type="radio" id="tp_filter_op2" name="tp_filter_op" value="or" onclick="tp_filter_click()" /><label
          for="tp_filter_op2">#(tablepager_filter.3)</label></td>
      </tr>
      <tr>
        <td><select style="padding: 2px; width: 95%;" name="tp_filter_r2" id="tp_filter_r2"><%=TablePagerUtils.toOptionHTML(col)%></select></td>
        <td>
          <%
          	if (isDate) {
          		out.write("<div id='tp_filter_date2'></div>");
          	} else {
          		out.write("<input type='text' name='tp_filter_v2' id='tp_filter_v2' />");
          	}
          %>
        </td>
      </tr>
    </table>
  </div>
  <div class="b" style="text-align: right; margin-top: 6px;">
    <input type="submit" id="idTablePagerFilterSave" value="#(Button.Ok)" class="button2"
      onclick="tp_filter_save()" /> <input type="button" value="#(Button.Cancel)"
      onclick="$win(this).close();" />
  </div>
</div>
<script type="text/javascript">
  function tp_filter_click() {
    var obj = $("tp_filter_op0");
    $Actions[!obj.checked ? 'enable' : 'disable'](obj.up('tr').next());
  }
  
  function tp_filter_save() {
    var act = $Actions['ajaxTablePagerFilterSave'];
    act();
  }

  (function() {
    var date = $("tp_filter_date1");
    if (date) {
      var format = "<%=col.getFormat()%>";
      date.update($UI.createButtonInputField("tp_filter_v1", function(ev) {
        var act = $Actions["calendarTablePagerFilter"];
        act.show("tp_filter_v1", format);
      }));
      $("tp_filter_date2").update($UI.createButtonInputField("tp_filter_v2", function(ev) {
        var act = $Actions["calendarTablePagerFilter"];
        act.show("tp_filter_v2", format);
      }));
    }
  })();
</script>