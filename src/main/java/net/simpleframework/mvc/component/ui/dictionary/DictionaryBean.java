package net.simpleframework.mvc.component.ui.dictionary;

import static net.simpleframework.common.I18n.$m;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.coll.ArrayUtils;
import net.simpleframework.common.xml.AbstractElementBean;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageDocument;
import net.simpleframework.mvc.PageRequestResponse;
import net.simpleframework.mvc.component.ComponentException;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ui.colorpalette.ColorPaletteBean;
import net.simpleframework.mvc.component.ui.listbox.ListboxBean;
import net.simpleframework.mvc.component.ui.tree.TreeBean;
import net.simpleframework.mvc.component.ui.window.WindowBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class DictionaryBean extends WindowBean {
	private String bindingId;

	private String bindingText;

	private boolean multiple;

	private String clearAction, refreshAction;

	private boolean showHelpTooltip = true;

	private AbstractDictionaryTypeBean dictionaryTypeBean;

	private String jsSelectCallback;

	public DictionaryBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
		setDestroyOnClose(false);
		setWidth(240);
	}

	public DictionaryBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public String getJsSelectCallback() {
		return jsSelectCallback;
	}

	public DictionaryBean setJsSelectCallback(final String jsSelectCallback) {
		this.jsSelectCallback = jsSelectCallback;
		return this;
	}

	public AbstractDictionaryTypeBean getDictionaryTypeBean() {
		return dictionaryTypeBean;
	}

	public DictionaryBean setDictionaryTypeBean(final ComponentParameter cParameter,
			final AbstractDictionaryTypeBean dictionaryTypeBean) {
		this.dictionaryTypeBean = dictionaryTypeBean;

		if (dictionaryTypeBean instanceof DictionaryTreeBean) {
			final DictionaryTreeBean tree = (DictionaryTreeBean) dictionaryTypeBean;

			final String ref = tree.getRef();
			final TreeBean treeBean = (TreeBean) cParameter.getComponentBeanByName(ref);
			if (treeBean == null) {
				throw ComponentException.wrapException_ComponentRef(ref);
			}

			final String dictionaryId = hashId();
			treeBean.setCheckboxesThreeState(false);
			treeBean.setCheckboxes((Boolean) cParameter.getBeanProperty("multiple"));
			treeBean.setWidth("100%");
			treeBean.setContainerId("tree" + dictionaryId);
			treeBean.setJsDblclickCallback("selected" + dictionaryId + "(branch, ev);");
			treeBean.setJsCheckCallback("treeCheck" + dictionaryId + "(branch, checked, ev);");
			treeBean.setSelector((String) cParameter.getBeanProperty("selector"));

			// 参考DictionaryLoad，在页面装载时动态放入
			tree.setAttr("$$component", treeBean);
			cParameter.removeComponentBean(treeBean);
		} else if (dictionaryTypeBean instanceof DictionaryListBean) {
			final DictionaryListBean list = (DictionaryListBean) dictionaryTypeBean;
			final String ref = list.getRef();
			final ListboxBean listBean = (ListboxBean) cParameter.getComponentBeanByName(ref);
			if (listBean == null) {
				throw ComponentException.wrapException_ComponentRef(ref);
			}
			final String dictionaryId = hashId();
			listBean.setContainerId("list" + dictionaryId);
			listBean.setCheckbox((Boolean) cParameter.getBeanProperty("multiple"));
			listBean.setWidth("100%");
			listBean.setRunImmediately(false);
			listBean.setJsDblclickCallback("selected" + dictionaryId + "(item, json, ev);");
			setContentStyle("padding: 0;");

			list.setAttr("$$component", listBean);
			cParameter.removeComponentBean(listBean);
		} else if (dictionaryTypeBean instanceof DictionaryColorBean) {
			final String ref = ((DictionaryColorBean) dictionaryTypeBean).getRef();
			final ColorPaletteBean colorPalette = (ColorPaletteBean) cParameter
					.getComponentBeanByName(ref);
			if (colorPalette == null) {
				throw ComponentException.wrapException_ComponentRef(ref);
			}
			final String dictionaryId = hashId();
			colorPalette.setContainerId("color" + dictionaryId);
			colorPalette.setRunImmediately(false);
			colorPalette.setChangeCallback("change_" + dictionaryId + "(value);");

			setWidth(435);
			setHeight(340);
			setResizable(false);
			setDestroyOnClose(true);
			setTitle($m("createComponentBean.0"));
		} else if (dictionaryTypeBean instanceof DictionaryFontBean) {
			setWidth(280);
			setHeight(230);
			setResizable(false);
			setDestroyOnClose(true);
			setTitle($m("createComponentBean.1"));
		} else if (dictionaryTypeBean instanceof DictionarySmileyBean) {
			setResizable(false);
			setWidth(440);
			setHeight(240);
			setTitle($m("createComponentBean.2"));
		}
		return this;
	}

	public DictionaryBean addListboxRef(final PageRequestResponse rRequest, final String ref) {
		final ComponentParameter cParameter = ComponentParameter.get(rRequest, this);
		return setDictionaryTypeBean(cParameter, new DictionaryListBean(this).setRef(ref));
	}

	public DictionaryBean addTreeRef(final PageRequestResponse rRequest, final String ref) {
		final ComponentParameter cParameter = ComponentParameter.get(rRequest, this);
		return setDictionaryTypeBean(cParameter, new DictionaryTreeBean(this).setRef(ref));
	}

	public DictionaryBean addSmiley(final PageRequestResponse rRequest) {
		final ComponentParameter cParameter = ComponentParameter.get(rRequest, this);
		return setDictionaryTypeBean(cParameter, new DictionarySmileyBean(this));
	}

	@Override
	public boolean isPopup() {
		return true;
	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	public String getUrl() {
		return null;
	}

	public String getBindingId() {
		return bindingId;
	}

	public DictionaryBean setBindingId(final String bindingId) {
		this.bindingId = bindingId;
		return this;
	}

	public String getBindingText() {
		return bindingText;
	}

	public DictionaryBean setBindingText(final String bindingText) {
		this.bindingText = bindingText;
		return this;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public DictionaryBean setMultiple(final boolean multiple) {
		this.multiple = multiple;
		return this;
	}

	public String getClearAction(final ComponentParameter cParameter) {
		if ("false".equalsIgnoreCase(clearAction)) {
			return null;
		}
		if (!StringUtils.hasText(clearAction)) {
			final StringBuilder sb = new StringBuilder();
			sb.append("var o;");
			if (StringUtils.hasText(bindingId)) {
				sb.append("o=$(\"").append(bindingId).append("\");if(o) o.clear();");
			}
			if (StringUtils.hasText(bindingText)) {
				sb.append("o=$(\"").append(bindingText).append("\");if(o) o.clear();");
			}
			sb.append("$Actions[\"").append(cParameter.getBeanProperty("name")).append("\"].close();");
			return sb.toString();
		}
		return clearAction;
	}

	public DictionaryBean setClearAction(final String clearAction) {
		this.clearAction = clearAction;
		return this;
	}

	public String getRefreshAction(final ComponentParameter cParameter) {
		if ("false".equalsIgnoreCase(refreshAction)) {
			return null;
		}
		if (!StringUtils.hasText(refreshAction)) {
			final StringBuilder sb = new StringBuilder();
			final AbstractDictionaryTypeBean dictionaryType = getDictionaryTypeBean();
			if (dictionaryType instanceof DictionaryTreeBean) {
				sb.append("$Actions[\"").append(((DictionaryTreeBean) dictionaryType).getRef());
				sb.append("\"].refresh();");
			}
			return sb.toString();
		}
		return refreshAction;
	}

	public DictionaryBean setRefreshAction(final String refreshAction) {
		this.refreshAction = refreshAction;
		return this;
	}

	public boolean isShowHelpTooltip() {
		return showHelpTooltip;
	}

	public DictionaryBean setShowHelpTooltip(final boolean showHelpTooltip) {
		this.showHelpTooltip = showHelpTooltip;
		return this;
	}

	public static class DictionaryListBean extends AbstractDictionaryTypeBean {

		private String ref;

		public DictionaryListBean(final DictionaryBean dictionaryBean, final XmlElement xmlElement) {
			super(dictionaryBean, xmlElement);
		}

		public DictionaryListBean(final DictionaryBean dictionaryBean) {
			super(dictionaryBean, null);
		}

		public String getRef() {
			return ref;
		}

		public DictionaryListBean setRef(final String ref) {
			this.ref = ref;
			return this;
		}
	}

	public static class DictionaryTreeBean extends AbstractDictionaryTypeBean {

		private String ref;

		public DictionaryTreeBean(final DictionaryBean dictionaryBean, final XmlElement xmlElement) {
			super(dictionaryBean, xmlElement);
		}

		public DictionaryTreeBean(final DictionaryBean dictionaryBean) {
			this(dictionaryBean, null);
		}

		public String getRef() {
			return ref;
		}

		public DictionaryTreeBean setRef(final String ref) {
			this.ref = ref;
			return this;
		}
	}

	public static class DictionaryColorBean extends AbstractDictionaryTypeBean {

		private String ref;

		public DictionaryColorBean(final DictionaryBean dictionaryBean, final XmlElement xmlElement) {
			super(dictionaryBean, xmlElement);
		}

		public DictionaryColorBean(final DictionaryBean dictionaryBean) {
			this(dictionaryBean, null);
		}

		public String getRef() {
			return ref;
		}

		public DictionaryColorBean setRef(final String ref) {
			this.ref = ref;
			return this;
		}
	}

	public static class DictionaryFontBean extends AbstractDictionaryTypeBean {

		public DictionaryFontBean(final DictionaryBean dictionaryBean, final XmlElement xmlElement) {
			super(dictionaryBean, xmlElement);
		}

		public DictionaryFontBean(final DictionaryBean dictionaryBean) {
			this(dictionaryBean, null);
		}
	}

	public static class DictionarySmileyBean extends AbstractDictionaryTypeBean {

		public DictionarySmileyBean(final DictionaryBean dictionaryBean, final XmlElement xmlElement) {
			super(dictionaryBean, xmlElement);
		}

		public DictionarySmileyBean(final DictionaryBean dictionaryBean) {
			this(dictionaryBean, null);
		}
	}

	static abstract class AbstractDictionaryTypeBean extends AbstractElementBean {
		private final DictionaryBean dictionaryBean;

		public AbstractDictionaryTypeBean(final DictionaryBean dictionaryBean,
				final XmlElement xmlElement) {
			super(xmlElement);
			this.dictionaryBean = dictionaryBean;
		}

		public DictionaryBean getDictionaryBean() {
			return dictionaryBean;
		}
	}

	@Override
	protected String[] elementAttributes() {
		return ArrayUtils.add(new String[] { "jsSelectCallback", "clearAction", "refreshAction" },
				super.elementAttributes());
	}
}
