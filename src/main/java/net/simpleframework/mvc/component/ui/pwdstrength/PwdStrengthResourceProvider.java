package net.simpleframework.mvc.component.ui.pwdstrength;

import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.IComponentRegistry;
import net.simpleframework.mvc.component.IComponentResourceProvider.AbstractComponentResourceProvider;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class PwdStrengthResourceProvider extends AbstractComponentResourceProvider {
	public PwdStrengthResourceProvider(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String[] getCssPath(final PageParameter pParameter) {
		return new String[] { getCssResourceHomePath(pParameter) + "/pwdstrength.css" };
	}

	@Override
	public String[] getJavascriptPath(final PageParameter pParameter) {
		return new String[] { getResourceHomePath() + "/js/pwdstrength.js" };
	}
}
