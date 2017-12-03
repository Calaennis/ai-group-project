import java.util.InputMismatchException;
import java.util.Scanner;

public class Play {

	private GameManager gameManager;
	
	public static void main (String[] args) {
		new Play().play();
	}
	
	public Play() {
		gameManager = new GameManager();
	}
	
	public void play () {
		boolean playing = true;
		
		// Continually play until player exits
		while (playing) {
			System.out.println("Hello Hero! Welcome to the Most Amazing Game Ever!");
			gameManager.newGame(promptName());
			// Game loop
			do {
				System.out.println("\f" + gameManager.getPlayer() + "\n");
				printGameStatus();
				Action action = promptAction();
				gameManager.performAction(action);
			} while (! gameManager.gameOver());
			
			if (gameManager.playerWon()) {
				System.out.println("You won!");
			} else {
				System.out.println("You lost...");
			}
			
			
			playing = promptPlayAgain();
		}
	}
	
	public String promptName () {
		Scanner in = new Scanner(System.in);
		boolean namePicked = false;
		String name;
		
		do {
			System.out.print("Please, enter your name, hero!\n--> ");
			name = in.nextLine();
			namePicked = promptYesNo("Are you sure you wish to be called " + name + "?");
		} while (!namePicked);
		
		return name;
	}
	
	public boolean promptPlayAgain () {
		return promptYesNo("Play again?");
	}
	
	public boolean promptYesNo (String message) {
		Scanner in = new Scanner(System.in);
		boolean valid = false;
		boolean answer = false;
		
		while (!valid) {
			System.out.print(message + " ((Y)es, (N)o)\n--> ");
			char response = in.next().charAt(0);
			
			if (response == 'y' || response == 'Y') {
				answer = true;
				valid = true;
			}
			else if (response == 'n' || response == 'N') {
				valid = true;
			}
		}
		
		System.out.println();
		return answer;
	}
	
	public void printGameStatus () {
		System.out.printf("Current day: %d\nDays until the Boss arrives: %d\n\n", gameManager.getCurrentDay(), gameManager.getDaysRemaining());
	}
	
	public Action promptAction () {
		Scanner in = new Scanner(System.in);
		boolean actionChosen = false;
		Action action = null;
		
		while (!actionChosen) {
			System.out.printf("What would you like to do, %s?\n", gameManager.getPlayer().getName());
			System.out.print("\t1) Fight a creature of the same level"
					+ "\n\t2) Fight a creature one level higher than me"
					+ "\n\t3) Fight a creature two levels higher than me"
					+ "\n\t4) Fight a creature three levels higher than me"
					+ "\n\t5) Sleep"
					+ "\n--> ");
			
			boolean valid = false;
			
			while (!valid) {
				try {
					int response = in.nextInt();
					switch (response) {
					case 1:
						action = Action.SAME_LEVEL;
						break;
					
					case 2:
						action = Action.ONE_HIGHER;
						break;
						
					case 3:
						action = Action.TWO_HIGHER;
						break;
						
					case 4:
						action = Action.THREE_HIGHER;
						break;
						
					case 5:
						action = Action.SLEEP;
						break;
						
					default:
						continue;
					}
					
					valid = true;
					actionChosen = true;
				} catch (InputMismatchException IME) {
					
				}
			}
		}
		
		System.out.println();
		return action;
	}
	
}
