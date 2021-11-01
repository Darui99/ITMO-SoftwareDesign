package graph;

public class Edge {
    public int firstVer, secondVer;

    public Edge(int firstVer, int secondVer) {
        if (firstVer < secondVer) {
            this.firstVer = firstVer;
            this.secondVer = secondVer;
        } else {
            this.firstVer = secondVer;
            this.secondVer = firstVer;
        }
    }
}
