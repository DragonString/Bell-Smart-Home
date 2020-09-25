package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.GroupRole
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 권한 엔티티
 */
@Entity
@Table(name = "group_permission", uniqueConstraints = [UniqueConstraint(columnNames = ["node_group_id", "member_group_id", "group_permission"])])
@NamedQuery(name = "GroupPermission.findAll", query = "SELECT g FROM GroupPermission g")
class GroupPermission : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_permission_id", unique = true, nullable = false)
    var groupPermissionId: Long? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assign_date", nullable = false)
    var assignDate: Date? = null

    @Column(name = "group_permission", nullable = false)
    var groupPermission: GroupRole? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_group_id", nullable = false)
    var nodeGroup: NodeGroup? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_group_id", nullable = false)
    var memberGroup: MemberGroup? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}