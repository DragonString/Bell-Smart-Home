package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.EnableStatusRule
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 엔티티
 */
@Entity
@Table(name = "node")
@NamedQuery(name = "Node.findAll", query = "SELECT n FROM Node n")
class Node : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_id", unique = true, nullable = false)
    var nodeId: Long? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approval_date")
    var approvalDate: Date? = null

    @Column(name = "control_mode", nullable = false)
    var controlMode: Byte? = null

    @Column(name = "enable_status", nullable = false)
    var enableStatus: EnableStatusRule? = null

    @Column(name = "version", nullable = false)
    var version: String? = null

    @Column(name = "node_name", nullable = false, length = 50)
    var nodeName: String? = null

    @Column(name = "alias", nullable = false, length = 50)
    var alias: String? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_date", nullable = false)
    var registerDate: Date? = null

    @Column(length = 64)
    var token: String? = null

    @Column(nullable = false, length = 50)
    var uid: String? = null

    @OneToMany(mappedBy = "node")
    var nodeConnectionLogs: List<NodeConnectionLog>? = null

    @OneToMany(mappedBy = "node")
    var nodeGroupItems: List<NodeGroupItem>? = null

    @OneToMany(mappedBy = "node")
    var nodeItems: List<NodeItem>? = null


//    fun addNodeConnectionLog(nodeConnectionLog: NodeConnectionLog): NodeConnectionLog {
//        getNodeConnectionLogs().add(nodeConnectionLog)
//        nodeConnectionLog.setNode(this)
//        return nodeConnectionLog
//    }
//
//    fun removeNodeConnectionLog(nodeConnectionLog: NodeConnectionLog): NodeConnectionLog {
//        getNodeConnectionLogs().remove(nodeConnectionLog)
//        nodeConnectionLog.setNode(null)
//        return nodeConnectionLog
//    }
//
//    fun addNodeGroupItem(nodeGroupItem: NodeGroupItem): NodeGroupItem {
//        getNodeGroupItems().add(nodeGroupItem)
//        nodeGroupItem.setNode(this)
//        return nodeGroupItem
//    }
//
//    fun removeNodeGroupItem(nodeGroupItem: NodeGroupItem): NodeGroupItem {
//        getNodeGroupItems().remove(nodeGroupItem)
//        nodeGroupItem.setNode(null)
//        return nodeGroupItem
//    }
//
//    fun addNodeItem(nodeItem: NodeItem): NodeItem {
//        getNodeItems().add(nodeItem)
//        nodeItem.setNode(this)
//        return nodeItem
//    }
//
//    fun removeNodeItem(nodeItem: NodeItem): NodeItem {
//        getNodeItems().remove(nodeItem)
//        nodeItem.setNode(null)
//        return nodeItem
//    }

    companion object {
        private const val serialVersionUID = 1L
    }
}