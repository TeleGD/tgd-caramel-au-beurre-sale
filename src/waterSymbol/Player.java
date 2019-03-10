package waterSymbol;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Player {

	private String id;
	private int score;
	private Color filter;
	private ArrayList<Character> team;

	public Player() {
		this.score = 0;
		this.id = generateName();
		this.team = new ArrayList<Character>();
	}
	
	public Player(String id) {
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
	
	private String generateName() {
		ArrayList<String> firstnames = new ArrayList<String>(Arrays.asList("Amos","Axel","David","Fabien","Frantz","Frédéric","Lucas","Maxime","Nathan","Océane","Quentin","Tristan","Xavier"));
		ArrayList<String> lastnames = new ArrayList<String>(Arrays.asList("George","Pontet","Forlen","Bernier","Darbon","Venier","Thomas","Botreau-Roussel-Bonneterre","Barloy","Chazé","Charrier","Le Godais","Indice"));

		return firstnames.get((int) (Math.random()*firstnames.size()))+" "+lastnames.get((int) (Math.random()*lastnames.size()));
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

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
	}

	public void resetPACharacter(){
		for(Character character : team){
			character.resetPA();
		}
	}

}
