package com.iflytek.liteflow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 前端参数信息
 */
public class Loop<N extends BaseNode, E extends BaseEdge> implements Serializable {
    private static final long serialVersionUID = -7970127237360223253L;

    /**
     * 节点集合
     */
    private Collection<N> nodes = new LinkedHashSet<>();
    /**
     * 边集合
     */
    private Collection<E> edges = new ArrayList<>();

    public Collection<N> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<N> nodes) {
        this.nodes = nodes;
    }

    public Collection<E> getEdges() {
        return edges;
    }

    public void setEdges(Collection<E> edges) {
        this.edges = edges;
    }
}