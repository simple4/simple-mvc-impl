package net.simpleframework.mvc.component.ui.tooltip;

import net.simpleframework.common.StringUtils;
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
public class TipBean extends AbstractElementBean {

	public TipBean(final XmlElement xmlElement, final TooltipBean tooltipBean) {
		super(xmlElement);
	}

	public TipBean(final TooltipBean tooltipBean) {
		this(null, tooltipBean);
	}

	private String selector, title, content;

	private int width = 200;

	private String ajaxRequest;

	private int radius = 6;

	private boolean fixed;

	private double delay = 0.14;

	private EJavascriptEvent showOn;

	private double hideAfter;

	private boolean hideOthers;

	private HideOn hideOn;

	private Hook hook;

	private ETipPosition stem;

	private int offsetX, offsetY;

	private String target;

	private String jsTipCreate;

	public String getSelector() {
		return selector;
	}

	public TipBean setSelector(final String selector) {
		this.selector = selector;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public TipBean setTitle(final String title) {
		this.title = title;
		return this;
	}

	public String getContent() {
		return StringUtils.blank(content);
	}

	public TipBean setContent(final String content) {
		this.content = content;
		return this;
	}

	public String getAjaxRequest() {
		return ajaxRequest;
	}

	public TipBean setAjaxRequest(final String ajaxRequest) {
		this.ajaxRequest = ajaxRequest;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public TipBean setWidth(final int width) {
		this.width = width;
		return this;
	}

	public int getRadius() {
		return radius;
	}

	public TipBean setRadius(final int radius) {
		this.radius = radius;
		return this;
	}

	public boolean isFixed() {
		return fixed;
	}

	public TipBean setFixed(final boolean fixed) {
		this.fixed = fixed;
		return this;
	}

	public double getDelay() {
		return delay;
	}

	public TipBean setDelay(final double delay) {
		this.delay = delay;
		return this;
	}

	public EJavascriptEvent getShowOn() {
		return showOn;
	}

	public TipBean setShowOn(final EJavascriptEvent showOn) {
		this.showOn = showOn;
		return this;
	}

	public ETipPosition getStem() {
		return stem;
	}

	public TipBean setStem(final ETipPosition stem) {
		this.stem = stem;
		return this;
	}

	public double getHideAfter() {
		return hideAfter;
	}

	public TipBean setHideAfter(final double hideAfter) {
		this.hideAfter = hideAfter;
		return this;
	}

	public boolean isHideOthers() {
		return hideOthers;
	}

	public TipBean setHideOthers(final boolean hideOthers) {
		this.hideOthers = hideOthers;
		return this;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public TipBean setOffsetX(final int offsetX) {
		this.offsetX = offsetX;
		return this;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public TipBean setOffsetY(final int offsetY) {
		this.offsetY = offsetY;
		return this;
	}

	public String getTarget() {
		return target;
	}

	public TipBean setTarget(final String target) {
		this.target = target;
		return this;
	}

	public HideOn getHideOn() {
		return hideOn;
	}

	public TipBean setHideOn(final HideOn hideOn) {
		this.hideOn = hideOn;
		return this;
	}

	public Hook getHook() {
		return hook;
	}

	public TipBean setHook(final Hook hook) {
		this.hook = hook;
		return this;
	}

	public String getJsTipCreate() {
		return jsTipCreate;
	}

	public TipBean setJsTipCreate(final String jsTipCreate) {
		this.jsTipCreate = jsTipCreate;
		return this;
	}

	public static class HideOn extends AbstractElementBean {

		public HideOn(final XmlElement xmlElement) {
			super(xmlElement);
		}

		public HideOn() {
			this(null);
		}

		private ETipElement tipElement;

		private EJavascriptEvent event;

		public ETipElement getTipElement() {
			return tipElement == null ? ETipElement.target : tipElement;
		}

		public HideOn setTipElement(final ETipElement tipElement) {
			this.tipElement = tipElement;
			return this;
		}

		public EJavascriptEvent getEvent() {
			return event == null ? EJavascriptEvent.mouseout : event;
		}

		public HideOn setEvent(final EJavascriptEvent event) {
			this.event = event;
			return this;
		}
	}

	public static class Hook extends AbstractElementBean {

		public Hook(final XmlElement xmlElement) {
			super(xmlElement);
		}

		public Hook() {
			this(null);
		}

		private ETipPosition target;

		private ETipPosition tip;

		private boolean mouse;

		public ETipPosition getTarget() {
			return target == null ? ETipPosition.topLeft : target;
		}

		public Hook setTarget(final ETipPosition target) {
			this.target = target;
			return this;
		}

		public ETipPosition getTip() {
			return tip == null ? ETipPosition.bottomLeft : tip;
		}

		public Hook setTip(final ETipPosition tip) {
			this.tip = tip;
			return this;
		}

		public boolean isMouse() {
			return mouse;
		}

		public Hook setMouse(final boolean mouse) {
			this.mouse = mouse;
			return this;
		}
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "jsTipCreate", "content" };
	}
}
