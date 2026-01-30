package com.iflytek.liteflow.model;

import java.io.Serializable;

/**
 * 边信息
 */
public class Edge extends BaseEdge implements Serializable {
    private static final long serialVersionUID = -1476200907702851436L;

    /**
     * 开始节点
     */
    private Node sourceNode;
    /**
     * 目标节点
     */
    private Node targetNode;

    public Node getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(Node sourceNode) {
        this.sourceNode = sourceNode;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }
}