package games.caramelAuBeurreSale;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;

import games.caramelAuBeurreSale.board.Board;
import games.caramelAuBeurreSale.board.Generation;
import games.caramelAuBeurreSale.weapon.DrinkingWeapon;
import games.caramelAuBeurreSale.board.Case;

public class World extends BasicGameState {

	private int ID;
	private int state;
	private PlayerVendeur vendeurs;
	private TeamBuilder builder;
	private Board board;
	private int playerActifIndex;
	private List<Player> players;
	private Case caseSelected1;
	private Case caseSelected2;
	private Character characterSelected1;
	private Character characterSelected2;
	private Interface infos;
	private boolean a;
	private static Audio lifelight;
	private static float lifelightPos;
	private int[] mouse;
	static {
		World.lifelight = AppLoader.loadAudio("/musics/caramelAuBeurreSale/purgatoire.ogg");
		World.lifelight = AppLoader.loadAudio("/musics/caramelAuBeurreSale/ZOT.ogg");
		World.lifelightPos = 0;
	}

	public World (int ID) {
		this.ID = ID;
		this.state = 0;
	}

	@Override
	public int getID () {
		return this.ID;
	}

	public void placeCharacters(Player player) {
		ArrayList<Character> team = new ArrayList<>(player.getTeam());
		Case tile;
		int i = 0; int j=0;
		boolean found = false;
		int mirror, offsetWidth, offsetHeight;

		if (player == players.get(0)) {
			mirror = 1;
			offsetWidth = 0;
			offsetHeight = 0;
		} else if (player == players.get(1)) {
			mirror = -1;
			offsetWidth = board.getSize()[1] - 1;
			offsetHeight = board.getSize()[0] - 1;
		} else {
			mirror = -1;
			offsetWidth = board.getSize()[1]/2;
			offsetHeight = board.getSize()[0]/2;
		}


		for(Character character : team) {
			found = false;
			j = 0;
			while(!found && j < board.getSize()[1]) {
				i = 0;
				while(!found && i < board.getSize()[0]){
					int[] pos = {offsetHeight+i*mirror,offsetWidth+j*mirror};
					System.out.println("i : "+pos[0]+" ; j : "+pos[1]);
					tile = board.getCase(pos);
					if (tile.isAccessible()) {
						character.setCase(tile);
						found = true;
					}
					i++;
				}
				j++;
			}
		}

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
			vendeurs.update(container, game, delta, board);


			if (a) {
				a = false;


				for (Player player : players) {
					placeCharacters(player);
				}
				placeCharacters(vendeurs);
			}
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
			infos.render(container, game, context);
		}
	}

	public void play (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		if ((int)(Math.random()*2)==0) {
			World.lifelight = AppLoader.loadAudio("/musics/caramelAuBeurreSale/purgatoire.ogg");
		} else {
			World.lifelight = AppLoader.loadAudio("/musics/caramelAuBeurreSale/ZOT.ogg");
		}
		World.lifelight.playAsMusic(1, .4f, true);
		players = new ArrayList<Player>();
		caseSelected1 = null;

		players.add(new Player(1));
		players.add(new Player(2));
		vendeurs = new PlayerVendeur(3);

		Character v = new Character("Zhan",Classes.VENDEUR,new DrinkingWeapon(1, 1), vendeurs);
		vendeurs.ajouter(v);

		playerActifIndex = 0;

		infos = new Interface(null);

		builder = new TeamBuilder(4, container, players.get(0), players.get(1));
		board = Generation.generate(20,35);
		a = true;
	}

	public void pause (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
		World.lifelightPos = World.lifelight.getPosition();
		World.lifelight.stop();
	}

	public void resume (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
		World.lifelight.playAsMusic(1, .4f, true);
		World.lifelight.setPosition(World.lifelightPos);
	}

	public void stop (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
		World.lifelight.stop();
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

	public void click(GameContainer container, int button, int x, int y) {  //TODO : gérer la boucle de jeu pour faire jouer les deux joueurs !!
		if (!builder.areTeamsReady()) {
			return;
		}
		// Rencentre x et y dans le cadre du board
		int[] size = board.getSize();
		board.hidePossibleMove();   // Efface l'affichage des mouvements possibles
		float screenWidth = container.getWidth();
		float screenHeight = container.getHeight();
		float ratio = (screenWidth / 1920f) >= (screenHeight / 1080f) ? (screenWidth / 1920f) : (screenHeight / 1080f);
		float caseHeight = 1080f / (size[0]+4) * ratio;
		float caseWidth = 1920f / size[1] * ratio;
		int i = (int) Math.floor(y / caseHeight);
		int j = (int) Math.floor(x / caseWidth);
		if (i >= 0 && i < size[0] && j >= 0 && j < size[1]) { // Si on clique dans le board
			Case caseSelected = board.getCase(new int[]{i, j});
			if (button == 0) {	// Clic gauche de la souris
				caseSelected1 = caseSelected;
				characterSelected1 = caseSelected1.getCharacter();	// Récupère le character présent sur la case (s'il y en a un)
				infos = new Interface(characterSelected1);
				int[] pos1 = caseSelected1.getPos();
				System.out.println("Case selectionnée : i = "+ pos1[0] + " j = " + pos1[1]);
				if (characterSelected1 == null) {
					// Si le joueur ne selectionne pas un character, on annule la selection
					caseSelected1 = null;
					return;
				} else if (characterSelected1.getPlayer() != players.get(playerActifIndex)) {
					// Si le joueur selectionne un character de son adversaire, on annule la selection
					caseSelected1 = null;
					characterSelected1 = null;
					return;
				} else {
					board.showPossibleMove(characterSelected1);
				}
			}
			else if (button == 1 && (characterSelected1 != null)) {	// Clic droit avec un personnage déjà selectionné
				caseSelected2 = caseSelected;
				int[] pos2 = caseSelected2.getPos();
				characterSelected2 = caseSelected2.getCharacter();	// Récupère le charactère présent sur la case (s'il y en a un)
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
							if (characterSelected2.getPlayer() != players.get(playerActifIndex)) {    // Si le joueur selectionne un character de son adversaire, son déplacement est une attaque
								characterSelected1.attack(characterSelected2);  //TODO : débuguer
								System.out.println("ATTAQUE !");
							} else if (characterSelected2.getPlayer() == players.get(playerActifIndex)) {     // Si le joueur selectionne un de ses character comme destination, il effectue une action amicale : soin, item ...
								// TODO : SOINS du character soigné
								System.out.println("HEAL un character de la team");
							}
						} else if (caseSelected2.getType().equals("sale") || caseSelected2.getType().equals("mega_sale") ) { //La case de destination est un shelf collectable
							caseSelected2.collect(characterSelected1);
							System.out.println("Collect un shelf");
						}
					}
				}
				// Réinitialisation des selections :
				resetSelections();
			}
		}
		manageTurn(false);   // Vérifie si le joueur à épuisé tous les PA de ses character
	}

	public boolean checkEndTurn(){
		// Vérifie si players.get(playerActifIndex) a encore des characters ayant des PA
		for (Character character : players.get(playerActifIndex).getTeam()){
			if (character.getPA() > 0){
				return false;
			}
		}
		return true;
	}

	public void resetSelections(){
		board.hidePossibleMove();
		characterSelected1=null;
		characterSelected2=null;
		caseSelected2= null;
		caseSelected1=null;
	}

	public void manageTurn(boolean forceEndTurn){
		if (forceEndTurn || checkEndTurn()){    // Fin du tour du playerActif
			resetSelections();
			int newIndex = (playerActifIndex + 1) % players.size();
			playerActifIndex = newIndex;
			players.get(playerActifIndex).resetPACharacter();   // On reset les PA des characters du player à qui c'est maintenant le tour
		}
	}

	public void keyPressed(int key, char c) {
		if (key==Input.KEY_SPACE){
			manageTurn(true);   // Force la fin du tour du playerActif
		}
	}

}
