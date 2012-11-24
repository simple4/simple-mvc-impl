package net.simpleframework.mvc.component.ui.swfupload;

import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentHtmlRenderEx;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(SwfUploadRegistry.swfupload)
@ComponentBean(SwfUploadBean.class)
@ComponentRender(SwfUploadRender.class)
@ComponentResourceProvider(SwfUploadResourceProvider.class)
public class SwfUploadRegistry extends AbstractComponentRegistry {
	public static final String swfupload = "swfupload";

	@Override
	public SwfUploadBean createComponentBean(final PageParameter pParameter, final Object data) {
		final SwfUploadBean swfUpload = (SwfUploadBean) super.createComponentBean(pParameter, data);
		ComponentHtmlRenderEx.createAjaxRequest(ComponentParameter.get(pParameter, swfUpload));
		return swfUpload;
	}
}
