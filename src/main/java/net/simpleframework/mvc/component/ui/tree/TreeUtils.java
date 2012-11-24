package net.simpleframework.mvc.component.ui.tree;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import net.simpleframework.common.Convert;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.bean.BeanUtils;
import net.simpleframework.common.html.js.JavascriptUtils;
import net.simpleframework.common.logger.Log;
import net.simpleframework.common.logger.LogFactory;
import net.simpleframework.mvc.IForward;
import net.simpleframework.mvc.JsonForward;
import net.simpleframework.mvc.MVCContextFactory;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class TreeUtils {
	static Log log = LogFactory.getLogger(TreeUtils.class);

	public static final String BEAN_ID = "tree_@bid";

	public static IForward dropHandler(final ComponentParameter cParameter) {
		final JsonForward json = new JsonForward();
		final String treeName = (String) cParameter.getBeanProperty("name");
		final IForward forward = MVCContextFactory.permission().accessForward(cParameter,
				cParameter.getBeanProperty("roleDrop"));
		if (forward != null) {
			json.put("responseText", forward.getResponseText(cParameter));
			json.put("ajaxRequestId", treeName);
			json.put("dropOk", false);
		} else {
			final ITreeHandler tHandle = (ITreeHandler) cParameter.getComponentHandler();
			if (tHandle != null) {
				final TreeNode drag = getTreenodeById(cParameter, cParameter.getParameter("drag_id"));
				final TreeNode drop = getTreenodeById(cParameter, cParameter.getParameter("drop_id"));
				json.put("dropOk", tHandle.doDragDrop(cParameter, drag, drop));
			} else {
				json.put("dropOk", false);
			}
		}
		return json;
	}

	public static String nodeHandler(final ComponentParameter cParameter) {
		final TreeBean treeBean = (TreeBean) cParameter.componentBean;
		final TreeRender render = (TreeRender) treeBean.getComponentRegistry().getComponentRender();
		final TreeNode node = getTreenodeById(cParameter, cParameter.getParameter("nodeId"));
		if (node == null) {
			return "[]";
		} else {
			return render.jsonData(cParameter, getTreenodes(cParameter, node));
		}
	}

	public static TreeNodes getTreenodes(final ComponentParameter cParameter) {
		final ITreeHandler treeHandle = (ITreeHandler) cParameter.getComponentHandler();
		TreeNodes nodes = null;
		if (treeHandle != null) {
			nodes = treeHandle.getTreenodes(cParameter, null);
		}
		if (nodes == null) {
			nodes = ((TreeBean) cParameter.componentBean).getTreeNodes();
		}
		return nodes;
	}

	public static TreeNodes getTreenodes(final ComponentParameter cParameter, final TreeNode treeNode) {
		TreeNodes nodes = null;
		final ITreeHandler treeHandle = (ITreeHandler) cParameter.getComponentHandler();
		if (treeHandle != null) {
			nodes = treeHandle.getTreenodes(cParameter, treeNode);
		}
		if (nodes == null) {
			nodes = treeNode.children();
		}
		return nodes;
	}

	public static TreeNode getTreenodeById(final ComponentParameter cParameter, final String id) {
		final String[] idArray = StringUtils.split(id, "_");
		final Collection<TreeNode> treeNodes = getTreenodes(cParameter);
		return idArray != null ? findTreenode(cParameter,
				new LinkedList<String>(Arrays.asList(idArray)), treeNodes) : null;
	}

	private static TreeNode findTreenode(final ComponentParameter cParameter,
			final LinkedList<String> ll, final Collection<TreeNode> coll) {
		if (ll.size() == 0 || coll.size() == 0) {
			return null;
		}
		final String id = ll.get(0);
		for (final TreeNode node : coll) {
			if (id.equals(node.getId())) {
				ll.removeFirst();
				if (ll.size() == 0) {
					return node;
				}
				final Collection<TreeNode> treeNodes = getTreenodes(cParameter, node);
				final TreeNode find = findTreenode(cParameter, ll, treeNodes);
				if (find != null) {
					return find;
				}
			}
		}
		return null;
	}

	public static boolean isDynamicLoading(final ComponentParameter cParameter,
			final TreeNode treeNode) {
		return Convert.toBool(cParameter.getBeanProperty("dynamicLoading"));
	}

	static String genEvent2(final Object bean, final String prefix, final String[] properties) {
		if (properties == null) {
			return "";
		}
		final StringBuilder sb = new StringBuilder();
		for (final String property : properties) {
			final String event = (String) BeanUtils.getProperty(bean, property);
			if (StringUtils.hasText(event)) {
				sb.append(StringUtils.blank(prefix)).append(property).append(" = \"");
				sb.append(JavascriptUtils.escape(event)).append("\";");
			}
		}
		return sb.toString();
	}
}
