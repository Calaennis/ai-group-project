import java.util.ArrayList;
import java.util.List;

public class MonteCarloTreeSearch {
	private GameManager gameManager;
	private String playerName = "AI BOT 4000";
	private int trials = 10000;
	private Node root;
	
	public void populate () {
		gameManager = new GameManager();
		root = new Node(null, null);
		
		for (int i = 0; i < trials; i++) {
			gameManager.newGame(playerName);
			
			Node node = selection();
			if (!node.isTerminal()) {
				double score = 0;
				
				if (node.getVisitCount() == 0) {
					score = rollout(node);
				}
				else {
					node = expansion(node);
					score = rollout(node);
				}
				
				backpropagation(node, score);
			}
		}
	}
	
	public Node selection () {
		Node node = root;
		
		while (node.hasChildren()) {
			node = childWithMaxUcb1(node);
		}
		
		return node;
	}
	
	public Node expansion (Node node) {
		List<Node> children = new ArrayList<>();
		for (Action action : Action.values()) {
			children.add(new Node(node, action));
		}
		node.setChildren(children);
		return children.get(0);
	}
	
	public void backpropagation (Node node, double score) {
		while (node.getParent() != null) {
			node.incrementVisitCount();
			node.setWinScore(score);
			node = node.getParent();
		}
	}
	
	public double rollout (Node node) {
		while (true) {
			if (node.isTerminal()) {
				return gameManager.playerWon() ? 1 : -1;
			}
			Action action = Action.getRandomAction();
			node = simulate(action, node);
		}
	}
	
	public Node simulate (Action action, Node node) {
		Node newNode = new Node(node, action);
		gameManager.performAction(action);
		newNode.setTerminal(gameManager.gameOver());
		return newNode;
	}
	
	public Node childWithMaxUcb1 (Node node) {
		double ucb = -100f;
		int index = 0;
		
		for (int i = 0; i < node.size(); i++) {
			Node child = node.getChildren().get(i);
			
			double temp = child.getWinScore() + 2 * (Math.log(node.getVisitCount() / child.getVisitCount()));
			if (temp > ucb) {
				ucb = temp;
				index = i;
			}
		}
		
		return node.getChildren().get(index);
	}
}
