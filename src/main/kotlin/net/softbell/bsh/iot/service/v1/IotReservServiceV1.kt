package net.softbell.bsh.iot.service.v1

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.domain.entity.NodeReserv
import net.softbell.bsh.domain.entity.NodeReservAction
import net.softbell.bsh.domain.repository.NodeActionRepo
import net.softbell.bsh.domain.repository.NodeReservActionRepo
import net.softbell.bsh.domain.repository.NodeReservRepo
import net.softbell.bsh.dto.request.IotActionDto
import net.softbell.bsh.dto.request.IotReservDto
import net.softbell.bsh.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.BiConsumer
import javax.transaction.Transactional

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Reservation 서비스
 */
@Service
class IotReservServiceV1 {
    // Global Field
    @Autowired lateinit var memberService: MemberService
    @Autowired lateinit var nodeReservRepo: NodeReservRepo
    @Autowired lateinit var nodeReservActionRepo: NodeReservActionRepo
    @Autowired lateinit var nodeActionRepo: NodeActionRepo

    fun getAvailableAction(auth: Authentication): List<NodeAction?>? {
        // Field
        val member: Member?
        val listNodeAction: List<NodeAction?>?

        // Init
        member = memberService!!.getMember(auth.name)

        // Process
        listNodeAction = if (memberService.isAdmin(member)) nodeActionRepo!!.findAll() else nodeActionRepo!!.findByMember(member)

        // Return
        return listNodeAction
    }

    fun getAllReservs(auth: Authentication): List<NodeReserv?>? {
        // Field
        val member: Member?
        val listNodeReserv: List<NodeReserv?>?

        // Init
        member = memberService!!.getMember(auth.name)

        // Process
        listNodeReserv = if (memberService.isAdmin(member)) nodeReservRepo!!.findAll() else nodeReservRepo!!.findByMember(member)

        // Return
        return listNodeReserv
    }

    fun getReserv(auth: Authentication?, reservId: Long): NodeReserv? {
        // Field
        val optNodeReserv: Optional<NodeReserv?>
        val nodeReserv: NodeReserv

        // Init
        optNodeReserv = nodeReservRepo!!.findById(reservId)

        // Exception
        if (!optNodeReserv.isPresent) return null

        // Load
        nodeReserv = optNodeReserv.get()

        // Return
        return nodeReserv
    }

    @Transactional
    fun createReservation(auth: Authentication, iotReservDto: IotReservDto): Boolean {
        // Log
        log.info(BellLog.getLogHead() + "Creating Reservation (" + iotReservDto.getDescription() + ")")

        // Field
        val member: Member?
        val nodeReserv: NodeReserv
        val mapAction: HashMap<Long, IotActionDto>?
        val listNodeReservAction: MutableList<NodeReservAction>
        val enableStatus: EnableStatusRule

        // Init
        member = memberService!!.getMember(auth.name)
        listNodeReservAction = ArrayList()
        mapAction = iotReservDto.getMapAction()
        enableStatus = if (iotReservDto.isEnableStatus()) EnableStatusRule.ENABLE else EnableStatusRule.DISABLE

        // Exception
        if (member == null) return false

        // Data Process - Reservation Info
        nodeReserv = builder()
                .enableStatus(enableStatus)
                .description(iotReservDto.getDescription())
                .expression(iotReservDto.getExpression())
                .member(member)
                .build()

        // Data Process - Reservation Action Info
        if (mapAction != null) {
            mapAction.forEach(BiConsumer { key: Long?, value: IotActionDto ->
                if (value.getActionId() !== 0) {
                    // Field
                    val optNodeAction: Optional<NodeAction?>
                    val nodeReservAction: NodeReservAction

                    // Init
                    optNodeAction = nodeActionRepo!!.findById(value.getActionId())

                    // Build
                    if (optNodeAction.isPresent) {
                        nodeReservAction = builder()
                                .nodeAction(optNodeAction.get())
                                .nodeReserv(nodeReserv)
                                .build()

                        // List Add
                        listNodeReservAction.add(nodeReservAction)
                    }
                }
            })
            nodeReserv.setNodeReservActions(listNodeReservAction)
        }

        // DB - Save
        nodeReservRepo!!.save(nodeReserv)
        nodeReservActionRepo!!.saveAll(listNodeReservAction)

        // Log
        log.info(BellLog.getLogHead() + "Created Reservation (" + nodeReserv.getReservId() + ", " + iotReservDto.getDescription() + ")")

        // Return
        return true
    }

