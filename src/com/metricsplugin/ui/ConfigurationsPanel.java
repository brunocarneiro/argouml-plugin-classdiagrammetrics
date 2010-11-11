package com.metricsplugin.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.argouml.i18n.Translator;
import org.argouml.ui.ProjectBrowser;

import com.metricsplugin.util.Context;
import com.metricsplugin.util.MeasureScope;
import com.metricsplugin.util.MetricsConfiguration;

public class ConfigurationsPanel extends JDialog{

	
	private MetricsConfiguration metricsConfiguration;
	
	private JComboBox measureScope = new JComboBox(MeasureScope.values());
	private JCheckBox menuCategory = new JCheckBox();
	
	
	public ConfigurationsPanel(String title){
		setTitle(Translator.localize(title));
		Dimension dim = ProjectBrowser.getInstance().getToolkit().getScreenSize();
		Rectangle abounds = this.getBounds();
		setLocation((dim.width - abounds.width) / 2,
			      (dim.height - abounds.height) / 2);
	
		setSize(400,200);
		GridLayout layout = new GridLayout(2,2);
		setLayout(layout);
		add(new JLabel(Translator.localize(UiConstants.MEASURE_SCOPE_NAME)));
		add(measureScope);
		menuCategory.addActionListener(new ChangeMenuAction());
		add(new JLabel(Translator.localize(UiConstants.CATEGORY_MENU_HELP)));
		add(menuCategory);
		measureScope.addActionListener(new MeasureAction());
		this.setVisible(true);

	}

	public ConfigurationsPanel(){
		this(UiConstants.CONFIGURATION_FRAME_TITLE);
	}

	public MetricsConfiguration getMetricsConfiguration() {
		return metricsConfiguration;
	}


	public void setMetricsConfiguration(MetricsConfiguration metricsConfiguration) {
		this.metricsConfiguration = metricsConfiguration;
	}
	

}


class MeasureAction implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MeasureScope scope = (MeasureScope)((JComboBox)e.getSource()).getSelectedItem();
		((MetricsConfiguration)Context.getInstance().get(Context.METRICS_CONFIGURATION)).setEscopo(scope);
	}
}

class ChangeMenuAction implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Boolean categoryMenu = (Boolean) Context.getInstance().get(Context.CATEGORY_MENU_ENABLED);
		if(categoryMenu==null || categoryMenu==false)
			SwingHelper.createCategoryMenu();
		else{
			SwingHelper.restoreDefaultMenu();
		}
	}
}
