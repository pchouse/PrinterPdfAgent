/*
 * Copyright 2023 PChouse - Reflexão, Estudos e Sistemas Informáricos, Lda
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pt.pchouse.printer.pdf.agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@SpringBootApplication
public class Application
{

    /**
     * @since 1.0.0
     */
    @Value("${task.executor.core.pool.size}")
    private int taskExecutorCorePoolSize;

    /**
     * @since 1.0.0
     */
    @Value("${task.executor.max.pool.size}")
    private int taskExecutorMaxPoolSize;

    /**
     * @since 1.0.0
     */
    @Value("${task.executor.queue}")
    private int taskExecutorQueue;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * @return The default Task Executor
     * @since 1.0.0
     */
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(taskExecutorCorePoolSize);
        executor.setMaxPoolSize(taskExecutorMaxPoolSize);
        executor.setQueueCapacity(taskExecutorQueue);
        executor.setThreadNamePrefix("PrinterAgent");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setKeepAliveSeconds(60 * 5);
        executor.initialize();
        return executor;
    }

}
