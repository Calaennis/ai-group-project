import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Node {
    private double winScore;
    private int visitCount;
    private List<Node> children;

    public Node () {
        children = new ArrayList<>();
        winScore = 0.0;
        visitCount = 0;
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

    public void setWinScore (double winScore) {
        this.winScore = winScore;
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
}
