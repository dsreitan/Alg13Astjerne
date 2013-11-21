
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import Graph.*;
import Graph.Graph.Node;
import Graph.Graph.Edge;

public class Astjerne {

    private final boolean DIJKSTRAS = false;
    public int numberOfNodesFromSource = 0;
    private final List<Node> nodes;
    private final List<Edge> edges;
    public Set<Node> settledNodes;
    public Set<Node> unSettledNodes;
    public Map<Node, Node> predecessors;
    public Map<Node, Integer> distance;

    public Astjerne(Graph graph) {
        nodes = graph.getNodes();
        edges = graph.getEdges();
    }

    public Integer execute(Node source, Node target) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();

        distance.put(source, 0);
        unSettledNodes.add(source);

        while (!unSettledNodes.isEmpty()) {
            Node node = getUnSettledNodeWithLowestDistance();
            settleNode(node);
                    
            if (getCumulativeNodeDistance(node) == Integer.MAX_VALUE) {
                break;
            }

            for (Node neighbor : getNeighbors(node)) {
                if (isSettled(neighbor)) {
                    continue;
                }

                if (getCumulativeNodeDistance(neighbor)
                        > getCumulativeNodeDistance(node) + getDistanceBetweenNeighborNodes(node, neighbor)) {

                    distance.put(neighbor, getCumulativeNodeDistance(node)
                            + getDistanceBetweenNeighborNodes(node, neighbor));
                    predecessors.put(neighbor, node);
                    unSettledNodes.add(neighbor);
                }
                if (neighbor.equals(target)){
                    return getCumulativeNodeDistance(neighbor);
                }
            }
        }
        return -1;
    }

    private Node getUnSettledNodeWithLowestDistance() {
        Node lowest = null;
        for (Node node : unSettledNodes) {
            if (lowest == null) {
                lowest = node;
            } else if (getCumulativeNodeDistance(node) < getCumulativeNodeDistance(lowest)) {
                lowest = node;
            }
        }
        return lowest;
    }

    public Integer getCumulativeNodeDistance(Node node) {
        Integer distance = this.distance.get(node);
        if (distance == null) {
            return Integer.MAX_VALUE;
        } else {
            return distance;
        }
    }

    private int getDistanceBetweenNeighborNodes(Node source, Node target) {
        for (Edge edge : source.getOutEdges()) {
            if (edge.getSourceNode().equals(source) && edge.getDestinationNode().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Source and target are not neighbors");
    }
    
    public double getTotalDistance(Node a, Node b){
        
        return 0;
    }

    private void settleNode(Node node) {
        unSettledNodes.remove(node);
        settledNodes.add(node);
    }

    private boolean isSettled(Node node) {
        return settledNodes.contains(node);
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : node.getOutEdges()) {
            neighbors.add(edge.getDestinationNode());
        }
        return neighbors;
    }

    public static double rad(double x) {
        return x * Math.PI / 180;
    }

    public double getDistanceFromCoords(Node a, Node b) {
        int earthRadius = 6371;

        return earthRadius * Math.acos(
                Math.sin(rad(a.getBreddegrad()))
                * Math.sin(rad(b.getBreddegrad()))
                + Math.cos(rad(a.getBreddegrad()))
                * Math.cos(rad(b.getBreddegrad()))
                * Math.cos(Math.abs(rad(b.getLengdegrad() - a.getLengdegrad()))));
    }

    public double getTimeUsedFromDistance(double distance) {
        return distance / 100 * 60 * 60 * 100; // time -> minutt -> sekund -> millisekund
    }

    public void dump() {
        System.out.println("NodeID \t PredecessorID \t Runtime");
        for (Node node : nodes) {
            String nodeID = node.getId();
            String predecessorID = "Out of reach";
            String runtime = "";

            // If null; no predecessors -> no in node
            Node predecessor = predecessors.get(node);
            if (predecessor != null) {
                predecessorID = predecessor.getId();
                runtime = distance.get(node).toString();
            } else if (predecessor == null && distance.get(node) != null) {
                predecessorID = "Start node";
                runtime = "0";
            }

            if (predecessorID.length() < 5) {
                predecessorID += "\t";
            }

            System.out.println(nodeID + "\t " + predecessorID + " \t " + runtime);
        }



//        System.out.println(getDistanceFromCoords(nodes.get(3), nodes.get(1)) + " m");
//        System.out.println(getTimeUsedFromDistance(getDistanceFromCoords(nodes.get(3), nodes.get(1))));
//
//        System.out.println(getDistanceFromCoords(new Node("1", 63.4276336, 10.4324587), new Node("2", 63.4108125, 10.4250984)));
//        System.out.println(getTimeUsedFromDistance(getDistanceFromCoords(new Node("1", 63.4276336, 10.4324587), new Node("2", 63.4108125, 10.4250984))));
    }
}