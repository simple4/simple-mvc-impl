package net.simpleframework.mvc.component.ui.propeditor;

import java.util.Iterator;

import net.simpleframework.common.html.js.EJavascriptEvent;
import net.simpleframework.common.script.IScriptEval;
import net.simpleframework.common.script.ScriptEvalUtils;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentException;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(PropEditorRegistry.propEditor)
@ComponentBean(PropEditorBean.class)
@ComponentRender(PropEditorRender.class)
@ComponentResourceProvider(PropEditorResourceProvider.class)
public class PropEditorRegistry extends AbstractComponentRegistry {
	public static final String propEditor = "propEditor";

	@Override
	public PropEditorBean createComponentBean(final PageParameter pParameter, final Object data) {
		final PropEditorBean formEditor = (PropEditorBean) super
				.createComponentBean(pParameter, data);
		if (!(data instanceof XmlElement)) {
			return formEditor;
		}
		final IScriptEval scriptEval = pParameter.getScriptEval();
		final Iterator<?> it = ((XmlElement) data).elementIterator("field");
		while (it.hasNext()) {
			final XmlElement xmlElement = (XmlElement) it.next();
			final PropField propField = new PropField(xmlElement);
			propField.parseElement(scriptEval);
			formEditor.getFormFields().add(propField);

			final Iterator<?> it2 = xmlElement.elementIterator("component");
			while (it2.hasNext()) {
				final XmlElement xmlElement2 = (XmlElement) it2.next();
				final InputComp inputComp = new InputComp(xmlElement2);
				inputComp.parseElement(scriptEval);

				final Iterator<?> it3 = xmlElement2.elementIterator("event");
				while (it3.hasNext()) {
					final XmlElement xmlElement3 = (XmlElement) it3.next();
					try {
						final String name = ScriptEvalUtils.replaceExpr(scriptEval,
								xmlElement3.attributeValue("name"));
						final EJavascriptEvent e = Enum.valueOf(EJavascriptEvent.class, name);
						inputComp.getEventCallback().put(e,
								ScriptEvalUtils.replaceExpr(scriptEval, xmlElement3.getText()));
					} catch (final Exception e) {
						throw ComponentException.of(e);
					}
				}
				propField.getInputComponents().add(inputComp);
			}
		}
		return formEditor;
	}
}
