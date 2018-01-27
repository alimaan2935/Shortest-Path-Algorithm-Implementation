import javafx.util.Pair;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * File processing class to handle all the file reading and writing mechanism
 *
 * @author Ali Nawaz Maan <a.maan@uqconnect.edu.au>
 *
 */

public class FileProcessor {

    private ArrayList<Edge> edges;
    private Hashtable<Integer, Pair<String, String>> queries;
    private int noOfNodes;


    /**
     * Constructor for File processor.
     *
     * @param filename String filename
     * @param operation int operation type - 1 for environment reading, 2 for query reading, any other digit for output writing
     * @param result String resultant string for writing into output file
     */
    public FileProcessor(String filename, int operation, String result) {
        if (operation == 1){
            readEnvironment(filename);
        }else if (operation == 2) {
            queryReader(filename);
        }else {
            writeResult(filename, result);
        }
    }

    // getter method to get edges array list.
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    // getter method to get queries
    public Hashtable<Integer, Pair<String, String>> getQueries() {
        return queries;
    }

    // getter method to get number of nodes in the environment
    public int getNoOfNodes() {
        return noOfNodes;
    }

    /**
     * Environment reader method
     * reads environment file and generate edges
     * @param filename String
     */
    private void readEnvironment(String filename) {

        ArrayList<Edge> edges = new ArrayList<>();
        Hashtable<String, Integer> junctions = new Hashtable<>();
        int currentIndex = 0;

        try {
            InputStream fis=new FileInputStream(filename);
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));

            for (String line = br.readLine(); line != null; line = br.readLine()) {

                if (line.equals("")) {
                    break;
                }


                //Split the line
                String[] lineList = line.replaceAll("\\s+", "").split(";");

                //calculate normal edge length
                double edgelength = ((double) Integer.parseInt(lineList[3])/(double) Integer.parseInt(lineList[4])) * 2;

                //create first junction edge
                if (junctions.get(lineList[1]) != null) {
                    Edge junction1 = new Edge(lineList[1], 1 + lineList[0], junctions.get(lineList[1]), currentIndex, edgelength/2);
                    edges.add(junction1);
                }else {
                    Edge junction1 = new Edge(lineList[1], 1 + lineList[0], currentIndex, currentIndex+1, edgelength/2);
                    edges.add(junction1);
                    junctions.put(lineList[1], currentIndex);
                    currentIndex++;
                }

                //no of house edges
                int noOfHouseEdges = (Integer.parseInt(lineList[4])/2)-1;
                StringBuilder house1 = new StringBuilder();
                StringBuilder house2 = new StringBuilder();
                //create subsequent house edges
                for (int i = 0; i < noOfHouseEdges; i++) {
                    house1.append(i+1).append(lineList[0]);
                    house2.append(i+2).append(lineList[0]);
                    edges.add(new Edge(house1.toString(), house2.toString(), currentIndex, currentIndex+1, edgelength));
                    //edges.add(houseEdge);
                    currentIndex++;
                    house1.setLength(0);
                    house2.setLength(0);
                }

                //create last junction edge
                if (junctions.get(lineList[2]) != null) {
                    Edge junction2 = new Edge((Integer.parseInt(lineList[4])/2)+lineList[0], lineList[2], currentIndex, junctions.get(lineList[2]), edgelength/2);
                    edges.add(junction2);
                    currentIndex++;
                }else {
                    Edge junction2 = new Edge((Integer.parseInt(lineList[4])/2)+lineList[0], lineList[2], currentIndex, currentIndex+1, edgelength/2);
                    edges.add(junction2);
                    junctions.put(lineList[2], currentIndex+1);
                    currentIndex= currentIndex+2;
                }
                lineList = null; // experiment
            }
        }catch (IOException e){
            System.out.println("Error reading the file. Please remove any errors and try again.");
        }
        this.edges = edges;
        this.noOfNodes = currentIndex;
    }

    /**
     * Query file reader
     * @param filename String
     */
    private void queryReader(String filename) {
        Hashtable<Integer, Pair<String, String>> queries = new Hashtable<>();

        try {
            Scanner s = new Scanner(new File(filename));
            int lineNumber = 0;

            while (s.hasNextLine()) {

                String nextLine = s.nextLine();

                if (nextLine.equals("")) {
                    break;
                }

                String[] linelist = nextLine.replaceAll("\\s+", "").split(";");
                queries.put(lineNumber, new Pair<>(linelist[0], linelist[1]));
                lineNumber++;
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.queries = queries;
    }

    /**
     * Writing result into the given filename
     *
     * @param filename String
     * @param result resultant String
     */
    private static void writeResult (String filename, String result) {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            File outputFile = new File(filename);

            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }

            fw = new FileWriter(outputFile.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(result);
            bw.write("\n");

        }catch (IOException e) {
            System.out.println("Error writing the file");
            e.printStackTrace();
        }finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
