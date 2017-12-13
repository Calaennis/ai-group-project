
public class Player {

	private String name;
	private int level;
	private int exp;
	private int nextLevelExp;
	private int maxHp;
	private int currentHp;
	
	public Player (String name, int level) {
		this.name = name;
		this.level = level;
		
		initialize();
	}
	
	public Player (Player player) {
		this.name = player.name;
		this.level = player.level;
		this.exp = player.exp;
		this.nextLevelExp = player.nextLevelExp;
		this.maxHp = player.maxHp;
		this.currentHp = player.currentHp;
	}
	
	private void initialize () {
		exp = 0;
		setNextLevelExp();
		
		setMaxHp();
		currentHp = maxHp;
	}
	
	public String getName () {
		return name;
	}
	
	public int getLevel () {
		return level;
	}
	
	public int getExp () {
		return exp;
	}
	
	public int getNextLevelExp () {
		return nextLevelExp;
	}
	
	public int getTnl () {
		return nextLevelExp - exp;
	}
	
	public int getMaxHp () {
		return maxHp;
	}
	
	public int getCurrentHp () {
		return currentHp;
	}
	
	public void addExp (int exp) {
		this.exp += exp;
		
		while (true) {
			if (this.exp >= this.nextLevelExp) {
				levelUp();
			}
			else {
				break;
			}
		}
	}
	
	public void takeDamage (int damage) {
		currentHp -= damage;
		
		if (currentHp < 0) {
			currentHp = 0;
		}
	}
	
	public void sleep () {
		currentHp = maxHp;
	}
	
	private void levelUp () {
		//System.out.printf("%s has leveled up and is now level %d!\n\n", name, level + 1);
		// Increment the player's level
		level += 1;
		
		// Set the new experience needed for the next level
		setNextLevelExp();
		
		// Change the player's maxHP, and since they leveled give them full health
		setMaxHp();
		currentHp = maxHp;
	}
	
	public boolean isDead () {
		return currentHp <= 0;
	}
	
	private void setNextLevelExp () {
		nextLevelExp = Settings.EXP_FUNCTION.apply(level);
	}
	
	private void setMaxHp () {
		maxHp = Settings.HEALTH_FUNCTION.apply(level);
	}
	
	@Override
	public String toString () {
		return String.format("Name: %s\nLevel: %d\nMax HP: %d\nCurrent HP: %d\nExp: %d\nExp Required For Next Level: %d\n"
				+ "Exp To Next Level: %d", 
				name, level, maxHp, currentHp, exp, nextLevelExp, getTnl());
	}
}
