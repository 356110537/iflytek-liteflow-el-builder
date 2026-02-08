package com.iflytek.liteflow.model;

public enum NodeType {

    COMMON("普通节点"),
    IF("条件判断节点"),
    SWITCH("条件选择节点"),
    ITERATOR("迭代循环节点"),
    WHILE("WHILE循环节点"),
    BREAK("终止循环节点"),
    VIRTUAL("虚拟节点"),
    ;

    final String message;

    NodeType(String message) {
        this.message = message;
    }
}