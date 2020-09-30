package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import java.io.Serializable
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 엔티티
 */
@Entity
@Table(name = "node_item")
@NamedQuery(name = "NodeItem.findAll", query = "SELECT n FROM NodeItem n")
class NodeItem : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", unique = true, nullable = false)
    var itemId: Long? = null

    @Column(nullable = false, length = 50)
    var alias: String? = null

    @Column(name = "control_mode", nullable = false)
    var controlMode: Byte? = null

    @Column(name = "item_index", nullable = false)
    var itemIndex: Byte? = null

    @Column(name = "item_mode", nullable = false)
    var itemMode: ItemModeRule? = null

    @Column(name = "item_category", nullable = false)
    var itemCategory: ItemCategoryRule? = null

    @Column(name = "item_name", nullable = false, length = 50)
    var itemName: String? = null

    @Column(name = "item_type", nullable = false)
    var itemType: ItemTypeRule? = null

    @OneToMany(mappedBy = "nodeItem")
    var nodeActionItems: MutableList<NodeActionItem>? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    var node: Node? = null


    fun addNodeActionItem(nodeActionItem: NodeActionItem): NodeActionItem? {
        nodeActionItems?.add(nodeActionItem)
        nodeActionItem.nodeItem = this
        return nodeActionItem
    }

    fun removeNodeActionItem(nodeActionItem: NodeActionItem): NodeActionItem? {
        nodeActionItems?.remove(nodeActionItem)
        nodeActionItem.nodeItem = null
        return nodeActionItem
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}