package games.caramelAuBeurreSale.board;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.caramelAuBeurreSale.Character;
import games.caramelAuBeurreSale.Classes;

public class Case {
	private static Random rng;
	private static int[] scale;
	static {
		Case.rng = new Random();
		Case.scale = new int[]{1, 2, 5};
	}
	private int i, j;
	private String type;
	private int points;
	private Image sprite;
	private Character character;
	private Color filter;

	public Case(int i, int j, String type) {
		this.i = i;
		this.j = j;
		this.setType(type);
		this.filter = new Color(255,255,255,255);
	}

	public void setType(String type) {
		this.type = type;
		this.points = 0;
		switch (type) {
			case "wall": {
				type = "wall" + (Case.rng.nextInt(2)+1);
				break;
			}
			case "shelf": {
				type = "shelf" + (Case.rng.nextInt(3)+1);
				break;
			}
			case "sale": {
				this.points = Case.scale[Case.rng.nextInt(3)] * 10;
				break;
			}
			case "mega_sale": {
				this.points = Case.scale[Case.rng.nextInt(3)] * 100;
				break;
			}
		}
		this.sprite = AppLoader.loadPicture("/images/caramelAuBeurreSale/" + type + ".png");
	}

	public int [] getPos () {
		return new int [] {this.i, this.j};
	}

	public String getType() {
		return this.type;
	}

	public void setCharacter (Character character) {
		this.character = character;
	}

	public Character getCharacter () {
		return this.character;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (this.character != null){
			this.character.update(container,game,delta);
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context, float height, float width) {
		context.drawImage(sprite, this.j * width, this.i * height, width * (this.j + 1), (this.i + 1) * height, 0, 0, this.sprite.getWidth(), sprite.getHeight(), this.filter);
		if (this.character != null) {
			this.character.render(container, game, context, this.i * height, this.j * width, height, width);
		}
	}
	public void highlight(boolean b) {
		if (b)
			filter = new Color(187, 210, 225, 255);
		else
			filter = new Color(247, 180, 150, 255);
	}

	public void outlight() {
		filter = new Color(255,255,255,255);
	}

	public void collect(Character character) {
		if(character.getClasse() == Classes.VENDEUR) {

		} else if(type.equals("sale") || type.equals("mega_sale")) {
			if (character.getPA()>=1){  // Check si le character a assez de PA
				character.addPoint(this.points);
				this.setType("shelf");
				character.decrementPA(1);
			}
		}
	}

	public boolean isAccessible() {
		if (this.character != null) {
			return false;
		}
		switch(this.type) {
		case "wall" :
		case "sale" :
		case "mega_sale" :
		case "shelf":
			return false;
		default :
			return true;
		}
	}
}
