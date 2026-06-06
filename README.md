# iflytek-liteflow-plugin

`iflytek-liteflow-plugin` 是一个面向 LiteFlow 的流程编排转换插件，用于把前端传入的节点/连线结构转换为 LiteFlow EL 表达式，方便在后端项目中直接接收和处理可视化编排数据。

## 项目能力

- 将 `LiteFlowInfo` 中的节点和边关系解析为运行时节点树
- 自动合并并生成 LiteFlow EL 表达式
- 支持以下编排场景
    - `THEN`
    - `WHEN`
    - `IF`
    - `SWITCH`
    - `ITERATOR`
    - `WHILE`

## 技术栈与环境

- Java 8
- Maven
- LiteFlow 2.13.2

## 项目结构

```text
src/main/java/com/iflytek/liteflow
├─ model           节点、边和流程模型
└─ util            LiteFlow EL 生成工具
```

## 核心模型

### `Node 和 Edge`

前端编排数据入口对象，包含：

- `nodes`: 节点集合
- `edges`: 连线集合

### `BaseNode`

基础节点字段：

- `id`: 节点唯一标识
- `name`: 节点名称
- `type`: 节点类型
- `nodeId`: LiteFlow 节点 ID，由工具类根据节点类型自动构建，用于生成 LiteFlow 组件及 EL 表达式

支持的节点类型：

- `COMMON`: 普通节点
- `IF`: 条件判断节点
- `SWITCH`: 条件选择节点
- `ITERATOR`: 迭代循环节点
- `WHILE`: while 循环节点
- `BREAK`: 终止循环节点
- `VIRTUAL`: 虚拟节点

### `BaseEdge`

基础连线字段：

- `id`: 连线唯一标识
- `source`: 起始节点 ID
- `target`: 目标节点 ID
- `type`: 连线类型，`IF` 场景下可使用 `TRUE` / `FALSE`
- `mapping`: `SWITCH` 场景下的分支映射值

## 快速开始

### 1. 引入依赖

如果你要在其他项目中使用该插件，可以先安装到本地仓库：

```bash
mvn clean install
```

然后在目标项目中引入：

```xml
<dependency>
    <groupId>io.github.356110537</groupId>
    <artifactId>liteflow-el-builder</artifactId>
    <version>1.0.6</version>
</dependency>
```

### 2. 构造编排数据

下面是一个简化示例：

```json
{
  "nodes": [
    { "id": "A", "name": "A", "type": "COMMON" },
    { "id": "B", "name": "B", "type": "COMMON" },
    { "id": "C", "name": "C", "type": "IF" },
    { "id": "D", "name": "D", "type": "COMMON" },
    { "id": "E", "name": "E", "type": "COMMON" }
  ],
  "edges": [
    { "id":"E_1", "source": "A", "target": "B" },
    { "id":"E_2", "source": "B", "target": "C" },
    { "id":"E_3", "source": "C", "target": "D", "type": "TRUE" },
    { "id":"E_4", "source": "C", "target": "E", "type": "FALSE" }
  ]
}
```

### 3. 生成 EL 表达式

```java
import com.iflytek.liteflow.model.Node;
import com.iflytek.liteflow.model.Edge;
import com.iflytek.liteflow.model.NodeType;
import com.iflytek.liteflow.util.LiteFlowUtil;
import java.util.List;
import java.util.ArrayList;

public class Demo {

  public static void main(String[] args) {
    // 初始化节点信息
    List<Node> nodes = new ArrayList<>();
    Node node1 = new Node("A", "A", NodeType.COMMON);
    nodes.add(node1);
    Node node2 = new Node("B", "B", NodeType.COMMON);
    nodes.add(node2);
    Node node3 = new Node("C", "C", NodeType.COMMON);
    nodes.add(node3);

    // 初始化边信息
    List<Edge> edges = new ArrayList<>();
    // A -> B
    Edge edge1 = new Edge();
    edge1.setSource("A");
    edge1.setTarget("B");
    edges.add(edge1);

    // B -> C
    Edge edge2 = new Edge();
    edge2.setSource("B");
    edge2.setTarget("C");
    edges.add(edge2);

    LiteFlowUtil util = new LiteFlowUtil();
    String el = util.createEL(nodes, edges);
    System.out.println(el);
  }
}
```

## 循环节点说明

当节点类型为 `ITERATOR` 或 `WHILE` 时，可额外传入：

- `loop`: 子流程定义，类型仍为 `Loop`
- `breakNode`: 跳出循环使用的节点

插件会先递归解析子流程，再拼装为对应的循环 EL。

## 主要入口

- `LiteFlowUtil#createEL(nodes, edges)`: 生成最终 EL 表达式
- `LiteFlowUtil#jsonToNode(nodes, edges)`: 将前端编排数据转换为运行时节点关系
- `LiteFlowUtil#jsonToNodes(nodes)`: 提取流程中全部节点，包含循环内节点

## 测试

当前测试类位于 `src/test/java/com/iflytek/liteflow/LiteFlowApplicationTest.java`，并通过 `LiteFlowDataUtil` 构造了多组编排样例，包括：

- 普通串行流程
- `IF` 分支流程
- `SWITCH` 路由流程
- `ITERATOR` 循环流程
- 多起点与分组流程

## 已知约束

- EL 生成依赖图结构满足可归并条件，否则会抛出运行时异常
- `IF` 和 `SWITCH` 场景对分支汇聚关系有明确要求
- 为防止异常递归，内部最大归并次数限制为 `300`
- 节点最终生成的 LiteFlow 组件 ID 由工具类自动构造，前缀为 `N_` 或 `VR_`