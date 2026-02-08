package com.iflytek.liteflow.model;

/**
 * 循环节点
 */
public class LoopNode<N extends BaseNode, E extends BaseEdge> extends BaseNode {
    private static final long serialVersionUID = -965473405421976324L;

    /**
     * 循环迭代信息
     */
    private Loop<N, E> loop;
    /**
     * 跳出循环的节点
     */
    private N breakNode;

    public LoopNode() {
    }

    public LoopNode(String id, String name, NodeType type) {
        super(id, name, type);
    }

    public Loop<N, E> getLoop() {
        return loop;
    }

    public void setLoop(Loop<N, E> loop) {
        this.loop = loop;
    }

    public N getBreakNode() {
        return breakNode;
    }

    public void setBreakNode(N breakNode) {
        this.breakNode = breakNode;
    }
}