package waterSymbol;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import waterSymbol.board.Board;
import waterSymbol.board.Generation;
import waterSymbol.board.Case;

public class World extends BasicGameState {

	private int ID;
	private int state;
	private TeamBuilder builder;
	private Board board;
	private Player playerActif;
	private List<Player> players;
	private Case caseSelected1;
	private Case caseSelected2;
	private Character characterSelected1;
	private Character characterSelected2;
	private boolean a;
	private static Music lifelight;
	private int[] mouse;
	static {
		try {
			lifelight = new Music("res/musics/purgatoire.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public World (int ID) {
		this.ID = ID;
		this.state = 0;
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
		if (this.mouse != null) {
			this.click(container, this.mouse[0], this.mouse[1], this.mouse[2]);
			this.mouse = null;
		}

		if (!builder.areTeamsReady()) {
			// Construction des teams
			builder.update(container, game, delta);
		} else {
			//TODO en jeu
			board.update(container, game, delta);
			/*if (a) {
				a = false;
				Character character = players.get(0).getTeam().get(0);
				//board.moveCharacter(character, board.getCases () [0] [0]);
				board.showPossibleMove(character);
				System.out.println(board.connect(character, board.getCase(new int[]{2, 2})));
			}*/
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
		lifelight.loop(1, (float) 0.4);
		players = new ArrayList<Player>();
		caseSelected1 = null;

		players.add(new Player("Tristan"));
		players.add(new Player("Axel"));

		playerActif = players.get(0);

		builder = new TeamBuilder(1, container, players.get(0), players.get(1));
		board = Generation.generate();
		a = true;
	}

	public void pause (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
		lifelight.pause();
	}

	public void resume (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
		lifelight.resume();
	}

	public void stop (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
		lifelight.stop();
	}

	public void setState (int state) {
		this.state = state;
	}

	public int getState () {
		return this.state;
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		this.mouse = new int[]{button, x, y};
	}
	public void click(GameContainer container, int button, int x, int y) {
		if (!builder.areTeamsReady()) {
			return;
		}
		// Rencentre x et y dans le cadre du board
		int[] size = board.getSize();
		float screenWidth = container.getWidth();
		float screenHeight = container.getHeight();
		float ratio = (screenWidth / 1920f) >= (screenHeight / 1080f) ? (screenWidth / 1920f) : (screenHeight / 1080f);
		float caseHeight = 1080f / size[0] * ratio;
		float caseWidth = 1920f / size[1] * ratio;
		int i = (int) Math.floor(y / caseHeight);
		int j = (int) Math.floor(x / caseWidth);
		if (i >= 0 && i < size[0] && j >= 0 && j < size[1]) { // Si on clique dans le board
			Case caseSelected = board.getCase(new int[]{i, j});
			if (button == 0) {	// Clic gauche de la souris
				caseSelected1 = caseSelected;
				characterSelected1 = caseSelected1.getCharacter();	// Récupère le character présent sur la case (s'il y en a un)
				int[] pos1 = caseSelected1.getPos();
				System.out.println("Case selectionnée : i = "+ pos1[0] + " j = " + pos1[1]);
				if (characterSelected1 == null) {
					// Si le joueur ne selectionne pas un character, on annule la selection
					caseSelected1 = null;
					return;
				} else if (characterSelected1.getPlayer() == playerActif) {
					// Si le joueur selectionne un character de son adversaire, on annule la selection
					caseSelected1 = null;
					characterSelected1 = null;
					return;
				}
			}
			else if (button == 1 && (characterSelected1 != null)) {	// Clic droit avec un personnage déjà selectionné
				caseSelected2 = caseSelected;
				int[] pos2 = caseSelected2.getPos();
				characterSelected2 = caseSelected1.getCharacter();	// Récupère le charactère présent sur la case (s'il y en a un)
				int distance = Board.manhattanDistance(caseSelected1,caseSelected2);    // Calcul de la distance entre les deux cases : ce n'est pas la distance qui sera réellement parcourue !
				if (distance == 0) {	// Si le joueur selectionne la même case qu'avant
					//TODO : Action sur le personnage selectionné (auto-soin, utiliser item...)
					System.out.println("Action sur moi-même");
				} else {    // Le joueur selectionne une autre case
					if (caseSelected2.isAccessible() && board.getAccessibles().contains(caseSelected2)) {	// La case est vide et on peut s'y déplacer
						board.showPossibleMove(characterSelected1);
						List<Case> pathToFollow = board.connect(characterSelected1, caseSelected2);   // Construction du chemin à emprunter
						characterSelected1.move(pathToFollow);  // Character se déplace en suivant le chemin
						System.out.println("Je me déplace en case : i = " + pos2[0] + " j = " + pos2[1]);
					} else if (distance == 1) {    // La case a un character ou shelf dessus, on n'interragit avec que s'ils sont à côté du character1
						if (characterSelected2 != null) {    // La case de destination a un character dessus
							if (characterSelected2.getPlayer() != playerActif) {    // Si le joueur selectionne un character de son adversaire, son déplacement est une attaque
								//TODO : BASTON
								System.out.println("ATTAQUE !");
							} else if (characterSelected2.getPlayer() == playerActif) {     // Si le joueur selectionne un de ses character comme destination, il effectue une action amicale : soin, item ...
								// TODO : SOINS du character soigné
								System.out.println("HEAL un character de la team");
							}
						} else if (caseSelected2.getType().equals("sale") || caseSelected2.getType().equals("mega_sale") ) { //La case de destination est un shelf collectable
							caseSelected2.collect(characterSelected2);
							System.out.println("Collect un shelf");
						}
					}
				}
				// Réinitialisation des selections :
				caseSelected2 = null;
				characterSelected2 = null;
				caseSelected1 = null;
				characterSelected1=null;
			}
		}
	}

}
