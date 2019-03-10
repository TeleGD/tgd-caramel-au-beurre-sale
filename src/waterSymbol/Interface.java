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
		float wc = container.getWidth();
		Color violet = new Color(108,36,102);
		Color orange = new Color(239,138,38);
		if (character.getPlayer().getId() == "Axel") {
			context.setColor(orange);
		} else {
			context.setColor(violet);
		}
		context.drawString("Nom : "+character.getName(), 100, hc-120);
		context.drawString("Equipe du joueur : "+character.getPlayer().getId(), 100, hc-105);
		context.drawString("Classe : "+character.getClasse(), 100, hc-90);
		context.drawString("Arme : "+character.getWeapon().getType(), 100, hc-75);
		context.drawString("Santé : "+character.getHealth()+"/"+character.getMaxHealth(), 100, hc-60);
		context.drawString("Points de mouvement : "+character.getMovePoints(), 100, hc-45);
		context.drawString("Attaque/Défense : "+character.getAttack()+"/"+character.getDefense(), 100, hc-30);
	}

}
