package net.simpleframework.mvc.component.base.submit;

import net.simpleframework.mvc.AbstractUrlForward;
import net.simpleframework.mvc.IMultipartFile;
import net.simpleframework.mvc.MultipartPageRequest;
import net.simpleframework.mvc.PageRequestResponse;
import net.simpleframework.mvc.component.AbstractComponentHandler;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class AbstractSubmitHandler extends AbstractComponentHandler implements
		ISubmitHandler {

	@Override
	public AbstractUrlForward submit(final ComponentParameter cParameter) {
		return null;
	}

	protected IMultipartFile getMultipartFile(final PageRequestResponse rRequest,
			final String filename) {
		return rRequest.request instanceof MultipartPageRequest ? ((MultipartPageRequest) rRequest.request)
				.getFile(filename) : null;
	}
}
