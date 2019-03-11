package waterSymbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import waterSymbol.board.Board;
import waterSymbol.board.Case;
import waterSymbol.weapon.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class Character {

	static int moveDuration = 200;

	private String name;
	private Classes classe;
	private Image sprites[];
	private int direction;
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
	private List<Case> path;
	private int[] vector;

	private int PAMax;
	private int PA;

	private int k;

	private boolean cible_ok;

	/**
	 * Create a random charactere
	 *
	 * @param player
	 * @throws SlickException
	 */
	public Character(Player player) {
		this.name = generateName();
		this.classe = Classes.values()[(int) (Math.random()*Classes.values().length-1)];
		switch ((int) (Math.random()*3)) {
		case 0:
			weapon = new GreasyWeapon(1, 1);
			break;
		case 1:
			weapon = new SaltedWeapon(1, 1);
			break;
		case 2:
			weapon = new SweetWeapon(1, 1);
			break;
		}

		this.host = null;
		this.vector = new int[] {0,0};
		this.k = 0;
		this.maxHealth = 100;
		this.health = maxHealth;
		this.dead = false;
		this.ownPoint = 0;
		this.path = new ArrayList<Case>();
		generateStat();

		initAnim();

		this.PAMax = 2;
		this.PA = PAMax;

		this.player = player;
	}

	public Character(String name, Classes classe, Weapon weapon, Player player) {
		this.name = name;
		this.classe = classe;
		this.weapon = weapon;

		this.host = null;
		this.vector = new int[] {0,0};
		this.k = 0;
		this.maxHealth = 100;
		this.health = maxHealth;
		this.dead = false;
		this.ownPoint = 0;
		this.path = new ArrayList<Case>();

		generateStat();

		initAnim();

		this.player = player;
	}

	public void initAnim(){

		String path = "/images/characters/";

		switch (classe) {
		case WARRIOR:
			path += "WARRIOR";
			break;
		case KNIGHT:
			path += "KNIGHT";
			break;
		case NINJA:
			path += "NINJA";
			break;
		case RANGER:
			path += "RANGER";
			break;
		case VENDEUR:
			path += "VENDEUR";
			break;
		default:
			break;
		}

		if (weapon instanceof GreasyWeapon) {
			path += "_GREASY";
		} else if (weapon instanceof SaltedWeapon) {
			path += "_SALTED";
		} else if (weapon instanceof SweetWeapon) {
			path += "_SWEET";
		}

		path += ".png";

		Image im = AppLoader.loadPicture(path);
		sprites = new Image[4];
		for (int i=0 ; i<sprites.length ; i++) {
			sprites[i] = im.getSubImage(0, i*64, 64, 64);
		}

		direction = 2;

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
		return this.sprites[direction];
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
		if (this.host != null) {
			// Si le personnage se deplace, retirer le personnnage de sa case de depart
			this.host.setCharacter(null);
		}
		this.host = host;
		this.host.setCharacter(this);
		int[] pos = host.getPos();
		System.out.println("i : "+pos[0]+" ; j : "+pos[1]);
	}

	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}



	public void generateStat() {
		switch (classe) {
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
		case VENDEUR:
			this.movePoints = 15 ;
			this.attack = randInt(70, 90) ;
			this.defense = randInt(5, 10) ;
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

	public void attack(Character c){
		if (PA < 1){   // Check si le character a assez de PA
			return;
		}
		c.takeDamage(this);
		if(this.classe == Classes.NINJA){   //Le ninja peut potentiellement attaquer deux fois de suite
			decrementPA(1);
		}else{
			PA=0;
		}
	}

	public void takeDamage(Character c) {
		int delta = 1;
		if (randInt(0,100) <= 5) {
			delta = 2;
		}
		if (randInt(0,100) < this.agility) {
			// TODO Refaire les dégats selon les types d'arme
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
			direction = 0;
			vector[0] = -1;
			vector[1] = 0;
		} else if(hostPos[0] - cPos[0] < 0) {
			/* bas */
			direction = 2;
			vector[0] = 1;
			vector[1] = 0;
		} else if(hostPos[1] - cPos[1] > 0) {
			/* gauche */
			direction = 1;
			vector[0] = 0;
			vector[1] = -1;
		} else if(hostPos[1] - cPos[1] < 0) {
			/* droite */
			direction = 3;
			vector[0] = 0;
			vector[1] = 1;
		}
		setCase(c);
	}

	public void move(List<Case> path) {
		if (PA < 2 && classe != Classes.RANGER){   // Check si le character n'a pas assez de PA
			return;
		}
		this.path.addAll(path);
		decrementPA(1);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {

		if (k > 0 || path.size() != 0) {
			k -= delta ;
		}
		while (k <= 0 && path.size() != 0) {
			moveAnim(path.get(0));
			path.remove(0);
			k += moveDuration;
		}
		if (k <= 0 && path.size() == 0) {
			k = 0;
			vector[0] = 0;
			vector[1] = 0;
			if (host.getType().equals("teamO)") && getPlayer().getId()==1) {
				teamPoint();
			}
			if (host.getType().equals("teamV") && getPlayer().getId()==2) {
				teamPoint();
			}
		}

	}

	public void update(GameContainer container, StateBasedGame game, int delta, Board board) {
		if(classe == Classes.VENDEUR) {
			cible_ok = false;
			/*
			for(int i = 0; i < 5;i++) {
				for(int j = 0; j < 5;j++) {
					int[] pos = new int[] {i+getCase().getPos()[0],j+getCase().getPos()[1]};
					if(pos[0] <= 20 && pos[0] >= 0 && pos[1] <= 35 && pos[1] >= 0) {
						if(board.getCase(pos).getType().equals("shelf")) {
							move(board.connect(this,board.getCase(pos)));
							cible_ok = true;
						}
					} else {
						pos = new int[] {-1*i+getCase().getPos()[0],-1*j+getCase().getPos()[1] };
						if(pos[0] <= 20 && pos[0] >= 0 && pos[1] <= 35 && pos[1] >= 0) {
							if(board.getCase(pos).getType().equals("shelf")) {
								move(board.connect(this,board.getCase(pos)));
								cible_ok = true;
							}
						}
					}
				}
			}
			if(!cible_ok) {
				move(board.connect(this,board.getCase(new int[] {((int)Math.random()*5)+getCase().getPos()[0],((int)Math.random()*5)+getCase().getPos()[1]})));
			}*/
		}
		if (k > 0 || path.size() != 0) {
			k -= delta ;
		}
		while (k <= 0 && path.size() != 0) {
			moveAnim(path.get(0));
			path.remove(0);
			k += moveDuration;
		}
		if (k <= 0 && path.size() == 0) {
			k = 0;
			vector[0] = 0;
			vector[1] = 0;
		}

	}

	public void render(GameContainer container, StateBasedGame game, Graphics context, float i, float j, float height, float width) {
		float dj = j - (k*width*vector[1])/moveDuration ;
		float di = i - (k*height*vector[0])/moveDuration ;
		context.drawImage(this.sprites[direction], dj, di, dj + width, di + height, 0, 0, this.sprites[direction].getWidth(), this.sprites[direction].getHeight());
	}

	public int getPA(){
		return PA;
	}

	public void resetPA(){
		PA = PAMax;
	}

	public void decrementPA(int costPA){
		PA -= costPA;
		PA = Math.max(0,PA);
	}

}
