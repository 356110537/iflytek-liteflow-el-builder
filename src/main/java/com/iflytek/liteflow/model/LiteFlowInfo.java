package com.iflytek.liteflow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 前端参数信息
 */
public class LiteFlowInfo implements Serializable {
    private static final long serialVersionUID = -7970127237360223253L;

    /**
     * 节点集合
     */
    private Set<? extends BaseNode> nodes = new LinkedHashSet<>();
    /**
     * 边集合
     */
    private List<BaseEdge> edges = new ArrayList<>();

    public Set<? extends BaseNode> getNodes() {
        return nodes;
    }

    public void setNodes(Set<? extends BaseNode> nodes) {
        this.nodes = nodes;
    }

    public List<BaseEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<BaseEdge> edges) {
        this.edges = edges;
    }
}