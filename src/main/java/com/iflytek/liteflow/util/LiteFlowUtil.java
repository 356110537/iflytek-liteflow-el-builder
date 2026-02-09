package com.iflytek.liteflow.util;

import com.iflytek.liteflow.model.*;
import com.yomahub.liteflow.builder.el.ELBus;
import com.yomahub.liteflow.builder.el.ELWrapper;
import com.yomahub.liteflow.builder.el.LoopELWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

public class LiteFlowUtil {

    // 计数器
    private int index = 0;
    // 最大迭代次数，防止无限递归
    private static final int MAX_ITERATION_COUNT = 300;
    // 节点前缀
    private static final String NODE_PREFIX = "N_";
    // 虚拟节点前缀
    private static final String VIRTUAL_NODE_PREFIX = "VR_";

    /**
     * 获取所有节点集合
     */
    public <T extends BaseNode> Set<T> jsonToNodes(Collection<T> nodes) {
        Assert.notEmpty(nodes, "nodes cannot be empty");
        Set<T> nodeSet = new LinkedHashSet<>();
        for (T item : nodes) {
            item.setNodeId(buildLiteFlowId(item));
            nodeSet.add(item);
            if (item instanceof LoopNode) {
                LoopNode<T, ?> loopNode = (LoopNode<T, ?>) item;
                loopNode.setNodeId(item.getNodeId());
                Loop<T, ?> loop = loopNode.getLoop();
                Class<? extends BaseNode> aClass = item.getClass();
                if (loop != null) {
                    Set<T> temp = this.jsonToNodes(loop.getNodes());
                    if (ObjectUtils.isNotEmpty(temp)) {
                        nodeSet.addAll(temp);
                    }
                }
                T breakNode = loopNode.getBreakNode();
                if (breakNode != null) {
                    breakNode.setNodeId(buildLiteFlowId(breakNode));
                    nodeSet.add(breakNode);
                }
            }
        }
        return nodeSet;
    }

    /**
     * 生成EL表达式
     *
     * @param nodes 节点信息
     * @param edges 边信息
     */
    public String createEL(Collection<? extends BaseNode> nodes, Collection<? extends BaseEdge> edges) {
        Assert.notEmpty(nodes, "node cannot be empty");
        Set<Node> nodeSet = this.jsonToNode(nodes, edges);
        Node node = this.handle(nodeSet);
        if (node != null && node.getWrapper() != null && node.getWrapper().toEL() != null) {
            if (node.getWrapper().toEL().equals(ELBus.element(buildLiteFlowId(node)).toEL())) {
                return ELBus.then(node.getWrapper()).toEL();
            }
            return node.getWrapper().toEL();
        }
        throw new RuntimeException("EL expression generation failed");
    }

    /**
     * 前端数据转换成语法树
     */
    public Set<Node> jsonToNode(Collection<? extends BaseNode> nodeSet, Collection<? extends BaseEdge> edgeList) {
        Assert.notEmpty(nodeSet, "node cannot be empty");
        Set<Node> nodes = nodeSet.stream().map(this::covertNode).collect(Collectors.toSet());
        if (ObjectUtils.isEmpty(nodes)) return null;
        if (ObjectUtils.isNotEmpty(edgeList)){
            List<Edge> edges = edgeList.stream().map(this::covertEdge).collect(Collectors.toList());
            Map<String, Node> nodeMap = new HashMap<>();
            nodes.forEach(node -> nodeMap.put(node.getId(), node));
            // 建立Edge映射关系
            edges.forEach(edge -> {
                Node source = nodeMap.get(edge.getSource());
                Node target = nodeMap.get(edge.getTarget());
                edge.setSourceNode(source);
                edge.setTargetNode(target);
                if (source != null && target != null) {
                    target.addParent(source);
                    source.addChildren(target);
                    source.getEdges().add(edge);
                }
            });
        }
        // IF类型节点处理
        nodes.stream().filter(node -> node.getType() == NodeType.IF).forEach(node -> {
            IfNode ifNode = (IfNode) node;
            Set<Node> trueNodes = node.getEdges().stream().filter(edge -> edge.getType() == EdgeType.TRUE).map(Edge::getTargetNode).collect(Collectors.toSet());
            Set<Node> falseNodes = node.getEdges().stream().filter(edge -> edge.getType() == EdgeType.FALSE).map(Edge::getTargetNode).collect(Collectors.toSet());
            if (ObjectUtils.isNotEmpty(trueNodes)) {
                ifNode.setTrueNodes(trueNodes);
            }
            if (ObjectUtils.isNotEmpty(falseNodes)) {
                ifNode.setFalseNodes(falseNodes);
            }
        });
        // SWITCH类型节点处理
        nodes.stream().filter(node -> node.getType() == NodeType.SWITCH).forEach(node -> {
            SwitchNode switchNode = (SwitchNode) node;
            switchNode.getEdges().forEach(edge -> switchNode.getNodeMapping().computeIfAbsent(edge.getMapping(), v -> new LinkedHashSet<>()).add(edge.getTargetNode()));
        });
        return nodes.stream().filter(node -> ObjectUtils.isEmpty(node.getParents())).collect(Collectors.toSet());
    }

