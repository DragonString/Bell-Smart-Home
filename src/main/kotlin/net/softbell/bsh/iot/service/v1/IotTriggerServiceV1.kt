package net.softbell.bsh.iot.service.v1

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.TriggerLastStatusRule
import net.softbell.bsh.domain.TriggerStatusRule
import net.softbell.bsh.domain.entity.*
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
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Trigger 서비스
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

    fun getAllTriggers(auth: Authentication): List<NodeTrigger?>? {
        // Field
        val member: Member?
        val listNodeTrigger: List<NodeTrigger?>?

        // Init
        member = memberService.getMember(auth.name)

        // Load
        listNodeTrigger = if (memberService.isAdmin(member)) nodeTriggerRepo.findAll() else nodeTriggerRepo.findByMember(member)

        // Return
        return listNodeTrigger
    }

    fun getTrigger(auth: Authentication?, triggerId: Long): NodeTrigger? {
        // Field
        val optNodeTrigger: Optional<NodeTrigger?>

        // Init
        optNodeTrigger = nodeTriggerRepo.findById(triggerId)

        // Exception
        return if (!optNodeTrigger.isPresent) null else optNodeTrigger.get()

        // Return
    }

    @Transactional
    fun createTrigger(auth: Authentication, iotTriggerDto: IotTriggerDto?): Boolean {
        // Exception
        if (iotTriggerDto == null) return false

        // Field
        val member: Member?
        val nodeTrigger: NodeTrigger
        val mapAction: HashMap<Long, IotTriggerActionDto>?
        val listAction: MutableList<NodeTriggerAction>
        val enableStatus: EnableStatusRule

        // Init
        listAction = ArrayList()
        mapAction = iotTriggerDto.mapAction
        member = memberService.getMember(auth.name)
        enableStatus = if (iotTriggerDto.enableStatus) EnableStatusRule.ENABLE else EnableStatusRule.DISABLE

        // Exception
        if (member == null) return false

        // Data Process - Node Trigger
        nodeTrigger = NodeTrigger(
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
            mapAction.forEach(BiConsumer { key: Long?, value: IotTriggerActionDto ->
                // Field
                val optNodeAction: Optional<NodeAction?>
                val nodeTriggerAction: NodeTriggerAction

                // Init
                optNodeAction = nodeActionRepo.findById(key!!)

                // Build
                if (optNodeAction.isPresent) {
                    nodeTriggerAction = NodeTriggerAction(
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
    fun modifyTrigger(auth: Authentication?, triggerId: Long, iotTriggerDto: IotTriggerDto?): Boolean {
        // Exception
        if (iotTriggerDto == null) return false

        // Field
//		Member member;
        val optNodeTrigger: Optional<NodeTrigger?>
        val nodeTrigger: NodeTrigger
        val mapAction: HashMap<Long, IotTriggerActionDto>?
        val listAction: MutableList<NodeTriggerAction>
        val enableStatus: EnableStatusRule

        // Init
        listAction = ArrayList()
        mapAction = iotTriggerDto.mapAction
        //		member = memberService.getMember(auth.getName());
        optNodeTrigger = nodeTriggerRepo.findById(triggerId)
        enableStatus = if (iotTriggerDto.enableStatus) EnableStatusRule.ENABLE else EnableStatusRule.DISABLE

        // Exception
        if ( /*member == null || */!optNodeTrigger.isPresent) return false

        // Init
        nodeTrigger = optNodeTrigger.get()

        // DB - Trigger Action Clear
        nodeTriggerActionRepo.deleteAll(nodeTrigger.nodeTriggerActions!!)

        // Data Process - Node Trigger
        nodeTrigger.enableStatus = enableStatus
        nodeTrigger.description = iotTriggerDto.description
        nodeTrigger.expression = iotTriggerDto.expression

        // DB - Node Trigger Save
        nodeTriggerRepo.save(nodeTrigger)


        // Data Process - Node Trigger Action
        if (mapAction != null) {
            mapAction.forEach(BiConsumer { key: Long?, value: IotTriggerActionDto ->
                // Field
                val optNodeAction: Optional<NodeAction?>
                val nodeTriggerAction: NodeTriggerAction

                // Init
                optNodeAction = nodeActionRepo.findById(key!!)

                // Build
                if (optNodeAction.isPresent) {
                    nodeTriggerAction = NodeTriggerAction(
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
    fun setTriggerEnableStatus(auth: Authentication?, triggerId: Long, status: Boolean): Boolean {
        // Field
        val optNodeTrigger: Optional<NodeTrigger?>
        val nodeTrigger: NodeTrigger

        // Init
        optNodeTrigger = nodeTriggerRepo.findById(triggerId)

        // Exception
        if (!optNodeTrigger.isPresent) return false

        // Load
        nodeTrigger = optNodeTrigger.get()

        // DB - Update
        if (status) nodeTrigger.enableStatus = EnableStatusRule.ENABLE else nodeTrigger.enableStatus = EnableStatusRule.DISABLE

        // Return
        return true
    }

    @Transactional
    fun deleteTrigger(auth: Authentication?, triggerId: Long): Boolean {
        // Field
        val optNodeTrigger: Optional<NodeTrigger?>
        val nodeTrigger: NodeTrigger

        // Init
        optNodeTrigger = nodeTriggerRepo.findById(triggerId)

        // Exception
        if (!optNodeTrigger.isPresent) return false
        nodeTrigger = optNodeTrigger.get()

        // DB - Delete
        for (entity in nodeTrigger.nodeTriggerActions!!) nodeTriggerActionRepo.delete(entity)
        nodeTriggerRepo.delete(nodeTrigger)

        // Return
        return true
    }

    @Transactional
    fun procTrigger(nodeItem: NodeItem?): Boolean {
        // Field
        var isSuccess: Boolean
        val listNodeTrigger: List<NodeTrigger?>?

        // Init
        isSuccess = true
        listNodeTrigger = iotTriggerParserComp.convTrigger(nodeItem)

        // Process
        for (nodeTrigger in listNodeTrigger!!) {
            // Field
            var isOccur: Boolean?

            // Init
            isOccur = iotTriggerParserComp.parseEntity(nodeTrigger!!)

            // Exception
            if (nodeTrigger.lastStatus === TriggerLastStatusRule.UNKNOWN && isOccur == null ||
                    nodeTrigger.lastStatus === TriggerLastStatusRule.OCCUR && isOccur!! ||
                    nodeTrigger.lastStatus === TriggerLastStatusRule.RESTORE && !isOccur!!) continue

            // DB - Update
            if (isOccur == null) nodeTrigger.lastStatus = TriggerLastStatusRule.UNKNOWN // DB Update - Error
            else if (isOccur) nodeTrigger.lastStatus = TriggerLastStatusRule.OCCUR // Occur
            else nodeTrigger.lastStatus = TriggerLastStatusRule.RESTORE // Restore

            // Exec Action
            for (nodeAction in iotTriggerParserComp.getTriggerAction(nodeTrigger)) if (!iotActionService.execAction(nodeAction, nodeTrigger.member)) isSuccess = false
        }

        // Return
        return isSuccess
    }
}