    @Transactional
    fun modifyReservation(auth: Authentication?, reservId: Long, iotReservDto: IotReservDto): Boolean {
        // Field
        val optNodeReserv: Optional<NodeReserv?>
        val nodeReserv: NodeReserv
        val mapAction: HashMap<Long, IotActionDto>?
        val listNodeReservAction: MutableList<NodeReservAction>
        val enableStatus: EnableStatusRule

        // Init
        optNodeReserv = nodeReservRepo!!.findById(reservId)
        mapAction = iotReservDto.getMapAction()
        listNodeReservAction = ArrayList()
        enableStatus = if (iotReservDto.isEnableStatus()) EnableStatusRule.ENABLE else EnableStatusRule.DISABLE

        // Exception
        if (!optNodeReserv.isPresent) return false

        // Load
        nodeReserv = optNodeReserv.get()
        //				listNodeActionItem = nodeAction.getNodeActionItems();

        // DB - Action Item Clear
        nodeReservActionRepo!!.deleteAll(nodeReserv.getNodeReservActions())
        nodeReservActionRepo.flush()
        //				nodeActionRepo.flush();
//				for (NodeActionItem value : listNodeActionItem)
//					nodeAction.removeNodeActionItem(value);

        // Data Process - Item Info
        nodeReserv.setNodeReservActions(null)
        nodeReserv.setEnableStatus(enableStatus)
        nodeReserv.setDescription(iotReservDto.getDescription())
        nodeReserv.setExpression(iotReservDto.getExpression())

        // DB - Save
        nodeReservRepo.save(nodeReserv)

        // Data Process - Action Item Info
        if (mapAction != null) {
            mapAction.forEach(BiConsumer { key: Long?, value: IotActionDto ->
                if (value.getActionId() !== 0) {
                    // Field
                    val optNodeAction: Optional<NodeAction?>
                    val nodeReservAction: NodeReservAction

                    // Init
                    optNodeAction = nodeActionRepo!!.findById(value.getActionId())

                    // Build
                    if (optNodeAction.isPresent) {
                        nodeReservAction = builder()
                                .nodeAction(optNodeAction.get())
                                .nodeReserv(nodeReserv)
                                .build()

                        // List Add
                        listNodeReservAction.add(nodeReservAction)
                    }
                }
            })
            nodeReserv.setNodeReservActions(listNodeReservAction)
        }

        // DB - Update
//				nodeActionRepo.save(nodeAction);
        nodeReservActionRepo.saveAll(listNodeReservAction)

        // Return
        return true
    }

    @Transactional
    fun setTriggerEnableStatus(auth: Authentication?, reservId: Long, status: Boolean): Boolean {
        // Field
        val optNodeReserv: Optional<NodeReserv?>
        val nodeReserv: NodeReserv

        // Init
        optNodeReserv = nodeReservRepo!!.findById(reservId)

        // Exception
        if (!optNodeReserv.isPresent) return false

        // Load
        nodeReserv = optNodeReserv.get()

        // DB - Update
        if (status) nodeReserv.setEnableStatus(EnableStatusRule.ENABLE) else nodeReserv.setEnableStatus(EnableStatusRule.DISABLE)

        // Return
        return true
    }

    @Transactional
    fun deleteReserv(auth: Authentication?, reservId: Long): Boolean {
        // Field
        val optNodeReserv: Optional<NodeReserv?>
        val nodeReserv: NodeReserv

        // Init
        optNodeReserv = nodeReservRepo!!.findById(reservId)

        // Exception
        if (!optNodeReserv.isPresent) return false

        // Load
        nodeReserv = optNodeReserv.get()

        // DB - Delete
        nodeReservActionRepo!!.deleteAll(nodeReserv.getNodeReservActions())
        nodeReservRepo.delete(nodeReserv)

        // Return
        return true
    }
}