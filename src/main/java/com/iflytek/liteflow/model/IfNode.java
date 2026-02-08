package com.iflytek.liteflow.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 条件判断节点
 */
public class IfNode extends Node {
    private static final long serialVersionUID = 7472288101685007029L;

    /**
     * TRUE节点集合
     */
    private Set<Node> trueNodes = new LinkedHashSet<>();
    /**
     * FALSE节点集合
     */
    private Set<Node> falseNodes = new LinkedHashSet<>();

    public IfNode() {
        this.setType(NodeType.IF);
    }

    public IfNode(String id, String name) {
        super(id, name, NodeType.IF);
    }

    public IfNode(String id, String name, int hashcode) {
        super(id, name, NodeType.IF, hashcode);
    }

    public Set<Node> getTrueNodes() {
        return trueNodes;
    }

    public void setTrueNodes(Set<Node> trueNodes) {
        this.trueNodes = trueNodes;
    }

    public Set<Node> getFalseNodes() {
        return falseNodes;
    }

    public void setFalseNodes(Set<Node> falseNodes) {
        this.falseNodes = falseNodes;
    }
}