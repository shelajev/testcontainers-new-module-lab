package org.shelajev;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

public class MyTest {
    private static final Logger LOG = LoggerFactory.getLogger(MyTest.class);

    @Test
  void doSomethingTest() throws Exception {
        try (
            GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:6.0.5"))
        ) {
            redis.start();
            Assertions.assertThat(redis.isRunning()).isTrue();
        }
  }
}
