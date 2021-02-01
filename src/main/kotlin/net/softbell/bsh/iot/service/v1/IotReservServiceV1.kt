package net.softbell.bsh.iot.service.v1

import mu.KLogging
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.domain.entity.NodeReserv
import net.softbell.bsh.domain.entity.NodeReservAction
import net.softbell.bsh.domain.repository.NodeActionRepo
import net.softbell.bsh.domain.repository.NodeReservActionRepo
import net.softbell.bsh.domain.repository.NodeReservRepo
import net.softbell.bsh.dto.request.IotReservDto
import net.softbell.bsh.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
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

    fun getAvailableAction(member: Member): List<NodeAction> {
        // Return
        return if (memberService.isAdmin(member))
            nodeActionRepo.findAll()
        else
            nodeActionRepo.findByMember(member)
    }

    fun getReservs(): List<NodeReserv> {
        // Return
        return nodeReservRepo.findAll()
    }

    fun getPrivilegesReservs(member: Member): List<NodeReserv> {
        // Return
        return if (memberService.isAdmin(member))
            getReservs()
        else
            nodeReservRepo.findByMember(member)
    }

    fun getReserv(reservId: Long): NodeReserv? {
        // Init
        val optNodeReserv = nodeReservRepo.findById(reservId)

        // Exception
        if (!optNodeReserv.isPresent)
            return null

        // Return
        return optNodeReserv.get()
    }

    fun getPrivilegesReserv(member: Member, reservId: Long): NodeReserv? {
        // TODO 권한 체크 필요
        // Init
        val optNodeReserv = nodeReservRepo.findById(reservId)

        // Exception
        if (!optNodeReserv.isPresent)
            return null

        // Return
        return optNodeReserv.get()
    }

    @Transactional
    fun createReservation(member: Member, iotReservDto: IotReservDto): Boolean {
        // Log
        logger.info("Creating Reservation (" + iotReservDto.description + ")")

        // Init
        val listNodeReservAction: MutableList<NodeReservAction> = ArrayList()
        val mapAction = iotReservDto.mapAction
        val enableStatus = if (iotReservDto.enableStatus)
            EnableStatusRule.ENABLE
        else
            EnableStatusRule.DISABLE

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
    fun modifyReservation(reservId: Long, iotReservDto: IotReservDto): Boolean {
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

    fun modifyPrivilegesReservation(member: Member, reservId: Long, iotReservDto: IotReservDto): Boolean {
        // TODO 권한 체크 필요
        return modifyReservation(reservId, iotReservDto)
    }

    @Transactional
    fun setTriggerEnableStatus(reservId: Long, status: Boolean): Boolean {
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
        nodeReservRepo.save(nodeReserv)

        // Return
        return true
    }

    fun setPrivilegesTriggerEnableStatus(member: Member, reservId: Long, status: Boolean): Boolean {
        // TODO 권한 체크 필요
        return setTriggerEnableStatus(reservId, status)
    }

    @Transactional
    fun deleteReserv(reservId: Long): Boolean {
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

    fun deletePrivilegesReserv(member: Member, reservId: Long): Boolean {
        // TODO 권한 체크 필요
        return deleteReserv(reservId)
    }

    companion object : KLogging()
}