package waterSymbol.board;

public class Generation {
	public static Board generate() {
		int width = 35;
		int height = 20;

		Case[][] cases = new Case[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				cases[i][j] = new Case(i, j, "floor");
			}
		}

		cases[0][0] = new Case(0, 0, "teamO");

		cases[height-1][width-1] = new Case(height-1, width-1, "teamV");
		int nbObstacles = 13;

		int r = (int)(Math.random()*5+4);

		for(int k=0; k<r; k++) {
			int ran = (int)(Math.random()*(width-6)+3);

			if(cases[0][ran-1].getType().equals("wall") || cases[0][ran+1].getType().equals("wall") ) k--;
			else generateVerticalWall(cases, ran, height);
		}

		for(int k=r; k<nbObstacles; k++) {
			int i = (int)(Math.random()*height);
			int j = (int)(Math.random()*(width-2))+1;

			if(cases[0][j].getType().equals("wall")) k--;
			else generateWallBlock(cases, i, j, height, width, 0);
		}

		generateShelves(cases, height, width);
		generateSales(cases, height, width);

		createPath(cases, (int) (Math.random() * (height - 10)) + 5, 1, height, width);

		return new Board(cases);
	}

	private static void generateVerticalWall(Case[][] cases, int j, int height) {
		int r = (int)(Math.random()*10+5);
		for(int i=0; i<r ; i++) {
			cases[i][j] = new Case(i, j, "wall");
			//cases[height-i-1][width-j-1] = new Wall(width-j-1,height-i-1);
		}

		for(int i=(int)(r+2+Math.random()*(height-r-2)); i<height; i++) {
			cases[i][j] = new Case(i, j, "wall");
			//cases[height-i-1][width-j-1] = new Wall(width-j-1,height-i-1);
		}
	}

	private static void generateWallBlock(Case[][] cases, int i, int j, int height, int width, int iteration) {
		if(iteration<Math.random()*3+2) {
			cases[i][j] = new Case(i, j, "wall");

			if(j-1>0 && !(cases[0][j-1].getType().equals("wall")) && !(cases[i][j-1].getType().equals("wall"))) generateWallBlock(cases, i, j-1, height, width, iteration+1);
			if(j+1<width-1 && !(cases[0][j+1].getType().equals("wall")) && !(cases[i][j+1].getType().equals("wall"))) generateWallBlock(cases, i, j+1, height, width, iteration+1);
			if(i-1>=0 && !(cases[i-1][j].getType().equals("wall"))) generateWallBlock(cases, i-1, j, height, width, iteration+1);
			if(i+1<height && !(cases[i+1][j].getType().equals("wall"))) generateWallBlock(cases, i+1, j, height, width, iteration+1);
		}
	}

	private static void generateShelves(Case[][] cases, int height, int width) {
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				if(cases[i][j].getType().equals("wall") && (int)(Math.random()*8)==0) cases[i][j] = new Case(i, j, "shelf");
			}
		}

		for(int k=0; k<(int)(Math.random()*10); k++) {
			int j = (int)(Math.random()*(width-2)+1);
			int i = (int)(Math.random()*(height-2)+1);

			if(cases[0][j].getType().equals("wall")) k--;
			else cases[i][j] = new Case(i, j, "shelf");
		}
	}

	private static void generateSales(Case[][] cases, int height, int width) {
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				int rand = (int)(Math.random()*2);
				if(cases[i][j].getType().equals("wall") && (int)(Math.random()*8)==0) {
					if(rand==0) cases[i][j] = new Case(i, j, "sale");
					else cases[i][j] = new Case(i, j, "mega_sale");
				}
			}
		}

		for(int k=0; k<(int)(Math.random()*10); k++) {
			int j = (int)(Math.random()*(width-2)+1);
			int i = (int)(Math.random()*(height-2)+1);

			if(cases[0][j].getType().equals("wall")) k--;
			else if ((int)(Math.random()*2)==0) cases[i][j] = new Case(i, j, "sale");
			else cases[i][j] = new Case(i, j, "mega_sale");
		}
	}

	private static void createPath(Case[][] cases, int i, int j, int height, int width) {
		if(j<width) {
			cases[i][j] = new Case(i, j, "floor");
			if(i-1>0) cases[i-1][j] = new Case(i-1,j,"floor");
			if(i+1<height-1) cases[i+1][j] = new Case(i+1,j,"floor");

			int r = (int)(Math.random()*5);
			if(r==4 && i>=2) createPath(cases, i-1,j, height, width);
			else if(r==3 && i<height-2) createPath(cases, i+1,j, height, width);
			else createPath(cases, i,j+1, height, width);
		}
	}
}
