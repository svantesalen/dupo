package se.klartbra.dupo.view.components;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.language.LanguageController;
import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.control.network.HttpBrowserController;
import se.klartbra.dupo.control.network.Paths;
import se.klartbra.dupo.view.PopUp;
import se.klartbra.dupo.view.look.DupoTheme;
import se.klartbra.images.ImageLoader;
import se.klartbra.images.ImagePaths;

public class ButtonPanel {

	private static Logger log = LogManager.getLogger(ButtonPanel.class);
	private static ButtonPanel instance;

	private JPanel jPanel = new JPanel();
	private FocusableButton findCopiesButton;
	private FocusableButton breakButton;
	private JButton helpButton;
	private JButton languageButton;
	private boolean isFinding = false;

	public ButtonPanel() {
		instance = this;
		addComponents();
		jPanel.setBackground(DupoTheme.bgColor);
	}

	public static ButtonPanel getInstance() {
		return instance;
	}

	public boolean isFinding() {
		return isFinding;
	}

	private void addComponents() {
		jPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

		findCopiesButton = new FindButton(Words.get("FIND_DUPLICATES_BUTTON"));
		findCopiesButton.setToolTipText(getParam("FIND_DUPLICATES_BUTTON_TOOLTIP"));
		findCopiesButton.setBackground(DupoTheme.bgColor);
		findCopiesButton.setForeground(DupoTheme.button_txt);
		findCopiesButton.setFont(DupoTheme.buttons);
		setBorder(findCopiesButton);
		jPanel.add(findCopiesButton);

		breakButton = new BreakButton(Words.get("BREAK_DUPLICATES_BUTTON"));
		breakButton.setToolTipText(getParam("BREAK_DUPLICATES_BUTTON_TOOLTIP"));
		breakButton.setBackground(DupoTheme.bgColor);
		breakButton.setForeground(DupoTheme.button_txt);
		breakButton.setFont(DupoTheme.buttons);
		breakButton.setVisible(false);
		setBorder(breakButton);
		jPanel.add(breakButton);

		Icon icon = ImageLoader.createIcon(ImagePaths.QUESTIONMARK_ICON_PATH, 40, 40);
		helpButton = new JButton(icon);
		helpButton.setToolTipText(Words.get("HELP_BUTTON_TOOLTIP"));
		helpButton.setBorderPainted(false);
		helpButton.setContentAreaFilled(false);
		helpButton.setFocusable(false);
		addListener(helpButton);
		jPanel.add(helpButton);	

		icon = ImageLoader.createIcon(ImagePaths.FLAG_ICON_PATH, 40, 40);
		languageButton = new JButton(icon);
		languageButton.setToolTipText(Words.get("HELP_BUTTON_TOOLTIP"));
		languageButton.setBorderPainted(false);
		languageButton.setContentAreaFilled(false);
		languageButton.setFocusable(false);
		addListener(languageButton);
		jPanel.add(languageButton);	
	}

	private void setBorder(JButton button) {
		button.setBorder(
				BorderFactory.createCompoundBorder(button.getBorder(), 
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}

	public JPanel getPanel() {
		return jPanel;
	}

	private String getParam(String parameter) {
		return "<html><h3>"+Words.get(parameter)+"</h3></html>";
	}

	private void addListener(JButton button) {
		button.addActionListener(e -> {
			JButton buttonClicked = (JButton) e.getSource();
			if (buttonClicked == helpButton) {
				handleHelp();
			} else if (buttonClicked == languageButton) {
				LanguageController.newLanguage();		
			} else {
				log.error("Program error. Undefined button clicked: " + buttonClicked.getName());
			}
		});
	}

	public void handleHelp() {
		boolean isOk = HttpBrowserController.open(Paths.SERVER_HELP_FILE);
		if (!isOk) {
			PopUp.errorMessage(jPanel, Words.get("NO_INTERNET_CONNECTION"));
		}
	}

	public void setFindFocus() {
		findCopiesButton.requestFocus();
	}

	public void finding(boolean isFinding) {
		this.isFinding = isFinding;
		findCopiesButton.setVisible(!isFinding);
		breakButton.setVisible(isFinding);
	}

	public void repaint() {
		findCopiesButton.setText(Words.get("FIND_DUPLICATES_BUTTON"));
		findCopiesButton.setToolTipText(getParam("FIND_DUPLICATES_BUTTON_TOOLTIP"));	
		breakButton.setText(Words.get("BREAK_DUPLICATES_BUTTON"));
		helpButton.setToolTipText(Words.get("HELP_BUTTON_TOOLTIP"));
		breakButton.setToolTipText(getParam("BREAK_DUPLICATES_BUTTON_TOOLTIP"));
	}

}
