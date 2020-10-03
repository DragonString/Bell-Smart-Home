package net.softbell.bsh.iot.service.v1

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.TriggerLastStatusRule
import net.softbell.bsh.domain.TriggerStatusRule
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeTrigger
import net.softbell.bsh.domain.entity.NodeTriggerAction
import net.softbell.bsh.domain.repository.NodeActionRepo
import net.softbell.bsh.domain.repository.NodeTriggerActionRepo
import net.softbell.bsh.domain.repository.NodeTriggerRepo
import net.softbell.bsh.dto.request.IotTriggerActionDto
import net.softbell.bsh.dto.request.IotTriggerDto
import net.softbell.bsh.iot.component.v1.IotTriggerParserCompV1
import net.softbell.bsh.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.BiConsumer
import javax.transaction.Transactional

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT Node Trigger 서비스
 */
@Service
class IotTriggerServiceV1 {
    // Global Field
    @Autowired private lateinit var memberService: MemberService
    @Autowired private lateinit var iotActionService: IotActionServiceV1
    @Autowired private lateinit var iotTriggerParserComp: IotTriggerParserCompV1
    @Autowired private lateinit var nodeTriggerRepo: NodeTriggerRepo
    @Autowired private lateinit var nodeTriggerActionRepo: NodeTriggerActionRepo
    @Autowired private lateinit var nodeActionRepo: NodeActionRepo

    fun getAllTriggers(auth: Authentication): List<NodeTrigger> {
        // Init
        val member = memberService.getMember(auth.name) ?: return emptyList()

        // Return
        return if (memberService.isAdmin(member))
            nodeTriggerRepo.findAll()
        else
            nodeTriggerRepo.findByMember(member)
    }

    fun getTrigger(auth: Authentication, triggerId: Long): NodeTrigger? {
        // Init
        val optNodeTrigger = nodeTriggerRepo.findById(triggerId)

        // Return
        return if (optNodeTrigger.isPresent)
            optNodeTrigger.get()
        else
            null
    }

    @Transactional
    fun createTrigger(auth: Authentication, iotTriggerDto: IotTriggerDto): Boolean {
        // Init
        val listAction: MutableList<NodeTriggerAction> = ArrayList()
        val mapAction = iotTriggerDto.mapAction
        val member = memberService.getMember(auth.name) ?: return false
        val enableStatus = if (iotTriggerDto.enableStatus)
            EnableStatusRule.ENABLE
        else
            EnableStatusRule.DISABLE

        // Data Process - Node Trigger
        val nodeTrigger = NodeTrigger(
                enableStatus = enableStatus,
                description = iotTriggerDto.description,
                expression = iotTriggerDto.expression,
                member = member,
                lastStatus = TriggerLastStatusRule.WAIT
        )

        // DB - Node Trigger Save
        nodeTriggerRepo.save(nodeTrigger)


        // Data Process - Node Trigger Action
        if (mapAction != null) {
            mapAction.forEach(BiConsumer { key: Long, value: IotTriggerActionDto ->
                // Init
                val optNodeAction = nodeActionRepo.findById(key)

                // Build
                if (optNodeAction.isPresent) {
                    val nodeTriggerAction = NodeTriggerAction(
                            nodeTrigger = nodeTrigger,
                            nodeAction = optNodeAction.get(),
                            triggerStatus = TriggerStatusRule.RESTORE // TEMP
                    )

                    if (value.eventError && value.eventOccur && value.eventRestore)
                        nodeTriggerAction.triggerStatus = TriggerStatusRule.ALL
                    else if (value.eventOccur && value.eventRestore)
                        nodeTriggerAction.triggerStatus = TriggerStatusRule.OCCUR_AND_RESTORE
                    else if (value.eventError)
                        nodeTriggerAction.triggerStatus = TriggerStatusRule.ERROR
                    else if (value.eventOccur)
                        nodeTriggerAction.triggerStatus = TriggerStatusRule.OCCUR
                    else if (value.eventRestore)
                        nodeTriggerAction.triggerStatus = TriggerStatusRule.RESTORE

                    // List Add
                    listAction.add(nodeTriggerAction)
                }
            })
            nodeTrigger.nodeTriggerActions = listAction
        }

        // DB - Node Trigger Action Save
        nodeTriggerActionRepo.saveAll(listAction)


        // Return
        return true
    }

