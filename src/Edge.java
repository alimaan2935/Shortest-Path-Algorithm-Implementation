/**
 * An edge between 2 group of houses. This Edge is to be added into 2 group of houses
 * From group and to group. Although the edge is bidirectional, we have to specify thr 2 ends.
 *
 * @author Ali Nawaz Maan <a.maan@uqconnect.edu.au>
 *
 */
public class Edge {

    private int fromNodeIndex;
    private int toNodeIndex;
    private String fromNodeName;
    private String toNodeName;
    private double length;

    /**
     * Constructor for Edge
     *
     * @param fromNodeName String name of house group from where the edge is originating
     * @param toNodeName String name of house group to where the edge is heading
     * @param fromNodeIndex int index of "from" house group in the graph
     * @param toNodeIndex int index of "to" house group in the graph
     * @param length double - edge length between 2 house groups
     */
    public Edge (String fromNodeName, String toNodeName, int fromNodeIndex, int toNodeIndex, double length) {
        this.fromNodeName = fromNodeName;
        this.toNodeName = toNodeName;
        this.fromNodeIndex = fromNodeIndex;
        this.toNodeIndex = toNodeIndex;
        this.length = length;
    }

    // Getter methods for the edge

    public int getFromNodeIndex() {
        return fromNodeIndex;
    }

    public int getToNodeIndex() {
        return toNodeIndex;
    }

    public String getFromNodeName() {
        return fromNodeName;
    }

    public String getToNodeName() {
        return toNodeName;
    }

    public double getLength() {
        return length;
    }
}
