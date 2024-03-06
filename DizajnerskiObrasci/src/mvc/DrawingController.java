package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import command.AddShapeCommand;
import command.BringToBackZAxisCommand;
import command.BringToFrontZAxisCommand;
import command.Command;
import command.DeleteCommand;
import command.DownZAxisCommand;
import command.SelectCommand;
import command.UnselectCommand;
import command.UpZAxisCommand;
import command.UpdateCircleCommand;
import command.UpdateDonutCommand;
import command.UpdateHexagonAdapterCommand;
import command.UpdateLineCommand;
import command.UpdatePointCommand;
import command.UpdateRectangleCommand;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import geometry.Rectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import strategy.StrategySerialized;
import strategy.StrategyText;

public class DrawingController extends Observable {
	
	
	private DrawingFrame frame;
	private DrawingModel model;
	private Point startPoint = null;
	private Point upperLeftPoint = null;
	private List<Command> undoList = new ArrayList<Command>();
	private List<Command> redoList = new ArrayList<Command>();
	private List<String> loadedLogs = new ArrayList<String>();
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.frame = frame;
		this.model = model;
	}
	
	public void mouseClicked(MouseEvent me) {
			if (frame.getState() == 1) 
			{
				pointDraw(me);
			} 
			else if (frame.getState() == 2) 
			{
				lineDraw(me);
			} 
			else if (frame.getState() == 3) 
			{
				rectangleDraw(me);
			}
			else if (frame.getState() == 4) 
			{
				circleDraw(me);
			} 
			else if (frame.getState() == 5) 
			{
				donutDraw(me);
			}
			else if (frame.getState() == 6) 
			{
				drawHexagon(me);
			}
			else if (frame.getState() == 7) 
			{
				selectShape(me);
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "You have to pick shape first!");	
			}
	}
	
	//___________________________________Iscrtavanje oblika________________________________________
	
	public void pointDraw(MouseEvent me) 
	{
		Point temp = new Point(me.getX(), me.getY(), false, frame.getGlobalBorderColor());
		AddShapeCommand command = new AddShapeCommand(temp, model);
		command.execute();
		undoList.add(command);
		redoList.clear();
		frame.addLogToList(command.toString());
		frame.getView().repaint();
		informFrame();
	}

	public void lineDraw(MouseEvent me) 
	{
		if (startPoint == null) 
		{
			startPoint = new Point(me.getX(), me.getY());
		} 
		else
		{
			Shape temp = new Line(startPoint, new Point(me.getX(), me.getY()),false, frame.getGlobalBorderColor());
			AddShapeCommand command = new AddShapeCommand(temp, model);
			command.execute();
			undoList.add(command);
			redoList.clear();
			startPoint = null;
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		}
	}
	
	public void rectangleDraw(MouseEvent me) 
	{
		upperLeftPoint = new Point(me.getX(), me.getY());
		DlgRectangle dlgRectangle = new DlgRectangle();
		dlgRectangle.getTxtX().setText(Integer.toString(upperLeftPoint.getX()));
		dlgRectangle.getTxtY().setText(Integer.toString(upperLeftPoint.getY()));
		dlgRectangle.setBorderFill(frame.getGlobalBorderColor());
		dlgRectangle.setInnerFill(frame.getGlobalInnerColor());
		dlgRectangle.setVisible(true);
		
		Color innerColorRectangle;
		Color borderColorRectangle;
		
		if (dlgRectangle.isConfirmation()) 
		{
			int x = Integer.parseInt(dlgRectangle.getTxtX().getText());
			int y = Integer.parseInt(dlgRectangle.getTxtY().getText());
			int width = Integer.parseInt(dlgRectangle.getTxtWidth().getText().toString());
			int height = Integer.parseInt(dlgRectangle.getTxtHeight().getText().toString());
			
			
			innerColorRectangle = dlgRectangle.getInnerFill();			
			borderColorRectangle = dlgRectangle.getBorderFill();
			
			if (width > 0 && height > 0) 
			{
				Rectangle rectangle = new Rectangle(new Point(x,y), width, height, false, borderColorRectangle, innerColorRectangle);
				AddShapeCommand command = new AddShapeCommand(rectangle, model);
				command.execute();
				undoList.add(command);
				redoList.clear();
				frame.addLogToList(command.toString());
				frame.getView().repaint();
				informFrame();
			} 
			else 
			{
				JOptionPane.showMessageDialog(null, "The width and height must be greather than zero!");
			}
			
		}
	}

	public void circleDraw(MouseEvent me) 
	{
		Point center = new Point(me.getX(), me.getY());
		DlgCircle dlgCircle = new DlgCircle();
		dlgCircle.getTxtX().setText(Integer.toString(center.getX()));
		dlgCircle.getTxtY().setText(Integer.toString(center.getY()));
		dlgCircle.setBorderFill(frame.getGlobalBorderColor());
		dlgCircle.setInnerFill(frame.getGlobalInnerColor());
		dlgCircle.setVisible(true);
		
		Color innerColorCircle;
		Color borderColorCircle;

		if (dlgCircle.isConfirmation()) 
		{
			int x = Integer.parseInt(dlgCircle.getTxtX().getText());
			int y = Integer.parseInt(dlgCircle.getTxtY().getText());
			int r = Integer.parseInt(dlgCircle.getTxtRadius().getText());

			innerColorCircle = dlgCircle.getInnerFill();
			borderColorCircle = dlgCircle.getBorderFill();
			
			System.out.println(innerColorCircle);
			System.out.println(borderColorCircle);
			
			if (r > 0) 
			{		
				Circle circle = new Circle(new Point(x,y), r, false, borderColorCircle, innerColorCircle);
				AddShapeCommand command = new AddShapeCommand(circle, model);
				command.execute();
				undoList.add(command);
				redoList.clear();
				frame.addLogToList(command.toString());
				frame.getView().repaint();
				informFrame();
			} 
			else 
			{
				JOptionPane.showMessageDialog(null, "The radius must be greater than zero!");
			}
		}
	}
	
	public void donutDraw(MouseEvent me) 
	{
		Point center = new Point(me.getX(), me.getY());
		DlgDonut dlgDonut = new DlgDonut();
		dlgDonut.getTxtX().setText(Integer.toString(center.getX()));
		dlgDonut.getTxtY().setText(Integer.toString(center.getY()));
		dlgDonut.setBorderFill(frame.getGlobalBorderColor());
		dlgDonut.setInnerFill(frame.getGlobalInnerColor());
		dlgDonut.setVisible(true);
		
		
		if (dlgDonut.isConfirmation()) 
		{
			int x = Integer.parseInt(dlgDonut.getTxtX().getText());
			int y = Integer.parseInt(dlgDonut.getTxtY().getText());
			int innerRadius = Integer.parseInt(dlgDonut.getTxtInnerRadius().getText());
			int radius = Integer.parseInt(dlgDonut.getTxtRadius().getText());
			
			Color innerColorDonut;
			Color borderColorDonut;
			
			innerColorDonut = dlgDonut.getInnerFill();
			borderColorDonut = dlgDonut.getBorderFill();
			
			if (innerRadius < radius) 
			{
				Donut donut = new Donut(new Point(x,y), radius, innerRadius, false, borderColorDonut, innerColorDonut);
				AddShapeCommand command = new AddShapeCommand(donut, model);
				command.execute();
				undoList.add(command);
				redoList.clear();
				frame.addLogToList(command.toString());
				frame.getView().repaint();
				informFrame();
			} 
			else 
			{
				JOptionPane.showMessageDialog(null, "The radius must be greather than inner radius or inner radius must be greather than zero!");
			}
		} 
	}
	
	public void drawHexagon(MouseEvent me) {
		Point center = new Point(me.getX(), me.getY());
		DlgHexagon dlgHexagon = new DlgHexagon();
		dlgHexagon.getTxtX().setText(Integer.toString(center.getX()));
		dlgHexagon.getTxtY().setText(Integer.toString(center.getY()));
		dlgHexagon.setBorderFill(frame.getGlobalBorderColor());
		dlgHexagon.setInnerFill(frame.getGlobalInnerColor());
		dlgHexagon.setVisible(true);
		
		
		Color innerColorHexagon;
		Color borderColorHexagon;

		if (dlgHexagon.isConfirmation()) 
		{
			int x = Integer.parseInt(dlgHexagon.getTxtX().getText());
			int y = Integer.parseInt(dlgHexagon.getTxtY().getText());
			int r = Integer.parseInt(dlgHexagon.getTxtRadius().getText());

			innerColorHexagon = dlgHexagon.getInnerFill();
			borderColorHexagon = dlgHexagon.getBorderFill();
			
			if (r > 0) 
			{		
				HexagonAdapter hexagon = new HexagonAdapter(x, y, r, borderColorHexagon, innerColorHexagon, false);
				AddShapeCommand command = new AddShapeCommand(hexagon, model);
				command.execute();
				undoList.add(command);
				redoList.clear();
				frame.addLogToList(command.toString());
				frame.getView().repaint();
				informFrame();
			} 
			else 
			{
				JOptionPane.showMessageDialog(null, "The radius must be greater than zero!");
			}
		}
	}
	
	//_________________________________________Komande___________________________________________________
	
	
	public void selectShape(MouseEvent me) {
		
		int numberOfShapes = this.model.getShapes().size();
		boolean anyShapeSelected = false;
		
		for (int i = numberOfShapes - 1; i >= 0; i--) {
			Shape currentShape = this.model.getShapes().get(i);
			if (currentShape.contains(me.getX(), me.getY())) {
				anyShapeSelected = true;
				
				if (currentShape.isSelected()) {
					List<Shape> shapes = new ArrayList<Shape>();
					shapes.add(currentShape);
					UnselectCommand command = new UnselectCommand(shapes);
					command.execute();
					undoList.add(command);
					redoList.clear();
					frame.addLogToList(command.toString());
				} else {
					SelectCommand command = new SelectCommand(currentShape);
					command.execute();
					undoList.add(command);
					redoList.clear();
					frame.addLogToList(command.toString());
				}
				break;
			}
		}
		
		if (anyShapeSelected == false) {
			List<Shape> listForUnselection = new ArrayList<Shape>();
			for (Shape shape : this.model.getShapes()) {
				if (shape.isSelected()) {
					listForUnselection.add(shape);
				}
			}
			
			if (listForUnselection.size() > 0) {
				UnselectCommand command = new UnselectCommand(listForUnselection);
				command.execute();
				undoList.add(command);
				redoList.clear();
				frame.addLogToList(command.toString());
			}
		}

		frame.getView().repaint();
		informFrame();
	}

	
	public void deleteShape() {
		
		List<Shape> shapesToBeDeleted = new ArrayList<Shape>();
		for (Shape shape : this.model.getShapes()) {
			if (shape.isSelected()) {
				shapesToBeDeleted.add(shape);
			}
		}
		
		DeleteCommand command = new DeleteCommand(shapesToBeDeleted, model);
		command.execute();
		undoList.add(command);
		redoList.clear();
		frame.addLogToList(command.toString());
		frame.getView().repaint();
		informFrame();
		
	}

	public void updateShape() {
		
		Shape selectedShape = null;
		
		for (Shape shape : this.model.getShapes()) {
			if (shape.isSelected()) {
				selectedShape = shape;
			}
		}
		
		if (selectedShape instanceof Point) {
			DlgPoint dlgPoint = new DlgPoint();
			Point point = (Point)selectedShape;
			dlgPoint.getTxtX().setText(Integer.toString(point.getX()));
			dlgPoint.getTxtY().setText(Integer.toString(point.getY()));
			dlgPoint.setFill(point.getColor());
			dlgPoint.setVisible(true);
			
			if (dlgPoint.isConfirmation()) 
			{
				int x = Integer.parseInt(dlgPoint.getTxtX().getText());
				int y = Integer.parseInt(dlgPoint.getTxtY().getText());
				
				Color pointColor;
				if (dlgPoint.isColorConfirmation())
				{
					pointColor = dlgPoint.getFill();
				}
				else
				{
					pointColor = point.getColor();
				}
				
				Point newPoint = new Point(x, y, point.isSelected(), pointColor);
				UpdatePointCommand command = new UpdatePointCommand(point, newPoint);
				command.execute();
				undoList.add(command);
				frame.addLogToList(command.toString());
				frame.getView().repaint();
				informFrame();
				
			}
		}else if (selectedShape instanceof Line) {
			DlgLine dlgLine = new DlgLine();
			Line line = (Line)selectedShape;
			dlgLine.getTxtStartPointX().setText(Integer.toString(line.getStartPoint().getX()));
			dlgLine.getTxtStartPointY().setText(Integer.toString(line.getStartPoint().getY()));
			dlgLine.getTxtEndPointX().setText(Integer.toString(line.getEndPoint().getX()));
			dlgLine.getTxtEndPointY().setText(Integer.toString(line.getEndPoint().getY()));
			dlgLine.setFill(line.getColor());
			dlgLine.setVisible(true);
			
			if(dlgLine.isConfirmation())
			{
				int startPointX = Integer.parseInt(dlgLine.getTxtStartPointX().getText());
				int startPointY = Integer.parseInt(dlgLine.getTxtStartPointY().getText());
				int endPointX = Integer.parseInt(dlgLine.getTxtEndPointX().getText());
				int endPointY = Integer.parseInt(dlgLine.getTxtEndPointY().getText());
				
				Color lineColor;
				if(dlgLine.isColorConfirmation())
				{
					lineColor = dlgLine.getFill();
				}
				else
				{
					lineColor = line.getColor();
				}
				
				Line newLine = new Line(new Point(startPointX, startPointY), new Point(endPointX, endPointY), line.isSelected(), lineColor);
				UpdateLineCommand command = new UpdateLineCommand(line, newLine);
				command.execute();
				undoList.add(command);
				frame.addLogToList(command.toString());
				frame.getView().repaint();
				informFrame();
			}
		}else if (selectedShape instanceof Circle) {
			DlgCircle dlgCircle = new DlgCircle();
			Circle circle = (Circle)selectedShape;
			dlgCircle.getTxtX().setText(Integer.toString(circle.getCenter().getX()));
			dlgCircle.getTxtY().setText(Integer.toString(circle.getCenter().getY()));
			dlgCircle.getTxtRadius().setText(Integer.toString(circle.getRadius()));
			dlgCircle.setBorderFill(circle.getColor());
			dlgCircle.setInnerFill(circle.getInnerColor());
			dlgCircle.setVisible(true);
			
			if(dlgCircle.isConfirmation())
			{
				int centerX = Integer.parseInt(dlgCircle.getTxtX().getText());
				int centerY = Integer.parseInt(dlgCircle.getTxtY().getText());
				int radius = Integer.parseInt(dlgCircle.getTxtRadius().getText());
				
				Color borderColor;
				Color innerColor;
				
				borderColor = dlgCircle.getBorderFill();
				innerColor = dlgCircle.getInnerFill();
				
				System.out.println(borderColor);
				System.out.println(innerColor);
				
				Circle newCircle = new Circle(new Point(centerX, centerY), radius, circle.isSelected(), borderColor, innerColor);
				UpdateCircleCommand command = new UpdateCircleCommand(circle, newCircle);
				command.execute();
				undoList.add(command);
				frame.addLogToList(command.toString());
				frame.getView().repaint();
				informFrame();
			}
		}else if (selectedShape instanceof Rectangle) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			Rectangle rectangle = (Rectangle)selectedShape;
			dlgRectangle.getTxtX().setText(Integer.toString(rectangle.getUpperLeftPoint().getX()));
			dlgRectangle.getTxtY().setText(Integer.toString(rectangle.getUpperLeftPoint().getY()));
			dlgRectangle.getTxtHeight().setText(Integer.toString(rectangle.getHeight()));
			dlgRectangle.getTxtWidth().setText(Integer.toString(rectangle.getWidth()));
			dlgRectangle.setBorderFill(rectangle.getColor());
			dlgRectangle.setInnerFill(rectangle.getInnerColor());
			dlgRectangle.setVisible(true);
			
			if(dlgRectangle.isConfirmation())
			{
				int upperLeftPointX = Integer.parseInt(dlgRectangle.getTxtX().getText());
				int upperLeftPointY = Integer.parseInt(dlgRectangle.getTxtY().getText());
				int height = Integer.parseInt(dlgRectangle.getTxtHeight().getText());
				int width = Integer.parseInt(dlgRectangle.getTxtWidth().getText());
				
				Color borderColor;
				Color innerColor;
				borderColor = dlgRectangle.getBorderFill();	
				innerColor = dlgRectangle.getInnerFill();				
				
				Rectangle newRectangle = new Rectangle(new Point(upperLeftPointX, upperLeftPointY), height, width, rectangle.isSelected(), borderColor, innerColor);
				UpdateRectangleCommand command = new UpdateRectangleCommand(rectangle, newRectangle);
				command.execute();
				undoList.add(command);
				frame.addLogToList(command.toString());
				frame.getView().repaint();
				informFrame();
			}
		}else if (selectedShape instanceof Donut) {
			DlgDonut dlgDonut = new DlgDonut();
			Donut donut = (Donut)selectedShape;
			dlgDonut.getTxtX().setText(Integer.toString(donut.getCenter().getX()));
			dlgDonut.getTxtY().setText(Integer.toString(donut.getCenter().getY()));
			dlgDonut.getTxtRadius().setText(Integer.toString(donut.getRadius()));
			dlgDonut.getTxtInnerRadius().setText(Integer.toString(donut.getInnerRadius()));
			dlgDonut.setBorderFill(donut.getColor());
			dlgDonut.setInnerFill(donut.getInnerColor());
			dlgDonut.setVisible(true);
			
			if(dlgDonut.isConfirmation())
			{
				int centerX = Integer.parseInt(dlgDonut.getTxtX().getText());
				int centerY = Integer.parseInt(dlgDonut.getTxtY().getText());
				int radius = Integer.parseInt(dlgDonut.getTxtRadius().getText());
				int innerRadius = Integer.parseInt(dlgDonut.getTxtInnerRadius().getText());
				
				Color borderColor;
				Color innerColor;
				
				borderColor = dlgDonut.getBorderFill();
				innerColor = dlgDonut.getInnerFill();
				
				Donut newDonut = new Donut(new Point(centerX, centerY), radius, innerRadius, donut.isSelected(), borderColor, innerColor);
				UpdateDonutCommand command = new UpdateDonutCommand(donut, newDonut);
				command.execute();
				undoList.add(command);
				frame.addLogToList(command.toString());
				frame.getView().repaint();
				informFrame();
			}
		} else if (selectedShape instanceof HexagonAdapter) {
				DlgHexagon dlgHexagon = new DlgHexagon();
				HexagonAdapter hexagonAdapter = (HexagonAdapter)selectedShape;
				
				dlgHexagon.getTxtX().setText(Integer.toString(hexagonAdapter.getCenter().getX()));
				dlgHexagon.getTxtY().setText(Integer.toString(hexagonAdapter.getCenter().getY()));
				dlgHexagon.getTxtRadius().setText(Integer.toString(hexagonAdapter.getRadius()));
				dlgHexagon.setBorderFill(hexagonAdapter.getColor());
				dlgHexagon.setInnerFill(hexagonAdapter.getInnerColor());
				dlgHexagon.setVisible(true);
				
				if(dlgHexagon.isConfirmation()) {
					int centerX = Integer.parseInt(dlgHexagon.getTxtX().getText());
					int centerY = Integer.parseInt(dlgHexagon.getTxtY().getText());
					int radius = Integer.parseInt(dlgHexagon.getTxtRadius().getText());
					
					Color borderColor;
					Color innerColor;
					
					borderColor = dlgHexagon.getBorderFill();
					innerColor = dlgHexagon.getInnerFill();
					
					HexagonAdapter newHexagonAdapter = new HexagonAdapter(centerX, centerY, radius, borderColor, innerColor, false);
					UpdateHexagonAdapterCommand command = new UpdateHexagonAdapterCommand(hexagonAdapter, newHexagonAdapter);
					command.execute();
					undoList.add(command);
					frame.addLogToList(command.toString());
					frame.getView().repaint();
					informFrame();
				}
			}
		
		redoList.clear();
	}
	
	public void undo() {
		Command command = undoList.get(undoList.size() - 1);
		command.unexecute();
		undoList.remove(undoList.size() - 1);
		redoList.add(command);
		frame.getView().repaint();
		informFrame();
		frame.addLogToList("UNDO");
	}
	
	public void redo() {
		Command command = redoList.get(redoList.size() - 1);
		command.execute();
		redoList.remove(redoList.size() - 1);
		undoList.add(command);
		frame.getView().repaint();
		informFrame();
		frame.addLogToList("REDO");
	}
	
	public void upZAxis() {
		Shape selectedShape = null;
		
		for (Shape shape : model.getShapes()) {
			if (shape.isSelected()) {
				selectedShape = shape;
				break;
			}
		}
		
		UpZAxisCommand command = new UpZAxisCommand(selectedShape, model);
		command.execute();
		undoList.add(command);
		redoList.clear();
		frame.addLogToList(command.toString());
		frame.getView().repaint();
		informFrame();
		
	}
	
	public void downZAxis() {
		Shape selectedShape = null;
		
		for (Shape shape : model.getShapes()) {
			if (shape.isSelected()) {
				selectedShape = shape;
				break;
			}
		}
		
		DownZAxisCommand command = new DownZAxisCommand(selectedShape, model);
		command.execute();
		undoList.add(command);
		redoList.clear();
		frame.addLogToList(command.toString());
		frame.getView().repaint();
		informFrame();
		
	}
	
	public void bringToBackZAxis() {
		Shape selectedShape = null;
		
		for (Shape shape : model.getShapes()) {
			if (shape.isSelected()) {
				selectedShape = shape;
				break;
			}
		}
		
		BringToBackZAxisCommand command = new BringToBackZAxisCommand(selectedShape, model);
		command.execute();
		undoList.add(command);
		redoList.clear();
		frame.addLogToList(command.toString());
		frame.getView().repaint();
		informFrame();
	}
	
	public void bringToFrontZAxis() {
		Shape selectedShape = null;
		
		for (Shape shape : model.getShapes()) {
			if (shape.isSelected()) {
				selectedShape = shape;
				break;
			}
		}
		
		BringToFrontZAxisCommand command = new BringToFrontZAxisCommand(selectedShape, model);
		command.execute();
		undoList.add(command);
		redoList.clear();
		frame.addLogToList(command.toString());
		frame.getView().repaint();
		informFrame();
	}
	
	public void saveText() {
		List<String> logs = frame.getLogs();
		StrategyText strategy = new StrategyText(logs);
		strategy.save();
	}
	

	public void saveSerialized() {
		StrategySerialized strategy = new StrategySerialized(model.getShapes());
		strategy.save();
	}
	
	public void readSerialized() {
		JFileChooser jFileChooser = new JFileChooser(new File("D:\\"));
		jFileChooser.setDialogTitle("Open file");
		
		int result = jFileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			String path = jFileChooser.getSelectedFile().getAbsolutePath();
			try {
				ObjectInputStream os = new ObjectInputStream(new FileInputStream(path));
				List<Shape> shapes = (List<Shape>)os.readObject();
				model.replaceList(shapes);
				frame.getView().repaint();
				undoList.clear();
				redoList.clear();
				frame.clearLogToList();
				informFrame();
				os.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"Not found", "Error", JOptionPane.WARNING_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "File not saved", "Error", JOptionPane.WARNING_MESSAGE);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "File not saved", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	
	private void informFrame() {
		this.setChanged();
		this.notifyObservers(model.getShapes());
		
		if (undoList.size() > 0) {
			frame.enableUndoButton(true);
		} else {
			frame.enableUndoButton(false);
		}
		
		if (redoList.size() > 0) {
			frame.enableRedoButton(true);
		} else {
			frame.enableRedoButton(false);
		}
		
		if (loadedLogs.size() > 0) {
			frame.enableNextLogButton(true);
		} else {
			frame.enableNextLogButton(false);
		}
		
		
	}

	public void readLog() {
		
		model.getShapes().clear();
		
		JFileChooser jFileChooser = new JFileChooser(new File("D:\\"));
		jFileChooser.setDialogTitle("Open file");
		
		int result = jFileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			String path = jFileChooser.getSelectedFile().getAbsolutePath();	//preuzimamo putanju
			try {
				BufferedReader buffer = new BufferedReader(new FileReader(path));
				String line;
				
				loadedLogs = new ArrayList<String>();
				while((line = buffer.readLine()) != null) {
					loadedLogs.add(line);
				}
				
				frame.getView().repaint();
				undoList.clear();
				redoList.clear();
				frame.clearLogToList();
				informFrame();
				
				buffer.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"Not found", "Error", JOptionPane.WARNING_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "File not saved", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public void parseNextLog() {
		String log = loadedLogs.get(0); 
		System.out.println(loadedLogs.size());
		loadedLogs.remove(0);
		
		if (log.equals("UNDO")) {
			Command command = undoList.get(undoList.size() - 1);
			undoList.remove(undoList.size() - 1);
			command.unexecute();
			redoList.add(command);
			frame.addLogToList("UNDO");
			frame.getView().repaint();
			informFrame();
			return;
		} else if (log.equals("REDO")) {
			Command command = redoList.get(redoList.size() - 1);
			redoList.remove(redoList.size() - 1);
			command.execute();
			undoList.add(command);
			frame.addLogToList("REDO");
			frame.getView().repaint();
			informFrame();
			return;
		}
		
		String[] logParts = log.split("@");
		String commandName = logParts[0];
		String remainingPart = logParts[1];
		
		if (commandName.equals("Add")) {
			Shape shape = findShape(remainingPart);
			
			Command command = new AddShapeCommand(shape, model);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
			
		} else if (commandName.equals("Select")) {
			Shape shape = findShape(remainingPart);
			System.out.println(remainingPart);
			Shape shapeInModel = findShapeInModelList(shape); 
			Command command = new SelectCommand(shapeInModel);
			
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
			
		} else if (commandName.equals("Unselect")) {
			List<Shape> selectedShapes = new ArrayList<Shape>();
			for (Shape shape : model.getShapes()) {
				if (shape.isSelected()) {
					selectedShapes.add(shape);
				}
			}
			
			Command command = new UnselectCommand(selectedShapes);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
			
		} else if (commandName.equals("Down")) {
			Shape shape = findShape(remainingPart);
			
			Shape shapeInModel = findShapeInModelList(shape);
			Command command = new DownZAxisCommand(shapeInModel, model);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		} else if (commandName.equals("Up")) {
			Shape shape = findShape(remainingPart);
			
			Shape shapeInModel = findShapeInModelList(shape);
			Command command = new UpZAxisCommand(shapeInModel, model);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		} else if (commandName.equals("BringToBack")) {
			Shape shape = findShape(remainingPart);
			
			Shape shapeInModel = findShapeInModelList(shape);
			Command command = new BringToBackZAxisCommand(shapeInModel, model);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		} else if (commandName.equals("BringToFront")) {
			Shape shape = findShape(remainingPart);
			
			Shape shapeInModel = findShapeInModelList(shape);
			Command command = new BringToFrontZAxisCommand(shapeInModel, model);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		} else if (commandName.equals("Delete")) {
			List<Shape> selectedShapes = new ArrayList<Shape>();
			for (Shape shape : model.getShapes()) {
				if (shape.isSelected()) {
					selectedShapes.add(shape);
				}
			}
			
			Command command = new DeleteCommand(selectedShapes, model);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		} else if (commandName.equals("UpdatePoint")) {
			String[] oldAndNewPoints = remainingPart.split("#");
			String oldPointString = oldAndNewPoints[0];
			String newPointString = oldAndNewPoints[1];
			
			Shape oldPoint = findShape(oldPointString);
			Shape oldPointInModel = findShapeInModelList(oldPoint);
			Shape newPoint = findShape(newPointString);
			
			Command command = new UpdatePointCommand((Point)oldPointInModel, (Point)newPoint);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		} else if (commandName.equals("UpdateLine")) {
			String[] oldAndNewLines = remainingPart.split("#");
			String oldLineString = oldAndNewLines[0];
			String newLineString = oldAndNewLines[1];
			
			Shape oldLine = findShape(oldLineString);
			Shape oldLineInModel = findShapeInModelList(oldLine);
			Shape newLine = findShape(newLineString);
			
			Command command = new UpdateLineCommand((Line)oldLineInModel, (Line)newLine);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		} else if (commandName.equals("UpdateRectangle")) {
			String[] oldAndNewRectangles = remainingPart.split("#");
			String oldRectangleString = oldAndNewRectangles[0];
			String newRectangleString = oldAndNewRectangles[1];
			
			Shape oldRectangle = findShape(oldRectangleString);
			Shape oldRectangleInModel = findShapeInModelList(oldRectangle);
			Shape newRectangle = findShape(newRectangleString);
			
			Command command = new UpdateRectangleCommand((Rectangle)oldRectangleInModel, (Rectangle)newRectangle);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		} else if (commandName.equals("UpdateCircle")) {
			String[] oldAndNewCircles = remainingPart.split("#");
			String oldCircleString = oldAndNewCircles[0];
			String newCircleString = oldAndNewCircles[1];
			
			Shape oldCircle = findShape(oldCircleString);
			Shape oldCircleInModel = findShapeInModelList(oldCircle);
			Shape newCircle = findShape(newCircleString);
			
			Command command = new UpdateCircleCommand((Circle)oldCircleInModel, (Circle)newCircle);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		} else if (commandName.equals("UpdateDonut")) {
			String[] oldAndNewDonuts = remainingPart.split("#");
			String oldDonutString = oldAndNewDonuts[0];
			String newDonutString = oldAndNewDonuts[1];
			
			Shape oldDonut = findShape(oldDonutString);
			Shape oldDonutInModel = findShapeInModelList(oldDonut);
			Shape newDonut = findShape(newDonutString);
			
			Command command = new UpdateDonutCommand((Donut)oldDonutInModel, (Donut)newDonut);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		} else if (commandName.equals("UpdateHexagonAdapter")) {
			String[] oldAndNewHexagonAdapters = remainingPart.split("#");
			String oldHexagonAdapterString = oldAndNewHexagonAdapters[0];
			String newHexagonAdapterString = oldAndNewHexagonAdapters[1];
			
			Shape oldHexagonAdapter = findShape(oldHexagonAdapterString);
			Shape oldHexagonAdapterInModel = findShapeInModelList(oldHexagonAdapter);
			Shape newHexagonAdapter = findShape(newHexagonAdapterString);
			
			Command command = new UpdateHexagonAdapterCommand((HexagonAdapter)oldHexagonAdapterInModel, (HexagonAdapter)newHexagonAdapter);
			command.execute();
			undoList.add(command);
			frame.addLogToList(command.toString());
			frame.getView().repaint();
			informFrame();
		}
		
	}
	
	private Shape findShape(String shapeString) {
		String[] remainingPartSplitted = shapeString.split("&");
		String shapeName = remainingPartSplitted[0];
		String shapePropertiesString = remainingPartSplitted[1];
		
		if (shapeName.equals("Point")) {
			String[] propertiesSplitted = shapePropertiesString.split(",");
			int x = Integer.parseInt(propertiesSplitted[0]);
			int y = Integer.parseInt(propertiesSplitted[1]);
			boolean selected = Boolean.parseBoolean(propertiesSplitted[2]);
			Color color = new Color(Integer.parseInt(propertiesSplitted[3]));
			
			return new Point(x, y, selected, color);
			
		} else if (shapeName.equals("Line")){
			String[] propertiesSplitted = shapePropertiesString.split(",");
		    int startPointX = Integer.parseInt(propertiesSplitted[0]);
		    int startPointY = Integer.parseInt(propertiesSplitted[1]);
		    int endPointX = Integer.parseInt(propertiesSplitted[2]);
		    int endPointY = Integer.parseInt(propertiesSplitted[3]);
			boolean selected = Boolean.parseBoolean(propertiesSplitted[4]);
			Color color = new Color(Integer.parseInt(propertiesSplitted[5]));
			
		    return new Line(new Point(startPointX, startPointY),
		    		new Point(endPointX, endPointY),
		    		selected,
		    		color);
			
		} else if (shapeName.equals("Rectangle")) {
			String[] propertiesSplitted = shapePropertiesString.split(",");
		    int upperLeftPointX = Integer.parseInt(propertiesSplitted[0]);
		    int upperLeftPointY = Integer.parseInt(propertiesSplitted[1]);
		    int width = Integer.parseInt(propertiesSplitted[2]);
		    int height = Integer.parseInt(propertiesSplitted[3]);
			boolean selected = Boolean.parseBoolean(propertiesSplitted[4]);
			Color color = new Color(Integer.parseInt(propertiesSplitted[5]));
			Color innerColor = new Color(Integer.parseInt(propertiesSplitted[6]));
			
			return new Rectangle(new Point(upperLeftPointX, upperLeftPointY),
					width, height, selected, color, innerColor);
			
		} else if (shapeName.equals("Circle")) {
			String[] propertiesSplitted = shapePropertiesString.split(",");
		    int centerX = Integer.parseInt(propertiesSplitted[0]);
		    int centerY = Integer.parseInt(propertiesSplitted[1]);
		    int radius = Integer.parseInt(propertiesSplitted[2]);
			boolean selected = Boolean.parseBoolean(propertiesSplitted[3]);
			Color color = new Color(Integer.parseInt(propertiesSplitted[4]));
			Color innerColor = new Color(Integer.parseInt(propertiesSplitted[5]));
			
			return new Circle (new Point(centerX, centerY), radius, selected, color, innerColor); 
			
		} else if (shapeName.equals("Donut")) {
			String[] propertiesSplitted = shapePropertiesString.split(",");
		    int centerX = Integer.parseInt(propertiesSplitted[0]);
		    int centerY = Integer.parseInt(propertiesSplitted[1]);
		    int radius = Integer.parseInt(propertiesSplitted[2]);
		    int innerRadius = Integer.parseInt(propertiesSplitted[3]);
			boolean selected = Boolean.parseBoolean(propertiesSplitted[4]);
			Color color = new Color(Integer.parseInt(propertiesSplitted[5]));
			Color innerColor = new Color(Integer.parseInt(propertiesSplitted[6]));
			
			return new Donut (new Point(centerX, centerY), radius, innerRadius, selected, color, innerColor);
			
		} else if (shapeName.equals("Hexagon")) {
			String[] propertiesSplitted = shapePropertiesString.split(",");
		    int x = Integer.parseInt(propertiesSplitted[0]);
		    int y = Integer.parseInt(propertiesSplitted[1]);
		    int radius = Integer.parseInt(propertiesSplitted[2]);
			boolean selected = Boolean.parseBoolean(propertiesSplitted[3]);
			Color color = new Color(Integer.parseInt(propertiesSplitted[4]));
			Color innerColor = new Color(Integer.parseInt(propertiesSplitted[5]));
			
			return new HexagonAdapter (x, y, radius, color, innerColor, selected); 
			
		}
		
		return null;
	}
	
	private Shape findShapeInModelList(Shape loadedShape) {
		for (Shape shape : model.getShapes()) {
			if (shape.equals(loadedShape)) {
				return shape;
			}
		}
		
		return null;
	}
	
}