    /**
     * 构建LiteFlow节点ID
     */
    public static <T extends BaseNode> String buildLiteFlowId(NodeType type, int hashcode) {
        return type == NodeType.VIRTUAL ? VIRTUAL_NODE_PREFIX + hashcode : NODE_PREFIX + hashcode;
    }

    /**
     * 构建LiteFlow节点ID
     */
    public static <T extends BaseNode> String buildLiteFlowId(T node) {
        String nodeId = node.getType() == NodeType.VIRTUAL ? VIRTUAL_NODE_PREFIX + node.hashCode() : NODE_PREFIX + node.hashCode();
        return node.getNodeId() != null ? node.getNodeId() : nodeId;
    }

    /**
     * 转换成Node
     */
    private <T extends BaseNode> Node covertNode(T node) {
        Assert.notNull(node, "node must not be null");
        Assert.notNull(node.getType(), "node type must not be null");
        if (node instanceof Node) return (Node) node;
        NodeType type = node.getType();
        switch (type) {
            case IF:
                return new IfNode(node.getId(), node.getName(), node.hashCode());
            case SWITCH:
                return new SwitchNode(node.getId(), node.getName(), node.hashCode());
            case ITERATOR: {
                IteratorNode iteratorNode = new IteratorNode(node.getId(), node.getName(), node.hashCode());
                LoopNode<? extends BaseNode, ? extends BaseEdge> loopNode = (LoopNode<BaseNode, BaseEdge>) node;
                Loop<? extends BaseNode, ? extends BaseEdge> loop = loopNode.getLoop();
                if (loop != null) {
                    Loop<Node, Edge> item = new Loop<>();
                    item.setNodes(loop.getNodes().stream().map(this::covertNode).collect(Collectors.toSet()));
                    item.setEdges(loop.getEdges().stream().map(this::covertEdge).collect(Collectors.toList()));
                    iteratorNode.setLoop(item);
                }
                BaseNode breakNode = loopNode.getBreakNode();
                if (breakNode != null) {
                    iteratorNode.setBreakNode(this.covertNode(breakNode));
                }
                return iteratorNode;
            }
            case WHILE: {
                WhileNode whileNode = new WhileNode(node.getId(), node.getName(), node.hashCode());
                LoopNode<BaseNode, BaseEdge> loopNode = (LoopNode<BaseNode, BaseEdge>) node;
                Loop<BaseNode, BaseEdge> loop = loopNode.getLoop();
                if (loop != null) {
                    Loop<Node, Edge> item = new Loop<>();
                    item.setNodes(loop.getNodes().stream().map(this::covertNode).collect(Collectors.toSet()));
                    item.setEdges(loop.getEdges().stream().map(this::covertEdge).collect(Collectors.toList()));
                    whileNode.setLoop(item);
                }
                BaseNode breakNode = loopNode.getBreakNode();
                if (breakNode != null) {
                    whileNode.setBreakNode(this.covertNode(breakNode));
                }
                return whileNode;
            }
            default:
                return new Node(node.getId(), node.getName(), node.getType(), node.hashCode());
        }
    }

