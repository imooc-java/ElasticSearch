package com.imooc.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by corning on 2018/3/5.
 */
@Configuration
public class MyConfig {

    @Bean
    public TransportClient client() throws UnknownHostException {

        Settings settings = Settings.builder().put("cluster.name", "corning").build();

        PreBuiltTransportClient client = new PreBuiltTransportClient(settings);

        int[] local_ports = {9300, 9301, 9302};
        for (int port : local_ports) {
            client.addTransportAddress(new InetSocketTransportAddress(
                    InetAddress.getByName("localhost"), port
            ));
        }

        return client;

    }

}
