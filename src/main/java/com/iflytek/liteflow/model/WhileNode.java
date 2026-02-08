package com.iflytek.liteflow.model;

/**
 * WHILE循环节点
 */
public class WhileNode extends Node {
    private static final long serialVersionUID = -7147277729500879616L;

    public WhileNode() {
        this.setType(NodeType.WHILE);
    }

    public WhileNode(String id, String name) {
        super(id, name, NodeType.WHILE);
    }

    public WhileNode(String id, String name, int hashcode) {
        super(id, name, NodeType.WHILE, hashcode);
    }
}