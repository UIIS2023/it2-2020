package command;

import geometry.Circle;

public class UpdateCircleCommand implements Command {

	private Circle oldCircle;
	private Circle newCircle;
	private Circle intialCircle;
	
	public UpdateCircleCommand(Circle oldCircle, Circle newCircle) {
		this.oldCircle = oldCircle;
		this.newCircle = newCircle;
	}
	
	@Override
	public void execute() {
		this.intialCircle = oldCircle.clone();
		
		oldCircle.setCenter(newCircle.getCenter());
		try {
			oldCircle.setRadius(newCircle.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		oldCircle.setSelected(newCircle.isSelected());
		oldCircle.setColor(newCircle.getColor());
		oldCircle.setInnerColor(newCircle.getInnerColor());
	}
	@Override
	public void unexecute() {
		oldCircle.setCenter(intialCircle.getCenter());
		try {
			oldCircle.setRadius(intialCircle.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		oldCircle.setSelected(intialCircle.isSelected());
		oldCircle.setColor(intialCircle.getColor());
		oldCircle.setInnerColor(intialCircle.getInnerColor());
	}
	@Override
	public String toString() {
		return "UpdateCircle@" + intialCircle.toString() + "#" + newCircle.toString();
	}
	
}
