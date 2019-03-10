package waterSymbol;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import waterSymbol.board.Board;

public class PlayerVendeur {

	private String id;
	private int score;
	private Color filter;
	private ArrayList<Character> team;

	public PlayerVendeur(String id) {
		this.score = 0;
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

	public void addPoint(int p) {
		score += p;
	}

	public ArrayList<Character> getTeam() {
		return this.team;
	}
	
	public void createCharacter(Board board) {
		Character v;
		try {
			v = new Character(Classes.VENDEUR, this);
			this.ajouter(v);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] pos = new int[] { 0, 10}; 
		while(board.connect(team.get(0), board.getCase(pos)) == null) {
			pos[1] += 1;
		}
		team.get(0).setCase(board.getCase(pos));
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		
	}

	public void update (GameContainer container, StateBasedGame game, int delta, Board board) {
		
/*		for (Character character : team) {
			character.update(container, game, delta, board);
		}*/
	}
}
