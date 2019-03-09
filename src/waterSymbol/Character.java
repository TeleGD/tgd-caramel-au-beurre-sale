package waterSymbol;

import java.util.ArrayList;
import java.util.Random;

import waterSymbol.board.cases.Case;
import waterSymbol.weapon.Weapon;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.SpriteSheet;

public class Character {

	private String name;
	private String classe;
	private Image sprite;
	private SpriteSheet spsh;
	private Animation[] anim;
	private int health;
	private int maxHealth;
	private int movePoints;
	private int attack;
	private int defense;
	private int initiative;
	private int agility;
	private Weapon weapon;
	private boolean dead;
	private Player player;
	private Case host;

	public Character(String name, String type, String spsh, Weapon weapon, Player player) throws SlickException {
		this.name = name;
		this.classe = type;
		this.spsh = new SpriteSheet(new Image("res/images/characters/skeleton.png"), 64, 64);
		this.sprite = this.spsh.getSprite(2, 3);
		this.anim = new Animation[8];
		this.anim[0] = loadAnimation(this.spsh,1,2,0);
		this.health = 100;
		this.host = null;
		this.maxHealth = 100;
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
	
	public Image getSprite() {
		return this.sprite;
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

	public Case getCase () {
		return this.host;
	}

	public void setCase (Case host) {
		this.host = host;
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
				this.health -= (int) 1.2*damage*delta - this.defense;
			} else {
				if ((c.weapon.getTypeId() - this.weapon.getTypeId())%3 == 1) {
					this.health -= (int) 0.8*damage*delta - this.defense;
				} else {
					this.health -= damage*delta - this.defense;
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

	public Player getPlayer() {
		return player;
	}
	
	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		Animation animation = new Animation();
		for (int x = startX;x<endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return animation;
	}

}