    /**
     * 转换成Edge
     */
    private Edge covertEdge(BaseEdge baseEdge) {
        Edge edge = new Edge();
        edge.setId(baseEdge.getId());
        edge.setSource(baseEdge.getSource());
        edge.setTarget(baseEdge.getTarget());
        edge.setType(baseEdge.getType());
        edge.setMapping(baseEdge.getMapping());
        return edge;
    }

    /**
     * 根据节点生成EL表达式
     *
     * @param nodes 参与节点
     * @return 合并后节点
     */
    public Node handle(Set<Node> nodes) {
        Assert.notEmpty(nodes, "nodes must not be empty");
        if (index < MAX_ITERATION_COUNT) {
            index++;
            boolean f1 = this.findThen(nodes);
            boolean f2 = this.findWhen(nodes);
            boolean f3 = this.findIF(nodes);
            boolean f4 = this.findSwitch(nodes);
            boolean f5 = this.findLoop(nodes);
            if (f1 || f2 || f3 || f4 || f5) {
                this.handle(nodes);
            }
        }
        if (nodes.size() != 1) {
            throw new RuntimeException("An exception occurred while generating EL expressions, preventing them from being merged into a single node");
        }
        return nodes.iterator().next();
    }

    /**
     * 生成THEN/WHEN场景的ELWrapper
     *
     * @param nodes 参与节点集合
     * @param type  场景类型
     */
    private ELWrapper createWrapper(Set<Node> nodes, SceneType type) {
        Assert.notNull(type, "type must not be null");
        Assert.isTrue(ObjectUtils.isNotEmpty(nodes), "nodes must not be empty");
        List<ELWrapper> list = new ArrayList<>();
        for (Node item : nodes) {
            if (item != null && item.getWrapper() != null) {
                list.add(item.getWrapper());
            }
        }
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        if (list.size() == 1) {
            return list.iterator().next();
        }
        if (type == SceneType.THEN) {
            return ELBus.then(list.toArray());
        }
        if (type == SceneType.WHEN) {
            return ELBus.when(list.toArray());
        }
        throw new RuntimeException("Unsupported scenario type:" + type);
    }

    /**
     * 生成IF/SWITCH场景的ELWrapper
     *
     * @param node 参与节点
     */
    private ELWrapper createWrapper(Node node) {
        Assert.notNull(node, "node must not be null");
        NodeType type = node.getType();
        Assert.notNull(type, "type must not be null");
        switch (type) {
            case IF: {
                IfNode ifNode = (IfNode) node;
                Set<Node> trueNodes = ifNode.getTrueNodes();
                Set<Node> falseNodes = ifNode.getFalseNodes();
                // 构建IF节点表达式
                if (ObjectUtils.isNotEmpty(trueNodes)) {
                    Node trueNode = trueNodes.iterator().next();
                    Assert.notNull(trueNode.getWrapper(), "true node wrapper must not be null");
                    if (ObjectUtils.isNotEmpty(falseNodes)) {
                        Node falseNode = falseNodes.iterator().next();
                        Assert.notNull(falseNode.getWrapper(), "false node wrapper must not be null");
                        return ELBus.ifOpt(buildLiteFlowId(node), trueNode.getWrapper(), falseNode.getWrapper());
                    }
                    return ELBus.ifOpt(buildLiteFlowId(node), trueNode.getWrapper());
                }
                if (ObjectUtils.isNotEmpty(falseNodes)) {
                    Node falseNode = falseNodes.iterator().next();
                    Assert.notNull(falseNode.getWrapper(), "false node wrapper must not be null");
                    return ELBus.ifOpt(ELBus.not(buildLiteFlowId(node)), falseNode.getWrapper());
                }
                throw new RuntimeException("Scenarios that do not meet the IF condition");
            }
            case SWITCH: {
                Set<ELWrapper> childWrapper = new LinkedHashSet<>();
                SwitchNode switchNode = (SwitchNode) node;
                Map<String, Set<Node>> nodeMapping = switchNode.getNodeMapping();
                node.getChildren().forEach(child -> {
                    if (child.getWrapper() != null && ObjectUtils.isNotEmpty(nodeMapping)) {
                        nodeMapping.forEach((k, v) -> {
                            if (v.contains(child)) {
                                childWrapper.add(child.getWrapper().tag(k));
                            }
                        });
                    }
                });
                return ELBus.switchOpt(buildLiteFlowId(node)).to(childWrapper.toArray());
            }
            default: {
                throw new RuntimeException("Unsupported scenario type:" + type);
            }
        }
    }

