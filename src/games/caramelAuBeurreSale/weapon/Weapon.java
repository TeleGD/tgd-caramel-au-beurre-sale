package games.caramelAuBeurreSale.weapon;

public abstract class Weapon {
	
	protected int id;
	protected int typeId;
	protected int effectValue;
	
	public Weapon(int id, int value) {
		this.id = id;
		this.effectValue = value;
	}
	
	public abstract int getId();
	
	public abstract int getTypeId();
	
	public abstract String getType();
	
	public abstract int getEffectValue();

}
