package waterSymbol.board;

import waterSymbol.board.cases.*;

public class Generation {
	private static int width, height;
	private static Case[][] cases;
	private static float ratio;
	
	public static Board generate(int screenWidth, int screenHeight) {
		width = 35;
		height = 20;
		ratio = (screenWidth/1920f)>=(screenHeight/1080f)?(screenWidth/1920f):(screenHeight/1080f);
		
		cases = new Case[height][width];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				cases[i][j] = new Case(j,i,"floor",ratio);
			}
		}
		
		cases[0][0] = new Case(1, 1, "teamO", ratio);
		
		cases[1][0] = new Case(width-1, height-1, "teamV", ratio);
		int nbObstacles = 13;
		
		int r = (int)(Math.random()*5+4);
		
		for(int k=0; k<r; k++) {
			int ran = (int)(Math.random()*(width-6)+3);
			
			if(cases[0][ran-1].getType().equals("wall") || cases[0][ran+1].getType().equals("wall") ) k--;
			else generateVerticalWall(ran);			
		}
		
		for(int k=r; k<nbObstacles; k++) {
			int i = (int)(Math.random()*height);
			int j = (int)(Math.random()*(width-2))+1;
			
			if(cases[0][j].getType().equals("wall")) k--;
			else generateWallBlock(i,j,0);
		}
		
		generateShelves();
		generateSales();
		
		createPath((int)(Math.random()*(height-10))+5,1);
		
		return new Board(cases, height, width, screenWidth, screenHeight);
	}
	
	private static void generateVerticalWall(int j) {
		int r = (int)(Math.random()*10+5);
		for(int i=0; i<r ; i++) {
			cases[i][j] = new Case(j,i,"wall",ratio);
			//cases[height-i-1][width-j-1] = new Wall(width-j-1,height-i-1);
		}
		
		for(int i=(int)(r+2+Math.random()*(height-r-2)); i<height; i++) {
			cases[i][j] = new Case(j,i,"wall",ratio);
			//cases[height-i-1][width-j-1] = new Wall(width-j-1,height-i-1);
		}
	}
	
	private static void generateWallBlock(int i, int j, int iteration) {
		if(iteration<Math.random()*3+2) {
			cases[i][j] = new Case(j,i,"wall",ratio);

			if(j-1>0 && !(cases[0][j-1].getType().equals("wall")) && !(cases[i][j-1].getType().equals("wall"))) generateWallBlock(i,j-1, iteration+1);
			if(j+1<width-1 && !(cases[0][j+1].getType().equals("wall")) && !(cases[i][j+1].getType().equals("wall"))) generateWallBlock(i,j+1, iteration+1);
			if(i-1>=0 && !(cases[i-1][j].getType().equals("wall"))) generateWallBlock(i-1,j, iteration+1);
			if(i+1<height && !(cases[i+1][j].getType().equals("wall"))) generateWallBlock(i+1,j, iteration+1);
		}
	}
	
	private static void generateShelves() {
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				if(cases[i][j].getType().equals("wall") && (int)(Math.random()*8)==0) cases[i][j] = new Case(j,i,"shelf",ratio);
			}
		}
		
		for(int k=0; k<(int)(Math.random()*10); k++) {
			int j = (int)(Math.random()*(width-2)+1);
			int i = (int)(Math.random()*(height-2)+1);
			
			if(cases[0][j].getType().equals("wall")) k--;
			else cases[i][j] = new Case(j,i,"shelf",ratio);			
		}
	}
	
	private static void generateSales() {
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				int rand = (int)(Math.random()*2);
				if(cases[i][j].getType().equals("wall") && (int)(Math.random()*8)==0) {
					if(rand==0) cases[i][j] = new Case(j,i,"sale",ratio);
					else cases[i][j] = new Case(j,i,"mega_sale",ratio);
				}
			}
		}
		
		for(int k=0; k<(int)(Math.random()*10); k++) {
			int j = (int)(Math.random()*(width-2)+1);
			int i = (int)(Math.random()*(height-2)+1);
			
			if(cases[0][j].getType().equals("wall")) k--;
			else if ((int)(Math.random()*2)==0) cases[i][j] = new Case(j,i,"sale",ratio);
			else cases[i][j] = new Case(j,i,"mega_sale",ratio);
		}
	}
	
	private static void createPath(int i, int j) {
		if(j<width) {
			cases[i][j] = new Case(j,i,"floor",ratio);
			if(i-1>0) cases[i-1][j] = new Case(j,i-1,"floor",ratio);
			if(i+1<height-1) cases[i+1][j] = new Case(j,i+1,"floor",ratio);
			
			int r = (int)(Math.random()*5);
			if(r==4 && i>=2) createPath(i-1,j);
			else if(r==3 && i<height-2) createPath(i+1,j);
			else createPath(i,j+1);
		}
	}
}