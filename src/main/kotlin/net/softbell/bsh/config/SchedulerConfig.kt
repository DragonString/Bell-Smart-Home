package net.softbell.bsh.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 스케줄러 설정
 */
@Configuration
class SchedulerConfig : SchedulingConfigurer {
    // Global Field
    private val POOL_SIZE = 10
    override fun configureTasks(scheduledTaskRegistrar: ScheduledTaskRegistrar) {
        // Field
        val threadPoolTaskScheduler: ThreadPoolTaskScheduler

        // Init
        threadPoolTaskScheduler = ThreadPoolTaskScheduler()

        // Config
        threadPoolTaskScheduler.poolSize = POOL_SIZE
        threadPoolTaskScheduler.setThreadNamePrefix("BSH-Task-Pool-")
        threadPoolTaskScheduler.initialize()

        // Set
        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler)
    }
}