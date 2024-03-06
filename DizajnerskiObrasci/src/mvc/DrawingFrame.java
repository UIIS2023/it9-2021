package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import observer.Observer;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import drawingApp.Circle;
import drawingApp.DlgCircleMod;
import drawingApp.DlgModify;
import drawingApp.DlgPointMod;
import drawingApp.Donut;
import drawingApp.Line;
import drawingApp.PnlDrawing;
import drawingApp.Point;
import drawingApp.Rectangle;
import drawingApp.Shape;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;

public class DrawingFrame extends JFrame {
	private JPanel contentPane;
	// private DrawingController draw = new DrawingController(this);

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private JToggleButton tglbtnSelect;
	private JToggleButton btnPoint, tglbtnLine, tglbtnRec, tglbtnCircle, tglbtnDonut;
	private JPanel pnlS, pnlN, pnlW;
	private JToggleButton btnDelete;
	private boolean cekiranTacka, cekiranLinija, cekiranPravougaonik, cekiranKrug, cekiranKrugSaRupom, cekiranSelektuj,
			cekiranDelete, cekiranModify, undo, redo, save, open,next;

	private MouseEvent e;
	private JToggleButton btnModify;
	private Color borderColor = Color.black;
	private Color innerColor = Color.black;
	private Observer observer = new Observer(this);
	private boolean activeBorderColor = false, activeInnerColor = false;
	private DefaultListModel<String> dlm;
	DrawingView view = new DrawingView();
	DrawingController controller;
	private JButton btnRedo;
	private JButton btnUndo;
	private JToggleButton tglbtnInnerColor;
	private JToggleButton tglbtnBorderColor;
	private JToggleButton tglbtnHexagon;
	private JToggleButton tglbtnToFront;
	private JToggleButton tglbtnBringToFront;
	private JToggleButton tglbtnToBack;
	private JToggleButton tglbtnBringToBack;
	private JList<String> logList;
	private JScrollPane scrollPane;
	private JToggleButton btnFile;
	private JButton btnSave;
	private JButton btnOpen;
	private boolean isFileClicked;
	private JButton btnNextCommand;

