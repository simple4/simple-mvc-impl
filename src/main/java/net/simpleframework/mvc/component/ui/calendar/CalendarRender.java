package net.simpleframework.mvc.component.ui.calendar;

import net.simpleframework.common.StringUtils;
import net.simpleframework.mvc.component.AbstractComponentRender.ComponentJavascriptRender;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRenderUtils;
import net.simpleframework.mvc.component.IComponentRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class CalendarRender extends ComponentJavascriptRender {
	public CalendarRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final StringBuilder sb = new StringBuilder();
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		sb.append(actionFunc).append(".calendar = new CalendarDateSelect({");

		final String dateFormat = (String) cParameter.getBeanProperty("dateFormat");
		if (StringUtils.hasText(dateFormat)) {
			sb.append("dateFormat: \"").append(dateFormat).append("\",");
		}
		final String closeCallback = (String) cParameter.getBeanProperty("closeCallback");
		if (StringUtils.hasText(closeCallback)) {
			sb.append("onclose: function(cal) {");
			sb.append(closeCallback);
			sb.append("},");
		}
		sb.append("showTime: ").append(cParameter.getBeanProperty("showTime")).append(",");
		sb.append("effects: Browser.effects && ").append(cParameter.getBeanProperty("effects"));
		sb.append("});");

		// show函数
		sb.append(actionFunc).append(".show = function(inputField, dateFormat) { ");
		sb.append(actionFunc).append(".calendar.show(inputField || \"")
				.append(cParameter.getBeanProperty("inputField")).append("\", dateFormat); };");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}
}
