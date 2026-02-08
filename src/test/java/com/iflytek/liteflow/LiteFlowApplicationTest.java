package com.iflytek.liteflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iflytek.liteflow.model.BaseEdge;
import com.iflytek.liteflow.model.BaseNode;
import com.iflytek.liteflow.model.Loop;
import com.iflytek.liteflow.util.LiteFlowUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class LiteFlowApplicationTest {
    private static final Logger logger = LoggerFactory.getLogger(LiteFlowApplicationTest.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws Exception {
        LiteFlowUtil util = new LiteFlowUtil();
        Loop<BaseNode, BaseEdge> generator = LiteFlowDataUtil.generator9();
        logger.info("liteflow: {}", mapper.writeValueAsString(generator));
        Set<? extends BaseNode> nodes = util.jsonToNodes(generator.getNodes());
        for (BaseNode item : nodes) {
            logger.info("type:[{}] id:[{}] name:[{}] nodeId:[{}]", item.getClass().getSimpleName(), item.getId(), item.getName(), item.getNodeId());
        }
        String el = util.createEL(generator.getNodes(), generator.getEdges());
        logger.info("EL: {}", el);
    }
}