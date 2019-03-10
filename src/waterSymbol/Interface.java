package waterSymbol;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Interface {
	
	private Character character;
	
	public Interface(Character character) {
		this.character = character;
	}
	
	public Character getCharacter() {
		return this.character;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		float hc = container.getHeight();
		context.setColor(Color.black);
		context.drawString("Nom : "+character.getName(), 100, hc-100);
		context.drawString("Equipe du joueur : "+character.getPlayer().getId(), 100, hc-80);
		context.drawString("Classe : "+character.getClasse(), 100, hc-60);
	}

}
