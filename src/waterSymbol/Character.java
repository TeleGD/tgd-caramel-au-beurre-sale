package waterSymbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import waterSymbol.board.cases.Case;
import waterSymbol.weapon.*;

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
	private Classes classe;
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
	private int ownPoint;

	/**
	 * Create a random charactere
	 *
	 * @param player
	 * @throws SlickException
	 */
	public Character(Player player) throws SlickException {
		this.name = generateName();
		this.classe = Classes.values()[(int) (Math.random()*Classes.values().length)];
		switch ((int) (Math.random()*4)) {
		case 0:
			weapon = new DrinkingWeapon(1, 1);
			break;
		case 1:
			weapon = new GreasyWeapon(1, 1);
			break;
		case 2:
			weapon = new SaltedWeapon(1, 1);
			break;
		case 3:
			weapon = new SweetWeapon(1, 1);
			break;
		}

		this.host = null;
		this.maxHealth = 100;
		this.health = maxHealth;
		this.dead = false;
		this.ownPoint = 0;
		generateStat();

		initAnim();

		this.weapon = weapon;
		generateStat();
		this.player = player;
	}

	public Character(String name, Classes classe, Weapon weapon, Player player) throws SlickException {
		this.name = name;
		this.classe = classe;
		this.weapon = weapon;

		this.host = null;
		this.maxHealth = 100;
		this.health = maxHealth;
		this.dead = false;
		this.ownPoint = 0;
		generateStat();

		initAnim();

		this.maxHealth = 100;
		this.weapon = weapon;
		generateStat();
		this.player = player;
	}

	public void initAnim(){
		// TODO animation selon la classe
		String path = "res/images/characters/skeleton.png"; //TODO : CHANGER CE MAGIC STRING
		try {
			this.spsh = new SpriteSheet(new Image(path), 64, 64);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.sprite = this.spsh.getSprite(2, 3);
		this.anim = new Animation[8];

		this.anim[0] = loadAnimation(this.spsh,0,1,8);
		this.anim[1] = loadAnimation(this.spsh,0,1,9);
		this.anim[2] = loadAnimation(this.spsh,0,1,10);
		this.anim[3] = loadAnimation(this.spsh,0,1,11);
		this.anim[4] = loadAnimation(this.spsh,1,9,8);
		this.anim[5] = loadAnimation(this.spsh,1,9,9);
		this.anim[6] = loadAnimation(this.spsh,1,9,10);
		this.anim[7] = loadAnimation(this.spsh,1,9,11);
	}

	private String generateName() {
		ArrayList<String> keywords = new ArrayList<String>(Arrays.asList("array","break","do","else","end","for","function","if","in","let","nil","of","then","to","type","var","while"));

		return keywords.get((int) (Math.random()*17))+keywords.get((int) (Math.random()*17));
	}

	public String getName() {
		return this.name;
	}

	public Classes getClasse() {
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
		switch (classe) {
		case HEALER:
			this.movePoints = 4 ;
			this.attack = randInt(10, 25) ;
			this.defense = randInt(20, 50) ;
			this.initiative = randInt(45, 65) ;
			this.agility = randInt(50, 70) ;
			break;
		case RANGER:
			this.movePoints = 4 ;
			this.attack = randInt(25, 45) ;
			this.defense = randInt(25, 40) ;
			this.initiative = randInt(60, 80) ;
			this.agility = randInt(40, 60) ;
			break;
		case KNIGHT:
			this.movePoints = 2 ;
			this.attack = randInt(40, 60) ;
			this.defense = randInt(70, 90) ;
			this.initiative = randInt(30, 50) ;
			this.agility = randInt(10, 30) ;
			break;
		case NINJA:
			this.movePoints = 6 ;
			this.attack = randInt(40, 60) ;
			this.defense = randInt(10, 30) ;
			this.initiative = randInt(35, 55) ;
			this.agility = randInt(65, 85) ;
			break;
		case WARRIOR:
			this.movePoints = 3 ;
			this.attack = randInt(70, 90) ;
			this.defense = randInt(30, 50) ;
			this.initiative = randInt(30, 50) ;
			this.agility = randInt(20, 40) ;
			break;
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

	public void addPoint(int p) {
		this.ownPoint += p;
	}

	public void teamPoint() {
		player.addPoint(ownPoint);
		ownPoint = 0;
	}

	public Player getPlayer() {
		return player;
	}

	public void moveAnim(Case c) {
		int[] hostPos = host.getPos();
		int[] cPos = c.getPos();
		if(hostPos[0] - cPos[0] > 0) {
			/* haut */
			setCase(c);
		} else if(hostPos[0] - cPos[0] < 0) {
			/* bas */
			setCase(c);
		} else if(hostPos[1] - cPos[1] > 0) {
			/* gauche */
			setCase(c);
		} else if(hostPos[1] - cPos[1] < 0) {
			/* droite */
			setCase(c);
		}
	}

	public void move(List<Case> path) {
		for (Case c : path) {
			moveAnim(c);
		}
	}

	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		Animation animation = new Animation();
		for (int x = startX;x<endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return animation;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context, float i, float j, float height, float width) {
		context.drawImage(this.sprite, j, i, j + width, i + height, 0, 0, this.sprite.getWidth(), this.sprite.getHeight());
	}

}
