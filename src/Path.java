import java.util.LinkedList;
import java.util.List;

public class Path {
	private LinkedList<Action> actions;
	
	public Path () {
		actions = new LinkedList<>();
	}
	
	public void addAction (Action action) {
		actions.add(action);
	}
	
	public List<Action> getPath () {
		return actions;
	}
	
	@Override
	public String toString () {
		String path = new String();
		
		for (Action action : actions) {
			path += action.toString() + "\n";
		}
		
		return path;
	}
}
