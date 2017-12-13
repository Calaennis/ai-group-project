import java.util.Random;

public enum Action {
	LOWER_LEVEL, SAME_LEVEL, ONE_HIGHER, TWO_HIGHER, THREE_HIGHER, FOUR_OR_MORE_HIGHER, SLEEP;
	
	public static Action getBossAction (int level) {
		int difference = Settings.BOSS_LEVEL - level;
		
		if (difference < 0) {
			return LOWER_LEVEL;
		}
		else if (difference == 0) {
			return SAME_LEVEL;
		}
		else if (difference == 1) {
			return ONE_HIGHER;
		}
		else if (difference == 2) {
			return TWO_HIGHER;
		}
		else if (difference == 3) {
			return THREE_HIGHER;
		}
		else {
			return FOUR_OR_MORE_HIGHER;
		}
	}
	
	public static Action getRandomAction () {
		Action[] actions = Action.values();
		return actions[new Random().nextInt(actions.length)];
	}
	
	public static Action[] getPossibleActions () {
		return new Action[] {SAME_LEVEL, ONE_HIGHER, TWO_HIGHER, THREE_HIGHER, SLEEP};
	}
}