    /**
     * 更新节点链表
     * <P>支持THEN/WHEN场景</P>
     *
     * @param wrapper 合并后的ELWrapper
     * @param nodes   参与节点
     * @param type    类型
     */
    private void updateChain(ELWrapper wrapper, Set<Node> nodes, SceneType type) {
        Assert.notNull(type, "type must not be null");
        Assert.isTrue(ObjectUtils.isNotEmpty(nodes), "nodes must not be empty");
        switch (type) {
            case THEN: {
                ArrayList<Node> list = new ArrayList<>(nodes);
                Node begin = list.get(0);
                begin.setType(NodeType.COMMON);
                begin.setWrapper(wrapper);
                Node end = list.get(nodes.size() - 1);
                for (Node child : end.getChildren()) {
                    child.getParents().removeAll(nodes);
                    child.addParent(begin);
                }
                begin.getChildren().removeAll(nodes);
                if (ObjectUtils.isNotEmpty(end.getChildren())) {
                    begin.addChildren(end.getChildren());
                }
                break;
            }
            case WHEN: {
                Node item = nodes.iterator().next();
                item.setWrapper(wrapper);
                item.setType(NodeType.COMMON);
                for (Node parent : item.getParents()) {
                    parent.getChildren().removeAll(nodes);
                    parent.addChildren(item);
                    if (parent instanceof IfNode) {
                        IfNode ifNode = (IfNode) parent;
                        if (ifNode.getTrueNodes().containsAll(nodes)) {
                            ifNode.getTrueNodes().removeAll(nodes);
                            ifNode.getTrueNodes().add(item);
                        }
                        if (ifNode.getFalseNodes().containsAll(nodes)) {
                            ifNode.getFalseNodes().removeAll(nodes);
                            ifNode.getFalseNodes().add(item);
                        }
                    }
                    if (parent instanceof SwitchNode) {
                        SwitchNode switchNode = (SwitchNode) parent;
                        for (Set<Node> nodeSet : switchNode.getNodeMapping().values()) {
                            if (nodeSet.containsAll(nodes)) {
                                nodeSet.removeAll(nodes);
                                nodeSet.add(item);
                                break;
                            }
                        }
                    }
                }
                for (Node child : item.getChildren()) {
                    child.getParents().removeAll(nodes);
                    child.addParent(item);
                }
                break;
            }
            default: {
                throw new RuntimeException("Unsupported scenario type:" + type);
            }
        }
    }

    /**
     * 更新节点链表
     * <P>支持IF/SWITCH场景</P>
     *
     * @param wrapper 合并后的ELWrapper
     * @param item    参与节点
     */
    private void updateChain(ELWrapper wrapper, Node item) {
        Assert.notNull(wrapper, "wrapper must not be null");
        Assert.notNull(item, "node must not be null");
        item.setType(NodeType.COMMON);
        item.setWrapper(wrapper);
        Set<Node> children = item.getChildren();
        for (Node child : children) {
            for (Node grandchild : child.getChildren()) {
                grandchild.getParents().remove(child);
                grandchild.getParents().add(item);
            }
        }
        Set<Node> nodes = item.getChildren().stream().flatMap(node -> node.getChildren().stream()).collect(Collectors.toSet());
        item.setChildren(nodes);
    }

    /**
     * 查找THEN场景
     */
    public boolean findThen(Set<Node> startNodes) {
        if (ObjectUtils.isEmpty(startNodes)) {
            return false;
        }
        return startNodes.stream().anyMatch(node -> this.deepThen(node, new LinkedHashSet<>()));
    }

    /**
     * 递归查找THEN场景
     *
     * @param node  当前节点
     * @param nodes 已处理的节点集合
     */
    private boolean deepThen(Node node, Set<Node> nodes) {
        Set<Node> children = node.getChildren();
        if (ObjectUtils.isEmpty(children)) {
            return false;
        }
        if (node.getType() == NodeType.COMMON || node.getType() == NodeType.VIRTUAL) {
            if (children.size() == 1) {
                // 满足THEN处理条件
                Node item = children.iterator().next();
                if (item.getParents().size() == 1 && (item.getType() == NodeType.COMMON || item.getType() == NodeType.VIRTUAL)) {
                    nodes.add(node);
                    nodes.add(item);
                    if (!this.deepThen(item, nodes)) {
                        ELWrapper wrapper = this.createWrapper(nodes, SceneType.THEN);
                        this.updateChain(wrapper, nodes, SceneType.THEN);
                        return true;
                    }
                }
            }
        }
        return this.findThen(children);
    }

