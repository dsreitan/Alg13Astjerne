package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public static class Node {

        private String id;
        private double latitude;
        private double longitude;
        private String name;
        private List<Edge> outEdges = new ArrayList<>();
        private List<Edge> inEdges = new ArrayList<>();

        private Node(String id, double breddegrad, double lengdegrad, String navn) {
            this.id = id;
            this.latitude = breddegrad;
            this.longitude = lengdegrad;
            this.name = navn;
        }

        public Node(String id, double breddegrad, double lengdegrad) {
            this.id = id;
            this.latitude = breddegrad;
            this.longitude = lengdegrad;
            this.name = "";
        }

        public String getId() {
            return id;
        }

        public void setId(String nodenr) {
            this.id = nodenr;
        }

        public double getBreddegrad() {
            return latitude;
        }

        public void setBreddegrad(double breddegrad) {
            this.latitude = breddegrad;
        }

        public double getLengdegrad() {
            return longitude;
        }

        public void setLengdegrad(double lengdegrad) {
            this.longitude = lengdegrad;
        }

        public String getNavn() {
            return name;
        }

        public void setNavn(String navn) {
            this.name = navn;
        }

        public List<Edge> getOutEdges() {
            return new ArrayList<>(this.outEdges);
        }

        public List<Edge> getInEdges() {
            return new ArrayList<>(this.inEdges);
        }
    }

    public static class Edge {

        private final String id;
        private final Node sourceNode;
        private final Node destinationNode;
        private final int weight;

        private Edge(String id, Node sourceNode, Node destinationNode, int weight) {
            this.id = id;
            this.sourceNode = sourceNode;
            this.destinationNode = destinationNode;
            this.weight = weight;
        }

        public String getId() {
            return this.id;
        }

        public Node getSourceNode() {
            return this.sourceNode;
        }

        public Node getDestinationNode() {
            return this.destinationNode;
        }

        public int getWeight() {
            return this.weight;
        }
    }
    private Map<String, Node> internalNodeMap = new HashMap<>();
    private Map<String, Edge> internalEdgeMap = new HashMap<>();

    public Node addNode(String id, double latitude, double longitude, String name) throws RuntimeException {
        if (id == null) {
            throw new NullPointerException();
        }

        name = (name == null) ? name = "" : name.trim();

        if (id.length() == 0) {
            throw new IllegalArgumentException("ID must be 1 charater or longer");
        }

        if (internalNodeMap.containsKey(id)) {
            throw new RuntimeException("Duplicate Node ID");
        }

        Node node = new Node(id, latitude, longitude, name);
        internalNodeMap.put(node.id, node);

        return node;
    }

    public Edge addEdge(String id, Node source, Node destination, int weight) throws RuntimeException {

        if (id == null || source == null || destination == null) {
            throw new NullPointerException();
        }

        id = id.trim();

        if (id.length() == 0) {
            throw new IllegalArgumentException("ID must be 1 charater or longer");
        }

        if (internalEdgeMap.containsKey(id)) {
            throw new RuntimeException("Duplicate Edge ID");
        }

        Edge edge = new Edge(id, source, destination, weight);
        internalEdgeMap.put(edge.id, edge);

        edge.sourceNode.outEdges.add(edge);
        edge.destinationNode.inEdges.add(edge);
        return edge;
    }

    public Node getNode(String nodeId) {
        return internalNodeMap.get(nodeId);
    }

    public Edge getEdge(String edgeId) {
        return internalEdgeMap.get(edgeId);
    }

    public List<Node> getNodes() {
        return new ArrayList<>(internalNodeMap.values());
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(internalEdgeMap.values());
    }
}