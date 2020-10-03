package net.softbell.bsh.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

/**
 * @author : Bell(bell@softbell.net)
 * @description : 스케줄러 설정
 */
@Configuration
class SchedulerConfig : SchedulingConfigurer {
    // Global Field
    private val _POOL_SIZE = 10

    override fun configureTasks(scheduledTaskRegistrar: ScheduledTaskRegistrar) {
        // Init
        val threadPoolTaskScheduler = ThreadPoolTaskScheduler()

        // Config
        threadPoolTaskScheduler.poolSize = _POOL_SIZE
        threadPoolTaskScheduler.setThreadNamePrefix("BSH-Task-Pool-")
        threadPoolTaskScheduler.initialize()

        // Set
        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler)
    }
}