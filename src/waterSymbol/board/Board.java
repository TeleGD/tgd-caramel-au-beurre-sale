package waterSymbol.board;

import java.util.*;
import java.util.Map.Entry;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import waterSymbol.Character;

public class Board {
	private Case[][] cases;
	private HashMap<Case,Integer> traitees;
	private ArrayList<Case> accessibles;

	// Pour la gestion des clics :
	private int height, width;

	public Board(Case[][] cases, int height, int width) {
		this.cases = cases;
		this.height = height;
		this.width = width;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		for(int i=0; i<this.height; i++) {
			for(int j=0; j<this.width; j++) {
				cases[i][j].update(container, game, delta);
			}
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		float screenWidth = container.getWidth();
		float screenHeight = container.getHeight();
		float ratio = (screenWidth / 1920f) >= (screenHeight / 1080f) ? (screenWidth / 1920f) : (screenHeight / 1080f);
		float caseHeight = 1080f / this.height * ratio;
		float caseWidth = 1920f / this.width * ratio;
		for(int i=0; i<this.height; i++) {
			for(int j=0; j<this.width; j++) {
				cases[i][j].render(container, game, context, caseHeight, caseWidth);
			}
		}
	}

	public int[] getSize() {
		return new int[]{this.height, this.width};
	}

	private boolean find (List <Case> path, Case end, int remainder) {
		int length = path.size ();
		Case currentCase = path.get (length - 1);
		if (currentCase == end) {
			return true;
		} else if (remainder > 0) {
			remainder--;
			int [] pos = currentCase.getPos ();
			for (int k = 0; k < 4; k++) {
				int i = pos [0] + (k == 1 ? 1 : 0) - (k == 3 ? 1 : 0);
				int j = pos [1] + (k == 2 ? 1 : 0) - (k == 0 ? 1 : 0);
				if (0 <= i && i < this.height && 0 <= j && j < this.width) {
					Case next = this.cases [i] [j];
					if (next.getType().equals("floor") && next.getCharacter () == null && !path.contains (next)) {
						path.add (next);
						if (find (path, end, remainder)) {
							return true;
						}
						path.remove (length);
					}
				}
			}
		}
		return false;
	}

	/*public List<Case> find2 (Character character, Case end) {
		List <Case> path = new ArrayList<Case>();
		Case start = character.getCase ();
		Case current = start;
		boolean modI = false;
		boolean modJ = false;
		 Premier Algorithme qui bug si cul de sac
		while( (end.getJ() != current.getJ()) && (end.getI()!= current.getI()) ) {
			if (end.getJ() - current.getJ() > 0) {
				if ( cases[current.getI()][current.getJ()+1].isFloor() ){
					current = cases[current.getI()][current.getJ()+1];
					modJ = true;
				}
			} else if(end.getJ() - current.getJ() < 0){
				if ( cases[current.getI()][current.getJ()-1].isFloor() ){
					current = cases[current.getI()][current.getJ()-1];
					modJ = true;
				}
			}
			if(modJ) {
				path.add(current);
			}

			if (end.getI() - current.getI() > 0) {
				if ( cases[current.getI()+1][current.getJ()].isFloor() ){
					current = cases[current.getI()+1][current.getJ()];
					modI = true;
				}
			} else if(end.getI() - current.getI() < 0){
				if ( cases[current.getI()-1][current.getJ()].isFloor() ){
					current = cases[current.getI()-1][current.getJ()];
					modI = true;
				}
			}
			if (modI) {
				path.add(current);
			}
			modJ = false;
			modI = false;
		}
		return path;
	}
	*/

	public List <Case> connect (Character character, Case end) {
		Case start = character.getCase ();
		if (start != null && end != null) {
			if (end.getCharacter () == null) {
				List <Case> path = new ArrayList <Case> ();
				path.add (start);
				if (this.find (path, end, character.getMovePoints ())) {
					for (int i = path.size () - 1; i >= 3; i--) {
						Case currentCase = path.get (i);
						int [] currentPos = currentCase.getPos ();
						for (int j = i - 3; j >= 0; j -= 2) {
							Case nextCase = path.get (j);
							int [] nextPos = nextCase.getPos ();
							if (Math.abs (nextPos [0] - currentPos [0]) + Math.abs (nextPos [1] - currentPos [1]) <= 1) {
								while (i >= j + 2) {
									path.remove (--i);
								}
							}
						}
					}
					return path;
				}
			}
		}
		return null;
	}

	/*public void moveCharacter (Character character, Case end) {
		if (end != null) {
			end.setCharacter (character);
			character.setCase (end);
		} else {
			Case start = character.getCase ();
			if (start != null) {
				start.setCharacter (null);
			}
			character.setCase (null);
		}
	}*/

	public Case getCase(int[] pos){
		if (pos.length < 2 || pos[0] < 0 || pos[0] >= this.height || pos[1] < 0 || pos[1] >= this.width) {
			return null;
		} else {
			return this.cases[pos[0]][pos[1]];
		}
	}

	// public float getWidthCase() {
	// 	if (cases.length != 0) {
	// 		return cases[0][0].getWidth();
	// 	}else {
	// 		return -1;
	// 	}
	// }
	//
	// public float getHeightCase() {
	// 	if (cases.length != 0) {
	// 		return cases[0][0].getHeight();
	// 	}else {
	// 		return -1;
	// 	}
	// }

	// public Case getCase(int x, int y){
	// 	// Renvoit la case qui posède le point (x,y) (x et y en pixels !)
	// 	int j = x / (int) getWidthCase();
	// 	int i = y / (int) getHeightCase();
	//
	// 	return (getCases())[i][j];
	// }

	public void showPossibleMove(Character character) {
		int [] pos = character.getCase().getPos();
		int movePoints = character.getMovePoints()+1;
		traitees = new HashMap<Case,Integer>();
		accessibles = new ArrayList<Case>();
		parcourt(pos[0],pos[1],movePoints);
	}

	public void hidePossibleMove(Character character) {
		for(Entry<Case, Integer> entry : traitees.entrySet()) {
		    Case c = entry.getKey();
		    c.outlight();
		}
		traitees = null;
	}

	private void parcourt(int x, int y, int move) {
		traitees.put(cases[x][y],move);
		System.out.println("Traitement "+x+" "+y);
		if (move >= 0) {
			if (move == 0) {
				cases[x][y].highlight(false);
			} else {
				cases[x][y].highlight(true);
				accessibles.add(cases[x][y]);
			}
			if (x!=0 && (traitees.containsKey(cases[x-1][y]) && traitees.get(cases[x-1][y])<move || !traitees.containsKey(cases[x-1][y])) && cases[x-1][y].isAccessible()) {
				parcourt(x-1,y,move-1);
			}
			if (y!=0 && (traitees.containsKey(cases[x][y-1]) && traitees.get(cases[x][y-1])<move || !traitees.containsKey(cases[x][y-1])) && cases[x][y-1].isAccessible()) {
				parcourt(x,y-1,move-1);
			}
			if (x!=cases.length-1 && (traitees.containsKey(cases[x+1][y]) && traitees.get(cases[x+1][y])<move || !traitees.containsKey(cases[x+1][y])) && cases[x+1][y].isAccessible()) {
				parcourt(x+1,y,move-1);
			}
			if (y!=cases[0].length-1 && (traitees.containsKey(cases[x][y+1]) && traitees.get(cases[x][y+1])<move || !traitees.containsKey(cases[x][y+1])) && cases[x][y+1].isAccessible()) {
				parcourt(x,y+1,move-1);
			}
		}
	}

}
