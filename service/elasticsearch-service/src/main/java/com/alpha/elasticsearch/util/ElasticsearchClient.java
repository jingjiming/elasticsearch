package com.alpha.elasticsearch.util;

import com.alpha.config.util.SearchPropertiesUtils;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * Created by jiming.jing on 2020/4/28.
 */
@Component
public class ElasticsearchClient {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchClient.class);

    public TransportClient transportClient;

    /*public TransportClient client() {
        if (transportClient == null) {
            try {
                Settings settings = Settings.builder().put("cluster.name", "elasticsearch").put("client.transport.sniff", true)
                        .build();
                transportClient = new PreBuiltTransportClient(settings);
                transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.1.238"), 9300));
            } catch (Exception e) {
                logger.error("获取es client 错误:{}", e);
            }
        }
        return transportClient;
    }*/

    /**
     * 连接es
     *
     * @return
     */
    public TransportClient client() {
        if (transportClient == null) {
            try {
                //设置集群名称
                Settings settings = Settings.builder().put("cluster.name", SearchPropertiesUtils.getProperty("alpha.search.cluster.name")).build();
                //创建client
                transportClient = new PreBuiltTransportClient(settings)
                        .addTransportAddresses(
                                new TransportAddress(InetAddress.getByName(SearchPropertiesUtils.getProperty("alpha.es.url")), Integer.parseInt(SearchPropertiesUtils.getProperty("alpha.es.port"))),
                                new TransportAddress(InetAddress.getByName(SearchPropertiesUtils.getProperty("dyq.es.url2")), Integer.parseInt(SearchPropertiesUtils.getProperty("alpha.es.port2"))));
            } catch (Exception e) {
                logger.error("获取es client 错误:{}", e);
            }
        }
        return transportClient;
    }

    /**
     * 获取索引管理的IndicesAdminClient
     */
    public IndicesAdminClient getAdminClient() {
        return client().admin().indices();
    }
}
