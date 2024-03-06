package drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.MouseAdapter;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import geometry.Point;
import geometry.Line;
import geometry.Rectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Shape;

public class PnlDrawing extends JPanel {

	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	Shape temp;
	Point startPoint;
	Point upperLeftPoint;
	private Color innerColorCircle;
	private Color innerColorRectangle;
	private Color borderColorRectangle;
	private Color borderColorCircle;
	private Color innerColorDonut;
	private Color borderColorDonut;
	private Color pointColor;
	private Color lineColor;
	private FrmDrawing frmDrawing;
	
	public PnlDrawing(FrmDrawing frmDrawing) 
	{
		this.frmDrawing = frmDrawing; //ovime povezujemo framework i panel
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		setLayout(gridBagLayout);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				onMouseClicked(me);
			}
		});
	}

	private void onMouseClicked(MouseEvent me) 
	{
		if (frmDrawing.getState() == 1) 
		{
			pointDraw(me);
		} 
		else if (frmDrawing.getState() == 2) 
		{
			lineDraw(me);
		} 
		else if (frmDrawing.getState() == 3) 
		{
			rectangleDraw(me);
		}
		else if (frmDrawing.getState() == 4) 
		{
			circleDraw(me);
		} 
		else if (frmDrawing.getState() == 5) 
		{
			donutDraw(me);
		} 
		else if (frmDrawing.getState() == 6) 
		{
			selectShape(me);
		}
		else 
		{
			JOptionPane.showMessageDialog(null, "You have to pick shape first!");	
		}
		repaint();
	}

	public void pointDraw(MouseEvent me) 
	{
		temp = new Point(me.getX(), me.getY(), false, Color.PINK);
		shapes.add(temp);
		frmDrawing.getDlm().addElement(temp);
	}

	public void lineDraw(MouseEvent me) 
	{
		if (startPoint == null) 
		{
			startPoint = new Point(me.getX(), me.getY());
		} 
		else
		{
			temp = new Line(startPoint, new Point(me.getX(), me.getY()),false, Color.PINK);
			shapes.add(temp);
			startPoint = null;
			frmDrawing.getDlm().addElement(temp);
		}
	}
	
	public void rectangleDraw(MouseEvent me) 
	{
		upperLeftPoint = new Point(me.getX(), me.getY());
		DlgRectangle dlgRectangle = new DlgRectangle();
		dlgRectangle.getTxtX().setText(Integer.toString(upperLeftPoint.getX()));
		dlgRectangle.getTxtY().setText(Integer.toString(upperLeftPoint.getY()));
		dlgRectangle.setVisible(true);
		
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
				temp = new Rectangle(new Point(x,y), width, height, false, borderColorRectangle, innerColorRectangle);
				shapes.add(temp);
				frmDrawing.getDlm().addElement(temp);
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
		dlgCircle.setVisible(true);

		if (dlgCircle.isConfirmation()) 
		{
			int x = Integer.parseInt(dlgCircle.getTxtX().getText());
			int y = Integer.parseInt(dlgCircle.getTxtY().getText());
			int r = Integer.parseInt(dlgCircle.getTxtRadius().getText());

			innerColorCircle = dlgCircle.getInnerFill();
			borderColorCircle = dlgCircle.getBorderFill();
			
			if (r > 0) 
			{		
				temp = new Circle(new Point(x,y), r, false, borderColorCircle, innerColorCircle);
				shapes.add(temp);
				frmDrawing.getDlm().addElement(temp);
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
		dlgDonut.setVisible(true);
		
		if (dlgDonut.isConfirmation()) 
		{
			int x = Integer.parseInt(dlgDonut.getTxtX().getText());
			int y = Integer.parseInt(dlgDonut.getTxtY().getText());
			int innerRadius = Integer.parseInt(dlgDonut.getTxtInnerRadius().getText());
			int radius = Integer.parseInt(dlgDonut.getTxtRadius().getText());
			
			innerColorDonut = dlgDonut.getInnerFill();
			borderColorDonut = dlgDonut.getBorderFill();
			
			if (innerRadius < radius) 
			{
				temp = new Donut(new Point(x,y), radius, innerRadius, false, borderColorDonut, innerColorDonut);
				shapes.add(temp);
				frmDrawing.getDlm().addElement(temp);
			} 
			else 
			{
				JOptionPane.showMessageDialog(null, "The radius must be greather than inner radius or inner radius must be greather than zero!");
			}
		}
	}

//___________________________ Select _________________________________
	
	public void selectShape(MouseEvent me) 
	{
		Shape selected = null;
		Iterator<Shape> it = shapes.iterator();
		while (it.hasNext()) 
		{
			Shape shape = it.next();
			shape.setSelected(false);
			if (shape.contains(me.getX(), me.getY())) 
			{
				selected = shape;
			}
		}
		if (selected != null) 
		{
			selected.setSelected(true);
		}
		repaint();
	}

//___________________________ Modify __________________________________
	
	public void modifyShape() {
		for (int i = 0; i < shapes.size(); i++) 
		{
			if (shapes.get(i) instanceof Point) 
			{
				if (shapes.get(i).isSelected()) 
				{
					DlgPoint dlgPoint = new DlgPoint();
					Point point = (Point) shapes.get(i);
					dlgPoint.getTxtX().setText(Integer.toString(point.getX()));
					dlgPoint.getTxtY().setText(Integer.toString(point.getY()));
					dlgPoint.fill = pointColor;
					dlgPoint.setVisible(true);
					
					if (dlgPoint.isConfirmation()) 
					{
						int x = Integer.parseInt(dlgPoint.getTxtX().getText());
						int y = Integer.parseInt(dlgPoint.getTxtY().getText());
						if (dlgPoint.isColorConfirmation())
						{
							pointColor = dlgPoint.getFill();
						}
						else
						{
							pointColor = dlgPoint.fill;
						}
						temp = new Point(x, y, false, pointColor);
						shapes.add(temp);
						shapes.remove(shapes.get(i));
						frmDrawing.getDlm().remove(i);
						frmDrawing.getDlm().addElement(temp);
						repaint();
					}
				}
			}
			else if (shapes.get(i) instanceof Line) 
			{
				if (shapes.get(i).isSelected()) 
				{
					DlgLine dlgLine = new DlgLine();
					Line line = (Line) shapes.get(i);
					dlgLine.getTxtStartPointX().setText(Integer.toString(line.getStartPoint().getX()));
					dlgLine.getTxtStartPointY().setText(Integer.toString(line.getStartPoint().getY()));
					dlgLine.getTxtEndPointX().setText(Integer.toString(line.getEndPoint().getX()));
					dlgLine.getTxtEndPointY().setText(Integer.toString(line.getEndPoint().getY()));
					dlgLine.fill = lineColor;
					dlgLine.setVisible(true);
					
					if (dlgLine.isConfirmation()) 
					{
						int startX = Integer.parseInt(dlgLine.getTxtStartPointX().getText());
						int startY = Integer.parseInt(dlgLine.getTxtStartPointY().getText());
						int endX = Integer.parseInt(dlgLine.getTxtEndPointX().getText());
						int endY = Integer.parseInt(dlgLine.getTxtEndPointY().getText());
						if (dlgLine.isColorConfirmation()) 
						{
							lineColor = dlgLine.getFill();
						}
						else 
						{
							lineColor = dlgLine.fill;
						}
						temp = new Line(new Point(startX, startY), new Point(endX,endY), false, lineColor);
						shapes.add(temp);
						shapes.remove(shapes.get(i));
						frmDrawing.getDlm().remove(i);
						frmDrawing.getDlm().addElement(temp);
						repaint();
					}
				}
			}
			else if (shapes.get(i) instanceof Rectangle) 
			{
				if (shapes.get(i).isSelected()) 
				{
					Rectangle r = (Rectangle) shapes.get(i);
					DlgRectangle dlgRectangle = new DlgRectangle();
					dlgRectangle.getTxtX().setText(Integer.toString(r.getUpperLeftPoint().getX()));
					dlgRectangle.getTxtY().setText(Integer.toString(r.getUpperLeftPoint().getY()));
					dlgRectangle.getTxtHeight().setText(Integer.toString(r.getHeight()));
					dlgRectangle.getTxtWidth().setText(Integer.toString(r.getWidth()));
					dlgRectangle.innerFill = innerColorRectangle;
					dlgRectangle.borderFill = borderColorRectangle;
					dlgRectangle.setVisible(true);
					
					if (dlgRectangle.isConfirmation()) 
					{
						int x = Integer.parseInt(dlgRectangle.getTxtX().getText());
						int y = Integer.parseInt(dlgRectangle.getTxtY().getText());
						int width = Integer.parseInt(dlgRectangle.getTxtWidth().getText());
						int height = Integer.parseInt(dlgRectangle.getTxtHeight().getText());
						
						innerColorRectangle = dlgRectangle.getInnerFill();
						borderColorRectangle = dlgRectangle.getBorderFill();
						
						if (width > 0 && height > 0) 
						{
							temp = new Rectangle(new Point(x,y), width, height, false, borderColorRectangle, innerColorRectangle);
							shapes.add(temp);
							shapes.remove(shapes.get(i));
							frmDrawing.getDlm().removeElementAt(i);
							frmDrawing.getDlm().addElement(temp);
						} 
						else 
						{
							JOptionPane.showMessageDialog(null, "The width and height must be greather than zero!");
						}
						repaint();
					}
				}
			}
			else if (shapes.get(i) instanceof Donut) 
			{
				if (shapes.get(i).isSelected()) 
				{
					Donut donut = (Donut) shapes.get(i);
					DlgDonut dlgDonut = new DlgDonut();
					dlgDonut.getTxtX().setText(Integer.toString(donut.getCenter().getX()));
					dlgDonut.getTxtY().setText(Integer.toString(donut.getCenter().getY()));
					dlgDonut.getTxtInnerRadius().setText(Integer.toString(donut.getInnerRadius()));
					dlgDonut.getTxtRadius().setText(Integer.toString(donut.getRadius()));
					dlgDonut.innerFill = innerColorDonut;
					dlgDonut.borderFill = borderColorDonut;
					dlgDonut.setVisible(true);

					if (dlgDonut.isConfirmation()) 
					{
						int x = Integer.parseInt(dlgDonut.getTxtX().getText());
						int y = Integer.parseInt(dlgDonut.getTxtY().getText());
						int r = Integer.parseInt(dlgDonut.getTxtRadius().getText());
						int innerR = Integer.parseInt(dlgDonut.getTxtInnerRadius().getText());
						
						innerColorDonut = dlgDonut.getInnerFill();
						borderColorDonut = dlgDonut.getBorderFill();
						
						if (innerR < r) 
						{
							temp = new Donut(new Point(x,y), r, innerR, false, borderColorDonut, innerColorDonut);
							shapes.add(temp);
							shapes.remove(shapes.get(i));
							frmDrawing.getDlm().remove(i);
							frmDrawing.getDlm().addElement(temp);
						} 
						else 
						{
							JOptionPane.showMessageDialog(null, "The radius mast be greather than inner radius or inner radius mast be greather than zero!");
						}
						repaint();
					}
				}
			}
			else if (shapes.get(i) instanceof Circle) 
			{
				if (shapes.get(i).isSelected()) 
				{
					Circle c = (Circle) shapes.get(i);
					DlgCircle dlgCircle = new DlgCircle();
					dlgCircle.getTxtX().setText(Integer.toString(c.getCenter().getX()));
					dlgCircle.getTxtY().setText(Integer.toString(c.getCenter().getY()));
					dlgCircle.getTxtRadius().setText(Integer.toString(c.getRadius()));
					dlgCircle.innerFill = innerColorCircle;
					dlgCircle.borderFill = borderColorCircle;
					dlgCircle.setVisible(true);
					
					if (dlgCircle.isConfirmation()) 
					{
						int x = Integer.parseInt(dlgCircle.getTxtX().getText());
						int y = Integer.parseInt(dlgCircle.getTxtY().getText());
						int r = Integer.parseInt(dlgCircle.getTxtRadius().getText());
						
						innerColorCircle = dlgCircle.getInnerFill();
						borderColorCircle = dlgCircle.getBorderFill();
						
						if (r > 0) 
						{
							temp = new Circle(new Point(x,y), r, false, borderColorCircle, innerColorCircle);
							shapes.add(temp);
							shapes.remove(shapes.get(i));
							frmDrawing.getDlm().remove(i);
							frmDrawing.getDlm().addElement(temp);
						} 
						else 
						{
							JOptionPane.showMessageDialog(null, "The radius must be greater than zero!");
						}
						repaint();
					}
				}
			}
		}
	}
	
//___________________________ Delete __________________________________

	public void delete() 
	{
		for (int i = 0; i < shapes.size(); i++) 
		{
			if (shapes.get(i).isSelected()) 
			{
				DlgDelete dlgDelete = new DlgDelete();
				dlgDelete.setVisible(true);
				if (dlgDelete.isConfirmation()) 
				{
					shapes.remove(shapes.get(i));
					frmDrawing.getDlm().removeElementAt(i);
					repaint();
				}
			}
		}
	}
	
//____________________________ Paint __________________________________

	public void paint(Graphics g) 
	{
		super.paint(g);
		Iterator<Shape> it = shapes.iterator();
		while (it.hasNext()) 
		{
			temp = it.next();
			temp.draw(g);
		}
	}
}

