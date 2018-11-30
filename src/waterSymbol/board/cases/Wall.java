package waterSymbol.board.cases;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import waterSymbol.board.Case;

public class Wall extends Case {
	public Wall(int x, int y) {
		super(x, y);
		
		Image sprite = null;
		
		try {
			sprite = new Image("res/images/wall.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.setSprite(sprite);
	}
}
