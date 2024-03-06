package command;

import java.util.ArrayList;
import java.util.List;

import geometry.Shape;
import mvc.DrawingModel;

public class DeleteCommand implements Command {

	private List<Shape> shapes;
	private DrawingModel model;
	private List<Integer> positions = new ArrayList<Integer>();
	
	public DeleteCommand(List<Shape> shapes, DrawingModel model) {
		this.shapes = shapes;
		this.model = model;
	}
	
	
	@Override
	public void execute() {
		positions = new ArrayList<Integer>();
		for (Shape shape : shapes) {
			int index = this.model.getShapes().indexOf(shape);
			positions.add(index);
		}
		
		for (Shape shape : shapes) {
			this.model.remove(shape);
		}
		
	}

	@Override
	public void unexecute() {
		for (int i = 0; i < positions.size(); i++) {
			Shape currentShape = shapes.get(i);
			int currentIndex = positions.get(i);
			this.model.getShapes().add(currentIndex, currentShape);
		}
		
	}
	
	@Override
	public String toString() {
		String log = "Delete@";
		
		for (Shape shape : shapes) {
			log += shape.toString();
			log += "#";
		}
		
		return log;
	}
	

}
