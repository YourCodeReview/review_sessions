package lv.id.jc.algorithm.graph

import spock.lang.*

@Title("Dijkstra's Algorithm")
@See("https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm")
@Narrative("Dijkstra's algorithm is an algorithm for finding the fastest paths between nodes in a graph")
class DijkstrasAlgorithmSpec extends Specification {
    @Subject
    def algorithm = new DijkstrasAlgorithm()

    def 'should find a route for a simple graph'() {
        given:
        def graph = Graph.of([
                A: [B: 7, C: 2],
                B: [A: 3, C: 5],
                C: [A: 1, B: 3]
        ])

        when:
        def path = algorithm.findPath(graph, source, target)

        then:
        path == fastest

        and:
        graph.getDistance(path) == time as double

        where:
        source | target || time | fastest
        'A'    | 'A'    || 0    | ['A']
        'B'    | 'B'    || 0    | ['B']
        'C'    | 'C'    || 0    | ['C']
        'A'    | 'B'    || 5    | ['A', 'C', 'B']
    }

    def 'should find a route for a medium graph'() {
        given:
        def graph = Graph.of([
                A: [B: 5],
                B: [A: 5, C: 10],
                C: [B: 20, D: 5],
                D: [E: 5],
                E: [B: 5]
        ])

        when:
        def path = algorithm.findPath(graph, source, target)

        then:
        path == fastest

        and:
        graph.getDistance(path) == time as double

        where:
        source | target || time | fastest
        'A'    | 'A'    || 0    | ['A']
        'B'    | 'B'    || 0    | ['B']
        'A'    | 'B'    || 5    | ['A', 'B']
        'B'    | 'A'    || 5    | ['B', 'A']
        'A'    | 'C'    || 15   | ['A', 'B', 'C']
        'C'    | 'A'    || 20   | ['C', 'D', 'E', 'B', 'A']
    }

    def 'should find a route for a complex graph'() {
        given:
        def graph = Graph.of([
                A: [B: 5, H: 2],
                B: [A: 5, C: 7],
                C: [B: 7, D: 3, G: 4],
                D: [C: 20, E: 4],
                E: [F: 5],
                F: [G: 6],
                G: [C: 4],
                H: [G: 3]
        ])

        when:
        def path = algorithm.findPath(graph, source, target)

        then:
        path == fastest

        and:
        graph.getDistance(path) == time as double

        where:
        source | target || time | fastest
        'A'    | 'A'    || 0    | ['A']
        'B'    | 'B'    || 0    | ['B']
        'A'    | 'B'    || 5    | ['A', 'B']
        'B'    | 'A'    || 5    | ['B', 'A']
        'A'    | 'C'    || 9    | ['A', 'H', 'G', 'C']
        'C'    | 'A'    || 12   | ['C', 'B', 'A']
        'A'    | 'G'    || 5    | ['A', 'H', 'G']
        'C'    | 'D'    || 3    | ['C', 'D']
        'D'    | 'C'    || 19   | ['D', 'E', 'F', 'G', 'C']
        'B'    | 'D'    || 10   | ['B', 'C', 'D']
        'D'    | 'B'    || 26   | ['D', 'E', 'F', 'G', 'C', 'B']
        'D'    | 'H'    || 33   | ['D', 'E', 'F', 'G', 'C', 'B', 'A', 'H']
    }

    def 'should thrown NPE for an empty graph'() {
        given:
        def graph = Graph.of([:])

        when:
        algorithm.findPath(graph, 'A', 'B')

        then:
        thrown NullPointerException
    }

    def "should return an empty path if can't find a route"() {
        given: 'a simple graph with no edge between nodes'
        def graph = Graph.of([A: [:], B: [:]])

        when:
        def path = algorithm.findPath(graph, 'A', 'B')

        then:
        path == []
    }
}
