package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends SurfaceShape {

	private Point upperLeftPoint;
	private int width;
	private int height;
	
	public Rectangle() {
		
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height) {
		this.upperLeftPoint = upperLeftPoint;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected) {
		this(upperLeftPoint, width, height);
		setSelected(selected);
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color color) {
		this(upperLeftPoint, width, height);
		this.setColor(color);
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color color, Color innerColor) {
		this(upperLeftPoint, width, height, selected, color);
		this.setInnerColor(innerColor);
	}
	
	public int compareTo(Object o) {
		if (o instanceof Rectangle) {
			return this.area() - ((Rectangle) o).area();
		}
		return 0;
	}
	
	public void moveBy(int byX, int byY) {
		this.upperLeftPoint.moveBy(byX, byY);
	}
	
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillRect(this.upperLeftPoint.getX() + 1 , this.upperLeftPoint.getY() + 1, this.width - 1, this.height - 1);
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawRect(this.upperLeftPoint.getX(), this.upperLeftPoint.getY(), this.width, this.height);
		this.fill(g);
		
		if (isSelected()) {
			g.setColor(Color.GREEN);
			g.drawRect(this.upperLeftPoint.getX() - 3 , this.upperLeftPoint.getY() - 3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX() - 3 , this.upperLeftPoint.getY() + this.height - 3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX() + this.width - 3 , this.upperLeftPoint.getY() - 3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX() + this.width - 3 , this.upperLeftPoint.getY() + this.height - 3, 6, 6);
		}
	}
	
	public boolean contains(int x, int y) {
		if (this.upperLeftPoint.getX() <= x && this.upperLeftPoint.getY() <= y && this.upperLeftPoint.getX() + this.width >= x && this.upperLeftPoint.getY() + this.height >= y) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean contains(Point p) {
		if (this.upperLeftPoint.getX() <= p.getX() && this.upperLeftPoint.getY() <= p.getY() && this.upperLeftPoint.getX() + this.width >= p.getX() && this.upperLeftPoint.getY() + this.height >= p.getY()) {
			return true;
		} else {
			return false;
		}
	}
	
	public int area() {
		return width * height;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle pomocna = (Rectangle) obj;
			if (this.upperLeftPoint.equals(pomocna.upperLeftPoint) && this.width == pomocna.width && this.height == pomocna.height) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}
	
	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public String toString() {
		return "Rectangle&" + upperLeftPoint.getX() + "," + upperLeftPoint.getY() + "," 
				+ width + "," + height + "," + isSelected() + "," + getColor().getRGB() + "," + 
				getInnerColor().getRGB();
 	}
	
	public Rectangle clone() {
		Rectangle newRectangle = new Rectangle();
		newRectangle.setUpperLeftPoint(this.getUpperLeftPoint());
		newRectangle.setHeight(this.getHeight());
		newRectangle.setWidth(this.getWidth());
		newRectangle.setSelected(isSelected());
		newRectangle.setColor(getColor());
		newRectangle.setInnerColor(getInnerColor());
		
		return newRectangle;
	}
	
}


