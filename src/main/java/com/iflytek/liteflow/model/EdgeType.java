package com.iflytek.liteflow.model;

/**
 * 边类型枚举
 */
public enum EdgeType {

    TRUE("条件为真"),
    FALSE("条件为假"),
    ;

    final String message;

    EdgeType(String message) {
        this.message = message;
    }
}