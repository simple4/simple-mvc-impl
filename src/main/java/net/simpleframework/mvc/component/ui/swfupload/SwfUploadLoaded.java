package net.simpleframework.mvc.component.ui.swfupload;

import static net.simpleframework.common.I18n.$m;
import net.simpleframework.mvc.DefaultPageHandler;
import net.simpleframework.mvc.IForward;
import net.simpleframework.mvc.JavascriptForward;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;
import net.simpleframework.mvc.component.base.ajaxrequest.DefaultAjaxRequestHandler;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class SwfUploadLoaded extends DefaultPageHandler {

	@Override
	public void beforeComponentRender(final PageParameter pParameter) {
		super.beforeComponentRender(pParameter);

		final ComponentParameter cParameter = SwfUploadUtils.get(pParameter);
		final String swfUploadName = (String) cParameter.getBeanProperty("name");

		pParameter.addComponentBean(swfUploadName + "_upload", AjaxRequestBean.class).setHandleClass(
				UploadAction.class);
	}

	public static class UploadAction extends DefaultAjaxRequestHandler {
		@Override
		public Object getBeanProperty(final ComponentParameter cParameter, final String beanProperty) {
			if ("role".equals(beanProperty)) {
				final ComponentParameter nComponentParameter = SwfUploadUtils.get(cParameter);
				return nComponentParameter.getBeanProperty("roleUpload");
			}
			return super.getBeanProperty(cParameter, beanProperty);
		}

		@Override
		public IForward ajaxProcess(final ComponentParameter cParameter) {
			final ComponentParameter nComponentParameter = SwfUploadUtils.get(cParameter);
			final JavascriptForward js = new JavascriptForward();
			js.append("var act = $Actions['").append(nComponentParameter.getBeanProperty("name"))
					.append("'];");
			js.append("var fileQueue = $('fileQueue_").append(nComponentParameter.hashId())
					.append("');");
			js.append("if (fileQueue.select('.item').length == 0) {");
			js.append("  var msg = fileQueue.previous();");
			js.append("  msg.update('").append($m("SwfUploadLoaded.0")).append("');");
			js.append("  msg.$shake();");
			js.append("  (function() { msg.update(''); }).delay(4);");
			js.append("}");
			js.append("if (act.paramsObject)");
			js.append("  act.swf.setPostParams(act.paramsObject);");
			js.append("act.swf.startUpload();");
			return js;
		}
	}
}
