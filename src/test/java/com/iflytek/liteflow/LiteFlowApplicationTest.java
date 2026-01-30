package com.iflytek.liteflow;

import com.iflytek.liteflow.model.LiteFlowInfo;
import com.iflytek.liteflow.model.Node;
import com.iflytek.liteflow.util.LiteFlowUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class LiteFlowApplicationTest {
    private static final Logger logger = LoggerFactory.getLogger(LiteFlowApplicationTest.class);

    @Test
    public void test() {
        LiteFlowUtil util = new LiteFlowUtil();
        LiteFlowInfo generator = LiteFlowDataUtil.generator10();
        String el = util.createEL(generator);
        logger.info("EL: {}", el);
        Set<Node> nodes = util.jsonToNodes(generator);
        for (Node item : nodes) {
            logger.info("type:[{}] id:[{}] name:[{}] nodeId:[{}]", item.getClass().getSimpleName(), item.getId(), item.getName(), item.getNodeId());
        }
    }
}