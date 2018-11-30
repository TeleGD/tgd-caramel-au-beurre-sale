package waterSymbol;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Player {
	
	private String id;
	private ArrayList<Character> team;
	
	public Player(String id) {
		this.id = id;
	}
	
	public void setTeam(ArrayList<Character> team) {
		this.team = team;
	}
	
	public void ajouter(Character c) {
		this.team.add(c);
	}
	
	public String getId() {
		return this.id;
	}
	
	public ArrayList<Character> getTeam() {
		return this.team;
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
	}

}
