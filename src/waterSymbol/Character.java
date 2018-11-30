package waterSymbol;

public class Character {
	
	private String name;
	private String type;
	private int health;
	private int movePoints;
	private int attack;
	private int defense;
	private int resistance;
	private int speed;
	
	public Character(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getMovePoints() {
		return this.movePoints;
	}
	
	public int getAttack() {
		return this.attack;
	}
	
	public int getDefense() {
		return this.defense;
	}
	
	public int getResistance() {
		return this.resistance;
	}
	
	public int getSpeed() {
		return this.speed;
	}

}
