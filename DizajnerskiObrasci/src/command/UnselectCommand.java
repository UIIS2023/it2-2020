package command;

import java.util.List;

import geometry.Shape;

public class UnselectCommand implements Command {

	private List<Shape> shapes;
	
	public UnselectCommand(List<Shape> shapes) {
		this.shapes = shapes;
	}
	
	@Override
	public void execute() {
		for (Shape shape : shapes) {
			shape.setSelected(false);
		}
		
	}

	@Override
	public void unexecute() {
		for (Shape shape : shapes) {
			shape.setSelected(true);
		}
	}
	
	@Override
	public String toString() {
		return "Unselect@" + shapes.toString();
	}

}
