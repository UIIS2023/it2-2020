package command;

import geometry.Line;

public class UpdateLineCommand implements Command {

	private Line oldLine;
	private Line newLine;
	private Line intialLine;
	
	public UpdateLineCommand(Line oldLine, Line newLine) {
		this.oldLine = oldLine;
		this.newLine = newLine;
	}
	
	@Override
	public void execute() {
		this.intialLine = oldLine.clone();
		
		oldLine.setStartPoint(newLine.getStartPoint());
		oldLine.setEndPoint(newLine.getEndPoint());
		oldLine.setSelected(newLine.isSelected());
		oldLine.setColor(newLine.getColor());
	}
	@Override
	public void unexecute() {
		oldLine.setStartPoint(intialLine.getStartPoint());
		oldLine.setEndPoint(intialLine.getEndPoint());
		oldLine.setSelected(intialLine.isSelected());
		oldLine.setColor(intialLine.getColor());
		
	}
	@Override
	public String toString() {
		return "UpdateLine@" + intialLine.toString() + "#" + newLine.toString();
	}
	
	
}
