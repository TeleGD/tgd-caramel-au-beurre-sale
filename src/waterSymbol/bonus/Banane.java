package waterSymbol.bonus;

import waterSymbol.Character;

public class Banane extends Bonus {

	public Banane(Character character, int value) {
		super(character, value);
	}

	@Override
	public void activate() {
		character.takeDirectDamage(value);
	}

}
