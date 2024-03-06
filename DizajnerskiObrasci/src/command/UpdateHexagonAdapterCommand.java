package command;

import geometry.HexagonAdapter;

public class UpdateHexagonAdapterCommand implements Command {

	private HexagonAdapter oldHexagonAdapter;
	private HexagonAdapter newHexagonAdapter;
	private HexagonAdapter initialHexagonAdapter;
	
	public UpdateHexagonAdapterCommand(HexagonAdapter oldHexagonAdapter, HexagonAdapter newHexagonAdapter) {
		this.oldHexagonAdapter = oldHexagonAdapter;
		this.newHexagonAdapter = newHexagonAdapter;
	}
	
	@Override
	public void execute() {
		initialHexagonAdapter = oldHexagonAdapter.clone();
		
		oldHexagonAdapter.setX(newHexagonAdapter.getX());
		oldHexagonAdapter.setY(newHexagonAdapter.getY());
		oldHexagonAdapter.setRadius(newHexagonAdapter.getRadius());
		oldHexagonAdapter.setColor(newHexagonAdapter.getColor());
		oldHexagonAdapter.setInnerColor(newHexagonAdapter.getInnerColor());
	}

	@Override
	public void unexecute() {
		oldHexagonAdapter.setX(initialHexagonAdapter.getX());
		oldHexagonAdapter.setY(initialHexagonAdapter.getY());
		oldHexagonAdapter.setRadius(initialHexagonAdapter.getRadius());
		oldHexagonAdapter.setColor(initialHexagonAdapter.getColor());
		oldHexagonAdapter.setInnerColor(initialHexagonAdapter.getInnerColor());
	}
	
	@Override
	public String toString() {
		return "UpdateHexagonAdapter@" + initialHexagonAdapter.toString() + "#" + newHexagonAdapter.toString();
	}

}
