package command;

import geometry.Donut;

public class UpdateDonutCommand implements Command{
	
	private Donut oldDonut;
	private Donut newDonut;
	private Donut intialDonut;
	
	public UpdateDonutCommand(Donut oldDonut, Donut newDonut) {
		this.oldDonut = oldDonut;
		this.newDonut = newDonut;
	}
	
	@Override
	public void execute() {
		this.intialDonut = oldDonut.clone();
		
		oldDonut.setCenter(newDonut.getCenter());
		try {
			oldDonut.setRadius(newDonut.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		oldDonut.setInnerRadius(newDonut.getInnerRadius());
		oldDonut.setSelected(newDonut.isSelected());
		oldDonut.setColor(newDonut.getColor());
	}
	@Override
	public void unexecute() {
		oldDonut.setCenter(intialDonut.getCenter());
		try {
			oldDonut.setRadius(intialDonut.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		oldDonut.setInnerRadius(intialDonut.getInnerRadius());
		oldDonut.setSelected(intialDonut.isSelected());
		oldDonut.setColor(intialDonut.getColor());
		
	}
	@Override
	public String toString() {
		return "UpdateDonut@" + intialDonut.toString() + "#" + newDonut.toString();
	}

}
