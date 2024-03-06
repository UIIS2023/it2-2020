package command;

import geometry.Rectangle;

public class UpdateRectangleCommand implements Command{
	
	private Rectangle oldRectangle;
	private Rectangle newRectangle;
	private Rectangle intialRectangle;
	
	public UpdateRectangleCommand(Rectangle oldRectangle, Rectangle newRectangle) {
		this.oldRectangle = oldRectangle;
		this.newRectangle = newRectangle;
	}
	
	@Override
	public void execute() {
		this.intialRectangle = oldRectangle.clone();
		
		oldRectangle.setUpperLeftPoint(newRectangle.getUpperLeftPoint());
		oldRectangle.setHeight(newRectangle.getHeight());
		oldRectangle.setWidth(newRectangle.getWidth());
		oldRectangle.setSelected(newRectangle.isSelected());
		oldRectangle.setColor(newRectangle.getColor());
	}
	@Override
	public void unexecute() {
		oldRectangle.setUpperLeftPoint(intialRectangle.getUpperLeftPoint());
		oldRectangle.setHeight(intialRectangle.getHeight());
		oldRectangle.setWidth(intialRectangle.getWidth());
		oldRectangle.setSelected(intialRectangle.isSelected());
		oldRectangle.setColor(intialRectangle.getColor());
		
	}
	@Override
	public String toString() {
		return "UpdateRectangle@" + intialRectangle.toString() + "#" + newRectangle.toString();
	}

}
