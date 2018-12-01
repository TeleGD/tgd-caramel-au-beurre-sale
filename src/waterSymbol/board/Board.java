package waterSymbol.board;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import waterSymbol.board.cases.*;

public class Board {
	private Case[][] cases;
	private int nbCol, nbLig;
	private float ratio;
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
}
