package waterSymbol.board.cases;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Shelf extends Case {

	public Shelf(int x, int y) {
		super(x, y, "shelf"+((int)(Math.random() * 3)+1));
	}
}