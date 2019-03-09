package checkers;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import javax.swing.*;
import javax.swing.border.BevelBorder;


public class Display extends JFrame implements  Runnable	{

	/**
	 * 
	 */
//	private Piece[][] myBoard = new Piece[][]{
//		{new Piece(true),null,new Piece(true),null,new Piece(true),null,new Piece(true),null},
//		{null,new Piece(true),null,new Piece(true),null,new Piece(true),null,new Piece(true)},
//		{new Piece(true),null,new Piece(true),null,new Piece(true),null,new Piece(true),null},
//		{null,null,null,null,null,null,null,null},
//		{null,null,null,null,null,null,null,null},
//		{null,new Piece(false),null,new Piece(false),null,new Piece(false),null,new Piece(false)},
//		{new Piece(false),null,new Piece(false),null,new Piece(false),null,new Piece(false),null},
//		{null,new Piece(false),null,new Piece(false),null,new Piece(false),null,new Piece(false)}
//	};
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private JPanel boardPane;
	private static JLabel[] spaces = new JLabel[64];
	private static JTextField inputText = new JTextField();
	static JTextArea textOut;
	static Object inputWait = new Object();
	
	private Thread reader;
	private Thread reader2;
	private boolean quit; //used as signal for threads
	private final PipedInputStream pin = new PipedInputStream();
	private final PipedInputStream pin2 = new PipedInputStream();
	
	public Display() {
		super("Checkers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,800);
		boardPane = new JPanel();
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		JPanel spacer = new JPanel();
		contentPane.add(spacer);
		boardPane.setBackground(Color.gray);
		boardPane.setOpaque(true);
		boardPane.setBounds(0, 0, 600, 600);
		spacer.add(boardPane);
		boardPane.setLayout(new GridLayout(8,8));
		
		JPanel controllPanel = new JPanel();
		contentPane.add(controllPanel, BorderLayout.SOUTH);
		controllPanel.setLayout(new BorderLayout());
		textOut = new JTextArea();
		textOut.setEditable(false);
		textOut.setSize(600,400);
		textOut.setBackground(Color.BLACK);
		textOut.setForeground(Color.white);
		JScrollPane scrollableTextOut = new JScrollPane(textOut, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		controllPanel.add(scrollableTextOut, BorderLayout.CENTER);
		JPanel inputPanel = new JPanel();
		controllPanel.add(inputPanel, BorderLayout.SOUTH);
		inputPanel.add(inputText);
		inputText.setColumns(30);
		inputText.addActionListener(new EnterListener());
		JButton enterButton = new JButton("Enter");
		enterButton.addActionListener(new EnterListener());
		inputPanel.add(enterButton);
		
		//Emulating the console at the bottom of the Screen
		try {
			PipedOutputStream pout = new PipedOutputStream(this.pin);
			System.setOut(new PrintStream(pout,true));
		}catch(Exception e){
			textOut.append("couldnt redirect STDOUT");
		}
		
		try { 
			PipedOutputStream pout2 = new PipedOutputStream(this.pin2);
			System.setErr(new PrintStream(pout2, true));
		}catch(Exception e) {
			textOut.append("couldnt redirect STDERR" + e.getMessage());
		}
		
		//Starting threads to read console;
		
		quit = false;
		reader = new Thread(this);
		reader.setDaemon(true);
		reader.start();
		
		reader2 = new Thread(this);
		reader2.setDaemon(true);
		reader2.start();
		
		
		for(int i = 0; i < 64; i++) {
			spaces[i] = new JLabel();
			spaces[i].setBorder(new BevelBorder(1));
			boardPane.add(spaces[i]);
		}
		update();
	}
	
	private class EnterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			synchronized(inputWait) {
				textOut.append(inputText.getText());
				inputWait.notify();
				return;
			}
		}
		
	}

	
	public static void update() {
//		Piece[][] myBoard = Driver.getBoard();
		ImageIcon imageBlack = new ImageIcon("SRC\\Images\\BlackPiece.png");
		ImageIcon imageRed = new ImageIcon("SRC\\Images\\RedPiece.png");
		ImageIcon imageKingedRed = new ImageIcon("SRC\\Images\\kingedRed.png");
		ImageIcon imageKingedBlack = new ImageIcon("SRC\\Images\\KingedBlack.png");
		int index = 0;
		for(int x = 0; x < 8;x++) {
			for(int y = 0; y < 8; y++) {
				if(Driver.board[x][y] == null) {
					spaces[index].setBackground(Color.WHITE);
					spaces[index].setIcon(null);
//					System.out.print(" * ");		//debug display
					index++;
				} 
				else if(Driver.board[x][y].getKinged()) {
					
					if (Driver.board[x][y].getColor()) {
						spaces[index].setIcon(imageKingedBlack);
						
						index++;
					}
					else if (!(Driver.board[x][y].getColor())) {
						spaces[index].setIcon(imageKingedRed);
						
						index++;
						}
					
				}else {
					
					if (Driver.board[x][y].getColor()) {
						spaces[index].setIcon(imageBlack);
//						System.out.print("#");   //debug display
						index++;
					}
					else if (!(Driver.board[x][y].getColor())) {
						spaces[index].setIcon(imageRed);
//						System.out.print("@");		//debug display
						index++;
					}
				}
			}
//			System.out.print("\n"); //debug display
		}
		
		contentPane.validate();
		contentPane.repaint();
	}


	@Override
	public synchronized void run() {
		try {
			while (Thread.currentThread() == reader) {
				try {
					this.wait(100);
				}catch(InterruptedException e) {}
					if (pin.available()!=0) {
						String input = this.readLine(pin);
						textOut.append(input);
					}
					if (quit) return;
			}	
			while(Thread.currentThread()==reader2){
				try { 
					this.wait(100);
				}catch(InterruptedException e) {}
				if (pin2.available()!=0) {
					String input=this.readLine(pin2);
					textOut.append(input);
				}
				if(quit) return;
			}
		}catch(Exception e) {
			textOut.append("\nConsole reports an Internal error.");
			textOut.append("The error is :" + e);
		}
		
	}
	
	public synchronized String readLine(PipedInputStream in) throws IOException{
		
		String input="";
		do
		{
			int available = in.available();
			if (available==0)break;
			byte b[]=new byte[available];
			in.read(b);
			input=input+new String(b,0,b.length);
		}while(!input.endsWith("\n") && !input.endsWith("\r\n") && !quit) ;
		return input;
	}

	public static String getInput() {
		//TODO
		synchronized(inputWait) {
			while(true) {
				try {
					inputWait.wait();
					String temp = inputText.getText();
					inputText.setText("");
					textOut.append("\n");
					return temp;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return "error";
			}
		}
		
	}

	
}


