package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import net.softbell.bsh.domain.EnableStatusRule
import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "node")
@NamedQuery(name = "Node.findAll", query = "SELECT n FROM Node n")
class Node : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_id", unique = true, nullable = false)
    private val nodeId: Long? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approval_date")
    private val approvalDate: Date? = null

    @Column(name = "control_mode", nullable = false)
    private val controlMode: Byte? = null

    @Column(name = "enable_status", nullable = false)
    private val enableStatus: EnableStatusRule? = null

    @Column(name = "version", nullable = false)
    private val version: String? = null

    @Column(name = "node_name", nullable = false, length = 50)
    private val nodeName: String? = null

    @Column(name = "alias", nullable = false, length = 50)
    private val alias: String? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_date", nullable = false)
    private val registerDate: Date? = null

    @Column(length = 64)
    private val token: String? = null

    @Column(nullable = false, length = 50)
    private val uid: String? = null

    @OneToMany(mappedBy = "node")
    private val nodeConnectionLogs: List<NodeConnectionLog>? = null

    @OneToMany(mappedBy = "node")
    private val nodeGroupItems: List<NodeGroupItem>? = null

    @OneToMany(mappedBy = "node")
    private val nodeItems: List<NodeItem>? = null
    fun addNodeConnectionLog(nodeConnectionLog: NodeConnectionLog): NodeConnectionLog {
        getNodeConnectionLogs().add(nodeConnectionLog)
        nodeConnectionLog.setNode(this)
        return nodeConnectionLog
    }

    fun removeNodeConnectionLog(nodeConnectionLog: NodeConnectionLog): NodeConnectionLog {
        getNodeConnectionLogs().remove(nodeConnectionLog)
        nodeConnectionLog.setNode(null)
        return nodeConnectionLog
    }

    fun addNodeGroupItem(nodeGroupItem: NodeGroupItem): NodeGroupItem {
        getNodeGroupItems().add(nodeGroupItem)
        nodeGroupItem.setNode(this)
        return nodeGroupItem
    }

    fun removeNodeGroupItem(nodeGroupItem: NodeGroupItem): NodeGroupItem {
        getNodeGroupItems().remove(nodeGroupItem)
        nodeGroupItem.setNode(null)
        return nodeGroupItem
    }

    fun addNodeItem(nodeItem: NodeItem): NodeItem {
        getNodeItems().add(nodeItem)
        nodeItem.setNode(this)
        return nodeItem
    }

    fun removeNodeItem(nodeItem: NodeItem): NodeItem {
        getNodeItems().remove(nodeItem)
        nodeItem.setNode(null)
        return nodeItem
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}