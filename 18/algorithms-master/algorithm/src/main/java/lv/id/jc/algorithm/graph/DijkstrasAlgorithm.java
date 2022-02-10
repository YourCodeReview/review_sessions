package lv.id.jc.algorithm.graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Stream.iterate;

/**
 * Algorithm for finding the fastest paths between nodes in a graph.
 * <p>
 * The algorithm uses information about edge's distance to find the fastest path.
 *
 * @param <T> the type of vertex
 * @author Jegors Čemisovs
 * @since 1.0
 */
public class DijkstrasAlgorithm<T> implements SearchAlgorithm<T> {

    @Override
    public List<T> findPath(Graph<T> graph, T source, T target) {
        var queue = new ArrayDeque<T>();
        var distances = new HashMap<T, Double>();
        var previous = new HashMap<T, T>();
        queue.add(source);
        distances.put(source, .0);

        while (!queue.isEmpty()) {
            var prev = queue.removeFirst();
            graph.edges(prev).forEach((node, time) -> {
                var distance = distances.get(prev) + time.doubleValue();
                if (distance < distances.getOrDefault(node, Double.MAX_VALUE)) {
                    previous.put(node, prev);
                    distances.put(node, distance);
                    queue.addLast(node);
                }
            });
        }
        if (previous.containsKey(target) || source.equals(target)) {
            var path = new LinkedList<T>();
            iterate(target, Objects::nonNull, previous::get).forEach(path::addFirst);
            return path;
        }
        return List.of();
    }

}
