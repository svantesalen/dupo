package se.klartbra.dupo.view.components;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.view.look.Colors;
import se.klartbra.dupo.view.look.DupoTheme;

class PopUpListener extends MouseAdapter {
	private JPopupMenu popup;
	private PopUpHandler handler;

	private JMenu settingsMenu;
	private JMenuItem languageItem;
	private JMenuItem copyItem;

	public PopUpListener(PopUpHandler handler) {
		this.handler = handler;
		popup = new JPopupMenu();
		popup.setBackground(Colors.gray);
		popup.setPreferredSize(new Dimension(300, 80));
		popup.setFont(DupoTheme.popUp);
		addItems();
	}

	private void addItems() {
		settingsMenu = new JMenu(Words.get("POPUP_MENU_ITEM_SETTINGS"));
		copyItem = new JMenuItem(Words.get("POPUP_MENU_ITEM_COPY"));
		languageItem = new JMenuItem(Words.get("POPUP_MENU_ITEM_LANGUAGE"));
		settingsMenu.add(languageItem);
		copyItem.setEnabled(true);
		languageItem.setEnabled(true);
		copyItem.setAccelerator(KeyStroke.getKeyStroke("Ctrl-C"));
		popup.add(new JSeparator());
		setFontAndColors(settingsMenu);
		setFontAndColors(copyItem);
		setFontAndColors(languageItem);
		addListeners();
		popup.add(copyItem);
		popup.add(settingsMenu);
	}

	private void setFontAndColors(JMenuItem menuItem) {
		menuItem.setFont(DupoTheme.popUp);
		menuItem.setBackground(Colors.gray);
		menuItem.setForeground(Colors.white);
	}

	private void addListeners() {
		copyItem.addActionListener(e -> handler.copy());
		languageItem.addActionListener(e -> handler.language());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		maybeShowPopup(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		maybeShowPopup(e);
	}

	private void maybeShowPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	public void repaint() {
		settingsMenu.setText(Words.get("POPUP_MENU_ITEM_SETTINGS"));
		copyItem.setText(Words.get("POPUP_MENU_ITEM_COPY"));
		languageItem.setText(Words.get("POPUP_MENU_ITEM_LANGUAGE"));
//		popup.repaint();
//		settingsMenu.repaint();
		
	}
}
