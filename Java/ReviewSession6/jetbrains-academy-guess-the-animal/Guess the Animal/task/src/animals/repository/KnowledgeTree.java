package animals.repository;

import static java.lang.System.Logger.Level.TRACE;

public class KnowledgeTree {
    private static final System.Logger LOGGER = System.getLogger("");
    private TreeNode<String> root;
    private TreeNode<String> current;

    public void reset() {
        current = root;
    }

    public boolean isAnimal() {
        return current.isLeaf();
    }

    public boolean isStatement() {
        return !isAnimal();
    }

    public TreeNode<String> getCurrent() {
        return current;
    }

    public void next(final boolean direction) {
        current = direction ? current.getRight() : current.getLeft();
    }

    public TreeNode<String> getRoot() {
        return root;
    }

    public void setRoot(final TreeNode<String> root) {
        this.root = root;
        this.current = root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void addAnimal(final String animal, final String statement, final boolean isRight) {
        LOGGER.log(TRACE, "entering {0}, {1}, {2}", animal, statement, isRight);

        final var newAnimal = new TreeNode<>(animal);
        final var oldAnimal = new TreeNode<>(current.getData());
        current.setData(statement);
        current.setRight(isRight ? newAnimal : oldAnimal);
        current.setLeft(isRight ? oldAnimal : newAnimal);

        LOGGER.log(TRACE, "exiting {0}", animal);
    }

    public boolean deleteAnimal(final String animal) {
        LOGGER.log(TRACE, "entering, animal: {0}", animal);

        final var isSuccessful = deleteAnimal(animal, root, null);

        LOGGER.log(TRACE, "exiting, is successful: {0}", isSuccessful);
        return isSuccessful;
    }

    private boolean deleteAnimal(final String animal, final TreeNode<String> child, final TreeNode<String> parent) {
        if (child.isLeaf() && animal.equals(child.getData())) {
            final var source = parent.getRight() == child ? parent.getLeft() : parent.getRight();
            parent.setData(source.getData());
            parent.setRight(source.getRight());
            parent.setLeft(source.getLeft());
            return true;
        }
        return !child.isLeaf() &&
                (deleteAnimal(animal, child.getRight(), child) || deleteAnimal(animal, child.getLeft(), child));
    }
}
