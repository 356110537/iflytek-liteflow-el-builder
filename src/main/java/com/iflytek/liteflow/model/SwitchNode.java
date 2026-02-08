package com.iflytek.liteflow.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 条件选择节点
 */
public class SwitchNode extends Node {
    private static final long serialVersionUID = -2360765133623261266L;

    /**
     * 条件映射关系<条件ID, 节点集合>
     */
    private Map<String, Set<Node>> nodeMapping = new HashMap<>();

    public SwitchNode() {
        this.setType(NodeType.SWITCH);
    }

    public SwitchNode(String id, String name) {
        super(id, name, NodeType.SWITCH);
    }

    public SwitchNode(String id, String name, int hashcode) {
        super(id, name, NodeType.SWITCH, hashcode);
    }

    public Map<String, Set<Node>> getNodeMapping() {
        return nodeMapping;
    }

    public void setNodeMapping(Map<String, Set<Node>> nodeMapping) {
        this.nodeMapping = nodeMapping;
    }
}