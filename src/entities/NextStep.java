package entities;

public class NextStep extends Step { 
	
	public NextStep() {}
	
	public NextStep(Step step) {
		this.pos.x = step.pos.x;
		this.pos.y = step.pos.y;
		this.length2 = step.length2;
	}
	
	public double shading = 0;
}
