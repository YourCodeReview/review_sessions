package animals.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode<T> {
    private T data;
    private TreeNode<T> right;
    private TreeNode<T> left;

    TreeNode() {
    }

    public TreeNode(final T data) {
        this.data = data;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return left == null && right == null;
    }

    public T getData() {
        return data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(final TreeNode<T> right) {
        this.right = right;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(final TreeNode<T> left) {
        this.left = left;
    }
}
