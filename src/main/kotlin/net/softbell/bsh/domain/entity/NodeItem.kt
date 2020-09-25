package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import java.io.Serializable
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "node_item")
@NamedQuery(name = "NodeItem.findAll", query = "SELECT n FROM NodeItem n")
class NodeItem : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", unique = true, nullable = false)
    private val itemId: Long? = null

    @Column(nullable = false, length = 50)
    private val alias: String? = null

    @Column(name = "control_mode", nullable = false)
    private val controlMode: Byte? = null

    @Column(name = "item_index", nullable = false)
    private val itemIndex: Byte? = null

    @Column(name = "item_mode", nullable = false)
    private val itemMode: ItemModeRule? = null

    @Column(name = "item_category", nullable = false)
    private val itemCategory: ItemCategoryRule? = null

    @Column(name = "item_name", nullable = false, length = 50)
    private val itemName: String? = null

    @Column(name = "item_type", nullable = false)
    private val itemType: ItemTypeRule? = null

    @OneToMany(mappedBy = "nodeItem")
    private val nodeActionItems: List<NodeActionItem>? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private val node: Node? = null
    fun addNodeActionItem(nodeActionItem: NodeActionItem): NodeActionItem {
        getNodeActionItems().add(nodeActionItem)
        nodeActionItem.setNodeItem(this)
        return nodeActionItem
    }

    fun removeNodeActionItem(nodeActionItem: NodeActionItem): NodeActionItem {
        getNodeActionItems().remove(nodeActionItem)
        nodeActionItem.setNodeItem(null)
        return nodeActionItem
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}