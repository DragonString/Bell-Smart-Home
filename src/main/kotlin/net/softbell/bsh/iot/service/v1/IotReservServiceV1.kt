package net.softbell.bsh.iot.service.v1

import mu.KLogging
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.domain.entity.NodeReserv
import net.softbell.bsh.domain.entity.NodeReservAction
import net.softbell.bsh.domain.repository.NodeActionRepo
import net.softbell.bsh.domain.repository.NodeReservActionRepo
import net.softbell.bsh.domain.repository.NodeReservRepo
import net.softbell.bsh.dto.request.IotReservDto
import net.softbell.bsh.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.BiConsumer
import javax.transaction.Transactional

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT Node Reservation 서비스
 */
@Service
class IotReservServiceV1 {
    // Global Field
    @Autowired private lateinit var memberService: MemberService
    @Autowired private lateinit var nodeReservRepo: NodeReservRepo
    @Autowired private lateinit var nodeReservActionRepo: NodeReservActionRepo
    @Autowired private lateinit var nodeActionRepo: NodeActionRepo

    fun getAvailableAction(auth: Authentication): List<NodeAction> {
        // Init
        val member = memberService.getMember(auth.name) ?: return emptyList()

        // Return
        return if (memberService.isAdmin(member))
            nodeActionRepo.findAll()
        else
            nodeActionRepo.findByMember(member)
    }

    fun getAllReservs(auth: Authentication): List<NodeReserv> {
        // Init
        val member = memberService.getMember(auth.name) ?: return emptyList()

        // Return
        return if (memberService.isAdmin(member))
            nodeReservRepo.findAll()
        else
            nodeReservRepo.findByMember(member)
    }

    fun getReserv(auth: Authentication, reservId: Long): NodeReserv? {
        // Init
        val optNodeReserv = nodeReservRepo.findById(reservId)

        // Exception
        if (!optNodeReserv.isPresent)
            return null

        // Return
        return optNodeReserv.get()
    }

    @Transactional
    fun createReservation(auth: Authentication, iotReservDto: IotReservDto): Boolean {
        // Log
        logger.info("Creating Reservation (" + iotReservDto.description + ")")

        // Init
        val member = memberService.getMember(auth.name)
        val listNodeReservAction: MutableList<NodeReservAction> = ArrayList()
        val mapAction = iotReservDto.mapAction
        val enableStatus = if (iotReservDto.enableStatus)
            EnableStatusRule.ENABLE
        else
            EnableStatusRule.DISABLE

        // Exception
        if (member == null)
            return false

        // Data Process - Reservation Info
        val nodeReserv = NodeReserv(
                enableStatus = enableStatus,
                description = iotReservDto.description,
                expression = iotReservDto.expression,
                member = member
        )

        // Data Process - Reservation Action Info
        if (mapAction != null) {
            mapAction.forEach(BiConsumer { key: Long, (actionId) ->
                if (actionId != -1L) {
                    // Init
                    val optNodeAction = nodeActionRepo.findById(actionId)

                    // Build
                    if (optNodeAction.isPresent) {
                        val nodeReservAction = NodeReservAction(
                                nodeAction = optNodeAction.get(),
                                nodeReserv = nodeReserv
                        )

                        // List Add
                        listNodeReservAction.add(nodeReservAction)
                    }
                }
            })
            nodeReserv.nodeReservActions = listNodeReservAction
        }

        // DB - Save
        nodeReservRepo.save(nodeReserv)
        nodeReservActionRepo.saveAll(listNodeReservAction)

        // Log
        logger.info("Created Reservation (" + nodeReserv.reservId + ", " + iotReservDto.description + ")")

        // Return
        return true
    }

    @Transactional
    fun modifyReservation(auth: Authentication, reservId: Long, iotReservDto: IotReservDto): Boolean {
        // Init
        val optNodeReserv = nodeReservRepo.findById(reservId)
        val mapAction = iotReservDto.mapAction
        val listNodeReservAction: MutableList<NodeReservAction> = ArrayList()
        val enableStatus = if (iotReservDto.enableStatus)
            EnableStatusRule.ENABLE
        else
            EnableStatusRule.DISABLE

        // Exception
        if (!optNodeReserv.isPresent)
            return false

        // Load
        val nodeReserv = optNodeReserv.get()
        //				listNodeActionItem = nodeAction.getNodeActionItems();

        // DB - Action Item Clear
        nodeReservActionRepo.deleteAll(nodeReserv.nodeReservActions)
        nodeReservActionRepo.flush()
        //				nodeActionRepo.flush();
//				for (NodeActionItem value : listNodeActionItem)
//					nodeAction.removeNodeActionItem(value);

        // Data Process - Item Info
//        nodeReserv.nodeReservActions = null // TODO 이거 뭐지?
        nodeReserv.enableStatus = enableStatus
        nodeReserv.description = iotReservDto.description
        nodeReserv.expression = iotReservDto.expression

        // Data Process - Action Item Info
        if (mapAction != null) {
            mapAction.forEach(BiConsumer { key: Long?, (actionId) ->
                if (actionId != -1L) {
                    // Init
                    val optNodeAction = nodeActionRepo.findById(actionId)

                    // Build
                    if (optNodeAction.isPresent) {
                        val nodeReservAction = NodeReservAction(
                                nodeAction = optNodeAction.get(),
                                nodeReserv = nodeReserv
                        )

                        // List Add
                        listNodeReservAction.add(nodeReservAction)
                    }
                }
            })
            nodeReserv.nodeReservActions = listNodeReservAction
        }

        // DB - Update
//				nodeActionRepo.save(nodeAction);
        nodeReservActionRepo.saveAll(listNodeReservAction)

        // Return
        return true
    }

    @Transactional
    fun setTriggerEnableStatus(auth: Authentication, reservId: Long, status: Boolean): Boolean {
        // Init
        val optNodeReserv = nodeReservRepo.findById(reservId)

        // Exception
        if (!optNodeReserv.isPresent)
            return false

        // Load
        val nodeReserv = optNodeReserv.get()

        // DB - Update
        if (status)
            nodeReserv.enableStatus = EnableStatusRule.ENABLE
        else
            nodeReserv.enableStatus = EnableStatusRule.DISABLE

        // Return
        return true
    }

    @Transactional
    fun deleteReserv(auth: Authentication, reservId: Long): Boolean {
        // Init
        val optNodeReserv = nodeReservRepo.findById(reservId)

        // Exception
        if (!optNodeReserv.isPresent)
            return false

        // Load
        val nodeReserv = optNodeReserv.get()

        // DB - Delete
        nodeReservActionRepo.deleteAll(nodeReserv.nodeReservActions)
        nodeReservRepo.delete(nodeReserv)

        // Return
        return true
    }

    companion object : KLogging()
}