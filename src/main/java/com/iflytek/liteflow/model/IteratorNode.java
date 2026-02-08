package com.iflytek.liteflow.model;

/**
 * 迭代循环节点
 */
public class IteratorNode extends Node {
    private static final long serialVersionUID = -4083825003253280496L;

    public IteratorNode() {
        this.setType(NodeType.ITERATOR);
    }

    public IteratorNode(String id, String name) {
        super(id, name, NodeType.ITERATOR);
    }

    public IteratorNode(String id, String name, int hashcode) {
        super(id, name, NodeType.ITERATOR, hashcode);
    }
}