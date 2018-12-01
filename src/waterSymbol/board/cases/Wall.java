package waterSymbol.board.cases;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Wall extends Case {
	public Wall(int x, int y, float ratio) {
		super(x, y, "wall"+((int)(Math.random() * 2)+1), ratio);
	}
}
