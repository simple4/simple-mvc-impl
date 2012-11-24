package net.simpleframework.mvc.component.ui.tree;

import java.util.Collection;
import java.util.Iterator;

import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentRender(TreeRender.class)
@ComponentResourceProvider(TreeResourceProvider.class)
public abstract class AbstractTreeRegistry extends AbstractComponentRegistry {

	@Override
	public TreeBean createComponentBean(final PageParameter pParameter, final Object data) {
		final TreeBean treeBean = (TreeBean) super.createComponentBean(pParameter, data);
		if (data instanceof XmlElement) {
			final Iterator<?> it = ((XmlElement) data).elementIterator("treenode");
			while (it.hasNext()) {
				final XmlElement xmlElement = (XmlElement) it.next();
				setTreeNode(pParameter, treeBean, null, treeBean.getTreeNodes(), xmlElement);
			}
		}
		return treeBean;
	}

	protected abstract TreeNode createTreeNode(XmlElement xmlElement, TreeBean treeBean,
			TreeNode parent);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setTreeNode(final PageParameter pParameter, final TreeBean treeBean,
			final TreeNode parent, final Collection children, final XmlElement xmlElement) {
		final TreeNode node = createTreeNode(xmlElement, treeBean, parent);
		if (node == null) {
			return;
		}
		node.parseElement(pParameter.getScriptEval());
		children.add(node);
		final Iterator<?> it = xmlElement.elementIterator("treenode");
		while (it.hasNext()) {
			final XmlElement ele = (XmlElement) it.next();
			setTreeNode(pParameter, treeBean, node, node.children(), ele);
		}
	}
}
