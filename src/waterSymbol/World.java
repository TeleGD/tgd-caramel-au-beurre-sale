package waterSymbol;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import waterSymbol.board.Board;
import waterSymbol.board.Generation;
import waterSymbol.board.cases.Case;

public class World extends BasicGameState {

	private int ID;
	private int state;
	private TeamBuilder builder;
	private Board board;
	private Player playerActif;
	private List<Player> players;
	private Case caseSelected;
	
	public World (int ID) {
		this.ID = ID;
		this.state = 0;
		players = new ArrayList<Player>();
		caseSelected = null;
	}

	@Override
	public int getID () {
		return this.ID;
	}

	@Override
	public void init (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
	}

	@Override
	public void enter (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
		if (this.state == 0) {
			this.play (container, game);
		} else if (this.state == 2) {
			this.resume (container, game);
		}
	}

	@Override
	public void leave (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			this.pause (container, game);
		} else if (this.state == 3) {
			this.stop (container, game);
			this.state = 0; // TODO: remove
		}
	}

	@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = container.getInput ();
		if (input.isKeyDown (Input.KEY_ESCAPE)) {
			this.setState (1);
			game.enterState (2, new FadeOutTransition (), new FadeInTransition ());
		}
		
		if (!builder.areTeamsReady()) {
			// Construction des teams
			builder.update(container, game, delta);
		} else {
			//TODO en jeu
			board.update(container, game, delta);
		}
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */		
		if (!builder.areTeamsReady()) {
			// Construction des teams
			builder.render(container, game, context);
		} else {
			//TODO en jeu
			board.render(container, game, context);
		}
	}

	public void play (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		
		players.add(new Player("Tristan"));
		players.add(new Player("Axel"));
		
		playerActif = players.get(0);
		
		builder = new TeamBuilder(10, container, players.get(0), players.get(1));
		board = Generation.generate(container.getWidth(), container.getHeight());
		
	}

	public void pause (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
	}

	public void resume (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
	}

	public void stop (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
	}

	public void setState (int state) {
		this.state = state;
	}

	public int getState () {
		return this.state;
	}
	
	@Override
	public void mousePressed(int arg0, int x, int y) {
		if (!builder.areTeamsReady()) {
			return;
		}
		// Rencentre x et y dans le cadre du board
		x -= board.getX();
		y -= board.getY();
		if (x >= 0 && y >= 0 && x <= board.getWidth() && x <= board.getHeight()) {
			// Si on clique dans le board		
			caseSelected = (board.getCases())[ x / (int) board.getWidthCase()][ y / (int) board.getHeightCase()];
		}
		System.out.println("Case selectionnée : i = "+ x / (int) board.getWidthCase() + " j = " + y / (int) board.getHeightCase());
	}

}
