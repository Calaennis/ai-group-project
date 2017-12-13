import java.util.ArrayList;
import java.util.List;

public class MonteCarloTreeSearch {
	private GameManager gameManager;
	private GameManager simulatedGame;
	private String playerName = "AI BOT 4000";
	private long thinkTime = 5000;
	
	private double timeAliveMod = 1;
	private double levelsGainedMod = 10;
	private double victoryMod = 100;
	private double dayCountMod = 1;

	public static void main (String[] args) {
		MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();
		mcts.playOptimalGame();
	}

	public MonteCarloTreeSearch () {
		gameManager = new GameManager();
	}
	
	public void playOptimalGame () {
		gameManager.newGame(playerName);
		
		while (!gameManager.gameOver()) {
			gameManager.printGameState();
			Action action = findOptimalAction();
			System.out.println("Action: " + action.toString());
			gameManager.performAction(action);
			System.out.println();
		}
		
		System.out.println(gameManager.playerWon() ? "Bot wins!\n" : "Bot loses...\n");
		gameManager.printGameState();
	}
	
	public Action findOptimalAction () {
		Node root = new Node(null, null);
		populate(root);
		
		double highestUcb = Double.NEGATIVE_INFINITY;
		int index = 0;
		
		for (int i = 0; i < root.size(); i++) {
			double tempUcb = ucb1(root, root.getChildren().get(i));
			if (tempUcb > highestUcb) {
				highestUcb = tempUcb;
				index = i;
			}
			System.out.printf("%s: %f\n", root.getChildren().get(i).getAction().toString(), tempUcb);
		}
		
		return root.getChildren().get(index).getAction();
	}
	
	public void populate (Node root) {
		long endTime = System.currentTimeMillis() + thinkTime;
		
		while (System.currentTimeMillis() < endTime) {
			simulatedGame = new GameManager(gameManager);
			
			Node node = selection(root);
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
	
	public Node selection (Node node) {
		while (node.hasChildren() && !node.isTerminal()) {
			node = childWithMaxUcb1(node);
		}
		
		return node;
	}
	
	public Node expansion (Node node) {
		List<Node> children = new ArrayList<>();
		for (Action action : Action.getPossibleActions()) {
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
		int timeAlive = 0;
		int levelsGained = 0;
		int previousLevel = simulatedGame.getPlayer().getLevel();
		
		while (true) {
			if (node.isTerminal()) {
				return timeAlive * timeAliveMod + levelsGained * levelsGainedMod + 
						(simulatedGame.playerWon() ? victoryMod : 0) + 
						(simulatedGame.getCurrentDay()) * dayCountMod;
			}
			if (simulatedGame.isFinalDay()) {
				simulatedGame.fightBoss();
				node = new Node(node, Action.getBossAction(simulatedGame.getPlayer().getLevel()));
				node.setTerminal(simulatedGame.gameOver());
			}
			else {
				Action action = Action.getRandomAction();
				node = simulate(action, node);
			}
			
			if (!simulatedGame.gameOver()) {
				int level = simulatedGame.getPlayer().getLevel();
				timeAlive++;
				if (level > previousLevel) {
					levelsGained += level - previousLevel;;
				}
				
				previousLevel = level;
			}
		}
	}
	
	public Node simulate (Action action, Node node) {
		Node newNode = new Node(node, action);
		simulatedGame.performAction(action);
		newNode.setTerminal(simulatedGame.gameOver());
		return newNode;
	}
	
	public Node childWithMaxUcb1 (Node node) {
		double ucb = Double.NEGATIVE_INFINITY;
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
}
