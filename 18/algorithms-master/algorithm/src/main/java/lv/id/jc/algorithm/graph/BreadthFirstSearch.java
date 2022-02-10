package lv.id.jc.algorithm.graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.util.function.Predicate.not;
import static java.util.stream.Stream.iterate;

/**
 * Algorithm for finding the shortest paths between nodes in a graph.
 * <p>
 * The algorithm doesn't take into account the distance between nodes.
 *
 * @param <T> the type of vertex
 * @author Jegors Čemisovs
 * @since 1.0
 */
public class BreadthFirstSearch<T> implements SearchAlgorithm<T> {

    @Override
    public List<T> findPath(Graph<T> graph, T source, T target) {
        var queue = new ArrayDeque<T>();
        var visited = new HashSet<T>();
        var previous = new HashMap<T, T>();
        queue.add(source);

        while (!queue.isEmpty()) {
            var node = queue.removeFirst();
            if (target.equals(node)) {
                var path = new LinkedList<T>();
                iterate(node, Objects::nonNull, previous::get).forEach(path::addFirst);
                return path;
            }
            visited.add(node);
            graph.edges(node).keySet().stream()
                    .filter(not(visited::contains))
                    .forEach(it -> {
                        previous.computeIfAbsent(it, x -> node);
                        queue.addLast(it);
                    });
        }
        return List.of();
    }

}