    /**
     * 处理WHEN场景
     */
    public boolean findWhen(Set<Node> nodes) {
        if (ObjectUtils.isEmpty(nodes)) {
            return false;
        }
        if (this.handleWhen(nodes)) {
            return true;
        }
        return nodes.stream().anyMatch(node -> {
            List<Set<Node>> nodeList = new ArrayList<>();
            NodeType type = node.getType();
            if (type == NodeType.IF) {
                IfNode ifNode = (IfNode) node;
                nodeList.add(ifNode.getTrueNodes());
                nodeList.add(ifNode.getFalseNodes());
            } else if (type == NodeType.SWITCH) {
                SwitchNode switchNode = (SwitchNode) node;
                nodeList.addAll(switchNode.getNodeMapping().values());
            } else {
                nodeList.add(node.getChildren());
            }
            return nodeList.stream().anyMatch(this::findWhen);
        });
    }

    /**
     * 判断是否满足when场景,并合并节点
     *
     * @param nodes 需要处理的节点集合
     */
    private boolean handleWhen(Set<Node> nodes) {
        if (ObjectUtils.isEmpty(nodes) || nodes.size() < 2) return false;
        // 子节点有共同的孙子节点或都没有孙子节点
        Map<Node, Node> map = new HashMap<>();
        Map<Node, Set<Node>> itemMap = new HashMap<>();
        Node defaultNode = Node.builder();
        for (Node item : nodes) {
            if (item.getType() != NodeType.COMMON) continue;
            Set<Node> children = item.getChildren();
            if (children.size() <= 1) {
                Iterator<Node> iterator = children.iterator();
                Node node = iterator.hasNext() ? iterator.next() : defaultNode;
                map.put(item, node);
            }
        }
        // 找出拥有相同孙子节点的子节点
        map.forEach((k, v) -> itemMap.computeIfAbsent(v, m -> new LinkedHashSet<>()).add(k));
        List<Set<Node>> list = itemMap.values().stream().filter(item -> item.size() > 1).collect(Collectors.toList());
        if (ObjectUtils.isNotEmpty(list)) {
            for (Set<Node> item : list) {
                // 1.生成ElWrapper
                ELWrapper wrapper = this.createWrapper(item, SceneType.WHEN);
                // 2.更新节点链表
                this.updateChain(wrapper, item, SceneType.WHEN);
                // 3.更新参数nodes数据
                Node valid = item.iterator().next();
                nodes.removeIf(node -> item.contains(node) && !valid.equals(node));
            }
            return true;
        }
        return false;
    }

