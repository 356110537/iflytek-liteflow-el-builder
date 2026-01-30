package com.iflytek.liteflow.model;

import java.io.Serializable;

/**
 * 基础边信息
 */
public class BaseEdge implements Serializable {
    private static final long serialVersionUID = -7196806865171567911L;

    /**
     * 边唯一标识
     */
    private String id;
    /**
     * 开始节点
     */
    private String source;
    /**
     * 目标节点
     */
    private String target;
    /**
     * 边的类型
     */
    private EdgeType type;
    /**
     * SWITCH条件匹配关系
     */
    private String mapping;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public EdgeType getType() {
        return type;
    }

    public void setType(EdgeType type) {
        this.type = type;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }
}