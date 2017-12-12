import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Node {
    private double winScore;
    private int visitCount;
    private List<Node> children;
    private Action action;
    private boolean terminal;
    private Node parent;

    public Node (Node parent, Action action) {
        children = new ArrayList<>();
        winScore = 0.0;
        visitCount = 0;
        this.action = action;
        this.parent = parent;
        terminal = false;
    }

    public double getWinScore () {
        return winScore;
    }

    public int getVisitCount () {
        return visitCount;
    }

    public List<Node> getChildren () {
        return children;
    }
    
    public boolean isTerminal () {
    	return terminal;
    }
    
    public Action getAction () {
    	return action;
    }
    
    public Node getParent () {
    	return parent;
    }
    
    public void setTerminal (boolean terminal) {
    	this.terminal = terminal;
    }
    
    public void setChildren (List<Node> children) {
    	this.children = children;
    }
    
    public boolean hasChildren () {
    	return children.size() > 0;
    }
    
    public int size () {
    	return children.size();
    }

    public void setWinScore (double winScore) {
    	this.winScore = (this.winScore + winScore) / visitCount;
    }

    public void incrementVisitCount () {
        visitCount++;
    }

    public void addChildren () {

    }
    
    public void simulatePlaythrough () {
    	
    }

    public Node getRandomChildNode () {
        if (children.size() > 0) {
            return children.get(new Random().nextInt(children.size()));
        }
        return null;
    }

    @Override
    public String toString () {
        String str =String.format("WS: %f\nVC: %d\n#C: %d", winScore, visitCount, children.size());
        for (Node child : children) {
            str += child.toString();
        }
        return str;
    }
}
