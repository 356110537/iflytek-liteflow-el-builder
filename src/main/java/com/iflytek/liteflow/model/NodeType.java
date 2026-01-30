package com.iflytek.liteflow.model;

public enum NodeType {

    COMMON("普通节点"),
    IF("判断节点"),
    SWITCH("条件选择节点"),
    ITERATOR("迭代循环组件"),
    WHILE("WHILE循环组件"),
    VIRTUAL("虚拟节点"),
    ;

    final String message;

    NodeType(String message) {
        this.message = message;
    }
}