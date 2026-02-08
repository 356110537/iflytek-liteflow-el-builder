package com.iflytek.liteflow;

import com.iflytek.liteflow.model.*;

import java.util.*;

public class LiteFlowDataUtil {

    // 生成节点和边信息
    public static Loop<BaseNode, BaseEdge> generator() {
        // 初始化节点信息
        Map<String, BaseNode> map = new HashMap<>();
        for (int i = 0; i < 16; i++) {
            char letter = (char) ('A' + i);
            String nodeId = String.valueOf(letter);
            BaseNode node = new BaseNode();
            node.setId(nodeId);
            node.setName(nodeId);
            if (letter == 'J') {
                node = new IfNode(nodeId, nodeId);
                node.setType(NodeType.IF);
            } else {
                node.setType(NodeType.COMMON);
            }
            map.put(nodeId, node);
        }

        // 初始化边信息
        List<BaseEdge> edges = new ArrayList<>();
        // A -> B
        BaseEdge edge1 = new BaseEdge();
        edge1.setSource("A");
        edge1.setTarget("B");
        edges.add(edge1);

        // A -> C
        BaseEdge edge2 = new BaseEdge();
        edge2.setSource("A");
        edge2.setTarget("C");
        edges.add(edge2);

        //  C -> D
        BaseEdge edge3 = new BaseEdge();
        edge3.setSource("C");
        edge3.setTarget("D");
        edges.add(edge3);

        //  C -> E
        BaseEdge edge4 = new BaseEdge();
        edge4.setSource("C");
        edge4.setTarget("E");
        edges.add(edge4);

        //  E -> F
        BaseEdge edge5 = new BaseEdge();
        edge5.setSource("E");
        edge5.setTarget("F");
        edges.add(edge5);

        //  D -> I
        BaseEdge edge15 = new BaseEdge();
        edge15.setSource("D");
        edge15.setTarget("I");
        edges.add(edge15);

        //  F -> I
        BaseEdge edge16 = new BaseEdge();
        edge16.setSource("F");
        edge16.setTarget("I");
        edges.add(edge16);

        //  B -> G
        BaseEdge edge6 = new BaseEdge();
        edge6.setSource("B");
        edge6.setTarget("G");
        edges.add(edge6);

        //  B -> H
        BaseEdge edge7 = new BaseEdge();
        edge7.setSource("B");
        edge7.setTarget("H");
        edges.add(edge7);

        //  G -> J
        BaseEdge edge8 = new BaseEdge();
        edge8.setSource("G");
        edge8.setTarget("J");
        edges.add(edge8);

        //  H -> M
        BaseEdge edge9 = new BaseEdge();
        edge9.setSource("H");
        edge9.setTarget("M");
        edges.add(edge9);

        //  M -> N
        BaseEdge edge10 = new BaseEdge();
        edge10.setSource("M");
        edge10.setTarget("N");
        edges.add(edge10);

        //  N -> J
        BaseEdge edge11 = new BaseEdge();
        edge11.setSource("N");
        edge11.setTarget("J");
        edges.add(edge11);

        //  I -> J
        BaseEdge edge12 = new BaseEdge();
        edge12.setSource("I");
        edge12.setTarget("J");
        edges.add(edge12);

        //  J -> K
        BaseEdge edge13 = new BaseEdge();
        edge13.setSource("J");
        edge13.setTarget("K");
        edge13.setType(EdgeType.TRUE);
        edges.add(edge13);

        //  J -> L
        BaseEdge edge14 = new BaseEdge();
        edge14.setSource("J");
        edge14.setTarget("L");
        edge14.setType(EdgeType.FALSE);
        edges.add(edge14);

        //  K -> O
        BaseEdge edge17 = new BaseEdge();
        edge17.setSource("K");
        edge17.setTarget("O");
        edges.add(edge17);

        //  L -> P
        BaseEdge edge18 = new BaseEdge();
        edge18.setSource("L");
        edge18.setTarget("P");
        edges.add(edge18);

        Loop<BaseNode, BaseEdge> liteFlowInfo = new Loop<>();
        liteFlowInfo.setNodes(new HashSet<>(map.values()));
        liteFlowInfo.setEdges(edges);
        return liteFlowInfo;
    }

