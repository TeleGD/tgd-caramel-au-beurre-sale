package waterSymbol;

import java.util.ArrayList;
import java.util.Random;

public class Character {
	
	private String name;
	private String type;
	private int health;
	private int movePoints;
	private int attack;
	private int defense;
	private int initiative;
	private int agility;
	private String classe;
	
	public Character(String name, String type) {
		this.name = name;
		this.type = type;
		this.health = 100 ;
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

	
	public int getInitiative() {
		return this.initiative;
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
<<<<<<< HEAD
	public void generateStat() {
		ArrayList<String> classeListe = new ArrayList<String>() ;
		classeListe.add("healer") ;
		classeListe.add("ranger") ;
		classeListe.add("knight") ;
		classeListe.add("ninja") ;
		classeListe.add("warrior");
		
		
		int i = randInt(1, 4) ;
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
=======
	public void takeDamage(int damage) {
		health -= damage;
	}

>>>>>>> 56e4a822fc60dd3bd824cd8de546792883e488aa
}
