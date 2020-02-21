package net.softbell.bsh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 스케줄러 설정
 */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer
{
	// Global Field
    private final int POOL_SIZE = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar)
    {
    	// Field
        ThreadPoolTaskScheduler threadPoolTaskScheduler;
        
        // Init
        threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        
        // Config
        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("BSH-Task-Pool-");
        threadPoolTaskScheduler.initialize();
        
        // Set
        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
