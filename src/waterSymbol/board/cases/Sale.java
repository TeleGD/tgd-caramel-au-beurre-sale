package waterSymbol.board.cases;

public abstract class Sale extends Case {
	public Sale(int x, int y, String type, float ratio) {
		super(x, y, type, ratio);
	}
	
	@Override
	public boolean isAccessible() {
		return false;
	}
}