package net.simpleframework.mvc.impl;

import net.simpleframework.common.I18n;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.coll.ArrayUtils;
import net.simpleframework.common.web.Browser;
import net.simpleframework.mvc.AbstractMVCPage;
import net.simpleframework.mvc.IPageResourceProvider.AbstractPageResourceProvider;
import net.simpleframework.mvc.MVCUtils;
import net.simpleframework.mvc.PageParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class DefaultPageResourceProvider extends AbstractPageResourceProvider {
	// final static String S2_FILE = "/js/s2.js?v=2.0.0_b1";

	public final static String SIZZLE_FILE = "/js/sizzle.js";

	public final static String PROTOTYPE_FILE = "/js/prototype.js?v=1.7.1";

	public final static String EFFECTS_FILE = "/js/effects.js?v=1.9";

	public final static String DRAGDROP_FILE = "/js/dragdrop.js?v=1.9";

	@Override
	public String getName() {
		return "default";
	}

	@Override
	public String[] getCssPath(final PageParameter pParameter) {
		return new String[] { getCssResourceHomePath(pParameter) + "/default.css" };
	}

	@Override
	public String[] getJavascriptPath(final PageParameter pParameter) {
		final String rPath = getResourceHomePath();
		String[] jsArr = new String[] { MVCUtils.getPageResourcePath() + "/js/core.js",
				rPath + SIZZLE_FILE, rPath + PROTOTYPE_FILE, rPath + EFFECTS_FILE,
				rPath + "/js/simple_" + I18n.getLocale().toString() + ".js", rPath + "/js/simple.js",
				rPath + "/js/simple_ui.js" };
		if (Browser.get(pParameter.request).isTrident()) {
			jsArr = ArrayUtils.add(jsArr, rPath + "/js/excanvas.js");
		}
		return jsArr;
	}

	@Override
	public String getInitJavascriptCode(final PageParameter pParameter) {
		final StringBuilder sb = new StringBuilder();
		final AbstractMVCPage pageView;
		final String jsCode;
		if ((pageView = pParameter.getPage()) != null
				&& StringUtils.hasText(jsCode = pageView.getInitJavascriptCode(pParameter))) {
			sb.append(jsCode);
		}
		return sb.toString();
	}
}
