package games.caramelAuBeurreSale.weapon;

public class DrinkingWeapon extends Weapon {

	public DrinkingWeapon(int id, int value) {
		super(id, value);
		super.typeId = 4;
	}
	
	@Override
	public int getId() {
		return this.id;
	}
	
	@Override
	public int getTypeId() {
		return this.typeId;
	}
	
	public String getType() {
		return "Sens des affaires";
	}
	
	@Override
	public int getEffectValue() {
		return this.effectValue;
	}

}
