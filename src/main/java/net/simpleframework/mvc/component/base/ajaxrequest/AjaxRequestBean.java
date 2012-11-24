package net.simpleframework.mvc.component.base.ajaxrequest;

import net.simpleframework.common.StringUtils;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageDocument;
import net.simpleframework.mvc.component.AbstractComponentBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class AjaxRequestBean extends AbstractComponentBean {
	private String handleMethod;

	private String encoding;

	private String updateContainerId;

	private boolean updateContainerCache;

	private String confirmMessage;

	private EThrowException throwException;

	private boolean showLoading = true, loadingModal = false, parallel = false,
			disabledTriggerAction = true;

	private String jsCompleteCallback;

	private String urlForward;

	private String ajaxRequestId;

	private String role;

	public AjaxRequestBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
		setRunImmediately(false);
	}

	public AjaxRequestBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public String getHandleMethod() {
		return handleMethod;
	}

	public AjaxRequestBean setHandleMethod(final String handleMethod) {
		this.handleMethod = handleMethod;
		return this;
	}

	public boolean isUpdateContainerCache() {
		return updateContainerCache;
	}

	public AjaxRequestBean setUpdateContainerCache(final boolean updateContainerCache) {
		this.updateContainerCache = updateContainerCache;
		return this;
	}

	public String getEncoding() {
		return encoding;
	}

	public AjaxRequestBean setEncoding(final String encoding) {
		this.encoding = encoding;
		return this;
	}

	public boolean isShowLoading() {
		return showLoading;
	}

	public AjaxRequestBean setShowLoading(final boolean showLoading) {
		this.showLoading = showLoading;
		return this;
	}

	public boolean isLoadingModal() {
		return loadingModal;
	}

	public AjaxRequestBean setLoadingModal(final boolean loadingModal) {
		this.loadingModal = loadingModal;
		return this;
	}

	public boolean isParallel() {
		return parallel;
	}

	public AjaxRequestBean setParallel(final boolean parallel) {
		this.parallel = parallel;
		return this;
	}

	public boolean isDisabledTriggerAction() {
		return disabledTriggerAction;
	}

	public AjaxRequestBean setDisabledTriggerAction(final boolean disabledTriggerAction) {
		this.disabledTriggerAction = disabledTriggerAction;
		return this;
	}

	public String getUrlForward() {
		return urlForward;
	}

	public AjaxRequestBean setUrlForward(final String urlForward) {
		this.urlForward = urlForward;
		return this;
	}

	public String getConfirmMessage() {
		return confirmMessage;
	}

	public AjaxRequestBean setConfirmMessage(final String confirmMessage) {
		this.confirmMessage = confirmMessage;
		return this;
	}

	public EThrowException getThrowException() {
		return throwException == null ? EThrowException.window : throwException;
	}

	public AjaxRequestBean setThrowException(final EThrowException throwException) {
		this.throwException = throwException;
		return this;
	}

	public String getUpdateContainerId() {
		return updateContainerId;
	}

	public AjaxRequestBean setUpdateContainerId(final String updateContainerId) {
		this.updateContainerId = updateContainerId;
		return this;
	}

	public String getJsCompleteCallback() {
		return jsCompleteCallback;
	}

	public AjaxRequestBean setJsCompleteCallback(final String jsCompleteCallback) {
		this.jsCompleteCallback = jsCompleteCallback;
		return this;
	}

	public String getAjaxRequestId() {
		return ajaxRequestId;
	}

	public AjaxRequestBean setAjaxRequestId(final String ajaxRequestId) {
		this.ajaxRequestId = ajaxRequestId;
		return this;
	}

	public String getRole() {
		return StringUtils.text(role, default_role);
	}

	public AjaxRequestBean setRole(final String role) {
		this.role = role;
		return this;
	}

	@Override
	public String getHandleClass() {
		final String handleClass = super.getHandleClass();
		if (StringUtils.hasText(getUrlForward())) {
			setHandleMethod("doUrlForward");
			if (!StringUtils.hasText(handleClass)) {
				return DefaultAjaxRequestHandler.class.getName();
			}
		}
		return handleClass;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "confirmMessage", "jsCompleteCallback", "urlForward" };
	}
}
