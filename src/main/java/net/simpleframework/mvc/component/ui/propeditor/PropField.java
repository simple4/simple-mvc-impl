package net.simpleframework.mvc.component.ui.propeditor;

import net.simpleframework.common.xml.AbstractElementBean;
import net.simpleframework.common.xml.XmlElement;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class PropField extends AbstractElementBean {

	private String label;

	private String labelStyle = "width: 100px;";

	private String description;

	private InputComps inputComps;

	public PropField(final XmlElement xmlElement) {
		super(xmlElement);
	}

	public PropField() {
		super(null);
	}

	public PropField(final String label) {
		super(null);
		setLabel(label);
	}

	public InputComps getInputComponents() {
		if (inputComps == null) {
			inputComps = InputComps.of();
		}
		return inputComps;
	}

	public PropField addComponents(final InputComp... components) {
		getInputComponents().append(components);
		return this;
	}

	public String getLabel() {
		return label;
	}

	public PropField setLabel(final String label) {
		this.label = label;
		return this;
	}

	public String getLabelStyle() {
		return labelStyle;
	}

	public PropField setLabelStyle(final String labelStyle) {
		this.labelStyle = labelStyle;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public PropField setDescription(final String description) {
		this.description = description;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "description" };
	}
}
