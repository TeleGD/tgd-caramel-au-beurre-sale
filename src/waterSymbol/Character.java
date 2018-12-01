package waterSymbol;

import java.util.ArrayList;
import java.util.Random;

import waterSymbol.weapon.Weapon;

public class Character {

	private String name;
	private String classe;
	private int health;
	private int maxHealth;
	private int movePoints;
	private int attack;
	private int defense;
	private int initiative;
	private int agility;
	private int [] pos;
	private Weapon weapon;
	private boolean dead;

	public Character(String name, String type, Weapon weapon) {
		this.name = name;
		this.classe = type;
		this.health = 100;
		this.maxHealth = 100;
		this.pos = new int [] {1, 1};
		this.weapon = weapon;
		this.dead = false;
		generateStat();
	}

	public String getName() {
		return this.name;
	}

	public String getClasse() {
		return this.classe;
	}

	public int getHealth() {
		return this.health;
	}
	
	public int getMaxHealth() { 
		return this.maxHealth;
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


	public int getInitiative() {
		return this.initiative;
	}

	public int getAgility() {
		return this.agility;
	}

	public Weapon getWeapon() {
		return this.weapon;
	}

	public boolean isDead() {
		return this.dead;
	}

	public int [] getPos () {
		return new int [] {this.pos [0], this.pos [1]};
	}

	public void setPos (int [] pos) {
		this.pos = new int [] {pos [0], pos [1]};
	}

	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}

	public void generateStat() {
		ArrayList<String> classeListe = new ArrayList<String>() ;
		classeListe.add("healer") ;
		classeListe.add("ranger") ;
		classeListe.add("knight") ;
		classeListe.add("ninja") ;
		classeListe.add("warrior");


		int i = randInt(0, 4) ;
		this.classe = classeListe.get(i) ;

		if (this.classe.equals("healer")) {
			this.movePoints = 4 ;
			this.attack = randInt(10, 25) ;
			this.defense = randInt(10, 25) ;
			this.initiative = randInt(45, 65) ;
			this.agility = randInt(30, 50) ;
		}

		if (this.classe.equals("ranger")) {
			this.movePoints = 3 ;
			this.attack = randInt(25, 40) ;
			this.defense = randInt(25, 40) ;
			this.initiative = randInt(80, 99) ;
			this.agility = randInt(60, 80) ;
		}

		if (this.classe.equals("knight")) {
			this.movePoints = 3 ;
			this.attack = randInt(40, 60) ;
			this.defense = randInt(80, 99) ;
			this.initiative = randInt(70, 90) ;
			this.agility = randInt(10, 30) ;
		}

		if (this.classe.equals("ninja")) {
			this.movePoints = 6 ;
			this.attack = randInt(60, 80) ;
			this.defense = randInt(10, 30) ;
			this.initiative = randInt(40, 60) ;
			this.agility = randInt(60, 80) ;
		}

		if (this.classe.equals("warrior")) {
			this.movePoints = 4 ;
			this.attack = randInt(70, 90) ;
			this.defense = randInt(30, 50) ;
			this.initiative = randInt(30, 50) ;
			this.agility = randInt(10, 25) ;
		}


	}

	public void takeDirectDamage(int damage) {
		this.health -= damage;
		if (this.health <= 0) {
			this.dead = true;
		}
	}

	public void takeDamage(Character c) {
		int delta = 1;
		if (randInt(0,100) <= 5) {
			delta = 2;
		}
		if (randInt(0,100) < this.agility) {
			int damage = c.attack + c.weapon.getEffectValue();
			if ((this.weapon.getTypeId() - c.weapon.getTypeId())%3 == 1) {
				this.health -= (int) 1.2*damage*delta;
			} else {
				if ((c.weapon.getTypeId() - this.weapon.getTypeId())%3 == 1) {
					this.health -= (int) 0.8*damage*delta;
				} else {
					this.health -= damage*delta;
				}
			}
			if (this.health <= 0) {
				this.dead = true;
			}
		}
	}
		
	public void takeDirectHealing(int heal) {
		if (this.maxHealth-this.health <= heal) {
			this.health = this.maxHealth;
		} else {
			this.health += heal;
		}
	}
	
	public void takeHealing(Character c) {
		int delta = 1;
		if (randInt(0,100) <= 5) {
			delta = 2;
		}
		if (c.weapon.getTypeId() == 4) {
			if (this.maxHealth-this.health <= c.weapon.getEffectValue()*delta) {
				this.health = this.maxHealth;
			} else {
				this.health += this.weapon.getEffectValue()*delta;
			}
		}
	}


}
