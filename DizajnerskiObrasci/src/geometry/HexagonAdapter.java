package geometry;

import java.awt.Color;
import java.awt.Graphics;

import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {
	
	private Hexagon hexagon;
	private int x;
	private int y;
	private int radius;
	
	public HexagonAdapter(int x, int y, int radius) {
		this.hexagon = new Hexagon(x, y, radius);
	}
	
	public HexagonAdapter(int x, int y, int radius, Color borderColor, Color innerColor, boolean selected) {
		this(x, y, radius);
		this.hexagon.setBorderColor(borderColor);
		this.hexagon.setAreaColor(innerColor);
		this.hexagon.setSelected(selected);
	}
	
	public Point getCenter() {
		return new Point(this.hexagon.getX(), this.hexagon.getY());
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.hexagon.setX(this.hexagon.getX() + byX);
		this.hexagon.setY(this.hexagon.getY() + byY);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof HexagonAdapter) {
			HexagonAdapter toCompare = (HexagonAdapter)o;
			return toCompare.getRadius() - this.getRadius();
		}
		
		return 0;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter pomocna = (HexagonAdapter) obj;
			if (this.x == pomocna.x && this.y == pomocna.y && this.radius == pomocna.radius) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	

	@Override
	public boolean contains(Point p) {
		return this.hexagon.doesContain(this.hexagon.getX(), this.hexagon.getY());
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(int x, int y) {
		return this.hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		this.hexagon.paint(g);
	}
	
	@Override
	public void setSelected(boolean selected) {
		this.hexagon.setSelected(selected);
	}
	
	@Override
	public boolean isSelected() {
		return this.hexagon.isSelected();
	}
	
	@Override
	public void setColor(Color color) {
		this.hexagon.setBorderColor(color);
	}
	
	@Override
	public void setInnerColor(Color color) {
		this.hexagon.setAreaColor(color);
	}
	
	@Override
	public Color getColor() {
		return this.hexagon.getBorderColor();
	}
	
	@Override
	public Color getInnerColor() {
		return this.hexagon.getAreaColor();
	}
	
	public HexagonAdapter clone() {
		HexagonAdapter newHexagonAdapter = new HexagonAdapter(this.hexagon.getX(),
				this.hexagon.getY(), this.hexagon.getR(), this.hexagon.getBorderColor(),
				this.hexagon.getAreaColor(), this.hexagon.isSelected());
		
		return newHexagonAdapter;
		
	}
	
	@Override
	public String toString() {
		return "Hexagon&" + this.hexagon.getX() + "," +
				this.hexagon.getY() + "," + 
				this.hexagon.getR() + "," +
				this.hexagon.isSelected() + "," +
				this.hexagon.getBorderColor().getRGB() + "," +
				this.hexagon.getAreaColor().getRGB();
 	}
	
	public void setX(int x) {
		this.hexagon.setX(x);
	}
	
	public int getX() {
		return this.hexagon.getX();
	}
	
	public void setY(int y) {
		this.hexagon.setY(y);
	}
	
	public int getY() {
		return this.hexagon.getY();
	}
	
	public void setRadius(int radius) {
		this.hexagon.setR(radius);
	}
	
	public int getRadius() {
		return this.hexagon.getR();
	}

}
