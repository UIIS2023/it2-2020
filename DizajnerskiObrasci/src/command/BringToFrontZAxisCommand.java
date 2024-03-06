package command;

import geometry.Shape;
import mvc.DrawingModel;
public class BringToFrontZAxisCommand implements Command{

	private Shape shape;
	private DrawingModel model;
	private int index;
	
	public BringToFrontZAxisCommand(Shape shape, DrawingModel model) {
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		this.index = this.model.getShapes().indexOf(shape);
		this.model.remove(shape);
		this.model.getShapes().add(shape);
	}

	@Override
	public void unexecute() {
		this.model.remove(shape);
		this.model.getShapes().add(index, shape);
	}
	
	@Override
	public String toString() {
		return "BringToFront@" + shape.toString();
	}

}
