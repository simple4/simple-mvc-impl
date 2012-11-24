package net.simpleframework.mvc.component.ui.propeditor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.simpleframework.common.html.js.EJavascriptEvent;
import net.simpleframework.common.xml.AbstractElementBean;
import net.simpleframework.common.xml.XmlElement;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class InputComp extends AbstractElementBean {

	private String name;

	private EInputCompType type;

	private String style;

	/**
	 * 扩展属性，格式：readonly;rows:6
	 */
	private String attributes;

	private String defaultValue;

	private Map<EJavascriptEvent, String> eventCallback;

	public InputComp(final XmlElement xmlElement) {
		super(xmlElement);
	}

	public InputComp() {
		super(null);
	}

	public InputComp(final String name) {
		super(null);
		setName(name);
	}

	public String getName() {
		return name;
	}

	public InputComp setName(final String name) {
		this.name = name;
		return this;
	}

	public EInputCompType getType() {
		return type == null ? EInputCompType.text : type;
	}

	public InputComp setType(final EInputCompType type) {
		this.type = type;
		return this;
	}

	public String getStyle() {
		return style;
	}

	public InputComp setStyle(final String style) {
		this.style = style;
		return this;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public InputComp setDefaultValue(final Enum<?>... vals) {
		final StringBuilder sb = new StringBuilder();
		int i = 0;
		for (final Enum<?> v : vals) {
			if (i++ > 0) {
				sb.append(";");
			}
			sb.append(v.ordinal()).append("=").append(v);
		}
		return setDefaultValue(sb.toString());
	}

	public InputComp setDefaultValue(final String defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	public String getAttributes() {
		return attributes;
	}

	public InputComp setAttributes(final String attributes) {
		this.attributes = attributes;
		return this;
	}

	public Map<EJavascriptEvent, String> getEventCallback() {
		if (eventCallback == null) {
			eventCallback = new ConcurrentHashMap<EJavascriptEvent, String>();
		}
		return eventCallback;
	}

	public InputComp addEvent(final EJavascriptEvent event, final String javascript) {
		getEventCallback().put(event, javascript);
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "defaultValue" };
	}
}
