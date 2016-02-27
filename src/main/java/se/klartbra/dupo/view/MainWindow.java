package se.klartbra.dupo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.model.AllFilesWithCopies;
import se.klartbra.dupo.view.components.ButtonPanel;
import se.klartbra.dupo.view.components.DupoListPanel;
import se.klartbra.dupo.view.components.DupoTextArea;

/**
 * The applications main window.
 * @author svante
 *
 */
public class MainWindow {
	private static MainWindow instance;
	private static Logger log = LogManager.getLogger(MainWindow.class);

	private DupoTextArea  dupoTextArea = new DupoTextArea();
	private DupoListPanel dupoListPanel = new DupoListPanel();
	private ButtonPanel buttonPanel =  new ButtonPanel();

	private JFrame mainFrame;

	private MainWindow() {
		setup();
	}

	public static void createAndShowGui() {	
		instance =  new MainWindow();
	}

	public static MainWindow getInstance() {
		return instance;
	}

	private void setup() {
		JComponent contentPane = new JPanel();
		contentPane.setFocusable(false);
		contentPane.setOpaque(true); // Content panes has to be opaque.
		contentPane.setLayout(new BorderLayout());
		addComponentsToPane(contentPane);

		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(1000,710));
		mainFrame.add(contentPane);
		mainFrame.setContentPane(contentPane);
		mainFrame.pack();
		mainFrame.setVisible(true);		

		buttonPanel.setFindFocus();
		GuiHelper.center(mainFrame);
	}

	public void setText(String text) {
		dupoTextArea.setText(text);
	}

	public void addText(String text) {
		dupoTextArea.addText(text);
	}

	public void addSearchInfoText(String text) {
		dupoTextArea.addSearchInfoText(text);
	}

	private void addComponentsToPane(JComponent contentPane) {
		contentPane.add(dupoListPanel.getPanel(), BorderLayout.WEST);
		contentPane.add(dupoTextArea.getScrollPane(), BorderLayout.CENTER);
		contentPane.add(buttonPanel.getPanel(), BorderLayout.SOUTH);
	}

	public void populateListPanel(AllFilesWithCopies allFilesWithCopies) {
		dupoListPanel.populate(allFilesWithCopies);
	}
	
	public void setFinding(boolean isFinding) {
			buttonPanel.finding(isFinding);
	}
	
	public void repaint() {
		buttonPanel.repaint();
		dupoListPanel.repaint();
		dupoTextArea.repaint();
	}
	
}
