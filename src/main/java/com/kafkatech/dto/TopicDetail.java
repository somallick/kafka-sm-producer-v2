package com.kafkatech.dto;

import lombok.Data;

@Data
public class TopicDetail {
    private String name;
    private int partitions;
    private int replicationFactor;
}
