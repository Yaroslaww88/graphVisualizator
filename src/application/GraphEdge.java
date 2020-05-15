package application;

class GraphEdge {

    private int id;
    GraphEdgeUI edgeUI;

    private int to;
    private int from;
    private int weight;

    public GraphEdge(int id, int from, int to, int weight, GraphEdgeUI edgeUI) {
        this.id = id;
        this.edgeUI = edgeUI;

        this.to = to;
        this.from = from;
        this.weight = weight;
    }

    public int getTo() {
        return to;
    }

    public int getFrom() {
        return from;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int newWeight) { this.weight = newWeight; }

    public int getId() { return id; }
}
