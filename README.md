# SpringKafkaDemo
簡單的 SpringKafka 專案

# Apache Kafka 簡介

Apache Kafka 是一個分佈式流式處理平台，旨在建立高度可擴展、容錯、分佈式的實時數據管道。

## 特點

- **分佈式系統**: Kafka 可以部署在多個節點上，實現數據的分佈式處理。
- **容錯性**: 具有高度的容錯能力，允許節點失效後繼續運行。
- **高吞吐量**: 適用於大量數據的高吞吐率和低延遲需求。
- **擴展性**: 易於擴展以應對增長的數據流量。
- **持久性**: 保留數據以供後續處理。

## 名詞解釋

- **Topic（主題）**: Kafka 中用於組織和區分消息的類別，生產者將消息發佈到主題，消費者則從主題訂閱消息。
- **Producer（生產者）**: 負責將消息發佈到 Kafka 主題的應用程序或服務。
- **Consumer（消費者）**: 從 Kafka 主題中消費消息的應用程序或服務。
- **Partition（分區）**: 主題的一個部分，消息被分發到不同的分區中，以實現擴展性和並行處理。
- **Broker（代理）**: Kafka 集群中的一個節點，負責存儲和處理消息。
- **Offset（偏移量）**: 標識主題中消息的唯一數字，用於跟踪消費者讀取消息的位置。

# 使用 Spring Kafka 和 Redis

整合 Spring Kafka 作為消息生產者和消費者，生產者和消費者之間通過 Redis 進行訊息傳遞，可以避免將過多的字串傳遞給kafka，只需要傳遞編號(key)傳送即可。

1. Kafka 生產者，將需要傳送的資料序列化為 JSON 字串。

2. 在生產者中將 JSON 字串存儲到 Redis 中，以 Redis 的 key 作為消息編號傳遞給生產者。

3. Kafka 消費者，已生產者傳遞的消息編號(key)取得須處理的資料。

# 啟動專案

docker-compose up -d

# 功能展示
- **GET http://localhost:8082/asynSend **: 非同步執行。
- ![image](https://github.com/k9798909/SpringKafkaDemo/assets/62507948/bca11454-5786-4012-8ac5-17239f6dcb09)
- **POST http://localhost:8082/book **: 生產者傳遞並等待消費者執行完新增的結果。
- ![image](https://github.com/k9798909/SpringKafkaDemo/assets/62507948/14599eaf-8999-4634-b881-44aa395c54a9)
- **GET http://localhost:8082/book **: 生產者等待消費者查詢完資料並回傳。
- ![image](https://github.com/k9798909/SpringKafkaDemo/assets/62507948/273c5481-44ab-49a2-acc3-22091574c92e)


