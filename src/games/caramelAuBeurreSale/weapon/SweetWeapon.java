package games.caramelAuBeurreSale.weapon;

public class SweetWeapon extends Weapon {

	public SweetWeapon(int id, int value) {
		super(id, value);
		super.typeId = 2;
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
	public String getType() {
		return "Sucre d'orge";
	}
	
	@Override
	public int getEffectValue() {
		return this.effectValue;
	}
	
}
