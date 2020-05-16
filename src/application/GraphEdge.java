package application;

import java.util.ArrayList;

class GraphEdge {

    private int id;
    GraphEdgeUI edgeUI;

    private GraphVertex to;
    private GraphVertex from;
    private int weight;
    private boolean blocked = false;

    public GraphEdge(int id, GraphVertex from, GraphVertex to, int weight, GraphEdgeUI edgeUI) {
        this.id = id;
        this.edgeUI = edgeUI;

        this.to = to;
        this.from = from;
        this.weight = weight;
    }

    public int getTo() {
        return to.id;
    }

    public int getFrom() {
        return from.id;
    }

    public int getWeight() {
        return weight;
    }

    public void lockChanges() {
        this.blocked = true;
        this.edgeUI.lockWeightInputField();
    }

    public void unlockChanges() {
        this.blocked = false;
        this.edgeUI.unlockWeightInputField();
    }

    public void setWeight(int newWeight) {
        if (!blocked)
            this.weight = newWeight;
    }

    public int getId() { return id; }
}
