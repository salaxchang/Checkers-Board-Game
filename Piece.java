package checkers;

import checkers.Coordinates;				

public class Piece implements Cloneable{

	Coordinates origin;
	Coordinates movingTo;
	Boolean color, kinged;
	private int direction;
	
	public Piece(Boolean color)  {
		this.color = color;
		this.kinged = false;
	}
	
	public Piece(Boolean color, Boolean kinged) {
		this.color = color;
		this.kinged = kinged;
	}
	
	public boolean getColor() {
		return color;
	}
	
	public boolean getKinged() {
		return kinged;
	}
	
	public void setKinged(boolean kinged) {
		this.kinged = kinged;
	}

	//=========================================================================================
	

	public Boolean move(Coordinates origin){
		this.origin = origin;
		
//		Piece[][] myBoard = Driver.getBoard();
		
		
		while(true) {
			System.out.println("moving left or right");
			String buffer = Display.getInput();

			
			if(buffer.equals("left") || buffer.equals("Left")) {
				System.out.println("\nUp or down");
				buffer = Display.getInput();
				if (buffer.equals("up") || buffer.equals("Up")) {
					direction = 1;
					break;

			if(this.kinged) {
				if(buffer.equals("left") || buffer.equals("Left")) {
					System.out.println("Up or down");
					buffer = Display.getInput();
					if (buffer.equals("up") || buffer.equals("Up")) {
						direction = 1;
						break;
					}
					else if (buffer.equals("down") || buffer.equals("Down")) {
						direction = 2;
						break;
					}
					else if (buffer.equals("exit") || buffer.equals("Exit")) {
						return true;
					}
					else{
						System.out.println("Invalid direction");
					}

				}
				else if(buffer.equals("right") || buffer.equals("Right")) {
					System.out.println("Up or down");
					buffer = Display.getInput();
					if (buffer.equals("up") || buffer.equals("Up")) {
						direction = 0;
						break;
					}
					else if (buffer.equals("down") || buffer.equals("Down")) {
						direction = 3;
						break;
					}
					else if (buffer.equals("exit") || buffer.equals("Exit")) {
						return true;
					}
					else{
						System.out.println("Invalid direction");
					}
				}
				else if (buffer.equals("exit") || buffer.equals("Exit")) {
					return true;
				}

				else{
					System.out.println("\nInvalid direction");
				}
			}
			else if(buffer.equals("right") || buffer.equals("Right")) {
				System.out.println("\nUp or down");
				buffer = Display.getInput();
				if (buffer.equals("up") || buffer.equals("Up")) {
					direction = 0;
					break;

			}
			else {
				if(buffer.equals("left") || buffer.equals("Left")) {
					if (this.color) {
						direction = 2;
						break;
					}
					else {
						direction = 1;
						break;
					}

				}
				else if(buffer.equals("right") || buffer.equals("Right")) {
					if(this.color) {
						direction = 3;
						break;
					}
					else {
						direction = 0;
						break;
					}
				}
				else if(buffer.equals("exit")||buffer.equals("Exit")) {
					return true;
				}

				else{
					System.out.println("\nInvalid direction");
				}
			
			else if (buffer.equals("exit") || buffer.equals("Exit")) {
				return true;

			}
		}	
			
			Coordinates destination = whereTo(origin);
			
			if (destination.locX > 7 || destination.locY > 7 || destination.locX < 0 || destination.locY < 0) {
				System.out.println("\nInvalid move");
				return true;
			}
			
			if ((Driver.board[destination.locY][destination.locX] == null)) {
				Driver.board[destination.locY][destination.locX] = (Piece) this.clone();
				Driver.board[origin.locY][origin.locX] = null;
				return false;
				
			}
			else {
				if(Driver.board[destination.locY][destination.locX].getColor() == this.getColor()) {
					System.out.println("\nYou can't jump your own piece!");
					return true;
				}
				else if (Driver.board[destination.locY][destination.locX].getColor() != this.getColor()) {
					return jumpPiece(destination);
				}
				else {
					System.out.println("\nError");
					return true;
				}
			}
				
	}
	
	private Boolean jumpPiece(Coordinates jumped) {
//		Piece[][] myBoard = Driver.getBoard();
			Coordinates destination = whereTo(jumped);
			if (destination.locX > 7 || destination.locY > 7 || destination.locX < 0 || destination.locY < 0) {
				System.out.println("\nInvalid move");
				return true;
			}
			
			if (Driver.board[destination.locY][destination.locX] == null) {
				if (this.color) {
					Driver.blackCaptureCnt();
				}else {
					Driver.redCaptureCnt();
				}
				Driver.board[jumped.locY][jumped.locX] = null;
				Driver.board[destination.locY][destination.locX] = this;
				Driver.board[origin.locY][origin.locX] = null;
				return false;
				
			}else {
				System.out.println("\nCan't jump out of the board");
				return true;
			}
			
			
	}

	private Coordinates whereTo(Coordinates start) {
		int x = start.getLocX();
		int y = start.getLocY();
		
		switch (direction) {
		case 0: 
			x += 1;
			y -= 1;
			break;
		case 1: 
			x -= 1; 
			y -= 1;
			break;
		case 2: 
			x -= 1; 
			y += 1;
			break;
		case 3:
			x += 1;
			y += 1;
			break;
		}
		
			
		return new Coordinates(x,y);
	}
	
	@Override
	public Object clone() {
		
		
		Piece temp = new Piece(this.color, this.kinged);
		return temp;
		
	}
}