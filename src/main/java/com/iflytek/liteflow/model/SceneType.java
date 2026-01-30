package com.iflytek.liteflow.model;

public enum SceneType {

    NODE("单节点"),
    THEN("串行场景"),
    WHEN("并行场景"),
    IF("条件判断场景"),
    SWITCH("条件选择场景"),
    ITERATOR("迭代场景"),
    WHILE("循环场景"),
    ;

    final String message;

    SceneType(String message) {
        this.message = message;
    }
}