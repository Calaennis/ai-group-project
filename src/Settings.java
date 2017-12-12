import java.util.function.IntFunction;

public class Settings {

	public static final int HP_INCREMENT = 10;
	public static final int BASE_HP = 10;
	public static final int BOSS_LEVEL = 15;
	public static final int DAY_LIMIT = 10;
	public static final int STARTING_LEVEL = 1;
	
	public static final IntFunction<Integer> EXP_FUNCTION = (int level) -> (int) (Math.pow(level, 2) * Math.log(level)) + level * 4;
	public static final IntFunction<Integer> HEALTH_FUNCTION = (int level) -> level * HP_INCREMENT + BASE_HP;
	
	
	public static void main (String[] args) {
		for (int i = 1; i < 50; i++) {
			System.out.println(i + ": " + EXP_FUNCTION.apply(i));
		}
	}
}
