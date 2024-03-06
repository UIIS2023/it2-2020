package command;

import geometry.Point;

public class UpdatePointCommand implements Command {
	
	private Point oldPoint;
	private Point newPoint;
	private Point intialPoint;
	
	public UpdatePointCommand(Point oldPoint, Point newPoint) {
		this.oldPoint = oldPoint;
		this.newPoint = newPoint;
	}
	
	@Override
	public void execute() {
		this.intialPoint = oldPoint.clone();
		
		oldPoint.setX(newPoint.getX());
		oldPoint.setY(newPoint.getY());
		oldPoint.setSelected(newPoint.isSelected());
		oldPoint.setColor(newPoint.getColor());
	}
	@Override
	public void unexecute() {
		oldPoint.setX(intialPoint.getX());
		oldPoint.setY(intialPoint.getY());
		oldPoint.setSelected(intialPoint.isSelected());
		oldPoint.setColor(intialPoint.getColor());
		
	}
	
	@Override
	public String toString() {
		return "UpdatePoint@" + intialPoint.toString() + "#" + newPoint.toString();
	}
	
}
