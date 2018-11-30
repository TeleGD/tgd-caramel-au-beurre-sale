package waterSymbol.bonus;

import waterSymbol.Character;

public abstract class Bonus {
	protected Character character;
	protected int value;

	public Bonus(Character character, int value) {
		this.character = character;
	}
	
	public abstract void activate();

}
