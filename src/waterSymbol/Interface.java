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
		// Affichage du score
		context.setColor(Color.orange);
		context.drawString("Nom : "+character.getName()+"\n", 100, container.getHeight()-100);
//			g.drawString("Score de "+p.getName()+ " : " + p.getScore(), 399, 10);
//			g.drawString("Nombre de points de Gravité restants à " + p.getName()+ " : " + p.getGravityPoint(), 700, 10);
	}

}
