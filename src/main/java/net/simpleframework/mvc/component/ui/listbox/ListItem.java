package net.simpleframework.mvc.component.ui.listbox;

import net.simpleframework.common.Convert;
import net.simpleframework.common.xml.ItemUIBean;
import net.simpleframework.common.xml.XmlElement;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class ListItem extends ItemUIBean {

	private final ListboxBean listboxBean;

	private String tip;

	private final Object data;

	public ListItem(final XmlElement xmlElement, final ListboxBean listboxBean, final Object data) {
		super(xmlElement);
		this.listboxBean = listboxBean;
		this.data = data;
		setText(Convert.toString(data));
	}

	public ListItem(final ListboxBean listboxBean, final Object data) {
		this(null, listboxBean, data);
	}

	public String getTip() {
		return tip;
	}

	public ListItem setTip(final String tip) {
		this.tip = tip;
		return this;
	}

	public Object getDataObject() {
		return data;
	}

	public ListboxBean getListboxBean() {
		return listboxBean;
	}
}
