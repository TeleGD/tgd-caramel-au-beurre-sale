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
	
	public Board(Case[][] cases, int nbLig, int nbCol, int width, int height) {
		this.ratio = (width/1920f)<(height/1080f)?(width/1920f):(height/1080f);
		
		this.nbLig = nbLig;
		this.nbCol = nbCol;
		this.cases = cases;
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
				cases[i][j].render(container, game, context, ratio);
			}
		}
	}
}
