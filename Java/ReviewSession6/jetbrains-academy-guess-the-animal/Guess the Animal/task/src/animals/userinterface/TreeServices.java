package animals.userinterface;

import animals.repository.KnowledgeTree;
import animals.repository.TreeNode;

import java.util.*;
import java.util.function.UnaryOperator;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.summarizingInt;

public final class TreeServices extends TextInterface {
    private static final UnaryOperator<String> toName = animal -> applyRules("animalName", animal);

    private final KnowledgeTree knowledgeTree;
    private final Map<String, List<String>> animals = new HashMap<>();

    TreeServices(final KnowledgeTree knowledgeTree) {
        this.knowledgeTree = knowledgeTree;
    }

    void list() {
        println("tree.list.animals");
        getAnimals().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> printf("tree.list.printf", entry.getKey(), entry.getValue().size()));
    }

    void search() {
        final var animal = ask("tree.search");
        final var facts = getAnimals().getOrDefault(animal, emptyList());
        final var feedback = facts.isEmpty() ? "tree.search.noFacts" : "tree.search.facts";
        println(feedback, animal);
        facts.forEach(fact -> printf("tree.search.printf", fact));
    }

    void delete() {
        if (knowledgeTree.getRoot().isLeaf()) {
            println("tree.delete.root");
            return;
        }
        final var animal = ask("animal");
        final var feedback = knowledgeTree.deleteAnimal(animal) ? "successful" : "fail";
        println("tree.delete." + feedback, toName.apply(animal));
    }

    void statistics() {
        final var stats = getStatistics();
        println("tree.stats.title");
        println("tree.stats.root", knowledgeTree.getRoot().getData());
        println("tree.stats.nodes", stats.getCount() * 2 - 1);
        println("tree.stats.animals", stats.getCount());
        println("tree.stats.statements", stats.getCount() - 1);
        println("tree.stats.height", stats.getMax());
        println("tree.stats.minimum", stats.getMin());
        println("tree.stats.average", stats.getAverage());
    }

    private IntSummaryStatistics getStatistics() {
        return getAnimals().values().stream().collect(summarizingInt(List::size));
    }

    void print() {
        printNode(knowledgeTree.getRoot(), false, " ");
    }

    private void printNode(final TreeNode<String> node, final boolean isRight, String prefix) {
        if (node.isLeaf()) {
            printf("tree.print.printf", prefix, getLine(isRight), node.getData());
            return;
        }
        printf("tree.print.printf", prefix, getLine(isRight), applyRules("question", node.getData()));
        prefix += isRight ? resourceBundle.getString("tree.print.vertical") : " ";
        printNode(node.getRight(), true, prefix);
        printNode(node.getLeft(), false, prefix);
    }

    private String getLine(final boolean isBranch) {
        return resourceBundle.getString(isBranch ? "tree.print.branch" : "tree.print.corner");
    }

    private Map<String, List<String>> getAnimals() {
        animals.clear();
        collectAnimals(knowledgeTree.getRoot(), new LinkedList<>());
        return animals;
    }

    private void collectAnimals(final TreeNode<String> node, final Deque<String> facts) {
        if (node.isLeaf()) {
            animals.put(toName.apply(node.getData()), List.copyOf(facts));
            return;
        }
        final var statement = node.getData();
        facts.add(statement);
        collectAnimals(node.getRight(), facts);
        facts.removeLast();
        facts.add(applyRules("negative", statement));
        collectAnimals(node.getLeft(), facts);
        facts.removeLast();
    }

}
