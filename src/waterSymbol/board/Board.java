package waterSymbol.board;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import waterSymbol.Character;
import waterSymbol.board.cases.*;

public class Board {
	private Case[][] cases;
	private int nbCol, nbLig;
	private float ratio;
	
	private ArrayList<Case> traitees; 

	// Pour la gestion des clics :
	private int x, y;
	private int height, width;
	
	public Board(Case[][] cases, int nbLig, int nbCol, int width, int height, float ratio) {
		this.nbLig = nbLig;
		this.nbCol = nbCol;
		this.cases = cases;
		
		this.height = height;
		this.width = width;
		this.x = 0;
		this.y = 0;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		for(int i=0; i<nbLig; i++) {
			for(int j=0; j<nbCol; j++) {
				cases[i][j].update(container, game, delta);
			}
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for(int i=0; i<nbLig; i++) {
			for(int j=0; j<nbCol; j++) {
				cases[i][j].render(container, game, context);
			}
		}
	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public Case[][] getCases(){
		return cases;
	}
	
	public float getWidthCase() {
		if (cases.length != 0) {
			return cases[0][0].getWidth();
		}else {
			return -1;
		}
	}
	
	public float getHeightCase() {
		if (cases.length != 0) {
			return cases[0][0].getHeight();
		}else {
			return -1;
		}
	}
	
	public void moveCharacter (Character character, int di, int dj) {
		int [] oldPos = character.getPos ();
		int [] newPos = new int [] {oldPos [0] + di, oldPos [1] + dj};
		if (0 < newPos [0] && newPos [0] < this.nbLig && 0 < newPos [1] && newPos [1] < this.nbCol && this.cases [newPos [0]] [newPos [1]].getCharacter () == null) {
			character.setPos (newPos);
			this.cases [oldPos [0]] [oldPos [1]].setCharacter (null);
			this.cases [newPos [0]] [newPos [1]].setCharacter (character);
		}
	}
	
	public void showPossibleMove(Character character) {
		int [] pos = character.getPos();
		int movePoints = character.getMovePoints()+1;
		
		traitees = new ArrayList<Case>();
		
		parcourt(pos[0],pos[1],movePoints);
	}
	
	public void hidePossibleMove(Character character) {
		for (Case c : traitees) {
			c.outlight();
		}
		
		traitees = null;
	}
	
	private void parcourt(int x, int y, int move) {
		traitees.add(cases[x][y]);
		System.out.println(move);
		if (move >= 0) {
			if (move == 0) {
				cases[x][y].highlight(false);
			} else {
				cases[x][y].highlight(true);
			}
			if (x!=0 && !traitees.contains(cases[x-1][y]) && cases[x-1][y].isAccessible()) {
				parcourt(x-1,y,move-1);
			}
			if (y!=0 && !traitees.contains(cases[x][y-1]) && cases[x][y-1].isAccessible()) {
				parcourt(x,y-1,move-1);
			}
			if (x!=cases.length-1 && !traitees.contains(cases[x+1][y]) && cases[x+1][y].isAccessible()) {
				parcourt(x+1,y,move-1);
			}
			if (y!=cases.length-1 && !traitees.contains(cases[x][y+1]) && cases[x][y+1].isAccessible()) {
				parcourt(x,y+1,move-1);
			}
		}
	}
	
}
