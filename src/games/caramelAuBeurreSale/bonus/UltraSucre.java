package games.caramelAuBeurreSale.bonus;

import games.caramelAuBeurreSale.Character;

public class UltraSucre extends Bonus {

	public UltraSucre(Character character, int value) {
		super(character, value);
	}
	
	public void activate() {
		character.takeDirectDamage(value);
	}

}
