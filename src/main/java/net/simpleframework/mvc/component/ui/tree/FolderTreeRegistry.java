package net.simpleframework.mvc.component.ui.tree;

import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentName;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(FolderTreeRegistry.folderTree)
@ComponentBean(FolderTreeBean.class)
public class FolderTreeRegistry extends AbstractTreeRegistry {
	public static final String folderTree = "folderTree";

	@Override
	protected TreeNode createTreeNode(final XmlElement xmlElement, final TreeBean treeBean,
			final TreeNode parent) {
		return null;
	}
}
