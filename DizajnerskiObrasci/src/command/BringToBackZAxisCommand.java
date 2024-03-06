package command;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBackZAxisCommand implements Command{

	private Shape shape;
	private DrawingModel model;
	private int index;
	
	public BringToBackZAxisCommand(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}
	
	@Override
	public void execute() {
		this.index = this.model.getShapes().indexOf(shape);
		this.model.getShapes().remove(shape);
		this.model.getShapes().add(0, shape);
	}

	@Override
	public void unexecute() {
		this.model.getShapes().remove(shape);
		this.model.getShapes().add(index, shape);
	}
	
	@Override
	public String toString() {
		return "BringToBack@" + shape.toString();
	}
	
}
