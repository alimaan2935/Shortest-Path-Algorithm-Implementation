import java.util.Comparator;

/**
 * A Comparator for priority queue.
 * It compares the node's distance from source and assign priority based on the
 * lowest distance.
 *
 * @author Ali Nawaz Maan <a.maan@uqconnect.edu.au>
 *
 */

public class MinSrcDistComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {

        return Double.compare(o1.getDistanceFromSource(), o2.getDistanceFromSource());
    }
}
