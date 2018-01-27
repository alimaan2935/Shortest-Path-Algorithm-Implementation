import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Main graph class to generate Navigational map.
 * It gets the query and find the shortest path between 2 houses.
 * Give the result in a string to write in the output file.
 *
 * @author Ali Nawaz Maan <a.maan@uqconnect.edu.au>
 *
 */

public class Graph {

    private ArrayList<Node> nodes = new ArrayList<>();
    private int noOfNodes;
    private Node startHouse;
    private Node goalHouse;

    /**
     * Constructor for Graph object.
     * Generates nodes and assign each edge to 2 nodes - from and to
     * @param edges ArrayList of Edges read from environment file.
     * @param noOfNodes int number of nodes in the Navigational map - graph
     */
    public Graph (ArrayList<Edge> edges, int noOfNodes) {

        this.noOfNodes = noOfNodes;

        //generate all nodes
        for (int n = 0; n < this.noOfNodes; n++) {
            this.nodes.add(new Node());
        }

        int noOfEdges = edges.size();

        // add each edge to 2 nodes, from node and to node
        // Also, set each node name - this will call the house generator method in
        // Node class to generate houses automatically.
        for (int i = 0; i < noOfEdges; i++) {

            //from
            this.nodes.get(edges.get(i).getFromNodeIndex()).getEdges().add(edges.get(i));
            this.nodes.get(edges.get(i).getFromNodeIndex()).setName(edges.get(i).getFromNodeName());

            //to
            this.nodes.get(edges.get(i).getToNodeIndex()).getEdges().add(edges.get(i));
            this.nodes.get(edges.get(i).getToNodeIndex()).setName(edges.get(i).getToNodeName());
        }
    }

    /**
     * Main Algorithm to find the shortest path between the start house and goal house.
     * Implemented uniform cost search algorithm to find the shortest path as the navigational
     * map is weighted based on road length and distance between each house group.
     * Exploring neighbors mechanism is handled with priority queue with an implementation
     * of comparator interface.
     *
     * @param startHouse String starting point house name
     * @param goalHouse String goal house name
     */
    public void getShortestPath(String startHouse, String goalHouse) {

        //get start and goal house nodes
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getHouse1().equals(startHouse) || nodes.get(i).getHouse2().equals(startHouse)) {
                this.startHouse = nodes.get(i);
            } else if (nodes.get(i).getHouse1().equals(goalHouse) || nodes.get(i).getHouse2().equals(goalHouse)) {
                this.goalHouse = nodes.get(i);
            }
        }


        //set starting point "distance from start" = 0
        this.startHouse.setDistanceFromSource(0);

        //Comparator and priority queue
        Comparator<Node> comparator = new MinSrcDistComparator();
        PriorityQueue<Node> nextHouses = new PriorityQueue<>(this.noOfNodes, comparator);

        //add starting house to queue
        nextHouses.add(this.startHouse);

        //loop until the queue is empty
        while (!nextHouses.isEmpty()) {

            Node nextHouse = nextHouses.remove();

            //House is visited as it is removed from queue
            nextHouse.setVisited(true);

            /*
              Break the loop if the current visiting house is actually the goal house.
             */
            if (nextHouse.equals(this.goalHouse)) {
                break;
            }

            // for all the neighbors set distance from source and add neighbors to priority queue
            // priority queue will handle the ordering based on the comparator
            ArrayList<Edge> neighborEdges = nextHouse.getEdges();

            for (int i=0; i<neighborEdges.size(); i++) {

                // set the new distance equals to current visiting house distance from source and neighbor's edge length
                double newDistance = nextHouse.getDistanceFromSource() + neighborEdges.get(i).getLength();
                Node neighbor = new Node();

                //Assign the neighbor based on the house name - we don't want to get the neighbor house which we are currently visiting
                if(!neighborEdges.get(i).getFromNodeName().equals(nextHouse.getName())) {
                    neighbor = this.nodes.get(neighborEdges.get(i).getFromNodeIndex());
                }else if(!neighborEdges.get(i).getToNodeName().equals(nextHouse.getName())) {
                    neighbor = this.nodes.get(neighborEdges.get(i).getToNodeIndex());
                }

                // set the neighbor new distance from source and add the neighbor to priority queue
                if (newDistance < neighbor.getDistanceFromSource()) {
                    neighbor.setDistanceFromSource(newDistance);
                    neighbor.setPrevNodeInPath(nextHouse);
                    if (!neighbor.isVisited()) {
                        nextHouses.add(neighbor);
                    }
                }
            }

        }
    }

    /**
     * Getting the resultant string for writing the output into a file.
     * The method constructs the string using StringBuilder into a specified format.
     * Logic is to get the goal house node and traverse back the the starting node
     * and build a string of path.
     *
     * @return String resultant string containing shortest path length and visiting points for that path.
     */
    public String getResult() {

        // If no path exists
        if (this.goalHouse.getDistanceFromSource() == (double)(Integer.MAX_VALUE)) {
            return "no-path";
        }

        //stack data structure to handle the ordering of the visiting path
        Stack<String> path = new Stack<>();
        Node pathHouse = this.goalHouse;
        path.push(pathHouse.getRoadName());

        while (pathHouse.getPrevNodeInPath() != null) {
            path.push(pathHouse.getPrevNodeInPath().getRoadName());
            pathHouse = pathHouse.getPrevNodeInPath();
        }

        // building the result string with path distance and actual road path
        // by popping string from the stack
        StringBuilder resultPath = new StringBuilder();
        resultPath.append(this.goalHouse.getDistanceFromSource());
        resultPath.append(" ; ");

        String duplicateCheck = path.peek();
        resultPath.append(path.pop());

        int size = path.size();

        for (int i = 0; i<size; i++) {

            String pop = path.pop();

            if (!pop.equals(duplicateCheck)) {
                resultPath.append(" - ");
                resultPath.append(pop);
            }
            duplicateCheck = pop;
        }

        return resultPath.toString();
    }

    /**
     * Resets all nodes to start the next query in line
     * Just call the resetNode method on each node
     */
    public void resetNodes () {
        for (Node i : nodes) {
            i.resetNode();
        }
    }

}
