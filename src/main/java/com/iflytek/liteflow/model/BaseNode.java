package com.iflytek.liteflow.model;

import java.io.Serializable;

/**
 * 基础节点信息
 */
public class BaseNode implements Serializable {
    private static final long serialVersionUID = 527351678692560306L;

    /**
     * 节点ID
     */
    private String id;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 节点类型
     */
    private NodeType type;
    /**
     * liteflow节点ID,根据节点ID构建liteflow组件以及生成EL表达式
     */
    private String nodeId;

    public BaseNode() {
        this.type = NodeType.COMMON;
    }

    public BaseNode(NodeType type) {
        this.type = type;
    }

    public BaseNode(String id, String name, NodeType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}