	public DrawingFrame() {

		setTitle("Drawing | Marko Karapandzic IT9-2021");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 100, 1057, 574);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		dlm = new DefaultListModel<String>();
		pnlS = new JPanel();
		pnlS.setBackground(Color.GRAY);
		pnlS.setForeground(Color.RED);
		contentPane.add(pnlS, BorderLayout.SOUTH);
		pnlW = new JPanel(new BorderLayout());
		pnlW.setBackground(Color.gray);
		pnlW.setForeground(Color.RED);
		contentPane.add(pnlW, BorderLayout.WEST);
		tglbtnSelect = new JToggleButton("Select");
		tglbtnSelect.setEnabled(false);
		tglbtnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranSelektuj == false) {
					cekiranSelektuj = true;
					cekiranTacka = false;
					cekiranPravougaonik = false;
					cekiranKrug = false;
					cekiranKrugSaRupom = false;
					cekiranLinija = false;
					cekiranDelete = false;
					cekiranModify = false;
				} else {
					buttonGroup.clearSelection();
					cekiranSelektuj = false;
				}
			}
		});

		btnUndo = new JButton("Undo");
		btnUndo.setEnabled(false);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonGroup.clearSelection();
				if (controller.getUndoStack().size() != 0) {
					setUndo(true);
					controller.misKlik(null);
				}

			}
		});

		logList = new JList<String>();
		logList.setEnabled(false);
		logList.setModel(dlm);
		logList.setSize(20, 2);
		logList.setFont(new Font("Arial Black", Font.BOLD, 8));

		// pnlS.add(textArea);

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(logList);
		pnlW.add(scrollPane, BorderLayout.CENTER);
		pnlS.add(btnUndo);
		btnRedo = new JButton("Redo");
		btnRedo.setEnabled(false);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonGroup.clearSelection();
				if (controller.getRedoStack().size() != 0) {
					setRedo(true);
					controller.misKlik(null);
				}
			}
		});
		pnlS.add(btnRedo);
		pnlS.add(tglbtnSelect);
		buttonGroup.add(tglbtnSelect);
		buttonGroup.add(btnRedo);
		buttonGroup.add(btnUndo);
		btnDelete = new JToggleButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.misKlik(null);
				buttonGroup.clearSelection();
			}
		});
		buttonGroup.add(btnDelete);

		btnModify = new JToggleButton("Modify");
		btnModify.setEnabled(false);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.misKlik(null);
				buttonGroup.clearSelection();
			}
		});

		pnlS.add(btnModify);
		pnlS.add(btnDelete);

		tglbtnToFront = new JToggleButton("To Front");
		tglbtnToFront.setEnabled(false);
		tglbtnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.misKlik(null);
				buttonGroup.clearSelection();
			}
		});
		pnlS.add(tglbtnToFront);

		tglbtnBringToFront = new JToggleButton("Bring To Front");
		tglbtnBringToFront.setEnabled(false);
		tglbtnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.misKlik(null);
				buttonGroup.clearSelection();
			}
		});
		pnlS.add(tglbtnBringToFront);

		tglbtnToBack = new JToggleButton("To Back");
		tglbtnToBack.setEnabled(false);
		tglbtnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.misKlik(null);
				buttonGroup.clearSelection();
			}
		});
		pnlS.add(tglbtnToBack);

		tglbtnBringToBack = new JToggleButton("Bring To Back");
		tglbtnBringToBack.setEnabled(false);
		tglbtnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.misKlik(null);
				buttonGroup.clearSelection();
			}
		});
		pnlS.add(tglbtnBringToBack);
		buttonGroup.add(btnModify);
		buttonGroup.add(tglbtnToBack);
		buttonGroup.add(tglbtnToFront);
		buttonGroup.add(tglbtnBringToBack);
		buttonGroup.add(tglbtnBringToFront);
		
		btnNextCommand = new JButton("NEXT");
		btnNextCommand.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setNext(true);
			}
		});
		btnNextCommand.setEnabled(false);
		pnlS.add(btnNextCommand);
		pnlN = new JPanel();
		pnlN.setBackground(Color.GRAY);
		pnlN.setForeground(Color.RED);
		contentPane.add(pnlN, BorderLayout.NORTH);

		btnPoint = new JToggleButton("Point");
		btnPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranTacka == false) {
					cekiranTacka = true;
					cekiranLinija = false;
					cekiranPravougaonik = false;
					cekiranKrug = false;
					cekiranKrugSaRupom = false;
					cekiranSelektuj = false;
				} else {
					buttonGroup.clearSelection();
					cekiranTacka = false;
				}
			}
		});

		tglbtnInnerColor = new JToggleButton("Inner Color");
		tglbtnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tglbtnInnerColor.isSelected()) {
					cekiranTacka = false;
					cekiranLinija = false;
					cekiranPravougaonik = false;
					cekiranKrug = false;
					cekiranKrugSaRupom = false;
					cekiranSelektuj = false;
					tglbtnBorderColor.setSelected(false);
				}
				innerColor = JColorChooser.showDialog(null, "Choose inner color", null);
				tglbtnInnerColor.setBackground(innerColor);
				tglbtnInnerColor.setSelected(false);
				activeInnerColor = true;
			}
		});

		tglbtnBorderColor = new JToggleButton("Border Color");
		tglbtnBorderColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tglbtnBorderColor.isSelected()) {
					cekiranTacka = false;
					cekiranLinija = false;
					cekiranPravougaonik = false;
					cekiranKrug = false;
					cekiranKrugSaRupom = false;
					cekiranSelektuj = false;
					tglbtnInnerColor.setSelected(false);
				}
				borderColor = JColorChooser.showDialog(null, "Choose border color", null);
				tglbtnBorderColor.setBackground(borderColor);
				tglbtnBorderColor.setSelected(false);
				activeBorderColor = true;
			}
		});

		btnFile = new JToggleButton("File");
		btnFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnFile.isSelected() && isFileClicked == true) {
					isFileClicked = false;
					controller.misKlik(null);
					buttonGroup.clearSelection();
				}
				if (btnFile.isSelected()) {
					isFileClicked = true;
					controller.misKlik(null);
					buttonGroup.clearSelection();
				}

			}

		});
		pnlN.add(btnFile);
		buttonGroup.add(btnFile);
		btnSave = new JButton("Save file");
		buttonGroup.add(btnSave);
		buttonGroup.add(btnOpen);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				save = true;
				controller.misKlik(null);
				buttonGroup.clearSelection();
			}
		});
		btnSave.setVisible(false);
		pnlN.add(btnSave);

		btnOpen = new JButton("Open file");
		btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				open=true;
				controller.misKlik(e);
				buttonGroup.clearSelection();
			}
		});
		pnlN.add(btnOpen);
		btnOpen.setVisible(false);
		pnlN.add(tglbtnBorderColor);

		pnlN.add(tglbtnInnerColor);
		buttonGroup.add(btnPoint);
		pnlN.add(btnPoint);

		// ***************************************************************

		tglbtnLine = new JToggleButton("Line");
		tglbtnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranLinija == false) {
					cekiranLinija = true;
					cekiranTacka = false;
					cekiranPravougaonik = false;
					cekiranKrug = false;
					cekiranKrugSaRupom = false;
					cekiranSelektuj = false;
				} else {
					buttonGroup.clearSelection();
					cekiranLinija = false;
				}
			}
		});
		buttonGroup.add(tglbtnLine);
		pnlN.add(tglbtnLine);

		tglbtnRec = new JToggleButton("Rectangle");
		tglbtnRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranPravougaonik == false) {
					cekiranPravougaonik = true;
					cekiranTacka = false;
					cekiranLinija = false;
					cekiranKrug = false;
					cekiranKrugSaRupom = false;
					cekiranSelektuj = false;
				} else {
					buttonGroup.clearSelection();
					cekiranPravougaonik = false;
				}
			}
		});
		buttonGroup.add(tglbtnRec);
		pnlN.add(tglbtnRec);

		tglbtnCircle = new JToggleButton("Circle");
		tglbtnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranKrug == false) {
					cekiranKrug = true;
					cekiranTacka = false;
					cekiranLinija = false;
					cekiranPravougaonik = false;
					cekiranKrugSaRupom = false;
					cekiranSelektuj = false;
				} else {
					buttonGroup.clearSelection();
					cekiranKrug = false;
				}
			}
		});

		buttonGroup.add(tglbtnCircle);
		pnlN.add(tglbtnCircle);

		tglbtnDonut = new JToggleButton("Donut");
		tglbtnDonut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranKrugSaRupom == false) {
					cekiranKrugSaRupom = true;
					cekiranTacka = false;
					cekiranLinija = false;
					cekiranPravougaonik = false;
					cekiranKrug = false;
					cekiranSelektuj = false;
				} else {
					buttonGroup.clearSelection();
					cekiranKrugSaRupom = false;
				}
			}
		});
		buttonGroup.add(tglbtnDonut);
		pnlN.add(tglbtnDonut);

		tglbtnHexagon = new JToggleButton("Hexagon");
		tglbtnHexagon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// controller.misKlik(null);
			}
		});
		pnlN.add(tglbtnHexagon);
		buttonGroup.add(tglbtnHexagon);
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.misKlik(e);
			}
		});

		getContentPane().add(view, BorderLayout.CENTER);
	}

	public JButton getBtnNextCommand() {
		return btnNextCommand;
	}

	public void setBtnNextCommand(JButton btnNextCommand) {
		this.btnNextCommand = btnNextCommand;
	}

	public DefaultListModel<String> getDlm() {
		return dlm;
	}

	public void setDlm(DefaultListModel<String> dlm) {
		this.dlm = dlm;
	}

	public JList<String> getLogList() {
		return logList;
	}

	public void setLogList(JList<String> logList) {
		this.logList = logList;
	}

	public boolean isFileClicked() {
		return isFileClicked;
	}

	public void setFileClicked(boolean isFileClicked) {
		this.isFileClicked = isFileClicked;
	}

	public JButton getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(JButton btnSave) {
		this.btnSave = btnSave;
	}

	public JButton getBtnOpen() {
		return btnOpen;
	}

	public void setBtnOpen(JButton btnOpen) {
		this.btnOpen = btnOpen;
	}

	public JToggleButton getTglbtnToFront() {
		return tglbtnToFront;
	}

	public void setTglbtnToFront(JToggleButton tglbtnToFront) {
		this.tglbtnToFront = tglbtnToFront;
	}

	public JToggleButton getTglbtnBringToFront() {
		return tglbtnBringToFront;
	}

	public void setTglbtnBringToFront(JToggleButton tglbtnBringToFront) {
		this.tglbtnBringToFront = tglbtnBringToFront;
	}

	public JToggleButton getTglbtnToBack() {
		return tglbtnToBack;
	}

	public void setTglbtnToBack(JToggleButton tglbtnToBack) {
		this.tglbtnToBack = tglbtnToBack;
	}

	public JToggleButton getTglbtnBringToBack() {
		return tglbtnBringToBack;
	}

	public void setTglbtnBringToBack(JToggleButton tglbtnBringToBack) {
		this.tglbtnBringToBack = tglbtnBringToBack;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public void setBtnUndo(JButton btnUndo) {
		this.btnUndo = btnUndo;
	}

	public JToggleButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JToggleButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public JToggleButton getBtnModify() {
		return btnModify;
	}

	public void setBtnModify(JToggleButton btnModify) {
		this.btnModify = btnModify;
	}

	public JToggleButton getTglbtnBorderColor() {
		return tglbtnBorderColor;
	}

	public void setTglbtnBorderColor(JToggleButton tglbtnBorderColor) {
		this.tglbtnBorderColor = tglbtnBorderColor;
	}

	public JToggleButton getTglbtnInnerColor() {
		return tglbtnInnerColor;
	}

	public void setTglbtnInnerColor(JToggleButton tglbtnInnerColor) {
		this.tglbtnInnerColor = tglbtnInnerColor;
	}

	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
		controller.addListener(observer);
	}

	public JToggleButton getTglbtnSelektuj() {
		return tglbtnSelect;
	}

	public void setTglbtnSelektuj(JToggleButton tglbtnSelektuj) {
		this.tglbtnSelect = tglbtnSelektuj;
	}

	public JToggleButton getTglbtnTacka() {
		return btnPoint;
	}

	public void setTglbtnTacka(JToggleButton tglbtnTacka) {
		this.btnPoint = tglbtnTacka;
	}

	public JToggleButton getTglbtnLinija() {
		return tglbtnLine;
	}

	public void setTglbtnLinija(JToggleButton tglbtnLinija) {
		this.tglbtnLine = tglbtnLinija;
	}

	public JToggleButton getTglbtnPravougaonik() {
		return tglbtnRec;
	}

	public void setTglbtnPravougaonik(JToggleButton tglbtnPravougaonik) {
		this.tglbtnRec = tglbtnPravougaonik;
	}

	public JToggleButton getTglbtnKrug() {
		return tglbtnCircle;
	}

	public void setTglbtnKrug(JToggleButton tglbtnKrug) {
		this.tglbtnCircle = tglbtnKrug;
	}

	public JToggleButton getTglbtnKrugSaRupom() {
		return tglbtnDonut;
	}

	public void setTglbtnKrugSaRupom(JToggleButton tglbtnKrugSaRupom) {
		this.tglbtnDonut = tglbtnKrugSaRupom;
	}

	public boolean isActiveBorderColor() {
		return activeBorderColor;
	}

	public void setActiveBorderColor(boolean activeBorderColor) {
		this.activeBorderColor = activeBorderColor;
	}

	public boolean isActiveInnerColor() {
		return activeInnerColor;
	}

	public void setActiveInnerColor(boolean activeInnerColor) {
		this.activeInnerColor = activeInnerColor;
	}

	public boolean getCekiranDelete() {
		return cekiranDelete;
	}

	public void setCekiranDelete(boolean cekiranDelete) {
		this.cekiranDelete = cekiranDelete;
	}

	public boolean isUndo() {
		return undo;
	}

	public void setUndo(boolean undo) {
		this.undo = undo;
	}

	public boolean isRedo() {
		return redo;
	}

	public void setRedo(boolean redo) {
		this.redo = redo;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public void setTglbtnHexagon(JToggleButton tglbtnHexagon) {
		this.tglbtnHexagon = tglbtnHexagon;
	}

	public DefaultListModel<String> getList() {
		return dlm;
	}

	public boolean isSave() {
		return save;
	}

	public void setSave(boolean save) {
		this.save = save;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}


}