    @Transactional
    fun modifyTrigger(auth: Authentication, triggerId: Long, iotTriggerDto: IotTriggerDto): Boolean {
        // Init
        val listAction: MutableList<NodeTriggerAction> = ArrayList()
        val mapAction = iotTriggerDto.mapAction
        val optNodeTrigger = nodeTriggerRepo.findById(triggerId)
        val enableStatus = if (iotTriggerDto.enableStatus)
            EnableStatusRule.ENABLE
        else
            EnableStatusRule.DISABLE

        // Exception
        if (!optNodeTrigger.isPresent)
            return false

        // Init
        val nodeTrigger = optNodeTrigger.get()

        // DB - Trigger Action Clear
        nodeTriggerActionRepo.deleteAll(nodeTrigger.nodeTriggerActions)

        // Data Process - Node Trigger
        nodeTrigger.enableStatus = enableStatus
        nodeTrigger.description = iotTriggerDto.description
        nodeTrigger.expression = iotTriggerDto.expression

        // DB - Node Trigger Save
        nodeTriggerRepo.save(nodeTrigger)


        // Data Process - Node Trigger Action
        if (mapAction != null) {
            mapAction.forEach(BiConsumer { key: Long, value: IotTriggerActionDto ->
                // Init
                val optNodeAction = nodeActionRepo.findById(key)

                // Build
                if (optNodeAction.isPresent) {
                    val nodeTriggerAction = NodeTriggerAction(
                            nodeTrigger = nodeTrigger,
                            nodeAction = optNodeAction.get(),
                            triggerStatus = TriggerStatusRule.RESTORE // TEMP
                    )

                    if (value.eventError && value.eventOccur && value.eventRestore)
                        nodeTriggerAction.triggerStatus = TriggerStatusRule.ALL
                    else if (value.eventOccur && value.eventRestore)
                        nodeTriggerAction.triggerStatus = TriggerStatusRule.OCCUR_AND_RESTORE
                    else if (value.eventError)
                        nodeTriggerAction.triggerStatus = TriggerStatusRule.ERROR
                    else if (value.eventOccur)
                        nodeTriggerAction.triggerStatus = TriggerStatusRule.OCCUR
                    else if (value.eventRestore)
                        nodeTriggerAction.triggerStatus = TriggerStatusRule.RESTORE

                    // List Add
                    listAction.add(nodeTriggerAction)
                }
            })
            nodeTrigger.nodeTriggerActions = listAction
        }

        // DB - Node Trigger Action Save
        nodeTriggerActionRepo.saveAll(listAction)

        // Return
        return true
    }

    @Transactional
    fun setTriggerEnableStatus(auth: Authentication, triggerId: Long, status: Boolean): Boolean {
        // Init
        val optNodeTrigger = nodeTriggerRepo.findById(triggerId)

        // Exception
        if (!optNodeTrigger.isPresent)
            return false

        // Load
        val nodeTrigger = optNodeTrigger.get()

        // DB - Update
        if (status)
            nodeTrigger.enableStatus = EnableStatusRule.ENABLE
        else
            nodeTrigger.enableStatus = EnableStatusRule.DISABLE

        // Return
        return true
    }

    @Transactional
    fun deleteTrigger(auth: Authentication, triggerId: Long): Boolean {
        // Init
        val optNodeTrigger = nodeTriggerRepo.findById(triggerId)

        // Exception
        if (!optNodeTrigger.isPresent)
            return false
        val nodeTrigger = optNodeTrigger.get()

        // DB - Delete
        for (entity in nodeTrigger.nodeTriggerActions)
            nodeTriggerActionRepo.delete(entity)
        nodeTriggerRepo.delete(nodeTrigger)

        // Return
        return true
    }

    @Transactional
    fun procTrigger(nodeItem: NodeItem): Boolean {
        // Init
        var isSuccess = true
        val listNodeTrigger = iotTriggerParserComp.convTrigger(nodeItem)

        // Process
        for (nodeTrigger in listNodeTrigger) {
            // Init
            val isOccur = iotTriggerParserComp.parseEntity(nodeTrigger)

            // Exception
            if (nodeTrigger.lastStatus == TriggerLastStatusRule.UNKNOWN && isOccur == null ||
                    nodeTrigger.lastStatus == TriggerLastStatusRule.OCCUR && isOccur!! ||
                    nodeTrigger.lastStatus == TriggerLastStatusRule.RESTORE && !isOccur!!)
                continue

            // DB - Update
            when {
                isOccur == null -> nodeTrigger.lastStatus = TriggerLastStatusRule.UNKNOWN // DB Update - Error
                isOccur -> nodeTrigger.lastStatus = TriggerLastStatusRule.OCCUR // Occur
                else -> nodeTrigger.lastStatus = TriggerLastStatusRule.RESTORE // Restore
            }

            // Exec Action
            for (nodeAction in iotTriggerParserComp.getTriggerAction(nodeTrigger))
                if (!iotActionService.execAction(nodeAction, nodeTrigger.member))
                    isSuccess = false
        }

        // Return
        return isSuccess
    }
}