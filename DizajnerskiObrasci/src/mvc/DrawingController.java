package mvc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import adapter.HexagonAdapter;
import observer.Observer;
import observer.ObserverManager;
import strategy.OpenPainting;
import strategy.SaveLog;
import strategy.SaveManager;
import strategy.SavePainting;
import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.RemoveShapeCmd;
import command.SelectedShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import drawingApp.Circle;
import drawingApp.DlgCircleEnter;
import drawingApp.DlgCircleMod;
import drawingApp.DlgEnter;
import drawingApp.DlgModify;
import drawingApp.DlgPointLine;
import drawingApp.DlgPointMod;
import drawingApp.Donut;
import drawingApp.DrawingApp;
import drawingApp.Line;
import drawingApp.Point;
import drawingApp.Rectangle;
import drawingApp.Shape;
import hexagon.Hexagon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.net.SocketTimeoutException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class DrawingController extends JPanel {

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private PropertyChangeSupport propertyChangeSupport;
	private DrawingFrame frame;
	private DrawingModel model;
	private List<Shape> selectedList = new ArrayList<Shape>();
	private List<Shape> deletedShapes = new ArrayList<Shape>();
	private Command command;
	private Stack<Command> undoStack = new Stack<Command>();
	private Stack<Command> redoStack = new Stack<Command>();
	private int r;
	
	private DefaultListModel<String> log;
	private Point startPoint;
	private Shape selected;
	private ObserverManager observerManager;
	private SaveManager saveManager;

	public DrawingController(DrawingFrame frame) {
		this.frame = frame;

		propertyChangeSupport = new PropertyChangeSupport(this);
		observerManager = new ObserverManager(this, propertyChangeSupport);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
					misKlik(arg0);
				
			}
		});

	}

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		log = frame.getList();
		propertyChangeSupport = new PropertyChangeSupport(this);
		observerManager = new ObserverManager(this, propertyChangeSupport);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
					misKlik(arg0);
				
			}
		});

	}

	public void misKlik(MouseEvent e) {
		
		int x1 = 0;
		int y1 = 0;

		// select
		if (frame.getTglbtnSelektuj().isSelected()) {
			selected = null;
			Iterator<Shape> i = model.getShapes().iterator();
			while (i.hasNext()) {
				Shape shape = i.next();
				if (shape.contains(e.getX(), e.getY())) {
					command = new SelectedShapeCmd(this, shape);
					if (shape.isSelected()) {
						command.unexecute();
						
						//System.out.println(command.toString());
						undoStack.push(command); // ako treba select i deselect takodje da idu u stack, odkomentarisati
						log.addElement(undoStack.peek().toString());
						redoStack.removeAllElements();
						if (getSelectedList().size() == 0) {
							observerManager.deselect();
						}
						//System.out.println(getSelectedList().size());
					} else {
						command.execute();
						//log.addElement(command.toString());
						//System.out.println(command.toString());
						if (getSelectedList().size() > 1) {
							observerManager.select();
						}
						undoStack.push(command); // ako treba select i deselect takodje da idu u stack, odkomentarisati
						log.addElement(undoStack.peek().toString());
						redoStack.removeAllElements();
						//System.out.println(getSelectedList().size());
					}
				}
			}
			if (selected != null) { // visak? Ne radi nista...
				selected.setSelected(true);
			}
			if (getSelectedList().size() > 0) {
				observerManager.notEmpty();
				if (getSelectedList().size() == 1) {
					observerManager.sizeIsOne();
				}

			}
			frame.repaint();

			// tacka

		} else if (frame.getTglbtnTacka().isSelected()) {
			if (frame.isActiveBorderColor()) {
				Point p = new Point(e.getX(), e.getY(), false);
				p.setColor(frame.getTglbtnBorderColor().getBackground());
				command = new AddShapeCmd(p, model);
				command.execute();
				log.addElement(command.toString());
				undoStack.push(command);
				redoStack.removeAllElements();
				frame.repaint();
			} else {
				DlgPointLine unos = new DlgPointLine();
				unos.setTitle("Point");
				unos.pack();
				unos.setVisible(true);
				if (unos.isOK()) {
					Point p = new Point(e.getX(), e.getY(), false);
					p.setColor(unos.getC());
					if (p.getColor() == null) {
						p.setColor(Color.BLACK);
					}
					command = new AddShapeCmd(p, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				}
			}

			// linija

		} else if (frame.getTglbtnLinija().isSelected()) {
			if (frame.isActiveBorderColor()) {
				if (startPoint == null) {
					startPoint = new Point(e.getX(), e.getY());
				} else {
					Point endPoint = new Point(e.getX(), e.getY());
					Line l = new Line(startPoint, endPoint, false);
					l.setColor(frame.getTglbtnBorderColor().getBackground());
					command = new AddShapeCmd(l, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
					startPoint = null;
				}

			} else {
				DlgPointLine unos = new DlgPointLine();
				if (startPoint == null) {
					startPoint = new Point(e.getX(), e.getY());
				} else {
					Point endPoint = new Point(e.getX(), e.getY());
					unos.setTitle("Line");
					unos.pack();
					unos.setVisible(true);
					if (unos.isOK()) {
						Line l = new Line(startPoint, endPoint, false);
						l.setColor(unos.getC());
						if (l.getColor() == null) {
							l.setColor(Color.BLACK);
						}
						command = new AddShapeCmd(l, model);
						command.execute();
						log.addElement(command.toString());
						undoStack.push(command);
						redoStack.removeAllElements();
						frame.repaint();
					}
					startPoint = null;
				}
			}

			// pravougaonik

		} else if (frame.getTglbtnPravougaonik().isSelected()) {
			if (frame.isActiveBorderColor()) {
				if (frame.isActiveInnerColor()) {
					DlgEnter unos = new DlgEnter();
					unos.getLblIzaberiteBoju().setVisible(false);
					unos.getLblIzaberiteBojuUnutrasnjosti().setVisible(false);
					unos.getBtnColorBC().setVisible(false);
					unos.getBtnColorIC().setVisible(false);
					unos.pack();
					unos.setTitle("Rectangle");
					unos.setVisible(true);
					if (unos.isOK()) {
						x1 = Integer.parseInt(unos.getTxtUnos().getText());
						y1 = Integer.parseInt(unos.getTxtUnos1().getText());
					}
					Rectangle r = new Rectangle(new Point(e.getX(), e.getY()), y1, x1, false);
					r.setColor(frame.getTglbtnBorderColor().getBackground());
					r.setColorUnutrasnjost(frame.getTglbtnInnerColor().getBackground());
					command = new AddShapeCmd(r, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				} else {
					DlgEnter unos = new DlgEnter();
					unos.getLblIzaberiteBoju().setVisible(false);
					unos.getBtnColorBC().setVisible(false);
					unos.pack();
					unos.setTitle("Rectangle");
					unos.setVisible(true);
					unos.getLblIzaberiteBojuIvice().setVisible(false);
					if (unos.isOK()) {
						x1 = Integer.parseInt(unos.getTxtUnos().getText());
						y1 = Integer.parseInt(unos.getTxtUnos1().getText());
					}
					Rectangle r = new Rectangle(new Point(e.getX(), e.getY()), y1, x1, false);
					r.setColor(frame.getTglbtnBorderColor().getBackground());
					if(unos.getColorUnutrasnjost()!=null) {
						r.setColorUnutrasnjost(unos.getColorUnutrasnjost());
					}else r.setColorUnutrasnjost(Color.black);
					
					command = new AddShapeCmd(r, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				}

			} else if (frame.isActiveInnerColor()) {
				DlgEnter unos = new DlgEnter();
				unos.getLblIzaberiteBojuUnutrasnjosti().setVisible(false);
				unos.getBtnColorIC().setVisible(false);
				unos.pack();
				unos.setTitle("Rectangle");
				unos.setVisible(true);
				if (unos.isOK()) {
					x1 = Integer.parseInt(unos.getTxtUnos().getText());
					y1 = Integer.parseInt(unos.getTxtUnos1().getText());
				}
				Rectangle r = new Rectangle(new Point(e.getX(), e.getY()), y1, x1, false);
				r.setColor(unos.getC());
				r.setColorUnutrasnjost(frame.getTglbtnInnerColor().getBackground());
				command = new AddShapeCmd(r, model);
				command.execute();
				log.addElement(command.toString());
				undoStack.push(command);
				redoStack.removeAllElements();
				frame.repaint();
			} else {
				DlgEnter unos = new DlgEnter();
				unos.pack();
				unos.setTitle("Rectangle");
				unos.setVisible(true);
				if (unos.isOK()) {
					x1 = Integer.parseInt(unos.getTxtUnos().getText());
					y1 = Integer.parseInt(unos.getTxtUnos1().getText());
				}
				Rectangle r = new Rectangle(new Point(e.getX(), e.getY()), y1, x1, false);
				r.setColor(unos.getC());
				if (r.getColor() == null) {
					r.setColor(Color.black);
				}
				r.setColorUnutrasnjost(unos.getColorUnutrasnjost());
				if (r.getColorUnutrasnjost() == null) {
					r.setColorUnutrasnjost(Color.black);
				}
				try {
					command = new AddShapeCmd(r, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Invalid value!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

			// krug entner

		} else if (frame.getTglbtnKrug().isSelected()) {
			if (frame.isActiveBorderColor()) {
				if (frame.isActiveInnerColor()) {
					DlgCircleEnter unos = new DlgCircleEnter();
					unos.getBtnColorBC().setVisible(false);
					unos.getLblInnerColor().setVisible(false);
					unos.getLblColorB().setVisible(false);
					unos.getBtnColorIN().setVisible(false);
					unos.pack();
					unos.setVisible(true);
					if (unos.isOK()) {
						x1 = Integer.parseInt(unos.getTxtPoluprecnik().getText());
					}
					Circle c = new Circle(new Point(e.getX(), e.getY()), x1, false);
					c.setColor(frame.getTglbtnBorderColor().getBackground());
					c.setColorUnutrasnjost(frame.getTglbtnInnerColor().getBackground());
					command = new AddShapeCmd(c, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				} else { // aktivan border, neaktivan inner
					DlgCircleEnter unos = new DlgCircleEnter();
					unos.getBtnColorBC().setVisible(false);
					unos.getLblColorB().setVisible(false);
					unos.getLblInnerColor().setVisible(true);
					unos.getBtnColorIN().setVisible(true);
					unos.pack();
					unos.setVisible(true);
					if (unos.isOK()) {
						x1 = Integer.parseInt(unos.getTxtPoluprecnik().getText());
					}
					Circle c = new Circle(new Point(e.getX(), e.getY()), x1, false);
					c.setColor(frame.getTglbtnBorderColor().getBackground());
					if(unos.getColorUnutrasnjost()==null) {
						c.setColorUnutrasnjost(Color.black);
					}
					else c.setColorUnutrasnjost(unos.getColorUnutrasnjost());
					command = new AddShapeCmd(c, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				}
			} else if (frame.isActiveInnerColor()) {
				DlgCircleEnter unos = new DlgCircleEnter();
				unos.getBtnColorBC().setVisible(true);
				unos.getLblColorB().setVisible(true);
				unos.getLblInnerColor().setVisible(false);
				unos.getBtnColorIN().setVisible(false);
				unos.pack();
				unos.setVisible(true);
				if (unos.isOK()) {
					x1 = Integer.parseInt(unos.getTxtPoluprecnik().getText());
				}
				Circle c = new Circle(new Point(e.getX(), e.getY()), x1, false);
				if(unos.getC()==null) {
					c.setColor(Color.black);
				}
				else c.setColor(unos.getC());
				c.setColorUnutrasnjost(frame.getTglbtnInnerColor().getBackground());
				command = new AddShapeCmd(c, model);
				command.execute();
				log.addElement(command.toString());
				undoStack.push(command);
				redoStack.removeAllElements();
				frame.repaint();
			} else {
				DlgCircleEnter krug = new DlgCircleEnter();
				krug.pack();
				krug.setVisible(true);
				if (krug.isOK()) {
					x1 = Integer.parseInt(krug.getTxtPoluprecnik().getText());
				}
				Circle c = new Circle(new Point(e.getX(), e.getY()), x1, false);
				c.setColor(krug.getC());
				if (c.getColor() == null) {
					c.setColor(Color.black);
				}
				c.setColorUnutrasnjost(krug.getColorUnutrasnjost());
				if (c.getColorUnutrasnjost() == null) {
					c.setColorUnutrasnjost(Color.BLACK);
				}
				try {
					command = new AddShapeCmd(c, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Invalid value!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

			// donut

		} else if (frame.getTglbtnKrugSaRupom().isSelected()) {
			if (frame.isActiveBorderColor()) {
				if (frame.isActiveInnerColor()) {
					DlgEnter unos = new DlgEnter();
					unos.getLblUnesite().setText("Circle radius:");
					unos.getLblUnesite1().setText("Inner circle radius:");
					unos.setTitle("Donut");
					unos.getLblIzaberiteBojuIvice().setVisible(false);
					unos.getBtnBojaIviceUnutrasnji().setVisible(false);
					unos.getLblIzaberiteBoju().setVisible(false);
					unos.getLblIzaberiteBojuUnutrasnjosti().setVisible(false);
					unos.getBtnColorBC().setVisible(false);
					unos.getBtnColorIC().setVisible(false);
					unos.pack();
					unos.setVisible(true);
					if (unos.isOK()) {
						x1 = Integer.parseInt(unos.getTxtUnos().getText());
						y1 = Integer.parseInt(unos.getTxtUnos1().getText());
					}
					Donut d = new Donut(new Point(e.getX(), e.getY()), x1, y1, false);
					d.setColor(frame.getTglbtnBorderColor().getBackground());
					d.setColorUnutrasnjostVeliki(frame.getTglbtnInnerColor().getBackground());
					d.setColorIviceMali(frame.getTglbtnBorderColor().getBackground());
					d.setColorUnutrasnostMali(getBackground());
					command = new AddShapeCmd(d, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				} else { // aktivan border, inner neaktivan
					DlgEnter unos = new DlgEnter();
					unos.getLblUnesite().setText("Circle radius:");
					unos.getLblUnesite1().setText("Inner circle radius:");
					unos.setTitle("Donut");
					unos.getLblIzaberiteBojuIvice().setVisible(false); // inner circle border color
					unos.getBtnBojaIviceUnutrasnji().setVisible(false);
					unos.getLblIzaberiteBoju().setVisible(false); // border
					unos.getBtnColorBC().setVisible(false);
					unos.getBtnColorIC().setVisible(true);
					unos.pack();
					unos.setVisible(true);
					if (unos.isOK()) {
						x1 = Integer.parseInt(unos.getTxtUnos().getText());
						y1 = Integer.parseInt(unos.getTxtUnos1().getText());
					}
					Donut d = new Donut(new Point(e.getX(), e.getY()), x1, y1, false);
					d.setColor(frame.getTglbtnBorderColor().getBackground());
					if(unos.getColorUnutrasnjost()==null) {
						d.setColorUnutrasnjostVeliki(Color.black);
					}
					else d.setColorUnutrasnjostVeliki(unos.getColorUnutrasnjost());
					d.setColorIviceMali(frame.getTglbtnBorderColor().getBackground());
					d.setColorUnutrasnostMali(getBackground());
					command = new AddShapeCmd(d, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				}
			} else if (frame.isActiveInnerColor()) {
				DlgEnter unos = new DlgEnter();
				unos.getLblUnesite().setText("Circle radius:");
				unos.getLblUnesite1().setText("Inner circle radius:");
				unos.setTitle("Donut");

				unos.getLblIzaberiteBojuIvice().setVisible(true); // inner circle border color
				unos.getBtnBojaIviceUnutrasnji().setVisible(true);
				unos.getLblIzaberiteBoju().setVisible(true); // border
				unos.getBtnColorBC().setVisible(true);
				unos.getBtnColorIC().setVisible(false);
				unos.getLblInnerColor().setVisible(false);
				unos.pack();
				unos.setVisible(true);
				if (unos.isOK()) {
					x1 = Integer.parseInt(unos.getTxtUnos().getText());
					y1 = Integer.parseInt(unos.getTxtUnos1().getText());
				}
				Donut d = new Donut(new Point(e.getX(), e.getY()), x1, y1, false);
				if(unos.getC()==null) {
					d.setColor(Color.black);
				}
				else d.setColor(unos.getC());
				d.setColorUnutrasnjostVeliki(frame.getTglbtnInnerColor().getBackground());
				if(unos.getColorUnutrasnjiKrugBoja()==null) {
					d.setColorIviceMali(Color.black);
				}
				else d.setColorIviceMali(unos.getColorUnutrasnjiKrugBoja());
				d.setColorUnutrasnostMali(getBackground());
				command = new AddShapeCmd(d, model);
				command.execute();
				log.addElement(command.toString());
				undoStack.push(command);
				redoStack.removeAllElements();
				frame.repaint();
			} else {
				DlgEnter unos = new DlgEnter();
				unos.getLblUnesite().setText("Circle radius:");
				unos.getLblUnesite1().setText("Inner circle radius:");
				unos.setTitle("Donut");
				unos.getLblIzaberiteBoju().setText("Border color:");
				unos.getLblIzaberiteBojuUnutrasnjosti().setVisible(true);
				unos.getLblIzaberiteBojuIvice().setVisible(true);
				unos.getBtnBojaIviceUnutrasnji().setVisible(true);

				unos.pack();
				unos.setVisible(true);
				if (unos.isOK()) {
					x1 = Integer.parseInt(unos.getTxtUnos().getText());
					y1 = Integer.parseInt(unos.getTxtUnos1().getText());
				}
				Donut d = new Donut(new Point(e.getX(), e.getY()), x1, y1, false);
				d.setColor(unos.getC());
				if (d.getColor() == null) {
					d.setColor(Color.black);
				}
				d.setColorIviceMali(unos.getColorIviceUnutrasnjiKrug());
				if (d.getColorIviceMali() == null) {
					d.setColorIviceMali(Color.black);
				}
				d.setColorUnutrasnostMali(unos.getColorUnutrasnjiKrugBoja());
				if (d.getColorUnutrasnostMali() == null) {
					d.setColorUnutrasnostMali(getBackground());
				}
				d.setColorUnutrasnjostVeliki(unos.getColorUnutrasnjost());
				if (d.getColorUnutrasnjostVeliki() == null) {
					d.setColorUnutrasnjostVeliki(Color.black);
				}
				try {
					command = new AddShapeCmd(d, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Invalid value!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (frame.getBtnDelete().isSelected()) {
			if (getSelectedList().size() == 0) {
				JOptionPane.showMessageDialog(null, "Shape must be selected!", "Error", JOptionPane.WARNING_MESSAGE);
			} else {
				Iterator<Shape> it = getSelectedList().iterator();
				int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm",
						JOptionPane.YES_NO_OPTION);
				if (selectedOption == JOptionPane.YES_OPTION) {
					frame.getBtnDelete().setSelected(false);
					while (it.hasNext()) {
						Shape toDelete = it.next();
						command = new RemoveShapeCmd(toDelete, model,model.getIndex(toDelete));
						command.execute();
						log.addElement(command.toString());
						undoStack.push(command);
						redoStack.removeAllElements();
						toDelete.setSelected(false);

					}
					Iterator<Shape> iterator = getSelectedList().iterator();
					while(iterator.hasNext()) {
						deletedShapes.add(iterator.next());
					}
					getSelectedList().clear();
					observerManager.delete();

				}
				frame.repaint();
			}
		} else if (frame.getBtnModify().isSelected()) {
			if (getSelectedList().size() > 1) {
				JOptionPane.showMessageDialog(null, "You can modify only one shape, please deselect unwanted!", "Error",
						JOptionPane.WARNING_MESSAGE);
			} else if (getSelectedList().size() == 0) {
				JOptionPane.showMessageDialog(null, "Please select shape to modify!", "Error",
						JOptionPane.WARNING_MESSAGE);
			} else {
				int korX = 0, korY = 0, x11 = 0, y11 = 0;

				Shape toModify = selectedList.get(0);

				Shape selected = toModify;
				if (selected != null) {

					if (selected instanceof Point) {
						Point p = (Point) selected;

						DlgPointMod izmena = new DlgPointMod();

						izmena.getTxtX().setText("" + p.getX());
						izmena.getTxtY().setText("" + p.getY());
						izmena.pack();
						izmena.setVisible(true);
						if (izmena.isOK()) {
							korX = Integer.parseInt(izmena.getTxtX().getText());
							korY = Integer.parseInt(izmena.getTxtY().getText());
							Color color = izmena.getC();
							Point newState = new Point(korX, korY);
							if (color != null) {
								newState.setColor(color);
							} else {
								newState.setColor(p.getColor());
							}
							command = new UpdatePointCmd(p, newState);
							command.execute();
							log.addElement(command.toString());
							undoStack.push(command);
							redoStack.removeAllElements();
							// model.getShapes().set(model.getShapes().indexOf(selected), p);
						}

					} else if (selected instanceof Line) {
						Line l = (Line) selected;
						DlgModify izmena = new DlgModify();
						izmena.setTitle("Line");
						izmena.getLblX().setText("Coordinate X of start point:");
						izmena.getLblY().setText("Coordinate Y of start point:");
						izmena.getLblUnesite().setText("Coordinate X of end poitn:");
						izmena.getLblUnesite1().setText("Coordinate Y of end point:");
						izmena.getTxtX().setText("" + l.getStartPoint().getX());
						izmena.getTxtY().setText("" + l.getStartPoint().getY());
						izmena.getTxtUnos().setText("" + l.getEndPoint().getX());
						izmena.getTxtUnos1().setText("" + l.getEndPoint().getY());
						izmena.getLblIzaberiteBojuUnutrasnjosti().setVisible(false);
						izmena.getBtnBojaUnutrasnjosti().setVisible(false);
						izmena.pack();
						izmena.setVisible(true);

						if (izmena.isOK()) {

							korX = Integer.parseInt(izmena.getTxtX().getText());
							korY = Integer.parseInt(izmena.getTxtY().getText());
							x11 = Integer.parseInt(izmena.getTxtUnos().getText());
							y11 = Integer.parseInt(izmena.getTxtUnos1().getText());
							Color c = izmena.getC();
							Line newState = new Line(new Point(korX, korY), new Point(x11, y11));
							if (c != null) {
								newState.setColor(c);
							} else {
								newState.setColor(l.getColor());
							}
							command = new UpdateLineCmd(l, newState);

							command.execute();
							log.addElement(command.toString());
							undoStack.push(command);
							redoStack.removeAllElements();

							// model.getShapes().set(model.getShapes().indexOf(selected), l);
						}
					} else if (selected instanceof Rectangle) {
						Rectangle r = (Rectangle) selected;
						DlgModify izmena = new DlgModify();
						izmena.setTitle("Rectangle");
						izmena.getTxtX().setText("" + r.getUpperLeftPoint().getX());
						izmena.getTxtY().setText("" + r.getUpperLeftPoint().getY());
						izmena.getTxtUnos().setText("" + r.getHeight());
						izmena.getTxtUnos1().setText("" + r.getWidth());
						izmena.pack();
						izmena.setVisible(true);
						if (izmena.isOK()) {
							korX = Integer.parseInt(izmena.getTxtX().getText());
							korY = Integer.parseInt(izmena.getTxtY().getText());
							x11 = Integer.parseInt(izmena.getTxtUnos().getText());
							y11 = Integer.parseInt(izmena.getTxtUnos1().getText());
							Rectangle newState = new Rectangle(new Point(korX, korY), x11, y11);
							Color c = izmena.getC();
							if (c == null) {
								newState.setColor(r.getColor());
							} else {
								newState.setColor(c);
							}
							Color innerC = izmena.getColorUnutrasnjost();
							if (innerC == null) {
								newState.setColorUnutrasnjost(r.getColorUnutrasnjost());
							} else {
								newState.setColorUnutrasnjost(innerC);
							}
							command = new UpdateRectangleCmd(r, newState);
							command.execute();
							log.addElement(command.toString());
							undoStack.push(command);
							redoStack.removeAllElements();
						}
					} else if (selected instanceof Donut) {
						Donut d = (Donut) selected;
						DlgModify izmena = new DlgModify();
						izmena.getLblUnesite().setText("Enter circle radius:");
						izmena.getLblUnesite1().setText("Enter inner circle radius:");
						izmena.setTitle("Donut");
						izmena.getLblIzaberiteBoju().setText("Border color:");
						izmena.getLblIzaberiteBojuUnutrasnjosti().setText("Circle color:");
						izmena.getLblIzaberiteBojuIvice().setVisible(true);
						izmena.getLblIzaberiteBojuUnutrasnjeg().setVisible(true);
						izmena.getBtnBojaIviceUnutrasnjegKruga().setVisible(true);
						izmena.getBtnBojaUnutrasnjegKruga().setVisible(false);
						izmena.getLblColor4().setVisible(false);
						izmena.getTxtX().setText("" + d.getCenter().getX());
						izmena.getTxtY().setText("" + d.getCenter().getY());
						izmena.getTxtUnos().setText("" + d.getRadius());
						izmena.getTxtUnos1().setText("" + d.getInnerRadius());
						izmena.pack();
						izmena.setVisible(true);
						if (izmena.isOK()) {
							korX = Integer.parseInt(izmena.getTxtX().getText());
							korY = Integer.parseInt(izmena.getTxtY().getText());
							x11 = Integer.parseInt(izmena.getTxtUnos().getText());
							y11 = Integer.parseInt(izmena.getTxtUnos1().getText());
							Donut newState = new Donut(new Point(korX, korY), x11, y11);
							Color c = izmena.getC();
							Color smallC = izmena.getColorIviceUnutrasnjiKrug();
							Color innerSmall = izmena.getColorUnutrasnjiKrugBoja();
							Color innerBig = izmena.getColorUnutrasnjost();
							if (c != null) {
								newState.setColor(c);
							} else {
								newState.setColor(d.getColor());
							}
							if (smallC != null) {
								newState.setColorIviceMali(smallC);
							} else {
								newState.setColorIviceMali(d.getColorIviceMali());
								;
							}
							if (innerSmall != null) {
								newState.setColorUnutrasnostMali(getBackground());
							} else {
								newState.setColorUnutrasnostMali(getBackground());

							}
							if (innerBig != null) {
								newState.setColorUnutrasnjostVeliki(innerBig);
							} else {
								newState.setColorUnutrasnjostVeliki(d.getColorUnutrasnjostVeliki());
							}
							command = new UpdateDonutCmd(d, newState);
							command.execute();
							log.addElement(command.toString());
							undoStack.push(command);
							redoStack.removeAllElements();
						}
					} else if (selected instanceof Circle) {
						Circle c = (Circle) selected;
						DlgCircleMod izmena = new DlgCircleMod();
						izmena.getTxtX().setText("" + c.getCenter().getX());
						izmena.getTxtY().setText("" + c.getCenter().getY());
						izmena.getTxtPoluprecnik().setText("" + c.getRadius());
						izmena.pack();
						izmena.setVisible(true);
						if (izmena.isOK()) {
							korX = Integer.parseInt(izmena.getTxtX().getText());
							korY = Integer.parseInt(izmena.getTxtY().getText());
							x11 = Integer.parseInt(izmena.getTxtPoluprecnik().getText());
							Circle newState = new Circle(new Point(korX, korY), x11);
							Color color = izmena.getC();
							Color innerColor = izmena.getColorUnutrasnjost();
							if (color != null) {
								newState.setColor(color);
							} else {
								newState.setColor(c.getColor());
							}
							if (innerColor != null) {
								newState.setColorUnutrasnjost(innerColor);
							} else {
								newState.setColorUnutrasnjost(c.getColorUnutrasnjost());
							}
							command = new UpdateCircleCmd(c, newState);
							command.execute();
							log.addElement(command.toString());
							undoStack.push(command);
							redoStack.removeAllElements();
						}
					} else if (selected instanceof HexagonAdapter) {
						HexagonAdapter h = (HexagonAdapter) selected;
						DlgCircleMod izmena = new DlgCircleMod();
						izmena.setTitle("Hexagon");
						izmena.getTxtX().setText("" + h.getX());
						izmena.getTxtY().setText("" + h.getY());
						izmena.getTxtPoluprecnik().setText("" + h.getR());
						izmena.pack();
						izmena.setVisible(true);
						if (izmena.isOK()) {
							korX = Integer.parseInt(izmena.getTxtX().getText());
							korY = Integer.parseInt(izmena.getTxtY().getText());
							x11 = Integer.parseInt(izmena.getTxtPoluprecnik().getText());
							HexagonAdapter newState = new HexagonAdapter(new Point(korX, korY), x11);
							if (izmena.getC() != null) {
								newState.setColor(izmena.getC());
							} else {
								newState.setColor(h.getColor());
							}
							if (izmena.getColorUnutrasnjost() != null) {
								newState.setInnerColor(izmena.getColorUnutrasnjost());
							} else {
								newState.setInnerColor(h.getInnerColor());
							}
							command = new UpdateHexagonCmd(h, newState);
							command.execute();
							log.addElement(command.toString());
							undoStack.push(command);
							redoStack.removeAllElements();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Shape must be selected!", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
				frame.repaint();
				this.setSelected(null);
				buttonGroup.clearSelection();
				// selected.setSelected(false);
				// getSelectedList().remove(selected);
			}

		} else if (frame.isUndo()) {
			
			observerManager.undoClicked();
			if(undoStack.peek().toString().contains("Deselect")) {
				undoStack.peek().execute();
				log.addElement("Undo: "+undoStack.peek().toString());
				redoStack.push(undoStack.pop());
			}else {
				
				undoStack.peek().unexecute();
				log.addElement("Undo: "+ undoStack.peek().toString());
				redoStack.push(undoStack.pop());
				
			}
			if(!deletedShapes.isEmpty()) {
				deletedShapes.get(deletedShapes.size()-1).setSelected(true);
				getSelectedList().add(deletedShapes.get(deletedShapes.size()-1));
				deletedShapes.remove(deletedShapes.size()-1);
			}
			
			frame.setUndo(false); // da ide jednom po jednom

		} else if (frame.isRedo()) {

			if (getRedoStack().size() == 1) {
				observerManager.redoIsOnOne();
			}
			if(redoStack.peek().toString().contains("Selected")) {
				redoStack.peek().unexecute();
				log.addElement("Redo: "+redoStack.peek().toString());
				undoStack.push(redoStack.pop());
			}else {
				redoStack.peek().execute();
				log.addElement("Redo: "+redoStack.peek().toString());
				undoStack.push(redoStack.pop());
			}
			
			
			frame.setRedo(false);
			frame.repaint();
			
		} else if (frame.getTglbtnHexagon().isSelected()) {
			if (frame.isActiveBorderColor()) {
				if (frame.isActiveInnerColor()) {
					DlgCircleEnter unos = new DlgCircleEnter();
					unos.getLblColorB().setVisible(false);
					unos.getLblInnerColor().setVisible(false);
					unos.getBtnColorBC().setVisible(false);
					unos.getBtnColorIN().setVisible(false);
					unos.setTitle("Hexagon");
					unos.pack();
					unos.setVisible(true);
					if (unos.isOK()) {
						r = Integer.parseInt(unos.getTxtPoluprecnik().getText());
					}
					Hexagon h = new Hexagon(e.getX(), e.getY(), r);
					h.setBorderColor(frame.getTglbtnBorderColor().getBackground());
					h.setAreaColor(frame.getTglbtnInnerColor().getBackground());
					HexagonAdapter hexagonAdapter = new HexagonAdapter(h);
					command = new AddShapeCmd(hexagonAdapter, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				} else {
					DlgCircleEnter unos = new DlgCircleEnter();
					unos.getLblColorB().setVisible(false);
					unos.getLblInnerColor().setVisible(true);
					unos.getBtnColorBC().setVisible(false);
					unos.getBtnColorIN().setVisible(true);
					unos.setTitle("Hexagon");
					unos.pack();
					unos.setVisible(true);
					if (unos.isOK()) {
						r = Integer.parseInt(unos.getTxtPoluprecnik().getText());
					}
					Hexagon h = new Hexagon(e.getX(), e.getY(), r);
					h.setBorderColor(frame.getTglbtnBorderColor().getBackground());
					if(unos.getColorUnutrasnjost()==null) {
						h.setAreaColor(Color.black);
					}
					else h.setAreaColor(unos.getColorUnutrasnjost());
					HexagonAdapter hexagonAdapter = new HexagonAdapter(h);
					command = new AddShapeCmd(hexagonAdapter, model);
					command.execute();
					log.addElement(command.toString());
					undoStack.push(command);
					redoStack.removeAllElements();
					frame.repaint();
				}

			} else if (frame.isActiveInnerColor()) {
				DlgCircleEnter unos = new DlgCircleEnter();
				unos.getLblColorB().setVisible(true);
				unos.getLblInnerColor().setVisible(false);
				unos.getBtnColorBC().setVisible(true);
				unos.getBtnColorIN().setVisible(false);
				unos.setTitle("Hexagon");
				unos.pack();
				unos.setVisible(true);
				if (unos.isOK()) {
					r = Integer.parseInt(unos.getTxtPoluprecnik().getText());
				}
				Hexagon h = new Hexagon(e.getX(), e.getY(), r);
				if(unos.getC()==null) {
					h.setBorderColor(Color.black);
				}
				else h.setBorderColor(unos.getC());
				h.setAreaColor(frame.getTglbtnInnerColor().getBackground());
				HexagonAdapter hexagonAdapter = new HexagonAdapter(h);
				command = new AddShapeCmd(hexagonAdapter, model);
				command.execute();
				log.addElement(command.toString());
				undoStack.push(command);
				redoStack.removeAllElements();
				frame.repaint();
			} else {
				DlgCircleEnter unos = new DlgCircleEnter();
				unos.getLblColorB().setVisible(true);
				unos.getLblInnerColor().setVisible(true);
				unos.getBtnColorBC().setVisible(true);
				unos.getBtnColorIN().setVisible(true);
				unos.setTitle("Hexagon");
				unos.pack();
				unos.setVisible(true);
				if (unos.isOK()) {
					r = Integer.parseInt(unos.getTxtPoluprecnik().getText());
				}
				Hexagon h = new Hexagon(e.getX(), e.getY(), r);
				h.setBorderColor(unos.getC());
				h.setAreaColor(unos.getColorUnutrasnjost());
				if(unos.getC()==null) {
					h.setBorderColor(Color.black);
				}
				if(unos.getColorUnutrasnjost()==null) {
					h.setAreaColor(Color.black);
				}
				HexagonAdapter hexagonAdapter = new HexagonAdapter(h);
				command = new AddShapeCmd(hexagonAdapter, model);
				command.execute();
				log.addElement(command.toString());
				undoStack.push(command);
				redoStack.removeAllElements();
				frame.repaint();
			}

		} else if (frame.getTglbtnToBack().isSelected()) {

			selected = getSelectedList().get(0);
			if (model.getIndex(selected) == 0) {
				JOptionPane.showMessageDialog(null, "Shape is already at bottom", "Error", JOptionPane.WARNING_MESSAGE);
			} else {
				observerManager.movedDown();
				command = new ToBackCmd(selected, model);
				command.execute();
				log.addElement(command.toString());
				undoStack.push(command);
				redoStack.removeAllElements();
				frame.repaint();
			}
		} else if (frame.getTglbtnToFront().isSelected()) {
			selected = getSelectedList().get(0);
			
			if (model.getIndex(selected) == model.getShapes().size() - 1) {
				JOptionPane.showMessageDialog(null, "Shape is already at top", "Error", JOptionPane.WARNING_MESSAGE);
			} else {
				observerManager.movedUp();
				command = new ToFrontCmd(selected, model);
				command.execute();
				
				log.addElement(command.toString());
				undoStack.push(command);
				redoStack.removeAllElements();
				frame.repaint();
			}

		} else if (frame.getTglbtnBringToBack().isSelected()) {
			selected = getSelectedList().get(0);
			if (model.getIndex(selected) == 0) {
				JOptionPane.showMessageDialog(null, "Shape is already at bottom", "Error", JOptionPane.WARNING_MESSAGE);
			} else {
				observerManager.movedToBottom();
				command = new BringToBackCmd(selected, model);
				command.execute();
				log.addElement(command.toString());
				undoStack.push(command);
				redoStack.removeAllElements();
				frame.repaint();
			}

		} else if (frame.getTglbtnBringToFront().isSelected()) {
			selected = getSelectedList().get(0);
			if (model.getIndex(selected) == model.getShapes().size() - 1) {
				JOptionPane.showMessageDialog(null, "Shape is already on top", "Error", JOptionPane.WARNING_MESSAGE);
			} else {
				observerManager.movedOnTop();
				command = new BringToFrontCmd(selected, model);
				command.execute();
				log.addElement(command.toString());
				undoStack.push(command);
				redoStack.removeAllElements();
				frame.repaint();
			}
		} else if (frame.isSave()) {
			JFileChooser draw = new JFileChooser();
			draw.setDialogTitle("Save drawing");
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".bin", "bin");
			draw.setAcceptAllFileFilterUsed(false);
			draw.setFileFilter(filter);
			draw.setVisible(true);
			if (draw.showSaveDialog(frame.getParent()) == JFileChooser.APPROVE_OPTION) {
				File saveDraw = draw.getSelectedFile();
				File logToSave;
				String path = saveDraw.getAbsolutePath();
				if (!path.endsWith(".bin") && !path.contains(".")) {
					saveDraw = new File(path + ".bin");
					logToSave = new File(path + ".txt");
				}
				String fileName = saveDraw.getPath();
				System.out.println("Save painting as: " + saveDraw.getAbsolutePath());
				if (fileName.substring(fileName.lastIndexOf("."), fileName.length()).contentEquals(".bin")) {
					fileName = saveDraw.getAbsolutePath().substring(0, fileName.lastIndexOf(".")) + ".txt";
					logToSave = new File(fileName);
					SaveManager savePainting = new SaveManager(new SavePainting());
					SaveManager saveLog = new SaveManager(new SaveLog());
					savePainting.save(model, saveDraw);
					saveLog.save(frame, logToSave);
				} else {
					JOptionPane.showMessageDialog(null, "Wrong file extension!");
				}
				model.getShapes().clear();
				frame.getLogList().removeAll();
				frame.getDlm().removeAllElements();
				getUndoStack().clear();
				getRedoStack().clear();
				frame.setSave(false);
			}
			frame.repaint();

		} else if (frame.isOpen()) {
			frame.setOpen(false);
			saveManager = new SaveManager(new OpenPainting());
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Open painting");
			chooser.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".bin", "bin");
			chooser.setFileFilter(filter);
			chooser.setVisible(true);
			if (chooser.showSaveDialog(frame.getParent()) == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				try {
					frame.getBtnNextCommand().setEnabled(true);
					frame.setNext(true);
					saveManager.open(this,model,frame,file);
					
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		else if (frame.isFileClicked()) {
			frame.getBtnOpen().setVisible(true);
			frame.getBtnSave().setVisible(true);
		} else {
			frame.getBtnOpen().setVisible(false);
			frame.getBtnSave().setVisible(false);
		}
		if(getSelectedList().size()!=1) {
			observerManager.oneShape();
		}
		
		if(model.getShapes().size()!=0) {
			observerManager.enableSelect();
		}else observerManager.disableSelect();
		
		if (getUndoStack().size() > 0) {
			observerManager.undoIsNotEmpty();
		} else if (getUndoStack().size() == 0) {
			observerManager.undoIsEmpty();
		}
		if (getRedoStack().size() == 0) {
			observerManager.redoIsEmpty();
		}
		if (model.getShapes().size() == 1) {
			observerManager.oneShape();
		}
		if (model.getShapes().size() != 0) {
			if (getSelectedList().size() == 1 && !model.getShapes().get(0).isSelected()
					&& !model.getShapes().get(model.getShapes().size() - 1).isSelected()) {
				observerManager.inBeetween();
			}
			if (model.getShapes().size() != 1) {
				if (model.getShapes().get(0).isSelected() && getSelectedList().size()==1) {
					observerManager.lowestSelected();
				} else if (model.getShapes().get(model.getShapes().size() - 1).isSelected() && getSelectedList().size()==1) {
					observerManager.highestSelected();
				}
			}
			if (model.getShapes().get(0).isSelected()) {
				observerManager.firstSelected();
			} else if (model.getShapes().get(model.getShapes().size() - 1).isSelected()) {
				observerManager.lastSelected();
			}
		}
		

		frame.repaint();
	}
	
	public ObserverManager getObserverManager() {
		return observerManager;
	}

	public void openFile(File file) {
		
	}

//***************************************************************************************

	public List<Shape> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<Shape> selectedList) {
		this.selectedList = selectedList;
	}

	public void addSelected(List<Shape> selectedList, Shape shape) {
		selectedList.add(shape);
	}

	public Shape getSelected() {
		return selected;
	}

	public void setSelected(Shape selected) {
		this.selected = selected;
	}

	public Shape getShapeSelected(int index) {
		return selectedList.get(index);
	}

	public Stack<Command> getUndoStack() {
		return undoStack;
	}

	public void setUndoStack(Stack<Command> undoStack) {
		this.undoStack = undoStack;
	}

	public Stack<Command> getRedoStack() {
		return redoStack;
	}

	public void setRedoStack(Stack<Command> redoStack) {
		this.redoStack = redoStack;
	}

	public void addListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
