package com.iflytek.liteflow.model;

import com.iflytek.liteflow.util.LiteFlowUtil;
import com.yomahub.liteflow.builder.el.ELBus;
import com.yomahub.liteflow.builder.el.ELWrapper;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 节点信息
 */
public class Node extends LoopNode implements Serializable {
    private static final long serialVersionUID = 421958199518091553L;

    /**
     * 节点ID
     */
    private String nodeId;
    /**
     * 父节点
     */
    private Set<Node> parents = new LinkedHashSet<>();
    /**
     * 子节点
     */
    private Set<Node> children = new LinkedHashSet<>();
    /**
     * 节点EL表达式对象
     */
    private ELWrapper wrapper;
    /**
     * 节点的Edge集合
     */
    private List<Edge> edges = new ArrayList<>();

    public Node() {
    }

    public Node(String id, String name, NodeType type) {
        super(id, name, type);
        this.setNodeId(LiteFlowUtil.buildLiteFlowId(type, this.hashCode()));
    }

    public Node(String id, String name, NodeType type, int hashcode) {
        super(id, name, type);
        this.setNodeId(LiteFlowUtil.buildLiteFlowId(type, hashcode));
        this.setWrapper(ELBus.element(LiteFlowUtil.buildLiteFlowId(type, hashcode)));
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Set<Node> getParents() {
        return parents;
    }

    public void setParents(Set<Node> parents) {
        this.parents = parents;
    }

    public Set<Node> getChildren() {
        return children;
    }

    public void setChildren(Set<Node> children) {
        this.children = children;
    }

    public ELWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(ELWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Node wrapper(ELWrapper wrapper) {
        this.wrapper = wrapper;
        return this;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }


    public static Node builder() {
        return new Node();
    }

    /**
     * 添加子节点
     */
    public void addChildren(Node node) {
        if (ObjectUtils.isEmpty(node)) {
            return;
        }
        this.children.add(node);
    }

    /**
     * 添加子节点
     */
    public void addChildren(Set<Node> nodes) {
        if (ObjectUtils.isEmpty(nodes)) {
            return;
        }
        this.children.addAll(nodes);
    }

    /**
     * 添加父节点
     */
    public void addParent(Node node) {
        if (ObjectUtils.isEmpty(node)) {
            return;
        }
        this.parents.add(node);
    }

    /**
     * 添加父节点
     */
    public void addParent(Set<Node> nodes) {
        if (ObjectUtils.isEmpty(nodes)) {
            return;
        }
        this.parents.addAll(nodes);
    }
}