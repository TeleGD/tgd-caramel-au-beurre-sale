package waterSymbol.weapon;

public class SaltedWeapon extends Weapon {

	public SaltedWeapon(int id, int value) {
		super(id, value);
		super.typeId = 1;
	}
	
	@Override
	public int getId() {
		return this.id;
	}
	
	@Override
	public int getTypeId() {
		return this.typeId;
	}
	
	@Override
	public int getEffectValue() {
		return this.effectValue;
	}
	
}
