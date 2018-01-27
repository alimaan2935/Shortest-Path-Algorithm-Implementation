# Discrete Search in City Map area to find shortest path
<p>
Navigation app is one of the most useful app ever invented. This is a  program that accepts as inputs:
</p>
<p>
- A text file containing a list of roads and its associated information.
- A text file containing a list of pairs of initial and goal locations.
</p>
<p>
The program outputs the shortest path for each query.
</p>

<h2>Input format</h2>
<p>
The input is two .txt files: One file contains environment information, i.e., the list of roads and associated information, while another file contains the queries.
</p>
<p>
The <strong> environment</strong> is represented as a list of road entries.
</p>
<p>
We assume that each road:
</p>
- Starts and ends at a junction.
- Is a two-way street.
- The length of a road is an integer number.
- Both the left and right side of the road are plots of lands.
<p>All plots have the
same size and have been numbered, such that all the plots on one side have odd numbers and on the other side have even numbers.
</p>
<p>
Each road entry is represented as a 5-tuple: [roadName, junction1, junction2, roadLength, nLots], where roadName is the name of the road that starts from junction1 and ends at junction2, has length roadLength units, and there are a total of nLots plots of land of equal size on the two sides of the road. When we move from junction1 to junction2, then the lots on our left have odd numbers, from 1 (closest to junction1) to nLots-1, while the lots on our right have even numbers, from 2 (closest to junction1) to nLots. Note that if a plot of land has more than one addresses, we will consider these different addresses to be different, as they refer to different entries of the land plot.
Each entry is written in a single line and each component of a tuple are separated by a semi-colon.
</p>
<p>
The queries file contains q lines, where q is the number of queries. Each query is asks for the shortest path to move from one plot of land to another. It is written in a single line, and consists of two addresses: the initial address followed by the goal address, separated by a semi-colon. The address’ format is the number of the plot followed by the name of the road.
The path length is based only on the distance to move from and to the road right in front of the plot of land of concern. Furthermore, the distance to move to the next door neighbour is equivalent to (2* roadLength/nLots).
</p>

<h2>
Output format </h2>
<p>
The output file contains q lines, where q is the number of queries in the input file. Each solution (the shortest path) is written in a single line. The solution at line q is the solution of the query at line q in the input file.
If there is a path that solves the query, the solution consists of two components, separated by a semi-colon. The first component is the path length. The second component is the path itself, written as a sequence of road and junctions travelled separated by a dash (“-“) sign. This sequence starts with the initial road and ends with the goal road.
If there is no path that solves the query, the written solution is no-path.
</p>
