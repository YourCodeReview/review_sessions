package lv.id.jc.algorithm.graph

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title("Generic Graph")
@Narrative("A generic implementation of Graph structure")
class GraphSpec extends Specification {

    def "should return edges for a given node"() {
        given: 'a simple graph with three nodes'
        def graph = Graph.of([
                A: [B: 7, C: 2],
                B: [A: 3, C: 5],
                C: [A: 1, B: 3]
        ])

        expect: 'The method returns expected edges for given node'
        graph.edges(node) == expected

        where:
        node | expected
        'A'  | [B: 7, C: 2]
        'B'  | [A: 3, C: 5]
        'C'  | [A: 1, B: 3]
    }

    def 'should calculate distance for a path'() {
        given: "a complex graph with eight nodes"
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

        expect: 'the distance for a path correctly calculated'
        graph.getDistance(path) == distance as double

        where: 'path and expected distance'
        path                      | distance
        ['A']                     | 0
        ['A', 'B']                | 5
        ['B', 'A']                | 5
        ['A', 'B', 'A']           | 10
        ['A', 'B', 'A', 'B']      | 15
        ['C', 'D']                | 3
        ['D', 'C']                | 20
        ['D', 'E', 'F', 'G', 'C'] | 19
    }

    def 'should be zero distance for an empty path'() {
        given: 'any graph'
        def graph = Graph.of(_ as Map)

        expect: 'the distance is zero for an empty path'
        graph.getDistance([]) == 0
    }

    def 'should be zero distance for any one node path'() {
        given: 'any graph'
        def graph = Graph.of(_ as Map)

        expect: 'the zero distance for any one-node path'
        graph.getDistance(oneNodePath) == 0

        where: 'the node may be of any type and even non-existent'
        oneNodePath << [
                ['A'], ['B'], [2], ['X' as char], [12.56]
        ]
    }

    def 'should throw NPE for incorrect path'() {
        given: "a medium graph with five nodes"
        def graph = Graph.of([
                A: [B: 5],
                B: [A: 5, C: 10],
                C: [B: 20, D: 5],
                D: [E: 5],
                E: [B: 5]
        ])

        when: 'we call the method with incorrect path'
        graph.getDistance(incorrectPath)

        then: 'the NPE thrown'
        thrown NullPointerException

        where: 'path is more then one node'
        incorrectPath << [
                ['E', 'D'], ['A', 'C'], ['A', 'B', 'D']
        ]
    }
}