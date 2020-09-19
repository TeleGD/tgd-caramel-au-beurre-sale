package games.caramelAuBeurreSale.weapon;

public class GreasyWeapon extends Weapon {

	public GreasyWeapon(int id, int value) {
		super(id, value);
		super.typeId = 3;
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
		return "Plaquette de beurre";
	}
	
	@Override
	public int getEffectValue() {
		return this.effectValue;
	}

}
