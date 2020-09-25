package net.softbell.bsh.iot.service.v1

import lombok.AllArgsConstructor
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
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.BiConsumer
import javax.transaction.Transactional
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Trigger 서비스
 */
@AllArgsConstructor
@Service
class IotTriggerServiceV1 {
    // Global Field
    private val memberService: MemberService? = null
    private val iotActionService: IotActionServiceV1? = null
    private val iotTriggerParserComp: IotTriggerParserCompV1? = null
    private val nodeTriggerRepo: NodeTriggerRepo? = null
    private val nodeTriggerActionRepo: NodeTriggerActionRepo? = null
    private val nodeActionRepo: NodeActionRepo? = null
    fun getAllTriggers(auth: Authentication): List<NodeTrigger?>? {
        // Field
        val member: Member?
        val listNodeTrigger: List<NodeTrigger?>?

        // Init
        member = memberService!!.getMember(auth.name)

        // Load
        listNodeTrigger = if (memberService.isAdmin(member)) nodeTriggerRepo!!.findAll() else nodeTriggerRepo!!.findByMember(member)

        // Return
        return listNodeTrigger
    }

    fun getTrigger(auth: Authentication?, triggerId: Long): NodeTrigger? {
        // Field
        val optNodeTrigger: Optional<NodeTrigger?>

        // Init
        optNodeTrigger = nodeTriggerRepo!!.findById(triggerId)

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
        mapAction = iotTriggerDto.getMapAction()
        member = memberService!!.getMember(auth.name)
        enableStatus = if (iotTriggerDto.isEnableStatus()) EnableStatusRule.ENABLE else EnableStatusRule.DISABLE

        // Exception
        if (member == null) return false

        // Data Process - Node Trigger
        nodeTrigger = builder()
                .enableStatus(enableStatus)
                .description(iotTriggerDto.getDescription())
                .expression(iotTriggerDto.getExpression())
                .member(member)
                .lastStatus(TriggerLastStatusRule.WAIT)
                .build()

        // DB - Node Trigger Save
        nodeTriggerRepo!!.save(nodeTrigger)


        // Data Process - Node Trigger Action
        if (mapAction != null) {
            mapAction.forEach(BiConsumer { key: Long, value: IotTriggerActionDto ->
                // Field
                val optNodeAction: Optional<NodeAction?>
                val nodeTriggerAction: NodeTriggerAction

                // Init
                optNodeAction = nodeActionRepo!!.findById(key)

                // Build
                if (optNodeAction.isPresent) {
                    nodeTriggerAction = builder()
                            .nodeTrigger(nodeTrigger)
                            .nodeAction(optNodeAction.get())
                            .build()
                    if (value.isEventError() && value.isEventOccur() && value.isEventRestore()) nodeTriggerAction.setTriggerStatus(TriggerStatusRule.ALL) else if (value.isEventOccur() && value.isEventRestore()) nodeTriggerAction.setTriggerStatus(TriggerStatusRule.OCCUR_AND_RESTORE) else if (value.isEventError()) nodeTriggerAction.setTriggerStatus(TriggerStatusRule.ERROR) else if (value.isEventOccur()) nodeTriggerAction.setTriggerStatus(TriggerStatusRule.OCCUR) else if (value.isEventRestore()) nodeTriggerAction.setTriggerStatus(TriggerStatusRule.RESTORE)

                    // List Add
                    listAction.add(nodeTriggerAction)
                }
            })
            nodeTrigger.setNodeTriggerActions(listAction)
        }

        // DB - Node Trigger Action Save
        nodeTriggerActionRepo!!.saveAll(listAction)


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
        mapAction = iotTriggerDto.getMapAction()
        //		member = memberService.getMember(auth.getName());
        optNodeTrigger = nodeTriggerRepo!!.findById(triggerId)
        enableStatus = if (iotTriggerDto.isEnableStatus()) EnableStatusRule.ENABLE else EnableStatusRule.DISABLE

        // Exception
        if ( /*member == null || */!optNodeTrigger.isPresent) return false

        // Init
        nodeTrigger = optNodeTrigger.get()

        // DB - Trigger Action Clear
        nodeTriggerActionRepo!!.deleteAll(nodeTrigger.getNodeTriggerActions())

        // Data Process - Node Trigger
        nodeTrigger.setEnableStatus(enableStatus)
        nodeTrigger.setDescription(iotTriggerDto.getDescription())
        nodeTrigger.setExpression(iotTriggerDto.getExpression())

        // DB - Node Trigger Save
        nodeTriggerRepo.save(nodeTrigger)


        // Data Process - Node Trigger Action
        if (mapAction != null) {
            mapAction.forEach(BiConsumer { key: Long, value: IotTriggerActionDto ->
                // Field
                val optNodeAction: Optional<NodeAction?>
                val nodeTriggerAction: NodeTriggerAction

                // Init
                optNodeAction = nodeActionRepo!!.findById(key)

                // Build
                if (optNodeAction.isPresent) {
                    nodeTriggerAction = builder()
                            .nodeTrigger(nodeTrigger)
                            .nodeAction(optNodeAction.get())
                            .build()
                    if (value.isEventError() && value.isEventOccur() && value.isEventRestore()) nodeTriggerAction.setTriggerStatus(TriggerStatusRule.ALL) else if (value.isEventOccur() && value.isEventRestore()) nodeTriggerAction.setTriggerStatus(TriggerStatusRule.OCCUR_AND_RESTORE) else if (value.isEventError()) nodeTriggerAction.setTriggerStatus(TriggerStatusRule.ERROR) else if (value.isEventOccur()) nodeTriggerAction.setTriggerStatus(TriggerStatusRule.OCCUR) else if (value.isEventRestore()) nodeTriggerAction.setTriggerStatus(TriggerStatusRule.RESTORE)

                    // List Add
                    listAction.add(nodeTriggerAction)
                }
            })
            nodeTrigger.setNodeTriggerActions(listAction)
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
        optNodeTrigger = nodeTriggerRepo!!.findById(triggerId)

        // Exception
        if (!optNodeTrigger.isPresent) return false

        // Load
        nodeTrigger = optNodeTrigger.get()

        // DB - Update
        if (status) nodeTrigger.setEnableStatus(EnableStatusRule.ENABLE) else nodeTrigger.setEnableStatus(EnableStatusRule.DISABLE)

        // Return
        return true
    }

    @Transactional
    fun deleteTrigger(auth: Authentication?, triggerId: Long): Boolean {
        // Field
        val optNodeTrigger: Optional<NodeTrigger?>
        val nodeTrigger: NodeTrigger

        // Init
        optNodeTrigger = nodeTriggerRepo!!.findById(triggerId)

        // Exception
        if (!optNodeTrigger.isPresent) return false
        nodeTrigger = optNodeTrigger.get()

        // DB - Delete
        for (entity in nodeTrigger.getNodeTriggerActions()) nodeTriggerActionRepo!!.delete(entity)
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
        listNodeTrigger = iotTriggerParserComp!!.convTrigger(nodeItem)

        // Process
        for (nodeTrigger in listNodeTrigger!!) {
            // Field
            var isOccur: Boolean?

            // Init
            isOccur = iotTriggerParserComp.parseEntity(nodeTrigger)

            // Exception
            if (nodeTrigger.getLastStatus() === TriggerLastStatusRule.ERROR && isOccur == null ||
                    nodeTrigger.getLastStatus() === TriggerLastStatusRule.OCCUR && isOccur!! ||
                    nodeTrigger.getLastStatus() === TriggerLastStatusRule.RESTORE && !isOccur!!) continue

            // DB - Update
            if (isOccur == null) nodeTrigger.setLastStatus(TriggerLastStatusRule.ERROR) // DB Update - Error
            else if (isOccur) nodeTrigger.setLastStatus(TriggerLastStatusRule.OCCUR) // Occur
            else nodeTrigger.setLastStatus(TriggerLastStatusRule.RESTORE) // Restore

            // Exec Action
            for (nodeAction in iotTriggerParserComp.getTriggerAction(nodeTrigger)) if (!iotActionService!!.execAction(nodeAction, nodeTrigger.getMember())) isSuccess = false
        }

        // Return
        return isSuccess
    }
}