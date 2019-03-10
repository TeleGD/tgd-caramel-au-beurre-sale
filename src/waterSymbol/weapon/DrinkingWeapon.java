package waterSymbol.weapon;

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
		return "";
	}
	
	@Override
	public int getEffectValue() {
		return this.effectValue;
	}

}
