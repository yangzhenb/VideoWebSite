package com.yangz.tank;

import redis.clients.jedis.Jedis;

public class Test3 {

    public static void main(String[] args) {

        Jedis jedis1 = new Jedis("192.168.1.122",9000);
        Jedis jedis2 = new Jedis("192.168.1.122",9001);
        jedis1.set("tank","hello world 1");
        System.out.println(jedis2.get("tank"));
    }
}
