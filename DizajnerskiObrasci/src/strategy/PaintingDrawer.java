package strategy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import adapter.HexagonAdapter;
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
import drawingApp.Donut;
import drawingApp.Line;
import drawingApp.Point;
import drawingApp.Rectangle;
import hexagon.Hexagon;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class PaintingDrawer {
	Stack<Integer> indeksi = new Stack<Integer>();

	private DrawingController controller;
	private DrawingModel model;
	private DrawingFrame frame;
	Command command;

	public PaintingDrawer(DrawingController controller, DrawingModel model, DrawingFrame frame) {
		this.controller = controller;
		this.model = model;
		this.frame = frame;
	}

	public void paint(String toDraw) {
		
		// ako ima add,update,select,deselect,toback....
		if (toDraw.contains("Added") && !toDraw.contains("Undo") )
			added(toDraw);
		else if (toDraw.contains("Removed") && !toDraw.contains("Undo") )
			removed(toDraw);
		else if (toDraw.contains("Updated") && !toDraw.contains("Undo") )
			updated(toDraw);
		else if (toDraw.contains("Brought to back") && !toDraw.contains("Undo") )
			btb(toDraw);
		else if (toDraw.contains("Brought to front") && !toDraw.contains("Undo") )
			btf(toDraw);
		else if (toDraw.contains("Selected") && !toDraw.contains("Undo") )
			select(toDraw);
		else if (toDraw.contains("Deselect") && !toDraw.contains("Undo") )
			deselect(toDraw);
		else if (toDraw.contains("Moved up") && !toDraw.contains("Undo") )
			movedup(toDraw);
		else if (toDraw.contains("Moved down") && !toDraw.contains("Undo"))
			movedDown(toDraw);
		else if (toDraw.contains("Undo") )
			undo(toDraw);
		/*else if (toDraw.contains("Redo") && !toDraw.contains("Undo"))
			redo(toDraw);*/
	}

	public void added(String toDraw) {
			if(toDraw.contains("Point")) {
				Point p;
				Color color;
				int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
				int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
				p = new Point(x,y);
				if(!toDraw.contains("null")) {
					String rgb = toDraw.substring(toDraw.indexOf("j"));
					String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
					int r = Integer.parseInt(rgbValues[0]);
			        int g = Integer.parseInt(rgbValues[1]);
			        int b = Integer.parseInt(rgbValues[2]);
			        color = new Color(r,g,b);
			        p.setColor(color);
				} else p.setColor(Color.black);
		        command = new AddShapeCmd(p,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
				//System.out.println(p.hashCode());
				
			}
			else if(toDraw.contains("Line")) {
				int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
				int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
				String endPoint = toDraw.substring(toDraw.indexOf(";"));
				int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
				int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
				Line l = new Line (new Point(x1,y1), new Point(x2,y2));
				if(!toDraw.contains("null")) {
					String rgb = toDraw.substring(toDraw.indexOf("j"));
					String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
					int r = Integer.parseInt(rgbValues[0]);
			        int g = Integer.parseInt(rgbValues[1]);
			        int b = Integer.parseInt(rgbValues[2]);
			        Color color = new Color(r,g,b);
			        l.setColor(color);
				} else l.setColor(Color.black);
		        command = new AddShapeCmd(l,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
				
			}
			else if(toDraw.contains("Circle")) {
				Circle c;
				Color borderColor,innerColor;
				int b,red,g,red2,g2,b2;
				int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
				int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
				Point center = new Point(x,y);
				String radius = toDraw.substring(toDraw.indexOf("Radius"));
				int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
				c = new Circle(center,r);
				String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
				if(!border.contains("null")) {
					String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
					String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
					red = Integer.parseInt(rgbValues[0]);
			        g = Integer.parseInt(rgbValues[1]);
			        b = Integer.parseInt(rgbValues[2]);
			        borderColor = new Color(red,g,b);
			        c.setColor(borderColor);
				}else {borderColor = Color.black;c.setColor(borderColor);}
				
				
		        String inner = toDraw.substring(toDraw.indexOf("Inner"));
		        if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 red2 = Integer.parseInt(rgbValues2[0]);
				     g2 = Integer.parseInt(rgbValues2[1]);
				     b2 = Integer.parseInt(rgbValues2[2]);
				     innerColor = new Color(red2,g2,b2);
				     c.setColorUnutrasnjost(innerColor);
		        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
		        command = new AddShapeCmd(c,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();		
			}
			else if(toDraw.contains("Rectangle")) {
				Rectangle r;
				int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
				int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
				Point upper = new Point(x,y);
				String h = toDraw.substring(toDraw.indexOf("Height"));
				int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
				String w = toDraw.substring(toDraw.indexOf("Width"));
				int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
				r = new Rectangle(upper,height,width);
				String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
				if(!border.contains("null")) {
					String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
					String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
					int red = Integer.parseInt(rgbValues[0]);
					int  g = Integer.parseInt(rgbValues[1]);
					int   b = Integer.parseInt(rgbValues[2]);
					Color   borderColor = new Color(red,g,b);
					r.setColor(borderColor);
				} else r.setColor(Color.black);
				 String inner = toDraw.substring(toDraw.indexOf("Inner"));
			    if(!inner.contains("null")) {
			        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
			        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
			        	 int red2 = Integer.parseInt(rgbValues2[0]);
			        	 int  g2 = Integer.parseInt(rgbValues2[1]);
			        	 int  b2 = Integer.parseInt(rgbValues2[2]);
			        	 Color innerColor = new Color(red2,g2,b2);
			        	 r.setColorUnutrasnjost(innerColor);
			    }else r.setColorUnutrasnjost(Color.black);
			    command = new AddShapeCmd(r,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();	
			}
			else if(toDraw.contains("Donut")) {
				Donut d;
				int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
				int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
				Point center = new Point(x,y);
				String radius = toDraw.substring(toDraw.indexOf("Radius"));
				int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
				String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
				int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
				d = new Donut(center,r,ir);
				d.setColorUnutrasnostMali(frame.getBackground());
				String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
				if(!border.contains("null")) {
					String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
					String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
					int red = Integer.parseInt(rgbValues[0]);
					int  g = Integer.parseInt(rgbValues[1]);
					int   b = Integer.parseInt(rgbValues[2]);
					Color   borderColor = new Color(red,g,b);
					d.setColor(borderColor);
					d.setColorIviceMali(borderColor);
				}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
				String inner = toDraw.substring(toDraw.indexOf("Inner color"));
				 if(!inner.contains("null")) {
					 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 d.setColorUnutrasnjostVeliki(innerColor);
				 } else {d.setColorUnutrasnjostVeliki(Color.black);}
				 	command = new AddShapeCmd(d,model);
			        command.execute();
			        controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
			}
			else if(toDraw.contains("Hexagon")) {
				HexagonAdapter h;
				int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
				int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
				Point center = new Point(x,y);
				String radius = toDraw.substring(toDraw.indexOf("Radius"));
				int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
				h = new HexagonAdapter(center,r);
				String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
				if(!border.contains("null")) {
					String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
					String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
					int red = Integer.parseInt(rgbValues[0]);
					int  g = Integer.parseInt(rgbValues[1]);
					int   b = Integer.parseInt(rgbValues[2]);
					Color   borderColor = new Color(red,g,b);
					h.setColor(borderColor);
				}else {h.setColor(Color.black);}
				String inner = toDraw.substring(toDraw.indexOf("Inner color"));
				 if(!inner.contains("null")) {
					 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 h.setInnerColor(innerColor);
				 }else {h.setInnerColor(Color.black);}
				 command = new AddShapeCmd(h,model);
			        command.execute();
			        controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
			}
			controller.getObserverManager().redoIsEmpty();
			observers();
		}

	public void removed(String toDraw) {

		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
	        command = new RemoveShapeCmd(p,model,model.getIndex(p));
	        indeksi.push(model.getIndex(p));
	        command.execute();
	        
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
	        command = new RemoveShapeCmd(l,model,model.getIndex(l));
	        indeksi.push(model.getIndex(l));
	        command.execute();
	        
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        command = new RemoveShapeCmd(c,model,model.getIndex(c));
	        indeksi.push(model.getIndex(c));
	        command.execute();
	        
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    command = new RemoveShapeCmd(r,model,model.getIndex(r));
		    indeksi.push(model.getIndex(r));
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 	command = new RemoveShapeCmd(d,model,model.getIndex(d));
			 	  indeksi.push(model.getIndex(d));
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 command = new RemoveShapeCmd(h,model,model.getIndex(h));
			  indeksi.push(model.getIndex(h));
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		controller.getObserverManager().delete();
		controller.getObserverManager().redoIsEmpty();
		observers();
	}

	public void updated(String toDraw) {

		if(toDraw.contains("Point")) {
			
			String original = toDraw.substring(toDraw.indexOf("Point"),toDraw.indexOf("into"));
			String newState = toDraw.substring(toDraw.indexOf("into"));
			Point p;
			Color color;
			int x = Integer.parseInt(original.substring((original.indexOf("("))+1 ,original.indexOf(",")));
			int y = Integer.parseInt(original.substring((original.indexOf(","))+1 ,original.indexOf(")")));
			p = new Point(x,y);
			if(!original.contains("null")) {
				String rgb = original.substring(original.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			Point p2;
			Color color2;
			int x2 = Integer.parseInt(newState.substring((newState.indexOf("("))+1 ,newState.indexOf(",")));
			int y2 = Integer.parseInt(newState.substring((newState.indexOf(","))+1 ,newState.indexOf(")")));
			p2 = new Point(x2,y2);
			if(!newState.contains("null")) {
				String rgb = newState.substring(newState.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color2 = new Color(r,g,b);
		        p2.setColor(color2);
			}else p2.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(p)) {
					model.get(i).setSelected(model.get(i).isSelected());
					command = new UpdatePointCmd((Point)model.get(i),p2);
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}
			
		}
		else if(toDraw.contains("Line")) {
			String original = toDraw.substring(toDraw.indexOf("Updated"),toDraw.indexOf("into"));
			int x1 = Integer.parseInt(original.substring((original.indexOf("("))+1 ,original.indexOf(",")));
			int y1 = Integer.parseInt(original.substring((original.indexOf(","))+1 ,original.indexOf(")")));
			String endPoint = original.substring(original.indexOf(";"));
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!original.contains("null")) {
				String rgb = original.substring(original.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			String newState = toDraw.substring(toDraw.indexOf("into"));
			int x11 = Integer.parseInt(newState.substring((newState.indexOf("("))+1 ,newState.indexOf(",")));
			int y11 = Integer.parseInt(newState.substring((newState.indexOf(","))+1 ,newState.indexOf(")")));
			String endPointNew = newState.substring(newState.indexOf(";"));
			int x21 = Integer.parseInt(endPointNew.substring((endPointNew.indexOf("("))+1 ,endPointNew.indexOf(",")));
			int y21 = Integer.parseInt(endPointNew.substring((endPointNew.indexOf(","))+1 ,endPointNew.indexOf(")")));
			Line l2 = new Line(new Point(x11,y11), new Point(x21,y21));
			if(!newState.contains("null")) {
				String rgb = newState.substring(newState.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l2.setColor(color);
			}else l2.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(l)) {
					model.get(i).setSelected(model.get(i).isSelected());
					command = new UpdateLineCmd((Line)model.get(i),l2);
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}
		}
		else if(toDraw.contains("Circle")) {
			String original = toDraw.substring(toDraw.indexOf("Updated"),toDraw.indexOf("into"));
			String newState = toDraw.substring(toDraw.indexOf("into"));
			Circle c,c2;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(original.substring((original.indexOf("="))+3,(original.indexOf(","))));
			int y = Integer.parseInt(original.substring((original.indexOf(","))+1,original.indexOf(")")));
			Point center = new Point(x,y);
			String radius = original.substring(original.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = original.substring(original.indexOf("Border"),original.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
	        String inner = original.substring(original.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
			int x1 = Integer.parseInt(newState.substring((newState.indexOf("="))+3,(newState.indexOf(","))));
			int y1 = Integer.parseInt(newState.substring((newState.indexOf(","))+1,newState.indexOf(")")));
			Point center2 = new Point(x1,y1);
			String radius2 = newState.substring(newState.indexOf("Radius"));
			int r2 = Integer.parseInt(radius2.substring(radius2.indexOf("=")+2,radius2.indexOf("=")+4)) ;
			c2 = new Circle(center2,r2);
			String border2 = newState.substring(newState.indexOf("Border"),newState.indexOf("Inner"));
			if(!border2.contains("null")) {
				String rgb = border2.substring(border2.indexOf("j"),border2.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c2.setColor(borderColor);
			}else {borderColor = Color.black;c2.setColor(borderColor);}
			 String inner2 = newState.substring(newState.indexOf("Inner"));
			 if(!inner2.contains("null")) {
				 String rgb2 = inner2.substring(inner2.indexOf("j"),inner2.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c2.setColorUnutrasnjost(innerColor);
			 } else {innerColor = Color.black; c2.setColorUnutrasnjost(innerColor);}
			 for(int i=0 ; i<model.getShapes().size();i++) {
					if(model.get(i).equals(c)) {
						model.get(i).setSelected(model.get(i).isSelected());
						command = new UpdateCircleCmd((Circle)model.get(i),c2);
						command.execute();
						controller.getUndoStack().push(command);
						controller.getRedoStack().removeAllElements();
						frame.repaint();
						//System.out.println(model.get(i).hashCode());
					}
				}
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			String original = toDraw.substring(toDraw.indexOf("Updated"),toDraw.indexOf("into"));
			String newState = toDraw.substring(toDraw.indexOf("into"));
			int x = Integer.parseInt(original.substring((original.indexOf("("))+1 ,original.indexOf(",")));
			int y = Integer.parseInt(original.substring((original.indexOf(","))+1,original.indexOf(")")));
			Point upper = new Point(x,y);
			String h = original.substring(original.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = original.substring(original.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = original.substring(original.indexOf("Border"),original.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = original.substring(original.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
			int x1 = Integer.parseInt(newState.substring((newState.indexOf("("))+1 ,newState.indexOf(",")));
			int y1 = Integer.parseInt(newState.substring((newState.indexOf(","))+1,newState.indexOf(")")));
			Point upper1 = new Point(x1,y1);
			String h1 = newState.substring(newState.indexOf("Height"));
			int height1 = Integer.parseInt(h1.substring((h1.indexOf("=")+2),(h1.indexOf(";")-1)));
			String w1 = newState.substring(newState.indexOf("Width"));
			int width1 = Integer.parseInt(w1.substring((w1.indexOf("="))+1,(w1.indexOf(";")-1)));
			Rectangle r2 = new Rectangle(upper1,height1,width1);
			String border1 = newState.substring(newState.indexOf("Border"),newState.indexOf("Inner"));
			if(!border1.contains("null")) {
				String rgb = border1.substring(border1.indexOf("j"),border1.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r2.setColor(borderColor);
			}else r2.setColor(Color.black);
			String inner1 = newState.substring(newState.indexOf("Inner"));
		    if(!inner1.contains("null")) {
		    	 String rgb2 = inner1.substring(inner1.indexOf("j"),inner1.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 r2.setColorUnutrasnjost(innerColor);
		    }else r2.setColorUnutrasnjost(Color.black);
		    for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(r)) {
					model.get(i).setSelected(model.get(i).isSelected());
					command = new UpdateRectangleCmd((Rectangle)model.get(i),r2);
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}
		}
		else if(toDraw.contains("Donut")) {
			String original = toDraw.substring(toDraw.indexOf("Updated"),toDraw.indexOf("into"));
			String newState = toDraw.substring(toDraw.indexOf("into"));
			Donut d;
			int x = Integer.parseInt(original.substring((original.indexOf("("))+1 ,original.indexOf(",")));
			int y = Integer.parseInt(original.substring((original.indexOf(","))+1,original.indexOf(")")));
			Point center = new Point(x,y);
			String radius = original.substring(original.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = original.substring(original.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = original.substring(original.indexOf("Border"),original.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = original.substring(original.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
				int x1 = Integer.parseInt(newState.substring((newState.indexOf("("))+1 ,newState.indexOf(",")));
				int y1 = Integer.parseInt(newState.substring((newState.indexOf(","))+1,newState.indexOf(")")));
				Point center1 = new Point(x1,y1);
				String radius1 = newState.substring(newState.indexOf("Radius"));
				int r1 = Integer.parseInt(radius1.substring((radius1.indexOf("=")+2),(radius1.indexOf(";")-1)));
				String innerRadius1 = newState.substring(newState.indexOf("Inner radius"));
				int ir1 = Integer.parseInt(innerRadius1.substring((innerRadius1.indexOf("=")+2),(innerRadius1.indexOf(";")-1)));
				Donut d2 = new Donut(center1,r1,ir1);
				d2.setColorUnutrasnostMali(frame.getBackground());
				String border1 = newState.substring(newState.indexOf("Border"),newState.indexOf("Inner color"));
				if(!border1.contains("null")) {
					String rgb = border1.substring(border1.indexOf("j"),border1.indexOf("]"));
					String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
					int red = Integer.parseInt(rgbValues[0]);
					int  g = Integer.parseInt(rgbValues[1]);
					int   b = Integer.parseInt(rgbValues[2]);
					Color   borderColor = new Color(red,g,b);
					d2.setColor(borderColor);
					d2.setColorIviceMali(borderColor);
				}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
				String inner1 = newState.substring(newState.indexOf("Inner color"));
				 if(!inner1.contains("null")) {
					 String rgb2 = inner1.substring(inner1.indexOf("j"),inner1.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 d2.setColorUnutrasnjostVeliki(innerColor);
				 }else {d2.setColorUnutrasnjostVeliki(Color.black);}
				 for(int i=0 ; i<model.getShapes().size();i++) {
						if(model.get(i).equals(d)) {
							model.get(i).setSelected(model.get(i).isSelected());
							command = new UpdateDonutCmd((Donut)model.get(i),d2);
							command.execute();
							controller.getUndoStack().push(command);
							controller.getRedoStack().removeAllElements();
							frame.repaint();
							//System.out.println(model.get(i).hashCode());
						}
					}
		}
		else if(toDraw.contains("Hexagon")) {
			String original = toDraw.substring(toDraw.indexOf("Updated"),toDraw.indexOf("into"));
			String newState = toDraw.substring(toDraw.indexOf("into"));
			HexagonAdapter h;
			int x = Integer.parseInt(original.substring((original.indexOf("("))+1 ,original.indexOf(",")));
			int y = Integer.parseInt(original.substring((original.indexOf(","))+1,original.indexOf(")")));
			Point center = new Point(x,y);
			String radius = original.substring(original.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = original.substring(original.indexOf("Border"),original.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = original.substring(original.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
				int x1 = Integer.parseInt(newState.substring((newState.indexOf("("))+1 ,newState.indexOf(",")));
				int y1 = Integer.parseInt(newState.substring((newState.indexOf(","))+1,newState.indexOf(")")));
				Point center1 = new Point(x1,y1);
				String radius1 = newState.substring(newState.indexOf("Radius"));
				int r1 = Integer.parseInt(radius1.substring((radius1.indexOf("=")+2),(radius1.indexOf(";")-1)));
				HexagonAdapter h2 = new HexagonAdapter(center1,r1);
				String border1 = newState.substring(newState.indexOf("Border"),newState.indexOf("Inner color"));
				if(!border1.contains("null")) {
					String rgb = border1.substring(border1.indexOf("j"),border1.indexOf("]"));
					String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
					int red = Integer.parseInt(rgbValues[0]);
					int  g = Integer.parseInt(rgbValues[1]);
					int   b = Integer.parseInt(rgbValues[2]);
					Color   borderColor = new Color(red,g,b);
					h2.setColor(borderColor);
				}else {h2.setColor(Color.black);}
				String inner1 = newState.substring(newState.indexOf("Inner color"));
				 if(!inner1.contains("null")) {
					 String rgb2 = inner1.substring(inner1.indexOf("j"),inner1.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 h2.setInnerColor(innerColor);
				 }else{h2.setInnerColor(Color.black);}
				 for(int i=0 ; i<model.getShapes().size();i++) {
						if(model.get(i).equals(h)) {
							model.get(i).setSelected(model.get(i).isSelected());
							command = new UpdateHexagonCmd((HexagonAdapter)model.get(i),h2);
							command.execute();
							controller.getUndoStack().push(command);
							controller.getRedoStack().removeAllElements();
							frame.repaint();
						}
					}
		}
		controller.getObserverManager().redoIsEmpty();
		observers();
	}

	public void btb(String toDraw) {


		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			p.setSelected(true);
	        command = new BringToBackCmd(p,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			l.setSelected(true);
	        command = new BringToBackCmd(l,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        c.setSelected(true);
	        command = new BringToBackCmd(c,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    r.setSelected(true);
		    command = new BringToBackCmd(r,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 d.setSelected(true);
			 	command = new BringToBackCmd(d,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 h.setSelected(true);
			 command = new BringToBackCmd(h,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
	
		controller.getObserverManager().redoIsEmpty();
		observers();
	}

	public void btf(String toDraw) {
		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			p.setSelected(true);
	        command = new BringToFrontCmd(p,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			l.setSelected(true);
	        command = new BringToFrontCmd(l,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        c.setSelected(true);
	        command = new BringToFrontCmd(c,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    r.setSelected(true);
		    command = new BringToFrontCmd(r,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 d.setSelected(true);
			 	command = new BringToFrontCmd(d,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 h.setSelected(true);
			 command = new BringToFrontCmd(h,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		controller.getObserverManager().redoIsEmpty();
		observers();
	}

	public void select(String toDraw) {
		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(p)) {
					command = new SelectedShapeCmd(controller,(Point)model.get(i));
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
				
			}
			
		}else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(l)) {
					command = new SelectedShapeCmd(controller,(Line)model.get(i));
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(c)) {
					command = new SelectedShapeCmd(controller,(Circle)model.get(i));
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(r)) {
					command = new SelectedShapeCmd(controller,(Rectangle)model.get(i));
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 for(int i=0 ; i<model.getShapes().size();i++) {
					if(model.get(i).equals(d)) {
						command = new SelectedShapeCmd(controller,(Donut)model.get(i));
						command.execute();
						controller.getUndoStack().push(command);
						controller.getRedoStack().removeAllElements();
						frame.repaint();
						//System.out.println(model.get(i).hashCode());
					}
				}
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 for(int i=0 ; i<model.getShapes().size();i++) {
					if(model.get(i).equals(h)) {
						command = new SelectedShapeCmd(controller,(HexagonAdapter)model.get(i));
						command.execute();
						controller.getUndoStack().push(command);
						controller.getRedoStack().removeAllElements();
						frame.repaint();
						//System.out.println(model.get(i).hashCode());
					}
				}
		}
	
		controller.getObserverManager().redoIsEmpty();
		observers();
	}

	public void deselect(String toDraw) {

		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(p)) {
					command = new SelectedShapeCmd(controller,(Point)model.get(i));
					command.unexecute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
				
			}
			
		}else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(l)) {
					command = new SelectedShapeCmd(controller,(Line)model.get(i));
					command.unexecute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(c)) {
					command = new SelectedShapeCmd(controller,(Circle)model.get(i));
					command.unexecute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(r)) {
					command = new SelectedShapeCmd(controller,(Rectangle)model.get(i));
					command.unexecute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 for(int i=0 ; i<model.getShapes().size();i++) {
					if(model.get(i).equals(d)) {
						command = new SelectedShapeCmd(controller,(Donut)model.get(i));
						command.unexecute();
						controller.getUndoStack().push(command);
						controller.getRedoStack().removeAllElements();
						frame.repaint();
						//System.out.println(model.get(i).hashCode());
					}
				}
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 for(int i=0 ; i<model.getShapes().size();i++) {
					if(model.get(i).equals(h)) {
						command = new SelectedShapeCmd(controller,(HexagonAdapter)model.get(i));
						command.unexecute();
						controller.getUndoStack().push(command);
						controller.getRedoStack().removeAllElements();
						frame.repaint();
						//System.out.println(model.get(i).hashCode());
					}
				}
		}
	
		controller.getObserverManager().redoIsEmpty();
	
		observers();
	}

	public void movedup(String toDraw) {

		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			p.setSelected(true);
	        command = new ToFrontCmd(p,model);
	        command.execute();
	        
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			//System.out.println(toDraw + '\n');
			//System.out.println(endPoint);
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			//System.out.println(x2);
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			 l.setSelected(true);
	        command = new ToFrontCmd(l,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        c.setSelected(true);
	        command = new ToFrontCmd(c,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    r.setSelected(true);
		    command = new ToFrontCmd(r,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 d.setSelected(true);
			 	command = new ToFrontCmd(d,model);
		        command.execute();
		       
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 h.setSelected(true);
			 command = new ToFrontCmd(h,model);
		        command.execute();
		       
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		controller.getObserverManager().redoIsEmpty();
		observers();
	}

	public void movedDown(String toDraw) {
		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			 p.setSelected(true);
	        command = new ToBackCmd(p,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			//System.out.println(toDraw + '\n');
			//System.out.println(endPoint);
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			//System.out.println(x2);
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			 l.setSelected(true);
	        command = new ToBackCmd(l,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        c.setSelected(true);
	        command = new ToBackCmd(c,model);
	        command.execute();
	      
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    r.setSelected(true);
		    command = new ToBackCmd(r,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 d.setSelected(true);
			 	command = new ToBackCmd(d,model);
		        command.execute();
		       
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 h.setSelected(true);
			 command = new ToBackCmd(h,model);
		        command.execute();
		       
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		controller.getObserverManager().redoIsEmpty();
		observers();
	}

	public void undo(String toDraw) {
		controller.getObserverManager().undoClicked();
//ако садржи ундо, пошаљи га у нову функцију која ће их слати у исто као пејнт само без услова за ундо и да исто пролазе кроз све само иде анегзекјут
		undoPaint(toDraw);
	}

	
	public void undoPaint(String toDraw) {
		if (toDraw.contains("Added")  && !toDraw.contains("Redo"))
			addedUndo(toDraw);
		else if (toDraw.contains("Removed")  && !toDraw.contains("Redo"))
			removedUndo(toDraw);
		else if (toDraw.contains("Updated")  && !toDraw.contains("Redo"))
			updatedUndo(toDraw);
		else if (toDraw.contains("Brought to back")  && !toDraw.contains("Redo"))
			btbUndo(toDraw);
		else if (toDraw.contains("Brought to front") && !toDraw.contains("Redo"))
			btfUndo(toDraw);
		else if (toDraw.contains("Selected")  && !toDraw.contains("Redo"))
			selectUndo(toDraw);
		else if (toDraw.contains("Deselect")  && !toDraw.contains("Redo"))
			deselectUndo(toDraw);
		else if (toDraw.contains("Moved up")  && !toDraw.contains("Redo"))
			movedupUndo(toDraw);
		else if (toDraw.contains("Moved down")  && !toDraw.contains("Redo"))
			movedDownUndo(toDraw);
		
	}
	private void movedDownUndo(String toDraw) {

		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			p.setSelected(true);
	        command = new ToFrontCmd(p,model);
	        command.execute();
	        
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			//System.out.println(toDraw + '\n');
			//System.out.println(endPoint);
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			//System.out.println(x2);
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			 l.setSelected(true);
	        command = new ToFrontCmd(l,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        c.setSelected(true);
	        command = new ToFrontCmd(c,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    r.setSelected(true);
		    command = new ToFrontCmd(r,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 d.setSelected(true);
			 	command = new ToFrontCmd(d,model);
		        command.execute();
		       
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 h.setSelected(true);
			 command = new ToFrontCmd(h,model);
		        command.execute();
		        
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}

		observers();
		
	}

	private void movedupUndo(String toDraw) {


		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			 p.setSelected(true);
	        command = new ToBackCmd(p,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			//System.out.println(toDraw + '\n');
			//System.out.println(endPoint);
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			//System.out.println(x2);
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			 l.setSelected(true);
	        command = new ToBackCmd(l,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        c.setSelected(true);
	        command = new ToBackCmd(c,model);
	        command.execute();
	       
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    r.setSelected(true);
		    command = new ToBackCmd(r,model);
	        command.execute();
	        
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 d.setSelected(true);
			 	command = new ToBackCmd(d,model);
		        command.execute();
		       
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			  h.setSelected(true);
			 command = new ToBackCmd(h,model);
		        command.execute();
		      
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}

	
		observers();
	}

	private void deselectUndo(String toDraw) {

		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(p)) {
					command = new SelectedShapeCmd(controller,(Point)model.get(i));
					command.unexecute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
				
			}
			
		}else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(l)) {
					command = new SelectedShapeCmd(controller,(Line)model.get(i));
					command.unexecute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(c)) {
					command = new SelectedShapeCmd(controller,(Circle)model.get(i));
					command.unexecute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(r)) {
					command = new SelectedShapeCmd(controller,(Rectangle)model.get(i));
					command.unexecute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 for(int i=0 ; i<model.getShapes().size();i++) {
					if(model.get(i).equals(d)) {
						command = new SelectedShapeCmd(controller,(Donut)model.get(i));
						command.unexecute();
						controller.getUndoStack().push(command);
						controller.getRedoStack().removeAllElements();
						frame.repaint();
						//System.out.println(model.get(i).hashCode());
					}
				}
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 for(int i=0 ; i<model.getShapes().size();i++) {
					if(model.get(i).equals(h)) {
						command = new SelectedShapeCmd(controller,(HexagonAdapter)model.get(i));
						command.unexecute();
						controller.getUndoStack().push(command);
						controller.getRedoStack().removeAllElements();
						frame.repaint();
						//System.out.println(model.get(i).hashCode());
					}
				}
		}
	
		controller.getObserverManager().redoIsEmpty();
	
		observers();
	}

	private void selectUndo(String toDraw) {

		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(p)) {
					command = new SelectedShapeCmd(controller,(Point)model.get(i));
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
				
			}
			
		}else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(l)) {
					command = new SelectedShapeCmd(controller,(Line)model.get(i));
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(c)) {
					command = new SelectedShapeCmd(controller,(Circle)model.get(i));
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(r)) {
					command = new SelectedShapeCmd(controller,(Rectangle)model.get(i));
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 for(int i=0 ; i<model.getShapes().size();i++) {
					if(model.get(i).equals(d)) {
						command = new SelectedShapeCmd(controller,(Donut)model.get(i));
						command.execute();
						controller.getUndoStack().push(command);
						controller.getRedoStack().removeAllElements();
						frame.repaint();
						//System.out.println(model.get(i).hashCode());
					}
				}
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 for(int i=0 ; i<model.getShapes().size();i++) {
					if(model.get(i).equals(h)) {
						command = new SelectedShapeCmd(controller,(HexagonAdapter)model.get(i));
						command.execute();
						controller.getUndoStack().push(command);
						controller.getRedoStack().removeAllElements();
						frame.repaint();
						//System.out.println(model.get(i).hashCode());
					}
				}
		}
	
		controller.getObserverManager().redoIsEmpty();
	
		observers();
	}

	private void btfUndo(String toDraw) {

		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			p.setSelected(true);
	        command = new BringToBackCmd(p,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			//System.out.println(toDraw + '\n');
			//System.out.println(endPoint);
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			//System.out.println(x2);
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			l.setSelected(true);
	        command = new BringToBackCmd(l,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        c.setSelected(true);
	        command = new BringToBackCmd(c,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    r.setSelected(true);
		    command = new BringToBackCmd(r,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 d.setSelected(true);
			 	command = new BringToBackCmd(d,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 h.setSelected(true);
			 command = new BringToBackCmd(h,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
	
		observers();
	}

	private void btbUndo(String toDraw) {



		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			p.setSelected(true);
	        command = new BringToFrontCmd(p,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			//System.out.println(toDraw + '\n');
			//System.out.println(endPoint);
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			//System.out.println(x2);
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			l.setSelected(true);
	        command = new BringToFrontCmd(l,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        c.setSelected(true);
	        command = new BringToFrontCmd(c,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    r.setSelected(true);
		    command = new BringToFrontCmd(r,model);
	        command.execute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 d.setSelected(true);
			 	command = new BringToFrontCmd(d,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 h.setSelected(true);
			 command = new BringToFrontCmd(h,model);
		        command.execute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
	
		observers();

	}


	private void updatedUndo(String toDraw) {


		if(toDraw.contains("Point")) {
			
			String original = toDraw.substring(toDraw.indexOf("Point"),toDraw.indexOf("into"));
			String newState = toDraw.substring(toDraw.indexOf("into"));
			Point p;
			Color color;
			int x = Integer.parseInt(original.substring((original.indexOf("("))+1 ,original.indexOf(",")));
			int y = Integer.parseInt(original.substring((original.indexOf(","))+1 ,original.indexOf(")")));
			p = new Point(x,y);
			if(!original.contains("null")) {
				String rgb = original.substring(original.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
			Point p2;
			Color color2;
			int x2 = Integer.parseInt(newState.substring((newState.indexOf("("))+1 ,newState.indexOf(",")));
			int y2 = Integer.parseInt(newState.substring((newState.indexOf(","))+1 ,newState.indexOf(")")));
			p2 = new Point(x2,y2);
			if(!newState.contains("null")) {
				String rgb = newState.substring(newState.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color2 = new Color(r,g,b);
		        p2.setColor(color2);
			}else p2.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(p2)) {
					command = new UpdatePointCmd((Point)model.get(i),p);
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}
			
		}
		else if(toDraw.contains("Line")) {
			String original = toDraw.substring(toDraw.indexOf("Updated"),toDraw.indexOf("into"));
			int x1 = Integer.parseInt(original.substring((original.indexOf("("))+1 ,original.indexOf(",")));
			int y1 = Integer.parseInt(original.substring((original.indexOf(","))+1 ,original.indexOf(")")));
			String endPoint = original.substring(original.indexOf(";"));
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!original.contains("null")) {
				String rgb = original.substring(original.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
			String newState = toDraw.substring(toDraw.indexOf("into"));
			int x11 = Integer.parseInt(newState.substring((newState.indexOf("("))+1 ,newState.indexOf(",")));
			int y11 = Integer.parseInt(newState.substring((newState.indexOf(","))+1 ,newState.indexOf(")")));
			String endPointNew = newState.substring(newState.indexOf(";"));
			int x21 = Integer.parseInt(endPointNew.substring((endPointNew.indexOf("("))+1 ,endPointNew.indexOf(",")));
			int y21 = Integer.parseInt(endPointNew.substring((endPointNew.indexOf(","))+1 ,endPointNew.indexOf(")")));
			Line l2 = new Line(new Point(x11,y11), new Point(x21,y21));
			if(!newState.contains("null")) {
				String rgb = newState.substring(newState.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l2.setColor(color);
			}else l2.setColor(Color.black);
			for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(l2)) {
					command = new UpdateLineCmd((Line)model.get(i),l);
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}
		}
		else if(toDraw.contains("Circle")) {
			String original = toDraw.substring(toDraw.indexOf("Updated"),toDraw.indexOf("into"));
			String newState = toDraw.substring(toDraw.indexOf("into"));
			Circle c,c2;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(original.substring((original.indexOf("="))+3,(original.indexOf(","))));
			int y = Integer.parseInt(original.substring((original.indexOf(","))+1,original.indexOf(")")));
			Point center = new Point(x,y);
			String radius = original.substring(original.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = original.substring(original.indexOf("Border"),original.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
	        String inner = original.substring(original.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
			int x1 = Integer.parseInt(newState.substring((newState.indexOf("="))+3,(newState.indexOf(","))));
			int y1 = Integer.parseInt(newState.substring((newState.indexOf(","))+1,newState.indexOf(")")));
			Point center2 = new Point(x1,y1);
			String radius2 = newState.substring(newState.indexOf("Radius"));
			int r2 = Integer.parseInt(radius2.substring(radius2.indexOf("=")+2,radius2.indexOf("=")+4)) ;
			c2 = new Circle(center2,r2);
			String border2 = newState.substring(newState.indexOf("Border"),newState.indexOf("Inner"));
			if(!border2.contains("null")) {
				String rgb = border2.substring(border2.indexOf("j"),border2.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c2.setColor(borderColor);
			}else {borderColor = Color.black;c2.setColor(borderColor);}
			 String inner2 = newState.substring(newState.indexOf("Inner"));
			 if(!inner2.contains("null")) {
				 String rgb2 = inner2.substring(inner2.indexOf("j"),inner2.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c2.setColorUnutrasnjost(innerColor);
			 } else {innerColor = Color.black; c2.setColorUnutrasnjost(innerColor);}
			 for(int i=0 ; i<model.getShapes().size();i++) {
					if(model.get(i).equals(c2)) {
						command = new UpdateCircleCmd((Circle)model.get(i),c);
						command.execute();
						controller.getUndoStack().push(command);
						controller.getRedoStack().removeAllElements();
						frame.repaint();
						//System.out.println(model.get(i).hashCode());
					}
				}
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			String original = toDraw.substring(toDraw.indexOf("Updated"),toDraw.indexOf("into"));
			String newState = toDraw.substring(toDraw.indexOf("into"));
			int x = Integer.parseInt(original.substring((original.indexOf("("))+1 ,original.indexOf(",")));
			int y = Integer.parseInt(original.substring((original.indexOf(","))+1,original.indexOf(")")));
			Point upper = new Point(x,y);
			String h = original.substring(original.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = original.substring(original.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = original.substring(original.indexOf("Border"),original.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = original.substring(original.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
			int x1 = Integer.parseInt(newState.substring((newState.indexOf("("))+1 ,newState.indexOf(",")));
			int y1 = Integer.parseInt(newState.substring((newState.indexOf(","))+1,newState.indexOf(")")));
			Point upper1 = new Point(x1,y1);
			String h1 = newState.substring(newState.indexOf("Height"));
			int height1 = Integer.parseInt(h1.substring((h1.indexOf("=")+2),(h1.indexOf(";")-1)));
			String w1 = newState.substring(newState.indexOf("Width"));
			int width1 = Integer.parseInt(w1.substring((w1.indexOf("="))+1,(w1.indexOf(";")-1)));
			Rectangle r2 = new Rectangle(upper1,height1,width1);
			String border1 = newState.substring(newState.indexOf("Border"),newState.indexOf("Inner"));
			if(!border1.contains("null")) {
				String rgb = border1.substring(border1.indexOf("j"),border1.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r2.setColor(borderColor);
			}else r2.setColor(Color.black);
			String inner1 = newState.substring(newState.indexOf("Inner"));
		    if(!inner1.contains("null")) {
		    	 String rgb2 = inner1.substring(inner1.indexOf("j"),inner1.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 r2.setColorUnutrasnjost(innerColor);
		    }else r2.setColorUnutrasnjost(Color.black);
		    for(int i=0 ; i<model.getShapes().size();i++) {
				if(model.get(i).equals(r2)) {
					command = new UpdateRectangleCmd((Rectangle)model.get(i),r);
					command.execute();
					controller.getUndoStack().push(command);
					controller.getRedoStack().removeAllElements();
					frame.repaint();
					//System.out.println(model.get(i).hashCode());
				}
			}
		}
		else if(toDraw.contains("Donut")) {
			String original = toDraw.substring(toDraw.indexOf("Updated"),toDraw.indexOf("into"));
			String newState = toDraw.substring(toDraw.indexOf("into"));
			Donut d;
			int x = Integer.parseInt(original.substring((original.indexOf("("))+1 ,original.indexOf(",")));
			int y = Integer.parseInt(original.substring((original.indexOf(","))+1,original.indexOf(")")));
			Point center = new Point(x,y);
			String radius = original.substring(original.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = original.substring(original.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = original.substring(original.indexOf("Border"),original.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = original.substring(original.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
				int x1 = Integer.parseInt(newState.substring((newState.indexOf("("))+1 ,newState.indexOf(",")));
				int y1 = Integer.parseInt(newState.substring((newState.indexOf(","))+1,newState.indexOf(")")));
				Point center1 = new Point(x1,y1);
				String radius1 = newState.substring(newState.indexOf("Radius"));
				int r1 = Integer.parseInt(radius1.substring((radius1.indexOf("=")+2),(radius1.indexOf(";")-1)));
				String innerRadius1 = newState.substring(newState.indexOf("Inner radius"));
				int ir1 = Integer.parseInt(innerRadius1.substring((innerRadius1.indexOf("=")+2),(innerRadius1.indexOf(";")-1)));
				Donut d2 = new Donut(center1,r1,ir1);
				d2.setColorUnutrasnostMali(frame.getBackground());
				String border1 = newState.substring(newState.indexOf("Border"),newState.indexOf("Inner color"));
				if(!border1.contains("null")) {
					String rgb = border1.substring(border1.indexOf("j"),border1.indexOf("]"));
					String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
					int red = Integer.parseInt(rgbValues[0]);
					int  g = Integer.parseInt(rgbValues[1]);
					int   b = Integer.parseInt(rgbValues[2]);
					Color   borderColor = new Color(red,g,b);
					d2.setColor(borderColor);
					d2.setColorIviceMali(borderColor);
				}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
				String inner1 = newState.substring(newState.indexOf("Inner color"));
				 if(!inner1.contains("null")) {
					 String rgb2 = inner1.substring(inner1.indexOf("j"),inner1.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 d2.setColorUnutrasnjostVeliki(innerColor);
				 }else {d2.setColorUnutrasnjostVeliki(Color.black);}
				 for(int i=0 ; i<model.getShapes().size();i++) {
						if(model.get(i).equals(d2)) {
							command = new UpdateDonutCmd((Donut)model.get(i),d);
							command.execute();
							controller.getUndoStack().push(command);
							controller.getRedoStack().removeAllElements();
							frame.repaint();
							//System.out.println(model.get(i).hashCode());
						}
					}
		}
		else if(toDraw.contains("Hexagon")) {
			String original = toDraw.substring(toDraw.indexOf("Updated"),toDraw.indexOf("into"));
			String newState = toDraw.substring(toDraw.indexOf("into"));
			HexagonAdapter h;
			int x = Integer.parseInt(original.substring((original.indexOf("("))+1 ,original.indexOf(",")));
			int y = Integer.parseInt(original.substring((original.indexOf(","))+1,original.indexOf(")")));
			Point center = new Point(x,y);
			String radius = original.substring(original.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = original.substring(original.indexOf("Border"),original.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = original.substring(original.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
				int x1 = Integer.parseInt(newState.substring((newState.indexOf("("))+1 ,newState.indexOf(",")));
				int y1 = Integer.parseInt(newState.substring((newState.indexOf(","))+1,newState.indexOf(")")));
				Point center1 = new Point(x1,y1);
				String radius1 = newState.substring(newState.indexOf("Radius"));
				int r1 = Integer.parseInt(radius1.substring((radius1.indexOf("=")+2),(radius1.indexOf(";")-1)));
				HexagonAdapter h2 = new HexagonAdapter(center1,r1);
				String border1 = newState.substring(newState.indexOf("Border"),newState.indexOf("Inner color"));
				if(!border1.contains("null")) {
					String rgb = border1.substring(border1.indexOf("j"),border1.indexOf("]"));
					String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
					int red = Integer.parseInt(rgbValues[0]);
					int  g = Integer.parseInt(rgbValues[1]);
					int   b = Integer.parseInt(rgbValues[2]);
					Color   borderColor = new Color(red,g,b);
					h2.setColor(borderColor);
				}else {h2.setColor(Color.black);}
				String inner1 = newState.substring(newState.indexOf("Inner color"));
				 if(!inner1.contains("null")) {
					 String rgb2 = inner1.substring(inner1.indexOf("j"),inner1.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 h2.setInnerColor(innerColor);
				 }else{h2.setInnerColor(Color.black);}
				 for(int i=0 ; i<model.getShapes().size();i++) {
						if(model.get(i).equals(h2)) {
							command = new UpdateHexagonCmd((HexagonAdapter)model.get(i),h);
							command.execute();
							controller.getUndoStack().push(command);
							controller.getRedoStack().removeAllElements();
							frame.repaint();
						}
					}
		}
	
		observers();
		
	}

	private void removedUndo(String toDraw) {


		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
	        command = new RemoveShapeCmd(p,model,indeksi.pop());
	        command.unexecute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			//System.out.println(toDraw + '\n');
			//System.out.println(endPoint);
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			//System.out.println(x2);
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
	        command = new RemoveShapeCmd(l,model,indeksi.pop());
	        command.unexecute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        command = new RemoveShapeCmd(c,model,indeksi.pop());
	        command.unexecute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    command = new RemoveShapeCmd(r,model,indeksi.pop());
	        command.unexecute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 	command = new RemoveShapeCmd(d,model,indeksi.pop());
		        command.unexecute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 command = new RemoveShapeCmd(h,model,indeksi.pop());
		        command.unexecute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
	
		observers();
		
	}

	public void addedUndo(String toDraw) {

		if(toDraw.contains("Point")) {
			Point p;
			Color color;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			p = new Point(x,y);
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        color = new Color(r,g,b);
		        p.setColor(color);
			} else p.setColor(Color.black);
	        command = new AddShapeCmd(p,model);
	        command.unexecute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Line")) {
			int x1 = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y1 = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1 ,toDraw.indexOf(")")));
			String endPoint = toDraw.substring(toDraw.indexOf(";"));
			//System.out.println(toDraw + '\n');
			//System.out.println(endPoint);
			int x2 = Integer.parseInt(endPoint.substring((endPoint.indexOf("("))+1 ,endPoint.indexOf(",")));
			//System.out.println(x2);
			int y2 = Integer.parseInt(endPoint.substring((endPoint.indexOf(","))+1 ,endPoint.indexOf(")")));
			Line l = new Line (new Point(x1,y1), new Point(x2,y2));
			if(!toDraw.contains("null")) {
				String rgb = toDraw.substring(toDraw.indexOf("j"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int r = Integer.parseInt(rgbValues[0]);
		        int g = Integer.parseInt(rgbValues[1]);
		        int b = Integer.parseInt(rgbValues[2]);
		        Color color = new Color(r,g,b);
		        l.setColor(color);
			} else l.setColor(Color.black);
	        command = new AddShapeCmd(l,model);
	        command.unexecute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();
			
		}
		else if(toDraw.contains("Circle")) {
			Circle c;
			Color borderColor,innerColor;
			int b,red,g,red2,g2,b2;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("="))+3,(toDraw.indexOf(","))));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring(radius.indexOf("=")+2,radius.indexOf("=")+4)) ;
			c = new Circle(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				red = Integer.parseInt(rgbValues[0]);
		        g = Integer.parseInt(rgbValues[1]);
		        b = Integer.parseInt(rgbValues[2]);
		        borderColor = new Color(red,g,b);
		        c.setColor(borderColor);
			}else {borderColor = Color.black;c.setColor(borderColor);}
			
			
	        String inner = toDraw.substring(toDraw.indexOf("Inner"));
	        if(!inner.contains("null")) {
	        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 red2 = Integer.parseInt(rgbValues2[0]);
			     g2 = Integer.parseInt(rgbValues2[1]);
			     b2 = Integer.parseInt(rgbValues2[2]);
			     innerColor = new Color(red2,g2,b2);
			     c.setColorUnutrasnjost(innerColor);
	        } else {innerColor = Color.black; c.setColorUnutrasnjost(innerColor);}
	        command = new AddShapeCmd(c,model);
	        command.unexecute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();		
		}
		else if(toDraw.contains("Rectangle")) {
			Rectangle r;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point upper = new Point(x,y);
			String h = toDraw.substring(toDraw.indexOf("Height"));
			int height = Integer.parseInt(h.substring((h.indexOf("=")+2),(h.indexOf(";")-1)));
			String w = toDraw.substring(toDraw.indexOf("Width"));
			int width = Integer.parseInt(w.substring((w.indexOf("="))+1,(w.indexOf(";")-1)));
			r = new Rectangle(upper,height,width);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				r.setColor(borderColor);
			} else r.setColor(Color.black);
			 String inner = toDraw.substring(toDraw.indexOf("Inner"));
		    if(!inner.contains("null")) {
		        	 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
		        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
		        	 int red2 = Integer.parseInt(rgbValues2[0]);
		        	 int  g2 = Integer.parseInt(rgbValues2[1]);
		        	 int  b2 = Integer.parseInt(rgbValues2[2]);
		        	 Color innerColor = new Color(red2,g2,b2);
		        	 r.setColorUnutrasnjost(innerColor);
		    }else r.setColorUnutrasnjost(Color.black);
		    command = new AddShapeCmd(r,model);
	        command.unexecute();
	        controller.getUndoStack().push(command);
			controller.getRedoStack().removeAllElements();
			frame.repaint();	
		}
		else if(toDraw.contains("Donut")) {
			Donut d;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			String innerRadius = toDraw.substring(toDraw.indexOf("Inner radius"));
			int ir = Integer.parseInt(innerRadius.substring((innerRadius.indexOf("=")+2),(innerRadius.indexOf(";")-1)));
			d = new Donut(center,r,ir);
			d.setColorUnutrasnostMali(frame.getBackground());
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				d.setColor(borderColor);
				d.setColorIviceMali(borderColor);
			}else {d.setColor(Color.black); d.setColorIviceMali(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 d.setColorUnutrasnjostVeliki(innerColor);
			 } else {d.setColorUnutrasnjostVeliki(Color.black);}
			 	command = new AddShapeCmd(d,model);
		        command.unexecute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		else if(toDraw.contains("Hexagon")) {
			HexagonAdapter h;
			int x = Integer.parseInt(toDraw.substring((toDraw.indexOf("("))+1 ,toDraw.indexOf(",")));
			int y = Integer.parseInt(toDraw.substring((toDraw.indexOf(","))+1,toDraw.indexOf(")")));
			Point center = new Point(x,y);
			String radius = toDraw.substring(toDraw.indexOf("Radius"));
			int r = Integer.parseInt(radius.substring((radius.indexOf("=")+2),(radius.indexOf(";")-1)));
			h = new HexagonAdapter(center,r);
			String border = toDraw.substring(toDraw.indexOf("Border"),toDraw.indexOf("Inner color"));
			if(!border.contains("null")) {
				String rgb = border.substring(border.indexOf("j"),border.indexOf("]"));
				String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");
				int red = Integer.parseInt(rgbValues[0]);
				int  g = Integer.parseInt(rgbValues[1]);
				int   b = Integer.parseInt(rgbValues[2]);
				Color   borderColor = new Color(red,g,b);
				h.setColor(borderColor);
			}else {h.setColor(Color.black);}
			String inner = toDraw.substring(toDraw.indexOf("Inner color"));
			 if(!inner.contains("null")) {
				 String rgb2 = inner.substring(inner.indexOf("j"),inner.indexOf("]"));
	        	 String[] rgbValues2 = rgb2.replaceAll("[^0-9,]", "").split(",");
	        	 int red2 = Integer.parseInt(rgbValues2[0]);
	        	 int  g2 = Integer.parseInt(rgbValues2[1]);
	        	 int  b2 = Integer.parseInt(rgbValues2[2]);
	        	 Color innerColor = new Color(red2,g2,b2);
	        	 h.setInnerColor(innerColor);
			 }else {h.setInnerColor(Color.black);}
			 command = new AddShapeCmd(h,model);
		        command.unexecute();
		        controller.getUndoStack().push(command);
				controller.getRedoStack().removeAllElements();
				frame.repaint();
		}
		observers();
	
	}
	public void observers() {
		if(controller.getSelectedList().size()==0) {
			controller.getObserverManager().delete();
		}
		if(model.getShapes().size()==0) {
			if(controller.getRedoStack().size()==0) {
				controller.getObserverManager().delete();
			}else {
				controller.getObserverManager().undoIsNotEmpty();
			}
		}else if(model.getShapes().size()==1) {
			if(controller.getRedoStack().size()==0) {
				controller.getObserverManager().undoIsNotEmpty();
			}else {
				controller.getObserverManager().undoIsNotEmpty();
				controller.getObserverManager().undoClicked();
			}
		}
		if(controller.getRedoStack().size()!=0) {
			controller.getObserverManager().undoClicked();
		}else {
			controller.getObserverManager().redoIsEmpty();
		}
		if(model.getShapes().size()!=0) {
			if(model.getShapes().size()==1 && controller.getSelectedList().size()==1) {
				controller.getObserverManager().delmod();
			}else if(controller.getSelectedList().size()==1) {
				if(model.getShapes().get(0).isSelected()) {
					controller.getObserverManager().lowestSelected();
					controller.getObserverManager().firstSelected();
				}else if(model.getShapes().get(model.getShapes().size()-1).isSelected()) {
					controller.getObserverManager().highestSelected();
					controller.getObserverManager().lastSelected();
				}else {
					controller.getObserverManager().inBeetween();
				}
				
			}else if(controller.getSelectedList().size()>1) {
				controller.getObserverManager().notEmpty();
				controller.getObserverManager().select();
			}
		}
		if(controller.getSelectedList().size()!=0) {
			controller.getObserverManager().notEmpty();
			if(controller.getSelectedList().size()>1) {
				controller.getObserverManager().select();
			}
		}
		if(frame.getTglbtnBringToBack().isSelected()) {
			controller.getObserverManager().movedToBottom();
			controller.getObserverManager().firstSelected();
		}
		if(frame.getTglbtnBringToFront().isSelected()) {
			controller.getObserverManager().movedOnTop();
			controller.getObserverManager().lastSelected();
		}
		if(frame.getTglbtnToBack().isSelected()) {
			controller.getObserverManager().movedDown();
		}
		if(frame.getTglbtnToFront().isSelected()) {
			controller.getObserverManager().movedUp();
		}
		if(controller.getUndoStack().isEmpty()) {
			controller.getObserverManager().undoIsEmpty();
		}
		if(controller.getRedoStack().isEmpty()) {
			controller.getObserverManager().redoIsEmpty();
		}
		
		
		
		
	}
	
	
}
