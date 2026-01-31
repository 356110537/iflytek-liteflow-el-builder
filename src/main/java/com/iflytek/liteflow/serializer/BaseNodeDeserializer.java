package com.iflytek.liteflow.serializer;

import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iflytek.liteflow.model.BaseNode;
import com.iflytek.liteflow.model.LiteFlowInfo;
import com.iflytek.liteflow.model.LoopNode;
import com.iflytek.liteflow.model.NodeType;

import java.io.IOException;

public class BaseNodeDeserializer extends JsonDeserializer<BaseNode> {

    @Override
    public BaseNode deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        JsonNode jsonNode = mapper.readTree(parser);
        String type = jsonNode.get("type").asText();
        NodeType nodeType = NodeType.valueOf(type);
        String id = jsonNode.get("id").asText();
        String name = jsonNode.get("name").asText();
        Assert.notNull(nodeType, "node type is null");
        if (nodeType != NodeType.WHILE && nodeType != NodeType.ITERATOR) {
            BaseNode baseNode = new BaseNode(nodeType);
            baseNode.setId(id);
            baseNode.setName(name);
            return baseNode;
        }
        LoopNode node = new LoopNode(id, name, nodeType);
        JsonNode loopJsonNode = jsonNode.get("loop");
        if (loopJsonNode != null && !loopJsonNode.isNull()) {
            LiteFlowInfo loopInfo = mapper.treeToValue(loopJsonNode, LiteFlowInfo.class);
            node.setLoop(loopInfo);
        }
        JsonNode breakJsonNode = jsonNode.get("breakNode");
        if (breakJsonNode != null && !breakJsonNode.isNull()) {
            BaseNode breakNode = mapper.treeToValue(breakJsonNode, BaseNode.class);
            node.setBreakNode(breakNode);
        }
        return node;
    }
}