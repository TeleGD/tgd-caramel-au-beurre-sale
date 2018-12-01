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

	private boolean find (List <Case> path, Case end, int remainder) {
		if (path.get (path.size () - 1) == end) {
			return true;
		} else if (remainder > 0) {
			remainder--;
			int length = path.size ();
			Case currentCase = path.get (path.size () - 1);
			int [] pos = currentCase.getPos ();
			for (int k = 0; k < 4; k++) {
				int i = pos [0] + (k == 1 ? 1 : 0) - (k == 3 ? 1 : 0);
				int j = pos [1] + (k == 2 ? 1 : 0) - (k == 0 ? 1 : 0);
				Case next = this.cases [i] [j];
				if (next instanceof Floor && next.getCharacter () == null && !path.contains (next)) {
					path.add (next);
					if (find (path, end, remainder)) {
						return true;
					}
					path.remove (length - 1);
				}
			}
		}
		return false;
	}

	public List <Case> connect (Character character, int i, int j) {
		Case start = character.getCase ();
		if (start != null && 0 < i && i < this.nbLig && 0 < j && j < this.nbCol) {
			Case end = this.cases [i] [j];
			if (end.getCharacter () == null) {
				List <Case> path = new ArrayList <Case> ();
				path.add (start);
				if (this.find (path, end, character.getMovePoints ())) {
					return path;
				}
			}
		}
		return null;
	}

	public void moveCharacter (Character character, int i, int j) {
		Case start = character.getCase ();
		start.setCharacter (null);
		character.setCase (null);
		if (start != null && 0 < i && i < this.nbLig && 0 < j && j < this.nbCol) {
			Case end = this.cases [i] [j];
			end.setCharacter (character);
			character.setCase (end);
		}
	}
}
