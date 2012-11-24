package net.simpleframework.mvc.component.ui.syntaxhighlighter;

import net.simpleframework.mvc.component.AbstractComponentRender.ComponentJavascriptRender;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRenderUtils;
import net.simpleframework.mvc.component.ComponentUtils;
import net.simpleframework.mvc.component.IComponentRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class SyntaxHighlighterRender extends ComponentJavascriptRender {

	public SyntaxHighlighterRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		final StringBuilder sb = new StringBuilder();
		final String hp = ComponentUtils.getResourceHomePath(SyntaxHighlighterBean.class) + "/js/";
		sb.append("SyntaxHighlighter.autoloader(");
		sb.append("['cpp', 'c++', 'c', '").append(hp).append("shBrushCpp.js'],");
		sb.append("['c#', 'c-sharp', 'csharp', '").append(hp).append("shBrushCSharp.js'],");
		sb.append("['css', '").append(hp).append("shBrushCss.js'],");
		sb.append("['groovy', '").append(hp).append("shBrushGroovy.js'],");
		sb.append("['java', '").append(hp).append("shBrushJava.js'],");
		sb.append("['js', 'javascript', '").append(hp).append("shBrushJScript.js'],");
		sb.append("['php', '").append(hp).append("shBrushPhp.js'],");
		sb.append("['py', 'python', '").append(hp).append("shBrushPython.js'],");
		sb.append("['ruby', 'rails', 'ror', 'rb', '").append(hp).append("shBrushRuby.js'],");
		sb.append("['sql', '").append(hp).append("shBrushSql.js'],");
		sb.append("['xml', 'xhtml', 'xslt', 'html', '").append(hp).append("shBrushXml.js']");
		sb.append(");");
		sb.append("SyntaxHighlighter.all({");
		sb.append("'toolbar' : false,");
		sb.append("'tab-size' : 2");
		sb.append("});");
		// loadScript
		final StringBuilder sb2 = new StringBuilder();
		sb.append(actionFunc).append(".editor = function() { $Actions['window_")
				.append(cParameter.hashId()).append("'](); };");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString(), sb2.toString());
	}
}
