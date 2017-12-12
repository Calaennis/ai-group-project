
public class GameManager {

	private int currentDay;
	private int finalDay;
	private Player player;
	private boolean bossDefeated;
	private boolean gameOver;
	
	public GameManager () {
		newGame("");
	}
	
	public void newGame (String playerName) {
		player = new Player(playerName, Settings.STARTING_LEVEL);
		currentDay = 1;
		finalDay = Settings.DAY_LIMIT;
		bossDefeated = false;
		System.out.println("Starting a new game!");
	}
	
	public boolean gameOver () {
		return gameOver;
	}
	
	public boolean playerWon () {
		return gameOver && bossDefeated;
	}
	
	public void performAction (Action action) {
		switch (action) {
		case LOWER_LEVEL:
			
			break;
			
		case SAME_LEVEL:
			player.takeDamage(player.getMaxHp() / 4);
			
			if (!checkGameOver()) {
				player.addExp(player.getLevel());
			}
			
			break;
			
		case ONE_HIGHER:
			player.takeDamage(player.getMaxHp() / 2);
			
			if (!checkGameOver()) {
				player.addExp(player.getLevel());
			}
			
			break;
			
		case TWO_HIGHER:
			player.takeDamage(3 * (player.getMaxHp() / 4));

			if (!checkGameOver()) {
				player.addExp(player.getLevel());
			}
			
			break;
			
		case THREE_HIGHER:
			player.takeDamage(player.getMaxHp() - 1);

			if (!checkGameOver()) {
				player.addExp(player.getLevel());
			}
			
			break;
			
		case FOUR_OR_MORE_HIGHER:
			player.takeDamage(player.getMaxHp());
			checkGameOver();
			break;
			
		case SLEEP:
			player.sleep();
			currentDay++;
			break;
			
		}
	}
	
	public void fightBoss () {
		performAction(Action.getBossAction(player.getLevel()));
		
		if (!player.isDead()) {
			bossDefeated = true;
		}
	}
	
	public boolean isFinalDay () {
		return currentDay == finalDay;
	}
	
	public int getCurrentDay () {
		return currentDay;
	}
	
	public int getFinalDay () {
		return finalDay;
	}
	
	public int getDaysRemaining () {
		return finalDay - currentDay;
	}
	
	public Player getPlayer () {
		return player;
	}
	
	public boolean isBossDefeated () {
		return bossDefeated;
	}
	
	private boolean checkGameOver () {
		if (player.isDead()) {
			gameOver = true;
			return true;
		}
		
		return false;
	}
}
