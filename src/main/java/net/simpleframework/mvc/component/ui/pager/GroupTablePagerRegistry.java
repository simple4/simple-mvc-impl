package net.simpleframework.mvc.component.ui.pager;

import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentName;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(GroupTablePagerRegistry.groupTablePager)
@ComponentBean(GroupTablePagerBean.class)
public class GroupTablePagerRegistry extends TablePagerRegistry {

	public static final String groupTablePager = "groupTablePager";

}