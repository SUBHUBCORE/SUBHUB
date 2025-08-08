package io.subHub.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tree nodes, all those that need to implement tree nodes need to inherit this class
 *
 * @author By
 * @since 1.0.0
 */
public class TreeNode<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Primary key
     */
    private Long id;
    /**
     * Superior ID
     */
    private Long pid;
    /**
     * Child Node List
     */
    private List<T> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
