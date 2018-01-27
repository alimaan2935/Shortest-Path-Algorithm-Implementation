/**
 * Main class to handle the string arguments and calling the file processor and graph object methods.
 * FileProcessor method does all the tricks of reading the file and writing the result of each query into
 * output.txt file
 *
 * @author Ali Nawaz Maan <a.maan@uqconnect.edu.au>
 *
 */

public class Main {

    public static void main(String[] args) {

        try {

            // process environment and query files
            FileProcessor f = new FileProcessor(args[0], 1, "env");
            FileProcessor f2 = new FileProcessor(args[1], 2, "query");
            FileProcessor f3;

            //generate navigational map  - initialize graph object with list of edges and number of nodes
            Graph g = new Graph(f.getEdges(), f.getNoOfNodes());

            // run each query from query file and give the resultant string to file processor object
            for (int i = 0; i < f2.getQueries().size(); i++) {

                long start = System.currentTimeMillis();
                g.getShortestPath(f2.getQueries().get(i).getKey(), f2.getQueries().get(i).getValue());
                long end = System.currentTimeMillis();
                System.out.println("Time for this query: " + (end - start) + " milliseconds");

                f3 = new FileProcessor(args[2], 0, g.getResult());

                // reset all nodes to start fresh for next query
                g.resetNodes();
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("**************   Nothing to worry about - just run the jar file with correct filenames and command line arguments" +
                    "    ************");
        }

    }
}
