package waterSymbol.bonus;

import waterSymbol.Character;

public class UltraSucre extends Bonus {

	public UltraSucre(Character character, int value) {
		super(character, value);
	}
	
	public void activate() {
		character.takeDamage(value);
	}

}
