package com.iflytek.liteflow.model;

/**
 * 循环节点
 */
public class LoopNode extends BaseNode {
    private static final long serialVersionUID = -965473405421976324L;

    /**
     * 循环迭代信息
     */
    private LiteFlowInfo loop;
    /**
     * 跳出循环的节点
     */
    private BaseNode breakNode;

    public LoopNode() {
    }

    public LoopNode(String id, String name, NodeType type) {
        super(id, name, type);
    }

    public LiteFlowInfo getLoop() {
        return loop;
    }

    public void setLoop(LiteFlowInfo loop) {
        this.loop = loop;
    }

    public BaseNode getBreakNode() {
        return breakNode;
    }

    public void setBreakNode(BaseNode breakNode) {
        this.breakNode = breakNode;
    }
}