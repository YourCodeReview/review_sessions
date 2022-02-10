package lv.id.jc.sample

import lv.id.jc.algorithm.graph.BreadthFirstSearch
import lv.id.jc.algorithm.graph.DijkstrasAlgorithm
import lv.id.jc.algorithm.graph.Graph

def complexGraph = Graph.of([
        A: [B: 5, H: 2],
        B: [A: 5, C: 7],
        C: [B: 7, D: 3, G: 4],
        D: [C: 20, E: 4],
        E: [F: 5],
        F: [G: 6],
        G: [C: 4],
        H: [G: 3]
])

def app = new SampleApp(complexGraph)

app.printRoute('D', 'C');
app.printRoute('A', 'G');
app.printRoute('D', 'H');

class SampleApp {
    final fastest = new DijkstrasAlgorithm();
    final shortest = new BreadthFirstSearch();
    final graph

    SampleApp(graph) {
        this.graph = graph
    }

    def printRoute(source, target) {
        var routeOne = shortest.findPath(graph, source, target);
        var routeTwo = fastest.findPath(graph, source, target);

        println """Find the path from $source to $target
        - the shortest take ${graph.getDistance(routeOne)} min and the path is ${routeOne}
        - the fastest take ${graph.getDistance(routeTwo)} min and the path is ${routeTwo}
        """
    }

}