    /**
     * 查找IF节点
     */
    public boolean findIF(Set<Node> nodes) {
        if (ObjectUtils.isEmpty(nodes)) {
            return false;
        }
        return nodes.stream().anyMatch(node -> {
            if (node == null) return false;
            // 循环子节点
            Set<Node> children = node.getChildren();
            if (NodeType.IF == node.getType()) {
                IfNode ifNode = (IfNode) node;
                Set<Node> trueNodes = ifNode.getTrueNodes();
                Set<Node> falseNodes = ifNode.getFalseNodes();
                if (ObjectUtils.isEmpty(trueNodes) && ObjectUtils.isEmpty(falseNodes)) {
                    throw new RuntimeException("The IF condition lacks a child node");
                }
                if (trueNodes.size() > 1 || falseNodes.size() > 1) {
                    return false;
                }
                if (trueNodes.size() == 1) {
                    Node trueNode = trueNodes.iterator().next();
                    if (falseNodes.size() == 1) {
                        // 1.true 和 false 都有节点
                        Node falseNode = falseNodes.iterator().next();
                        // 判断是否符合处理条件
                        Set<Node> endNodes = new HashSet<>();
                        endNodes.addAll(trueNode.getChildren());
                        endNodes.addAll(falseNode.getChildren());
                        // true节点和false节点拥有共同的子节点
                        boolean flag = ObjectUtils.isNotEmpty(trueNode.getChildren()) && ObjectUtils.isNotEmpty(falseNode.getChildren()) && endNodes.size() == 1;
                        // true节点和false节点都没有子节点
                        if (ObjectUtils.isEmpty(endNodes) || flag) {
                            ELWrapper wrapper = this.createWrapper(node);
                            this.updateChain(wrapper, ifNode);
                            return true;
                        }
                    } else {
                        // 2.只有true节点
                        // true节点没有子节点或子节点有多个父节点
                        boolean flag = trueNode.getChildren().size() == 1 && trueNode.getChildren().iterator().next().getParents().size() > 1;
                        if (ObjectUtils.isEmpty(trueNode.getChildren()) || flag) {
                            ELWrapper wrapper = this.createWrapper(node);
                            this.updateChain(wrapper, ifNode);
                            return true;
                        }
                    }
                }
                // 3.只有false节点,把条件取反执行 IF(NOT(N), A)
                if (falseNodes.size() == 1) {
                    Node falseNode = falseNodes.iterator().next();
                    // false节点没有子节点或子节点有多个父节点
                    boolean flag = falseNode.getChildren().size() == 1 && falseNode.getChildren().iterator().next().getParents().size() > 1;
                    if (ObjectUtils.isEmpty(falseNode.getChildren()) || flag) {
                        ELWrapper wrapper = this.createWrapper(node);
                        this.updateChain(wrapper, ifNode);
                        return true;
                    }
                }
            }
            return this.findIF(children);
        });
    }

    /**
     * 查找SWITCH节点
     */
    public boolean findSwitch(Set<Node> nodes) {
        if (ObjectUtils.isEmpty(nodes)) {
            return false;
        }
        return nodes.stream().anyMatch(node -> {
            if (node == null) return false;
            Set<Node> children = node.getChildren();
            if (ObjectUtils.isEmpty(children)) return false;
            if (NodeType.SWITCH == node.getType()) {
                SwitchNode switchNode = (SwitchNode) node;
                // 1.当条件节点的数量大于2则不满足处理条件
                if (switchNode.getNodeMapping().values().stream().anyMatch(item -> item.size() > 1)) {
                    return false;
                }
                // 2.检查SWITCH出现部分节点汇聚
                long count = children.stream().flatMap(item -> item.getChildren().stream()).collect(Collectors.toSet()).size();
                if (count > 1) {
                    throw new RuntimeException("SWITCH shows convergence of some nodes");
                }
                ELWrapper wrapper = this.createWrapper(node);
                this.updateChain(wrapper, switchNode);
                return true;
            }
            return this.findSwitch(children);
        });
    }

    /**
     * 查找循环节点
     */
    public boolean findLoop(Set<Node> nodes) {
        if (ObjectUtils.isEmpty(nodes)) {
            return false;
        }
        return nodes.stream().anyMatch(node -> {
            NodeType type = node.getType();
            if (type == NodeType.ITERATOR || type == NodeType.WHILE) {
                Loop<Node, Edge> loop = node.getLoop();
                if (loop != null) {
                    // 解析循环流程EL表达式
                    Set<Node> nodeSet = this.jsonToNode(loop.getNodes(), loop.getEdges());
                    Node item = this.handle(nodeSet);
                    Assert.notNull(item.getWrapper(), "The ELWrapper of the loop node cannot be null");
                    LoopELWrapper wrapper;
                    if (type == NodeType.ITERATOR) {
                        wrapper = ELBus.iteratorOpt(buildLiteFlowId(node)).doOpt(item.getWrapper());
                    } else {
                        wrapper = ELBus.whileOpt(buildLiteFlowId(node)).doOpt(item.getWrapper());
                    }
                    BaseNode breakNode = node.getBreakNode();
                    if (breakNode != null) {
                        breakNode.setType(NodeType.BREAK);
                        wrapper.breakOpt(buildLiteFlowId(breakNode));
                    }
                    node.setWrapper(wrapper);
                    node.setType(NodeType.COMMON);
                    return true;
                }
            }
            return this.findLoop(node.getChildren());
        });
    }
}