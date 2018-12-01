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

		int nbObstacles = 13;
		
		int r = (int)(Math.random()*5+4);
		
		for(int k=0; k<r; k++) {
			int ran = (int)(Math.random()*(width-6)+3);
			
			if(cases[0][ran-1] instanceof Wall || cases[0][ran+1] instanceof Wall ) k--;
			else generateVerticalWall(ran);			
		}
		
		for(int k=r; k<nbObstacles; k++) {
			int i = (int)(Math.random()*height);
			int j = (int)(Math.random()*(width-2))+1;
			
			if(cases[0][j] instanceof Wall) k--;
			else generateWallBlock(i,j,0);
		}
		
		createPath((int)(Math.random()*height-10)+5,1);
		
		return new Board(cases, height, width, screenWidth, screenHeight);
	}
	
	private static void generateVerticalWall(int j) {
		int r = (int)(Math.random()*10+5);
		for(int i=0; i<r ; i++) {
			cases[i][j] = new Wall(j,i);
			//cases[height-i-1][width-j-1] = new Wall(width-j-1,height-i-1);
		}
		
		for(int i=(int)(r+2+Math.random()*(height-r-2)); i<height; i++) {
			cases[i][j] = new Wall(j,i);
			//cases[height-i-1][width-j-1] = new Wall(width-j-1,height-i-1);
		}
	}
	
	private static void generateWallBlock(int i, int j, int iteration) {
		if(iteration<Math.random()*3+2) {
			cases[i][j] = new Wall(j,i);

			if(j-1>0 && !(cases[0][j-1] instanceof Wall) && !(cases[i][j-1] instanceof Wall)) generateWallBlock(i,j-1, iteration+1);
			if(j+1<width-1 && !(cases[0][j+1] instanceof Wall) && !(cases[i][j+1] instanceof Wall)) generateWallBlock(i,j+1, iteration+1);
			if(i-1>=0 && !(cases[i-1][j] instanceof Wall)) generateWallBlock(i-1,j, iteration+1);
			if(i+1<height && !(cases[i+1][j] instanceof Wall)) generateWallBlock(i+1,j, iteration+1);
		}
	}
	
	private static void createPath(int i, int j) {
		if(j<width) {
			cases[i][j] = new Floor(j,i);
			if(i-1>0) cases[i-1][j] = new Floor(j,i-1);
			if(i+1<width-1) cases[i+1][j] = new Floor(j,i+1);
			
			int r = (int)(Math.random()*5);
			if(r==4 && i>=2) createPath(i-1,j);
			else if(r==3 && i<width-2) createPath(i+1,j);
			else createPath(i,j+1);
		}
	}
}