package se.klartbra.dupo.view.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.Controller;
import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.model.AllFilesWithCopies;
import se.klartbra.dupo.model.FileWithCopies;
import se.klartbra.dupo.view.look.Colors;
import se.klartbra.dupo.view.look.DupoTheme;

public class DupoListPanel implements ListSelectionListener {
	private static final int MAX_ROWS = 23;

	private static Logger log = LogManager.getLogger(DupoListPanel.class);
	private static DupoListPanel instance;

	private JPanel jPanel = new JPanel();
	private DefaultListModel<String> jListModel = new DefaultListModel<>();
	private JList<String> jList;
	private AllFilesWithCopies allFilesWithCopies;

	/**
	 * CTOR
	 * @param allFilesWithCopies
	 */
	public DupoListPanel(AllFilesWithCopies allFilesWithCopies) {
		instance=this;
		this.allFilesWithCopies = allFilesWithCopies;
		jList = new JList<>(jListModel);
		setupScrollableSelectionList(jListModel);
		JScrollPane listScrollPane = new JScrollPane(jList);
		initiateBorders(listScrollPane);
		jPanel.add(listScrollPane, BorderLayout.CENTER);
		jPanel.setBackground(DupoTheme.bgColor);
		addListeners();
	}

	/**
	 * CTOR
	 */
	public DupoListPanel() {
		this(new AllFilesWithCopies());
	}

	public void populate(AllFilesWithCopies allFilesWithCopies) {
		if(allFilesWithCopies.size() == 0) {
			return;
		}
		this.allFilesWithCopies = allFilesWithCopies;
		for(FileWithCopies fileWithCopies: allFilesWithCopies.toArray()) {
			jListModel.addElement(fileWithCopies.getFile().getName());
			jList.setSelectedIndex(0);
		}
		jList.requestFocus();
	}

	private void setupScrollableSelectionList(DefaultListModel<String> jListModel) {
		jList.setModel(jListModel);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList.setSelectedIndex(0);
		jList.addListSelectionListener(this);
		jList.setVisibleRowCount(MAX_ROWS);
		jList.setFont(DupoTheme.jListFont);
		jList.setBackground(DupoTheme.bgColor);
		jList.setForeground(Colors.white);
		jList.setCellRenderer(getRenderer());
	}

	/**
	 * So we can set font and colors to a list cell.
	 * @return
	 */
	@SuppressWarnings("serial")
	private ListCellRenderer<? super String> getRenderer() {
		return new DefaultListCellRenderer() { 
			@Override
			public Component getListCellRendererComponent(
					JList<?> list,
					Object value, 
					int index, 
					boolean isSelected,
					boolean cellHasFocus) 
			{
				JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
				listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 6, 0,DupoTheme.bgColor));
				if(isSelected) {
					listCellRendererComponent.setBackground(DupoTheme.jListFontSelectedItemColor);
					listCellRendererComponent.setFont(DupoTheme.jListFontSelectedItem);
				}
				return listCellRendererComponent;
			}
		};
	}

	private void initiateBorders(JScrollPane listScrollPane) {		
		listScrollPane.setBorder(BorderFactory.createEmptyBorder());
		TitledBorder border = BorderFactory.createTitledBorder(Words.get("BORDER_TEXT_COPIES"));
		border.setTitleFont(DupoTheme.borderTitleFont);
		border.setTitleColor(DupoTheme.borderTitleColor);
		jPanel.setBorder(border);
	}

	private void addListeners() {
		jList.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped( KeyEvent e ) {/* EMPTY */}
			@Override
			public void keyPressed( KeyEvent e ) {/* EMPTY */}
			@Override
			public void keyReleased( KeyEvent e ) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Controller.getInstance().handleExit();
				}
			}
		} );
	}

	public JPanel getPanel() {
		return jPanel;
	}

	public static DupoListPanel getInstance() {	
		return instance; 
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()||jList.getSelectedIndex()<0) {
			return;
		}
		int index = jList.getSelectedIndex();
		String contents = allFilesWithCopies.toArray().get(index).toString();
		DupoTextArea textArea = DupoTextArea.getInstance();
		textArea.initiateContetns();
		textArea.setText(contents);
		DupoTextArea.getInstance().setCaretTopOfDoc();

	}

}




