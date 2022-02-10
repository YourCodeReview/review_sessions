package lv.id.jc.sample;

import lv.id.jc.algorithm.graph.BreadthFirstSearch;
import lv.id.jc.algorithm.graph.DijkstrasAlgorithm;
import lv.id.jc.algorithm.graph.Graph;
import lv.id.jc.algorithm.graph.SearchAlgorithm;

import java.util.Map;

public class GraphApp {
    private static final Graph<Character> graph = Graph.of(Map.of(
            'A', Map.of('B', 5, 'H', 2),
            'B', Map.of('A', 5, 'C', 7),
            'C', Map.of('B', 7, 'D', 3, 'G', 4),
            'D', Map.of('C', 20, 'E', 4),
            'E', Map.of('F', 5),
            'F', Map.of('G', 6),
            'G', Map.of('C', 4),
            'H', Map.of('G', 3)
    ));
    private static final SearchAlgorithm<Character> fastest = new DijkstrasAlgorithm<>();
    private static final SearchAlgorithm<Character> shortest = new BreadthFirstSearch<>();

    public static void main(String[] args) {
        printRoute('D', 'C');
        printRoute('A', 'G');
        printRoute('D', 'H');
    }

    @SuppressWarnings("squid:S106")
    private static void printRoute(Character source, Character target) {
        var routeOne = shortest.findPath(graph, source, target);
        var routeTwo = fastest.findPath(graph, source, target);
        var message = """
                        
                Find the path from %s to %s
                    - the shortest take %.0f min and the path is %s
                    - the fastest take %.0f min and the path is %s"""
                .formatted(
                        source, target,
                        graph.getDistance(routeOne), routeOne,
                        graph.getDistance(routeTwo), routeTwo);

        System.out.println(message);
    }
}
