package se.klartbra.dupo.view.components;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.controller.network.HttpBrowserController;
import se.klartbra.dupo.controller.network.Paths;
import se.klartbra.dupo.view.PopUp;
import se.klartbra.dupo.view.look.DupoTheme;
import se.klartbra.images.ImageLoader;
import se.klartbra.images.ImagePaths;

public class ButtonPanel {

	private static Logger log = LogManager.getLogger(ButtonPanel.class);
	private static ButtonPanel instance;

	private JPanel jPanel = new JPanel();
	private FocusableButton findCopiesButton;
	private FocusableButton deleteCopiesButton;
	private JButton helpButton;

	public ButtonPanel() {
		instance = this;
		addComponents();
		jPanel.setBackground(DupoTheme.bgColor);
	}

	public static ButtonPanel getInstance() {
		return instance;
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

		deleteCopiesButton = new DeleteButton(Words.get("DELETE_DUPLICATES_BUTTON"));
		deleteCopiesButton.setToolTipText(getParam("DELETE_DUPLICATES_BUTTON_TOOLTIP"));
		deleteCopiesButton.setBackground(DupoTheme.bgColor);
		deleteCopiesButton.setForeground(DupoTheme.button_txt);
		deleteCopiesButton.setFont(DupoTheme.buttons);
		setBorder(deleteCopiesButton);
//		jPanel.add(deleteCopiesButton);

		Icon icon = ImageLoader.createIcon(ImagePaths.QUESTIONMARK_ICON_PATH, 40, 40);
		helpButton = new JButton(icon);
		helpButton.setToolTipText(Words.get("HELP_BUTTON_TOOLTIP"));
		helpButton.setBorderPainted(false);
		helpButton.setContentAreaFilled(false);
		helpButton.setFocusable(false);
		addListener(helpButton);
		jPanel.add(helpButton);	
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
}
