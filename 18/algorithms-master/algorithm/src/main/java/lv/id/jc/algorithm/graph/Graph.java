package lv.id.jc.algorithm.graph;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * An interface for weighted directed graph (network)
 *
 * @param <T> the type of vertex in this graph
 * @author Jegors Čemisovs
 * @since 1.1
 */
@FunctionalInterface
public interface Graph<T> {
    /**
     * Creates a Graph object by given schema.
     * <p>
     * In a graph schema, each vertex is assigned an edge map.
     * If the vertex has no edges, then it should be assigned an empty map.
     *
     * @param schema of the graph
     * @param <T>    the type of vertex in this graph
     * @return graph object with given schema
     */
    static <T> Graph<T> of(Map<T, Map<T, Number>> schema) {
        return () -> schema;
    }

    /**
     * The schema of this graph.
     * <p>
     * In a graph schema, each vertex is assigned an edge map.
     * If the vertex has no edges, then it should be assigned an empty map.
     *
     * @return the graph scheme
     */
    Map<T, Map<T, Number>> schema();

    /**
     * Returns the edges of the given vertex,
     * or {@code null} if this graph contains no given vertex.
     *
     * <p>A return value of {@code null} does not <i>necessarily</i>
     * indicate that the specified vertex is not present in the graph;
     * it's also possible that in the graph schema, {@code null} was specified
     * for the edges of this vertex instead of an empty map.
     *
     * @param vertex vertex
     * @return all links for the given vertex
     * or null if no such vertex in the graph
     */
    default Map<T, Number> edges(T vertex) {
        return schema().get(vertex);
    }

    /**
     * Calculate the distance for the given path
     *
     * @param path the list of vertices representing the path
     * @return distance for the given path as double
     * @throws NullPointerException if {@code path} is incorrect and contains more than one vertex
     */
    default double getDistance(List<T> path) {
        return IntStream
                .range(1, path.size())
                .mapToObj(i -> edges(path.get(i - 1)).get(path.get(i)))
                .mapToDouble(Number::doubleValue)
                .sum();
    }
}
