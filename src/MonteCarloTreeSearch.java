import java.util.ArrayList;
import java.util.List;

public class MonteCarloTreeSearch {
	private GameManager gameManager;
	private String playerName = "AI BOT 4000";
	private int trials = 10000;
	private Node root;

	public static void main (String[] args) {
		MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();
		mcts.populate();
		//mcts.print();
		mcts.playOptimalGame();
	}

	public void playOptimalGame () {
		gameManager.newGame(playerName);
		Node currentPosition = root;
		
		while (!gameManager.gameOver()) {
			Action action = chooseOptimalAction(currentPosition);
			System.out.println("Action: " + action.toString());
			gameManager.performAction(action);
		}
		
		System.out.println(gameManager.playerWon() ? "Bot wins!" : "Bot loses...");
	}
	
	public Action chooseOptimalAction (Node node) {
		return null;
	}
	
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
		while (node != null) {
			node.incrementVisitCount();
			node.addWinScore(score);
			node = node.getParent();
		}
	}
	
	public double rollout (Node node) {
		while (true) {
			if (node.isTerminal()) {
				return gameManager.playerWon() ? 1 : -1;
			}
			if (gameManager.isFinalDay()) {
				gameManager.fightBoss();
				node = new Node(node, Action.getBossAction(gameManager.getPlayer().getLevel()));
				node.setTerminal(gameManager.gameOver());
			}
			else {
				Action action = Action.getRandomAction();
				node = simulate(action, node);
			}
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
			
			if (child.getVisitCount() == 0) {
				return child;
			}
			
			double temp = ucb1(node, child);
			if (temp > ucb) {
				ucb = temp;
				index = i;
			}
		}
		
		return node.getChildren().get(index);
	}
	
	public double ucb1 (Node parent, Node node) {
		return node.getWinScore() / node.getVisitCount() +
				2 * Math.sqrt(Math.log(parent.getVisitCount()) / node.getVisitCount());
	}

	public void print () {
		System.out.println(root);
	}
}
