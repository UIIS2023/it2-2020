package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle { //Circle je parent klasa, a Donut child klasa
									
	private int innerRadius;
	
	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected); // pre je bilo this.setSelected(selected);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		this.setColor(color);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		this.setInnerColor(innerColor);
	}
	
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int)(this.area() - ((Donut) o).area());
		}
		return 0;
	}
	
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.WHITE);
		g.fillOval(this.getCenter().getX() - this.innerRadius, this.getCenter().getY() - this.innerRadius, this.innerRadius * 2,  this.innerRadius * 2);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		java.awt.Shape outer = new Ellipse2D.Double(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(), 2 * getRadius(), 2 * getRadius());
		java.awt.Shape inner = new Ellipse2D.Double(getCenter().getX() - getInnerRadius(), getCenter().getY() - getInnerRadius(), 2 * getInnerRadius(), 2 * getInnerRadius());
		
		if (isSelected()) {
			g.setColor(Color.GREEN);
			g.drawRect(super.getCenter().getX() - 3, super.getCenter().getY() - 3, 6, 6);
			g.drawRect(super.getCenter().getX() - super.getRadius() - 3, super.getCenter().getY() - 3, 6, 6);
			g.drawRect(super.getCenter().getX() + super.getRadius() - 3, super.getCenter().getY() - 3, 6, 6);
			g.drawRect(super.getCenter().getX() - 3, super.getCenter().getY() - super.getRadius() - 3, 6, 6);
			g.drawRect(super.getCenter().getX() - 3, super.getCenter().getY() + super.getRadius() - 3, 6, 6);
		}
		
		Area donut = new Area(outer);
		donut.subtract(new Area(inner));
		
		g2d.setColor(getInnerColor());
		g2d.fill(donut);
		
		g2d.setColor(getColor());
		g2d.draw(donut);
		
		g2d.dispose();
		
	}

	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut pomocna = (Donut) obj;
			if (this.innerRadius == pomocna.innerRadius && this.getRadius() == pomocna.getRadius() && this.getCenter().equals(pomocna.getCenter())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}
	
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}
	
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}
	
	public String toString() {
		return "Donut&" + super.getCenter().getX() + "," +
				super.getCenter().getY() + "," +
				super.getRadius() + "," +
				innerRadius + "," +
				isSelected() + "," +
				getColor().getRGB() + "," +
				getInnerColor().getRGB();
	}
	
	public Donut clone() {
		Donut newDonut = new Donut();
		newDonut.setCenter(this.getCenter());
		try {
			newDonut.setRadius(this.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		newDonut.setInnerRadius(this.getInnerRadius());
		newDonut.setSelected(isSelected());
		newDonut.setColor(getColor());
		newDonut.setInnerColor(getInnerColor());
		
		return newDonut;
	}
	
}
