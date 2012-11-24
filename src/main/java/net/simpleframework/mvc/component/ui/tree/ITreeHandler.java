package net.simpleframework.mvc.component.ui.tree;

import java.util.Map;

import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.IComponentHandler;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public interface ITreeHandler extends IComponentHandler {

	/**
	 * 得到指定节点孩子列表
	 * 
	 * @param cParameter
	 * @param parent
	 *           父节点，null表示第一层
	 * @return
	 */
	TreeNodes getTreenodes(ComponentParameter cParameter, TreeNode parent);

	/**
	 * 
	 * @param cParameter
	 * @param parent
	 * @return
	 */
	Map<String, Object> getTreenodeAttributes(ComponentParameter cParameter, TreeNode parent);

	/**
	 * 拖放逻辑
	 * 
	 * @param cParameter
	 * @param drag
	 * @param drop
	 * @return
	 */
	boolean doDragDrop(ComponentParameter cParameter, TreeNode drag, TreeNode drop);
}
