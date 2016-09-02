package chessInterfacePackage;

public class Rating {
	public static int rating() {
		//All this is temporary used for testing
		/*
		System.out.print("What is the score?: "); 
		Scanner sc = new Scanner(System.in); 
		return sc.nextInt(); */
		
		int counter = 0; 
		counter += rateAttack(); 
		counter += rateMaterial();
		counter += rateMoveability();
		counter += ratePositional();
		AlphaBetaChess.flipBoard();
		//Then subtract from the other side (black)
		counter -= rateAttack(); 
		counter -= rateMaterial();
		counter -= rateMoveability();
		counter -= ratePositional();
		return counter; 
	}
	
	public static int rateAttack() {
		return 0; 
	}
	
	public static int rateMaterial() {
		return 0; 
	}
	
	public static int rateMoveability() {
		return 0; 
	}
	
	public static int ratePositional() {
		return 0; 
	}
}
