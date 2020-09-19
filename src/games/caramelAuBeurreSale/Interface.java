package games.caramelAuBeurreSale;

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
		if (character != null) {
			if (character.getPlayer().getId() == 1) {
				context.setColor(orange);
			} else if (character.getPlayer().getId() == 2) {
				context.setColor(violet);
			} else {
				context.setColor(Color.blue);
			}
		}
		if (character != null) {
			context.drawString("Nom : "+character.getName(), 100, hc-120);
			context.drawString("Equipe du joueur : "+character.getPlayer().getName(), 100, hc-105);
			context.drawString("Classe : "+character.getClasse(), 100, hc-90);
			context.drawString("Arme : "+character.getWeapon().getType(), 100, hc-75);
			context.drawString("Santé : "+character.getHealth()+"/"+character.getMaxHealth(), 100, hc-60);
			context.drawString("Points de mouvement : "+character.getMovePoints(), 100, hc-45);
			context.drawString("Attaque/Défense : "+character.getAttack()+"/"+character.getDefense(), 100, hc-30);
		} else {
			context.drawString("",100,hc-120);
		}
	}

}
