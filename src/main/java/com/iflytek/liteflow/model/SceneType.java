package com.iflytek.liteflow.model;

public enum SceneType {

    THEN("串行场景"),
    WHEN("并行场景"),
    ;

    final String message;

    SceneType(String message) {
        this.message = message;
    }
}