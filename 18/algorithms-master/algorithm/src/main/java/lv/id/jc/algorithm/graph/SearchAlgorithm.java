package lv.id.jc.algorithm.graph;

import java.util.List;

/**
 * A functional interface for graph search algorithm
 *
 * @param <T> the type of vertex
 * @author Jegors Čemisovs
 * @since 1.0
 */
@FunctionalInterface
public interface SearchAlgorithm<T> {
    /**
     * Find the path from the source node to the target
     *
     * @param graph  The graph in which we search for the path
     * @param source Search starting point identifier
     * @param target Search finish point identifier
     * @return Path found or empty list if path cannot be found
     */
    List<T> findPath(Graph<T> graph, T source, T target);
}
