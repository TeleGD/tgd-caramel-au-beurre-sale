package waterSymbol.board;

import waterSymbol.board.cases.*;

public class Generation {
	private static int width, height;
	private static Case[][] cases;

	public static Board generate(int screenWidth, int screenHeight) {
		width = 35;
		height = 20;
		cases = new Case[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				cases[i][j] = new Floor(j,i);
			}
		}

		return new Board(cases, height, width, screenWidth, screenHeight);
	}
}