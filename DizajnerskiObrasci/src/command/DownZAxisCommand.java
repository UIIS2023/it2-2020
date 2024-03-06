package command;

import geometry.Shape;
import mvc.DrawingModel;

public class DownZAxisCommand implements Command {
	
	private Shape shape;
	private int index;
	private DrawingModel model;

	public DownZAxisCommand(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}
	
	@Override
	public void execute() {
		index = model.getShapes().indexOf(shape);
		Shape helperShape = model.getShapes().get(index - 1);
		model.getShapes().set(index - 1, shape);
		model.getShapes().set(index, helperShape);
	}
	
	@Override
	public void unexecute() {
		Shape helperShape = model.getShapes().get(index);
		model.getShapes().set(index, shape);
		model.getShapes().set(index - 1, helperShape);
	}
	
	@Override
	public String toString() {
		return "Down@" + shape.toString();
	}
}
