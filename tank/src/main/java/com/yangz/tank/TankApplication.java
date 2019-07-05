package com.yangz.tank;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Callable;

@SpringBootApplication
@MapperScan("com.yangz.tank.dao")
public class TankApplication {

	public static void main(String[] args) {
		SpringApplication.run(TankApplication.class, args);
		System.out.println("测试创建分支修改之后合并分支到主分支（最安全的提交方式）");
		sdjsd
	}
}