    // 测试多WHEN IF 场景
    public static Loop<BaseNode, BaseEdge> generator1() {
        // 初始化节点信息
        Map<String, BaseNode> map = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            char letter = (char) ('A' + i);
            String nodeId = String.valueOf(letter);
            BaseNode node = new BaseNode();
            node.setId(nodeId);
            node.setName(nodeId);
            if (letter == 'D') {
                node = new IfNode(nodeId, nodeId);
            } else {
                node.setType(NodeType.COMMON);
            }
            map.put(nodeId, node);
        }

        // 初始化边信息
        List<BaseEdge> edges = new ArrayList<>();
        // A -> D
        BaseEdge edge1 = new BaseEdge();
        edge1.setSource("A");
        edge1.setTarget("D");
        edges.add(edge1);

        // B -> D
        BaseEdge edge2 = new BaseEdge();
        edge2.setSource("B");
        edge2.setTarget("D");
        edges.add(edge2);

        // C -> D
        BaseEdge edge3 = new BaseEdge();
        edge3.setSource("C");
        edge3.setTarget("D");
        edges.add(edge3);

        // D -> E
        BaseEdge edge4 = new BaseEdge();
        edge4.setSource("D");
        edge4.setTarget("E");
        edge4.setType(EdgeType.TRUE);
        edges.add(edge4);

        // D -> F
        BaseEdge edge5 = new BaseEdge();
        edge5.setSource("D");
        edge5.setTarget("F");
        edge5.setType(EdgeType.FALSE);
        edges.add(edge5);

        // G -> A
        BaseEdge edge6 = new BaseEdge();
        edge6.setSource("G");
        edge6.setTarget("A");
        edges.add(edge6);

        // G -> B
        BaseEdge edge7 = new BaseEdge();
        edge7.setSource("G");
        edge7.setTarget("B");
        edges.add(edge7);

        // G -> C
        BaseEdge edge8 = new BaseEdge();
        edge8.setSource("G");
        edge8.setTarget("C");
        edges.add(edge8);

