package waterSymbol.board.cases;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import waterSymbol.board.Case;

public class Floor extends Case {
	private Image sprite;

	public Floor(int x, int y) {
		super(x, y, "floor");
	}

}