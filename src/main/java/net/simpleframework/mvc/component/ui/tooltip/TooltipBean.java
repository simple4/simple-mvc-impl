package net.simpleframework.mvc.component.ui.tooltip;

import java.util.ArrayList;
import java.util.Collection;

import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageDocument;
import net.simpleframework.mvc.component.AbstractComponentBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TooltipBean extends AbstractComponentBean {

	private Collection<TipBean> tips;

	public TooltipBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public TooltipBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public Collection<TipBean> getTips() {
		if (tips == null) {
			tips = new ArrayList<TipBean>();
		}
		return tips;
	}

	public TooltipBean addTip(final TipBean tip) {
		getTips().add(tip);
		return this;
	}
}
