package com.learning.buildpack.model;


/*
    Model Class to Represent a Packet in the Redis Cluster
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacketMetaData {

    private String key;
    private String refID;
    private int age;
}