        Loop<BaseNode, BaseEdge> liteFlowInfo = new Loop<>();
        liteFlowInfo.setNodes(new HashSet<>(map.values()));
        liteFlowInfo.setEdges(edges);
        return liteFlowInfo;
    }

    // 测试 没有 IF 场景
    public static Loop<BaseNode, BaseEdge> generator2() {
        // 初始化节点信息
        Map<String, BaseNode> map = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            char letter = (char) ('A' + i);
            String nodeId = String.valueOf(letter);
            BaseNode node = new BaseNode();
            node.setId(nodeId);
            node.setName(nodeId);
            node.setType(NodeType.COMMON);
            map.put(nodeId, node);
        }

        // 初始化边信息
        List<BaseEdge> edges = new ArrayList<>();
        // A -> B
        BaseEdge edge1 = new BaseEdge();
        edge1.setSource("A");
        edge1.setTarget("B");
        edges.add(edge1);

        // B -> C
        BaseEdge edge2 = new BaseEdge();
        edge2.setSource("B");
        edge2.setTarget("C");
        edges.add(edge2);

        Loop<BaseNode, BaseEdge> liteFlowInfo = new Loop<>();
        liteFlowInfo.setNodes(new HashSet<>(map.values()));
        liteFlowInfo.setEdges(edges);
        return liteFlowInfo;
    }

    // 多IF场景
    public static Loop<BaseNode, BaseEdge> generator3() {
        // 初始化节点信息
        Map<String, BaseNode> map = new HashMap<>();
        for (int i = 0; i < 11; i++) {
            char letter = (char) ('A' + i);
            String nodeId = String.valueOf(letter);
            BaseNode node = new BaseNode();
            node.setId(nodeId);
            node.setName(nodeId);
            if (letter == 'D' || letter == 'H') {
                node = new IfNode(nodeId, nodeId);
            } else {
                node.setType(NodeType.COMMON);
            }
            map.put(nodeId, node);
        }

        // 初始化边信息
        List<BaseEdge> edges = new ArrayList<>();
        // A -> C
        BaseEdge edge1 = new BaseEdge();
        edge1.setSource("A");
        edge1.setTarget("C");
        edges.add(edge1);

        // B -> C
        BaseEdge edge2 = new BaseEdge();
        edge2.setSource("B");
        edge2.setTarget("C");
        edges.add(edge2);

        //  C -> D
        BaseEdge edge3 = new BaseEdge();
        edge3.setSource("C");
        edge3.setTarget("D");
        edges.add(edge3);

        //  D -> F
        BaseEdge edge4 = new BaseEdge();
        edge4.setSource("D");
        edge4.setTarget("F");
        edge4.setType(EdgeType.TRUE);
        edges.add(edge4);

        //  E -> H
        BaseEdge edge5 = new BaseEdge();
        edge5.setSource("E");
        edge5.setTarget("H");
        edges.add(edge5);

        //  F -> H
        BaseEdge edge15 = new BaseEdge();
        edge15.setSource("F");
        edge15.setTarget("H");
        edges.add(edge15);

        //  G -> H
        BaseEdge edge7 = new BaseEdge();
        edge7.setSource("G");
        edge7.setTarget("H");
        edges.add(edge7);

        //  H -> I
        BaseEdge edge16 = new BaseEdge();
        edge16.setSource("H");
        edge16.setTarget("I");
        edge16.setType(EdgeType.TRUE);
        edges.add(edge16);

        // H -> J
        BaseEdge edge6 = new BaseEdge();
        edge6.setSource("H");
        edge6.setTarget("J");
        edge6.setType(EdgeType.FALSE);
        edges.add(edge6);

        //  K -> E
        BaseEdge edge17 = new BaseEdge();
        edge17.setSource("K");
        edge17.setTarget("E");
        edges.add(edge17);

        //  K -> A
        BaseEdge edge18 = new BaseEdge();
        edge18.setSource("K");
        edge18.setTarget("A");
        edges.add(edge18);

        //  K -> B
        BaseEdge edge19 = new BaseEdge();
        edge19.setSource("K");
        edge19.setTarget("B");
        edges.add(edge19);

        //  K -> G
        BaseEdge edge20 = new BaseEdge();
        edge20.setSource("K");
        edge20.setTarget("G");
        edges.add(edge20);

        Loop<BaseNode, BaseEdge> liteFlowInfo = new Loop<>();
        liteFlowInfo.setNodes(new HashSet<>(map.values()));
        liteFlowInfo.setEdges(edges);
        return liteFlowInfo;
    }

    // NOT场景
    public static Loop<BaseNode, BaseEdge> generator4() {
        // 初始化节点信息
        Map<String, BaseNode> map = new HashMap<>();
        for (int i = 0; i < 11; i++) {
            char letter = (char) ('A' + i);
            String nodeId = String.valueOf(letter);
            BaseNode node = new BaseNode();
            node.setId(nodeId);
            node.setName(nodeId);
            if (letter == 'H') {
                node = new IfNode(nodeId, nodeId);
            } else {
                node.setType(NodeType.COMMON);
            }
            map.put(nodeId, node);
        }

        // 初始化边信息
        List<BaseEdge> edges = new ArrayList<>();
        // A -> C
        BaseEdge edge1 = new BaseEdge();
        edge1.setSource("A");
        edge1.setTarget("C");
        edges.add(edge1);

        // B -> C
        BaseEdge edge2 = new BaseEdge();
        edge2.setSource("B");
        edge2.setTarget("C");
        edges.add(edge2);

        //  C -> F
        BaseEdge edge4 = new BaseEdge();
        edge4.setSource("C");
        edge4.setTarget("F");
        edges.add(edge4);

        //  E -> K
        BaseEdge edge5 = new BaseEdge();
        edge5.setSource("E");
        edge5.setTarget("K");
        edges.add(edge5);

        //  F -> K
        BaseEdge edge15 = new BaseEdge();
        edge15.setSource("F");
        edge15.setTarget("K");
        edges.add(edge15);

        //  G -> K
        BaseEdge edge7 = new BaseEdge();
        edge7.setSource("G");
        edge7.setTarget("K");
        edges.add(edge7);

        //  K -> H
        BaseEdge edge17 = new BaseEdge();
        edge17.setSource("K");
        edge17.setTarget("H");
        edges.add(edge17);

        //  H -> I
        BaseEdge edge16 = new BaseEdge();
        edge16.setSource("H");
        edge16.setTarget("I");
        edge16.setType(EdgeType.TRUE);
        edges.add(edge16);

        // H -> J
        BaseEdge edge6 = new BaseEdge();
        edge6.setSource("H");
        edge6.setTarget("J");
        edge6.setType(EdgeType.FALSE);
        edges.add(edge6);

        //  D -> E
        BaseEdge edge18 = new BaseEdge();
        edge18.setSource("D");
        edge18.setTarget("E");
        edges.add(edge18);

        //  D -> A
        BaseEdge edge19 = new BaseEdge();
        edge19.setSource("D");
        edge19.setTarget("A");
        edges.add(edge19);

        //  D -> B
        BaseEdge edge20 = new BaseEdge();
        edge20.setSource("D");
        edge20.setTarget("B");
        edges.add(edge20);

        //  D -> G
        BaseEdge edge21 = new BaseEdge();
        edge21.setSource("D");
        edge21.setTarget("G");
        edges.add(edge21);

        Loop<BaseNode, BaseEdge> liteFlowInfo = new Loop<>();
        liteFlowInfo.setNodes(new HashSet<>(map.values()));
        liteFlowInfo.setEdges(edges);
        return liteFlowInfo;
    }

    public static Loop<BaseNode, BaseEdge> generator5() {
        // 初始化节点信息
        Map<String, BaseNode> map = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            char letter = (char) ('A' + i);
            String nodeId = String.valueOf(letter);
            BaseNode node = new BaseNode();
            node.setId(nodeId);
            node.setName(nodeId);
            if (letter == 'C') {
                node = new IfNode(nodeId, nodeId);
            } else {
                node.setType(NodeType.COMMON);
            }
            map.put(nodeId, node);
        }

        // 初始化边信息
        List<BaseEdge> edges = new ArrayList<>();
        // A -> B
        BaseEdge edge1 = new BaseEdge();
        edge1.setSource("A");
        edge1.setTarget("B");
        edges.add(edge1);

        // B -> C
        BaseEdge edge2 = new BaseEdge();
        edge2.setSource("B");
        edge2.setTarget("C");
        edges.add(edge2);

        // C -> D
        BaseEdge edge3 = new BaseEdge();
        edge3.setSource("C");
        edge3.setTarget("D");
        edge3.setType(EdgeType.TRUE);
        edges.add(edge3);

        // C -> E
        BaseEdge edge4 = new BaseEdge();
        edge4.setSource("C");
        edge4.setTarget("E");
        edge4.setType(EdgeType.TRUE);
        edges.add(edge4);

        // C -> F
        BaseEdge edge5 = new BaseEdge();
        edge5.setSource("C");
        edge5.setTarget("F");
        edge5.setType(EdgeType.FALSE);
        edges.add(edge5);

        // D -> G
        BaseEdge edge6 = new BaseEdge();
        edge6.setSource("D");
        edge6.setTarget("G");
        edges.add(edge6);

        // E -> G
        BaseEdge edge7 = new BaseEdge();
        edge7.setSource("E");
        edge7.setTarget("G");
        edges.add(edge7);

        // F -> H
        BaseEdge edge8 = new BaseEdge();
        edge8.setSource("F");
        edge8.setTarget("H");
        edges.add(edge8);

        Loop<BaseNode, BaseEdge> liteFlowInfo = new Loop<>();
        liteFlowInfo.setNodes(new HashSet<>(map.values()));
        liteFlowInfo.setEdges(edges);
        return liteFlowInfo;
    }

    // 分组
    public static Loop<BaseNode, BaseEdge> generator6() {
        // 初始化节点信息
        Map<String, BaseNode> map = new HashMap<>();
        for (int i = 0; i < 15; i++) {
            char letter = (char) ('A' + i);
            String nodeId = String.valueOf(letter);
            BaseNode node = new BaseNode();
            node.setId(nodeId);
            node.setName(nodeId);
            if (letter == 'B' || letter == 'G' || letter == 'H' || letter == 'M') {
                node.setType(NodeType.VIRTUAL);
            } else {
                node.setType(NodeType.COMMON);
            }
            map.put(nodeId, node);
        }

        // 初始化边信息
        List<BaseEdge> edges = new ArrayList<>();
        // A -> B
        BaseEdge edge1 = new BaseEdge();
        edge1.setSource("A");
        edge1.setTarget("B");
        edges.add(edge1);

        // B -> C
        BaseEdge edge2 = new BaseEdge();
        edge2.setSource("B");
        edge2.setTarget("C");
        edges.add(edge2);

        // B -> E
        BaseEdge edge3 = new BaseEdge();
        edge3.setSource("B");
        edge3.setTarget("E");
        edges.add(edge3);

        // C -> D
        BaseEdge edge4 = new BaseEdge();
        edge4.setSource("C");
        edge4.setTarget("D");
        edges.add(edge4);

        // E -> F
        BaseEdge edge5 = new BaseEdge();
        edge5.setSource("E");
        edge5.setTarget("F");
        edges.add(edge5);

        // D -> G
        BaseEdge edge6 = new BaseEdge();
        edge6.setSource("D");
        edge6.setTarget("G");
        edges.add(edge6);

        // F -> G
        BaseEdge edge7 = new BaseEdge();
        edge7.setSource("F");
        edge7.setTarget("G");
        edges.add(edge7);

        // G -> H
        BaseEdge edge8 = new BaseEdge();
        edge8.setSource("G");
        edge8.setTarget("H");
        edges.add(edge8);

        // H -> I
        BaseEdge edge9 = new BaseEdge();
        edge9.setSource("H");
        edge9.setTarget("I");
        edges.add(edge9);

        // H -> J
        BaseEdge edge10 = new BaseEdge();
        edge10.setSource("H");
        edge10.setTarget("J");
        edges.add(edge10);

        // I -> K
        BaseEdge edge11 = new BaseEdge();
        edge11.setSource("I");
        edge11.setTarget("K");
        edges.add(edge11);

        // J -> L
        BaseEdge edge12 = new BaseEdge();
        edge12.setSource("J");
        edge12.setTarget("L");
        edges.add(edge12);

        // K -> M
        BaseEdge edge13 = new BaseEdge();
        edge13.setSource("K");
        edge13.setTarget("M");
        edges.add(edge13);

        // L -> M
        BaseEdge edge14 = new BaseEdge();
        edge14.setSource("L");
        edge14.setTarget("M");
        edges.add(edge14);

        // M -> N
        BaseEdge edge15 = new BaseEdge();
        edge15.setSource("M");
        edge15.setTarget("N");
        edges.add(edge15);

        // M -> O
        BaseEdge edge16 = new BaseEdge();
        edge16.setSource("M");
        edge16.setTarget("O");
        edges.add(edge16);

        Loop<BaseNode, BaseEdge> liteFlowInfo = new Loop<>();
        liteFlowInfo.setNodes(new HashSet<>(map.values()));
        liteFlowInfo.setEdges(edges);
        return liteFlowInfo;
    }

    // IF只有ELSE场景
    public static Loop<BaseNode, BaseEdge> generator7() {
        // 初始化节点信息
        Map<String, BaseNode> map = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            char letter = (char) ('A' + i);
            String nodeId = String.valueOf(letter);
            BaseNode node = new BaseNode();
            node.setId(nodeId);
            node.setName(nodeId);
            if (letter == 'C') {
                node = new IfNode(nodeId, nodeId);
            } else {
                node.setType(NodeType.COMMON);
            }
            map.put(nodeId, node);
        }

        // 初始化边信息
        List<BaseEdge> edges = new ArrayList<>();
        // A -> B
        BaseEdge edge1 = new BaseEdge();
        edge1.setSource("A");
        edge1.setTarget("B");
        edges.add(edge1);

        // B -> C
        BaseEdge edge2 = new BaseEdge();
        edge2.setSource("B");
        edge2.setTarget("C");
        edges.add(edge2);

        // C -> D
        BaseEdge edge3 = new BaseEdge();
        edge3.setSource("C");
        edge3.setTarget("D");
        edge3.setType(EdgeType.FALSE);
        edges.add(edge3);

        Loop<BaseNode, BaseEdge> liteFlowInfo = new Loop<>();
        liteFlowInfo.setNodes(new HashSet<>(map.values()));
        liteFlowInfo.setEdges(edges);
        return liteFlowInfo;
    }

    // SWITCH场景
    public static Loop<BaseNode, BaseEdge> generator8() {
        // 初始化节点信息
        Map<String, BaseNode> map = new HashMap<>();
        for (int i = 0; i < 13; i++) {
            char letter = (char) ('A' + i);
            String nodeId = String.valueOf(letter);
            BaseNode node = new BaseNode();
            node.setId(nodeId);
            node.setName(nodeId);
            if (letter == 'D') {
                node = new IfNode(nodeId, nodeId);
            } else if (letter == 'E') {
                node = new SwitchNode(nodeId, nodeId);
            } else {
                node.setType(NodeType.COMMON);
            }
            map.put(nodeId, node);
        }

        // 初始化边信息
        List<BaseEdge> edges = new ArrayList<>();
        // A -> B
        BaseEdge edge1 = new BaseEdge();
        edge1.setSource("A");
        edge1.setTarget("B");
        edges.add(edge1);

        // B -> C
        BaseEdge edge2 = new BaseEdge();
        edge2.setSource("B");
        edge2.setTarget("C");
        edges.add(edge2);

        // B -> D
        BaseEdge edge3 = new BaseEdge();
        edge3.setSource("B");
        edge3.setTarget("D");
        edges.add(edge3);

        // C -> E
        BaseEdge edge4 = new BaseEdge();
        edge4.setSource("C");
        edge4.setTarget("E");
        edges.add(edge4);

        // D -> F
        BaseEdge edge5 = new BaseEdge();
        edge5.setSource("D");
        edge5.setTarget("F");
        edge5.setType(EdgeType.TRUE);
        edges.add(edge5);

        // D -> G
        BaseEdge edge6 = new BaseEdge();
        edge6.setSource("D");
        edge6.setTarget("G");
        edge6.setType(EdgeType.FALSE);
        edges.add(edge6);

        // F -> L
        BaseEdge edge7 = new BaseEdge();
        edge7.setSource("F");
        edge7.setTarget("L");
        edges.add(edge7);

        // G -> L
        BaseEdge edge8 = new BaseEdge();
        edge8.setSource("G");
        edge8.setTarget("L");
        edges.add(edge8);

        // E -> H
        BaseEdge edge9 = new BaseEdge();
        edge9.setSource("E");
        edge9.setTarget("H");
        edge9.setMapping("001");
        edges.add(edge9);

        // E -> I
        BaseEdge edge10 = new BaseEdge();
        edge10.setSource("E");
        edge10.setTarget("I");
        edge10.setMapping("001");
        edges.add(edge10);

        // E -> J
        BaseEdge edge11 = new BaseEdge();
        edge11.setSource("E");
        edge11.setTarget("J");
        edge11.setMapping("002");
        edges.add(edge11);

        // E -> K
        BaseEdge edge12 = new BaseEdge();
        edge12.setSource("E");
        edge12.setTarget("K");
        edge12.setMapping("003");
        edges.add(edge12);

        // H -> M
        BaseEdge edge13 = new BaseEdge();
        edge13.setSource("H");
        edge13.setTarget("M");
        edges.add(edge13);

        // I -> M
        BaseEdge edge14 = new BaseEdge();
        edge14.setSource("I");
        edge14.setTarget("M");
        edges.add(edge14);

        // J -> M
        BaseEdge edge15 = new BaseEdge();
        edge15.setSource("J");
        edge15.setTarget("M");
        edges.add(edge15);

        // K -> M
        BaseEdge edge16 = new BaseEdge();
        edge16.setSource("K");
        edge16.setTarget("M");
        edges.add(edge16);

        Loop<BaseNode, BaseEdge> liteFlowInfo = new Loop<>();
        liteFlowInfo.setNodes(new HashSet<>(map.values()));
        liteFlowInfo.setEdges(edges);
        return liteFlowInfo;
    }

    // Iterator循环场景
    public static Loop<BaseNode, BaseEdge> generator9() {
        // 初始化节点信息
        Map<String, BaseNode> map = new HashMap<>();
        Map<String, Node> hMap = new HashMap<>();
        for (int i = 0; i < 14; i++) {
            char letter = (char) ('A' + i);
            String nodeId = String.valueOf(letter);
            BaseNode node = new BaseNode();
            node.setId(nodeId);
            node.setName(nodeId);
            node.setType(NodeType.COMMON);
            if (letter == 'E') {
                node = new IfNode(nodeId, nodeId);
            } else if (letter == 'H') {
                node = new IteratorNode(nodeId, nodeId);
            } else if (letter == 'N') {
                node = new Node(nodeId, nodeId, NodeType.BREAK);
            } else if (letter == 'I' || letter == 'J' || letter == 'K' || letter == 'L') {
                Node item = new Node(nodeId, nodeId, NodeType.COMMON);
                hMap.put(nodeId, item);
                continue;
            }
            map.put(nodeId, node);
        }

        // 初始化边信息
        List<BaseEdge> edges = new ArrayList<>();
        // A -> B
        BaseEdge edge1 = new BaseEdge();
        edge1.setSource("A");
        edge1.setTarget("B");
        edges.add(edge1);

        // B -> C
        BaseEdge edge2 = new BaseEdge();
        edge2.setSource("B");
        edge2.setTarget("C");
        edges.add(edge2);

        // B -> D
        BaseEdge edge3 = new BaseEdge();
        edge3.setSource("B");
        edge3.setTarget("D");
        edges.add(edge3);

        // C -> E
        BaseEdge edge4 = new BaseEdge();
        edge4.setSource("C");
        edge4.setTarget("E");
        edges.add(edge4);

        // E -> F
        BaseEdge edge5 = new BaseEdge();
        edge5.setSource("E");
        edge5.setTarget("F");
        edge5.setType(EdgeType.TRUE);
        edges.add(edge5);

        // E -> G
        BaseEdge edge6 = new BaseEdge();
        edge6.setSource("E");
        edge6.setTarget("G");
        edge6.setType(EdgeType.FALSE);
        edges.add(edge6);

        // D -> H
        BaseEdge edge7 = new BaseEdge();
        edge7.setSource("D");
        edge7.setTarget("H");
        edges.add(edge7);

        Loop<Node, Edge> loop = getLiteFlowInfo(hMap);
        IteratorNode node = (IteratorNode) map.get("H");
        node.setLoop(loop);
        node.setBreakNode((Node) map.get("N"));
        map.remove("N");

        // H -> M
        BaseEdge edge13 = new BaseEdge();
        edge13.setSource("H");
        edge13.setTarget("M");
        edges.add(edge13);

        Loop<BaseNode, BaseEdge> liteFlowInfo = new Loop<>();
        liteFlowInfo.setNodes(new HashSet<>(map.values()));
        liteFlowInfo.setEdges(edges);
        return liteFlowInfo;
    }

    private static Loop<Node, Edge> getLiteFlowInfo(Map<String, Node> hMap) {
        List<Edge> hBaseEdges = new ArrayList<>();
        // I -> J
        Edge edge8 = new Edge();
        edge8.setSource("I");
        edge8.setTarget("J");
        hBaseEdges.add(edge8);

        // I -> K
        Edge edge9 = new Edge();
        edge9.setSource("I");
        edge9.setTarget("K");
        hBaseEdges.add(edge9);

        // J -> L
        Edge edge10 = new Edge();
        edge10.setSource("J");
        edge10.setTarget("L");
        hBaseEdges.add(edge10);

        // K -> L
        Edge edge11 = new Edge();
        edge11.setSource("K");
        edge11.setTarget("L");
        hBaseEdges.add(edge11);

        Loop<Node, Edge> loop = new Loop<>();
        loop.setNodes(new HashSet<>(hMap.values()));
        loop.setEdges(hBaseEdges);
        return loop;
    }

    // 2个开始节点
    public static Loop<BaseNode, BaseEdge> generator10() {
        Set<BaseNode> nodes = new LinkedHashSet<>();
        // 初始化节点信息
        for (int i = 0; i < 11; i++) {
            char letter = (char) ('A' + i);
            String nodeId = String.valueOf(letter);
            BaseNode node = new BaseNode();
            node.setType(NodeType.COMMON);
            node.setId(nodeId);
            node.setName(nodeId);
            nodes.add(node);
        }

        // 初始化边信息
        List<BaseEdge> edges = new ArrayList<>();
        // A -> C
        BaseEdge edge1 = new BaseEdge();
        edge1.setSource("A");
        edge1.setTarget("C");
        edges.add(edge1);

        // B -> C
        BaseEdge edge2 = new BaseEdge();
        edge2.setSource("B");
        edge2.setTarget("C");
        edges.add(edge2);

        //  C -> D
        BaseEdge edge3 = new BaseEdge();
        edge3.setSource("C");
        edge3.setTarget("D");
        edges.add(edge3);

        //  C -> E
        BaseEdge edge4 = new BaseEdge();
        edge4.setSource("C");
        edge4.setTarget("E");
        edges.add(edge4);

        //  D -> F
        BaseEdge edge5 = new BaseEdge();
        edge5.setSource("D");
        edge5.setTarget("F");
        edges.add(edge5);

        //  E -> I
        BaseEdge edge15 = new BaseEdge();
        edge15.setSource("E");
        edge15.setTarget("I");
        edges.add(edge15);

        //  F -> G
        BaseEdge edge16 = new BaseEdge();
        edge16.setSource("F");
        edge16.setTarget("G");
        edges.add(edge16);

        //  F -> H
        BaseEdge edge6 = new BaseEdge();
        edge6.setSource("F");
        edge6.setTarget("H");
        edges.add(edge6);

        //  I -> J
        BaseEdge edge7 = new BaseEdge();
        edge7.setSource("I");
        edge7.setTarget("J");
        edges.add(edge7);

        //  G -> K
        BaseEdge edge8 = new BaseEdge();
        edge8.setSource("G");
        edge8.setTarget("K");
        edges.add(edge8);

        //  H -> K
        BaseEdge edge9 = new BaseEdge();
        edge9.setSource("H");
        edge9.setTarget("K");
        edges.add(edge9);

        //  J -> K
        BaseEdge edge10 = new BaseEdge();
        edge10.setSource("J");
        edge10.setTarget("K");
        edges.add(edge10);

        Loop<BaseNode, BaseEdge> liteFlowInfo = new Loop<>();
        liteFlowInfo.setNodes(nodes);
        liteFlowInfo.setEdges(edges);
        return liteFlowInfo;
    }
}