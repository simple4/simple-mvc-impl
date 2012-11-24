package net.simpleframework.mvc.component.ui.tree;

import java.util.Map;

import net.simpleframework.common.coll.KVMap;
import net.simpleframework.mvc.component.AbstractComponentHandler;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class AbstractTreeHandler extends AbstractComponentHandler implements ITreeHandler {

	@Override
	public TreeNodes getTreenodes(final ComponentParameter cParameter, final TreeNode parent) {
		return null;
	}

	@Override
	public Map<String, Object> getTreenodeAttributes(final ComponentParameter cParameter,
			final TreeNode treeNode) {
		return new KVMap();
	}

	@Override
	public boolean doDragDrop(final ComponentParameter cParameter, final TreeNode drag,
			final TreeNode drop) {
		return false;
	}
}
