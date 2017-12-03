
public class GameManager {

	private int currentDay;
	private int finalDay;
	private Player player;
	private boolean bossDefeated;
	
	public GameManager () {
		newGame("");
	}
	
	public void newGame (String playerName) {
		player = new Player(playerName, Settings.STARTING_LEVEL);
		currentDay = 1;
		finalDay = Settings.DAY_LIMIT;
		bossDefeated = false;
	}
	
	public boolean gameOver () {
		return player.isDead() || bossDefeated;
	}
	
	public boolean playerWon () {
		return !player.isDead() && bossDefeated;
	}
	
	public void performAction (Action action) {
		switch (action) {
		case LOWER_LEVEL:
			
			break;
			
		case SAME_LEVEL:
			player.takeDamage(player.getMaxHp() / 4);
			player.addExp(player.getLevel());
			break;
			
		case ONE_HIGHER:
			player.takeDamage(player.getMaxHp() / 2);
			player.addExp(player.getLevel() + 1);
			break;
			
		case TWO_HIGHER:
			player.takeDamage(3 * (player.getMaxHp() / 4));
			player.addExp(player.getLevel() + 2);
			break;
			
		case THREE_HIGHER:
			player.takeDamage(player.getMaxHp() - 1);
			player.addExp(player.getLevel() + 3);
			break;
			
		case FOUR_OR_MORE_HIGHER:
			player.takeDamage(player.getMaxHp());
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
}
