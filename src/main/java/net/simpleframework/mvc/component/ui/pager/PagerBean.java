package net.simpleframework.mvc.component.ui.pager;

import static net.simpleframework.common.I18n.$m;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.ctx.permission.IPermissionHandler;
import net.simpleframework.mvc.PageDocument;
import net.simpleframework.mvc.component.AbstractContainerBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class PagerBean extends AbstractContainerBean {

	private EPagerBarLayout pagerBarLayout;

	private String title;

	private int pageItems;

	private int indexPages;

	/* 分页内容的jsp路径 */
	private String jspPath;

	private String noResultDesc;

	private String jsLoadedCallback;

	private boolean showEditPageItems = true;

	private String exportAction;

	private boolean showLoading = true, loadingModal = false;

	private String pageNumberParameterName, pageItemsParameterName;

	private String catalogId;

	private boolean assertCatalogNull;

	private String role;

	public PagerBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
		setRole(IPermissionHandler.sj_anonymous).setNoResultDesc($m("pager.0"));
	}

	public PagerBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public String getTitle() {
		return title;
	}

	public PagerBean setTitle(final String title) {
		this.title = title;
		return this;
	}

	public int getPageItems() {
		return pageItems > 0 ? pageItems
				: (getPagerBarLayout() == EPagerBarLayout.none ? Integer.MAX_VALUE : 15);
	}

	public PagerBean setPageItems(final int pageItems) {
		this.pageItems = pageItems;
		return this;
	}

	public boolean isShowEditPageItems() {
		return showEditPageItems;
	}

	public PagerBean setShowEditPageItems(final boolean showEditPageItems) {
		this.showEditPageItems = showEditPageItems;
		return this;
	}

	public int getIndexPages() {
		return indexPages > 0 ? indexPages : 9;
	}

	public PagerBean setIndexPages(final int indexPages) {
		this.indexPages = indexPages;
		return this;
	}

	public EPagerBarLayout getPagerBarLayout() {
		return pagerBarLayout != null ? pagerBarLayout : EPagerBarLayout.both;
	}

	public PagerBean setPagerBarLayout(final EPagerBarLayout pagerBarLayout) {
		this.pagerBarLayout = pagerBarLayout;
		return this;
	}

	public String getJspPath() {
		return jspPath;
	}

	public PagerBean setJspPath(final String jspPath) {
		this.jspPath = jspPath;
		return this;
	}

	public String getNoResultDesc() {
		return noResultDesc;
	}

	public PagerBean setNoResultDesc(final String noResultDesc) {
		this.noResultDesc = noResultDesc;
		return this;
	}

	public String getJsLoadedCallback() {
		return jsLoadedCallback;
	}

	public PagerBean setJsLoadedCallback(final String jsLoadedCallback) {
		this.jsLoadedCallback = jsLoadedCallback;
		return this;
	}

	public String getRole() {
		return StringUtils.text(role, default_role);
	}

	public PagerBean setRole(final String role) {
		this.role = role;
		return this;
	}

	public String getExportAction() {
		return exportAction;
	}

	public PagerBean setExportAction(final String exportAction) {
		this.exportAction = exportAction;
		return this;
	}

	static final String PAGE_NUMBER = "pageNumber";

	static final String PAGE_ITEMs = "pageItems";

	public String getPageNumberParameterName() {
		return StringUtils.text(pageNumberParameterName, PAGE_NUMBER);
	}

	public PagerBean setPageNumberParameterName(final String pageNumberParameterName) {
		this.pageNumberParameterName = pageNumberParameterName;
		return this;
	}

	public String getPageItemsParameterName() {
		return StringUtils.text(pageItemsParameterName, PAGE_ITEMs);
	}

	public PagerBean setPageItemsParameterName(final String pageItemsParameterName) {
		this.pageItemsParameterName = pageItemsParameterName;
		return this;
	}

	public boolean isShowLoading() {
		return showLoading;
	}

	public PagerBean setShowLoading(final boolean showLoading) {
		this.showLoading = showLoading;
		return this;
	}

	public boolean isLoadingModal() {
		return loadingModal;
	}

	public PagerBean setLoadingModal(final boolean loadingModal) {
		this.loadingModal = loadingModal;
		return this;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public PagerBean setCatalogId(final String catalogId) {
		this.catalogId = catalogId;
		return this;
	}

	public boolean isAssertCatalogNull() {
		return assertCatalogNull;
	}

	public PagerBean setAssertCatalogNull(final boolean assertCatalogNull) {
		this.assertCatalogNull = assertCatalogNull;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "jsLoadedCallback" };
	}
}
