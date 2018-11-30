package waterSymbol.bord.cases;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import waterSymbol.bord.Case;

public class Void extends Case {
	private Image sprite;

	public Void(int x, int y) {
		super(x, y);
		
		Image sprite = null;
		
		try {
			sprite = new Image("res/images/void.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.setSprite(sprite);
	}
}