package checkers;


public class Driver {
	
	// 2D array that holds the values of the Checker Board
	
	static Piece[][] board = new Piece[][]{
	{new Piece(true),null,new Piece(true),null,new Piece(true),null,new Piece(true),null},
	{null,new Piece(true),null,new Piece(true),null,new Piece(true),null,new Piece(true)},
	{new Piece(true),null,new Piece(true),null,new Piece(true),null,new Piece(true),null},
	{null,null,null,null,null,null,null,null},
	{null,null,null,null,null,null,null,null},
	{null,new Piece(false),null,new Piece(false),null,new Piece(false),null,new Piece(false)},
	{new Piece(false),null,new Piece(false),null,new Piece(false),null,new Piece(false),null},
	{null,new Piece(false),null,new Piece(false),null,new Piece(false),null,new Piece(false)}};
	
	// keeps track of the chips they captured
	private static int redCount = 0;
	private static int blackCount = 0;
	private static boolean game = true;
	
		// -------------------------------------- main 
	public static void main(String arg[]) {
//		DisplayThread t1 = new DisplayThread();
//		t1.start();
		Display ui = new Display();
		ui.setVisible(true);
		Display.update();
			
		boolean turn = true;				// alternates between the Black and Red player's turns
		while(game){
						
			if (turn) {
				blackTurn();
			}
			else {
				redTurn();
			}
			if(turn) {
				turn = false;
			}
			else {
				turn = true;
			}
			
			if (redCount == 9 || blackCount == 9) {		// keeps track of who the winner is after reached 9 chips
				System.out.println("Game over");
				//TODO 
				game = false;
			}
		}
		
	}
		
	private static void blackTurn(){
		//TODO
//		boolean looped = true;
//		int x = 0;
//		int y = 0;
//		while(looped) {
//			System.out.print("It is black's turn \nSelect a piece to move:");
//			x = Integer.parseInt(Display.getInput());
//			y = Integer.parseInt(Display.getInput());
//			System.out.println("");
//			Coordinates origin = new Coordinates(x,y);
//			looped = board[x][y].move(origin);
//		}
		int x = 0, y = 0;									// prompts user input of X & Y locations for Black pieces
		
		boolean intCheck = true;
	do {	
		System.out.print("\nIt is black's turn.");
		while (intCheck)
		{
			intCheck = false;
			
				try {	System.out.println("\nPlease select a B L A C K piece to move.");
					x = Integer.parseInt(Display.getInput());
						y = Integer.parseInt(Display.getInput());
						System.out.println("");
					}
					catch (Exception e) {
					System.out.println("\nInput Positive integers.");
					intCheck = true;
				}	
				 finally      {

                 }														// input validation checks if pieces are not null
				if(board[y][x]!= null) {
					if ((board[y][x].getColor() == true)) {
						System.out.println("Black chip was selected.");
						Coordinates origin = new Coordinates(x,y);
						board[y][x].move(origin);
						Display.update();
					}
					else { System.out.println("Red chip was selected.");			
					intCheck = true;
					}
				}
				else {	intCheck = true;
				}
		}
	}
	
	while (board[y][x]!= null);
	
	}

	
	private static void redTurn() {
			// TODO Auto-generated method stub
			
		
	int x = 0, y = 0;
		
		boolean intCheck = true;
	do {	
		System.out.print("\nIt is red's turn.");
		while (intCheck)
		{
			intCheck = false;
			
				try {	System.out.println("\nPlease select a R E D piece to move.");
					x = Integer.parseInt(Display.getInput());
						y = Integer.parseInt(Display.getInput());
						System.out.println("");
					}
					catch (Exception e) {
					System.out.println("\nInput Positive integers.");
					intCheck = true;
				}	
				 finally      {

                 }														// input validation checks if pieces are not null
				if(board[y][x]!= null) {
					if ((board[y][x].getColor() == false)) {
						System.out.println("Red chip was selected.");
						Coordinates origin = new Coordinates(x,y);
						board[y][x].move(origin);
						Display.update();
					}
					else { System.out.println("Black Chip was selected.");			
					intCheck = true;
					}
				}
				else {
					intCheck = true;
				}
		}
	}
	while (board[y][x]!= null);
	System.out.println("");
	}
	
//		synchronized(Display.inputWait) {
//			while(true) {
//				try {
//					Display.inputWait.wait();
//					break;
//				}catch(Exception e){
//					System.out.println("wait Error");
//				}	
//			}
//			
//		}
	
	public static Piece[][] getBoard(){
		return board;
	}
	
	public static void redCaptureCnt() {									// counter displays when a chip captures opponent and how many
		redCount++;
		System.out.println("Red chips catured: " + redCount + ".");
	}
	
	public static void blackCaptureCnt() {
		blackCount++;
		System.out.println("Black chips catured: " + blackCount + ".");
	}

//	static class DisplayThread implements Runnable{
//
//		private Thread t;
//		DisplayThread(){
//			
//		}
//		@Override
//		public void run() {
//			try {
//				Display frame = new Display();
//				frame.setVisible(true);
//				Display.update();
//				while(game) {
//					Thread.sleep(1);
//					Display.update();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();			
//			}
//		
//	}
//
//		public void start() {
//			if (t == null) {
//				t = new Thread (this);
//				t.start();
//			}
//			
//		}
//	}
}