package waterSymbol;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import waterSymbol.board.Board;

public class PlayerVendeur extends Player {

	private String id;
	private ArrayList<Character> team;

	public PlayerVendeur(String id) {
		super(id);
		this.id = id;
		this.team = new ArrayList<Character>();
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

	public void update (GameContainer container, StateBasedGame game, int delta, Board board) {
		
		for (Character character : team) {
			character.update(container, game, delta, board);
		}
	}
}
