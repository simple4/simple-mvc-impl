package net.simpleframework.mvc;

import net.simpleframework.common.ClassUtils;
import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.ComponentHandleException;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.IComponentHandler;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;
import net.simpleframework.mvc.component.base.ajaxrequest.DefaultAjaxRequestHandler;
import net.simpleframework.mvc.component.base.submit.AbstractSubmitHandler;
import net.simpleframework.mvc.component.base.submit.SubmitBean;
import net.simpleframework.mvc.component.base.validation.ValidationBean;
import net.simpleframework.mvc.component.ui.listbox.ListboxBean;
import net.simpleframework.mvc.component.ui.menu.AbstractMenuHandler;
import net.simpleframework.mvc.component.ui.menu.MenuBean;
import net.simpleframework.mvc.component.ui.menu.MenuItem;
import net.simpleframework.mvc.component.ui.menu.MenuItems;
import net.simpleframework.mvc.component.ui.pager.TablePagerBean;
import net.simpleframework.mvc.component.ui.tree.AbstractTreeHandler;
import net.simpleframework.mvc.component.ui.tree.TreeBean;
import net.simpleframework.mvc.component.ui.tree.TreeNode;
import net.simpleframework.mvc.component.ui.tree.TreeNodes;
import net.simpleframework.mvc.component.ui.window.WindowBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class AbstractBasePage extends AbstractMVELTemplatePage {

	protected AjaxRequestBean addAjaxRequest(final PageParameter pParameter, final String name) {
		return (AjaxRequestBean) addComponentBean(pParameter, name, AjaxRequestBean.class)
				.setHandleClass(getClass());
	}

	protected AjaxRequestBean addAjaxRequest(final PageParameter pParameter, final String name,
			final Class<? extends AbstractMVCPage> pageClass) {
		return addComponentBean(pParameter, name, AjaxRequestBean.class).setUrlForward(
				uriFor(pageClass));
	}

	protected ValidationBean addValidationBean(final PageParameter pParameter, final String name) {
		return addComponentBean(pParameter, name, ValidationBean.class);
	}

	protected TreeBean addTreeBean(final PageParameter pParameter, final String name) {
		return (TreeBean) addComponentBean(pParameter, name, TreeBean.class).setHandleClass(
				getClass());
	}

	protected ListboxBean addListBoxBean(final PageParameter pParameter, final String name) {
		return (ListboxBean) addComponentBean(pParameter, name, ListboxBean.class).setHandleClass(
				getClass());
	}

	protected TablePagerBean addTablePagerBean(final PageParameter pParameter, final String name) {
		return addComponentBean(pParameter, name, TablePagerBean.class);
	}

	protected WindowBean addWindowBean(final PageParameter pParameter, final String name) {
		return addComponentBean(pParameter, name, WindowBean.class);
	}

	public static class AjaxRequest extends DefaultAjaxRequestHandler {

		@Override
		@SuppressWarnings("unchecked")
		public IForward ajaxProcess(final ComponentParameter cParameter) {
			try {
				final AjaxRequestBean ajaxRequestBean = (AjaxRequestBean) cParameter.componentBean;
				final Class<AbstractMVCPage> handleClass = (Class<AbstractMVCPage>) ClassUtils
						.forName(ajaxRequestBean.getHandleClass());
				return (IForward) handleClass.getMethod(ajaxRequestBean.getHandleMethod(),
						ComponentParameter.class).invoke(AbstractMVCPage.get(cParameter), cParameter);
			} catch (final Exception e) {
				throw ComponentHandleException.of(e);
			}
		}
	}

	public static class Submit extends AbstractSubmitHandler {

		@Override
		@SuppressWarnings("unchecked")
		public AbstractUrlForward submit(final ComponentParameter cParameter) {
			try {
				final SubmitBean submitBean = (SubmitBean) cParameter.componentBean;
				final Class<AbstractMVCPage> handleClass = (Class<AbstractMVCPage>) ClassUtils
						.forName(submitBean.getHandleClass());
				return (AbstractUrlForward) handleClass.getMethod(submitBean.getHandleMethod(),
						ComponentParameter.class).invoke(AbstractMVCPage.get(cParameter), cParameter);
			} catch (final Exception e) {
				throw ComponentHandleException.of(e);
			}
		}
	}

	public static class Tree extends AbstractTreeHandler {

		@Override
		@SuppressWarnings("unchecked")
		public TreeNodes getTreenodes(final ComponentParameter cParameter, final TreeNode treeNode) {
			try {
				final TreeBean treeBean = (TreeBean) cParameter.componentBean;
				final Class<AbstractMVCPage> handleClass = (Class<AbstractMVCPage>) ClassUtils
						.forName(treeBean.getHandleClass());
				return (TreeNodes) handleClass.getMethod("getTreenodes", ComponentParameter.class,
						TreeNode.class).invoke(AbstractMVCPage.get(cParameter), cParameter, treeNode);
			} catch (final Exception e) {
				throw ComponentHandleException.of(e);
			}
		}
	}

	public static class Menu extends AbstractMenuHandler {

		@Override
		@SuppressWarnings("unchecked")
		public MenuItems getMenuItems(final ComponentParameter cParameter, final MenuItem menuItem) {
			try {
				final MenuBean menuBean = (MenuBean) cParameter.componentBean;
				final Class<AbstractMVCPage> handleClass = (Class<AbstractMVCPage>) ClassUtils
						.forName(menuBean.getHandleClass());
				return (MenuItems) handleClass.getMethod("getMenuItems", ComponentParameter.class,
						MenuItem.class).invoke(AbstractMVCPage.get(cParameter), cParameter, menuItem);
			} catch (final Exception e) {
				throw ComponentHandleException.of(e);
			}
		}
	}

	@Override
	public IComponentHandler createComponentHandler(final AbstractComponentBean componentBean) {
		if (componentBean instanceof AjaxRequestBean) {
			return new AjaxRequest();
		} else if (componentBean instanceof SubmitBean) {
			return new Submit();
		} else if (componentBean instanceof TreeBean) {
			return new Tree();
		} else if (componentBean instanceof MenuBean) {
			return new Menu();
		}
		return null;
	}
}
