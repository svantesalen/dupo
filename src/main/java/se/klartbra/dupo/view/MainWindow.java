package se.klartbra.dupo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.Controller;

public class MainWindow {
	private static MainWindow instance;
	private static Logger log = LogManager.getLogger(MainWindow.class);

	private JFrame mainFrame;
	private JTextPane textPane;
	private JButton selectButton;
	private MainWindow() {
		setup();
	}

	public static MainWindow getInstance() {
		return instance;
	}
	
	private void setup() {
		textPane = new JTextPane();
		textPane.setText("sad sdfg sdg");
		textPane.setEditable(false);	

		JScrollPane scrollPane = new JScrollPane(textPane);
		selectButton = new JButton("Select");
		selectButton.setPreferredSize(new Dimension(80,60));
		selectButton.addActionListener(e -> {
			handleButtonClick((JButton) e.getSource());
		});

		JPanel contentPane = new JPanel();
		contentPane.setOpaque(true); // Content panes has to be opaque.
		contentPane.setLayout(new BorderLayout());
		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(selectButton, BorderLayout.SOUTH);

		mainFrame = new JFrame();
		mainFrame.add(contentPane);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(710, 710));
		mainFrame.setContentPane(contentPane);
		mainFrame.pack();
		mainFrame.setVisible(true);		

		GuiHelper.center(mainFrame);
	}
	
	public void setText(String text) {
		textPane.setText(text);
	}

	public void addLine(String line) {
		textPane.setText(textPane.getText() +"\n" + line);
	}
	
	private void handleButtonClick(JButton buttonClicked) {
		if (buttonClicked == selectButton) {
			log.debug("Select button clicked.");
			Controller.getController().onClick();
		}  else {
			log.debug("Program error. Undefined button clicked: " + buttonClicked.getName());
		}
	}

	public static void createAndShowGui() {	
		instance =  new MainWindow();
	}
}
