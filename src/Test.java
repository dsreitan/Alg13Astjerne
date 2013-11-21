
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Date;
import Graph.*;
import Graph.Graph.Node;

public class Test {

    public static void main(String[] args) throws Exception {
        Graph graph = new Graph();
        try {
            String nodefil = "src/noder.txt";
            FileReader fileReader = new FileReader(new File(nodefil));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            int i = Integer.parseInt(stringTokenizer.nextToken());
            for (int j = 0; j < i; j++) {
                stringTokenizer = new StringTokenizer(bufferedReader.readLine());
                graph.addNode(
                        stringTokenizer.nextToken(),
                        Double.parseDouble(stringTokenizer.nextToken()),
                        Double.parseDouble(stringTokenizer.nextToken()),
                        stringTokenizer.nextToken());
            }
            
            String kantfil = "src/kanter.txt";
            fileReader = new FileReader(new File(kantfil));
            bufferedReader = new BufferedReader(fileReader);

            // Antall linjer i dokumentet
            int k = 0;
            while (bufferedReader.readLine() != null) {
                k++;
            }

            fileReader = new FileReader(new File(kantfil));
            bufferedReader = new BufferedReader(fileReader);
            stringTokenizer = new StringTokenizer(bufferedReader.readLine());

            for (int l = 0; l < k; l++) {
                graph.addEdge(
                        Integer.toString(l),
                        graph.getNode(stringTokenizer.nextToken()),
                        graph.getNode(stringTokenizer.nextToken()),
                        Integer.parseInt(stringTokenizer.nextToken()));
                String distance = stringTokenizer.nextToken();
                String speed = stringTokenizer.nextToken();
                stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            }
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            System.out.println("*** " + e + " **");
        }
        Node source = graph.getNode("0"); // 14881
        Node target = graph.getNode("3"); // 165
        if (source == null) {
            throw new NullPointerException();
        }

        Astjerne a = new Astjerne(graph);
        Date start = new Date();
        
        System.out.println(a.execute(source, target));
        Date stopp = new Date();

        //a.dump();
        double tid = stopp.getTime() - start.getTime();

        System.out.println("Gjennomløpstid: " + tid + " ms");
        System.out.println(source.getId() + " -> " + target.getId() + " = " + a.getCumulativeNodeDistance(target));
        System.out.println("* nodes: " + a.settledNodes.size());
    }
}