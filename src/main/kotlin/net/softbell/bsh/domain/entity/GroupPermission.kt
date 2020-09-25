package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import net.softbell.bsh.domain.GroupRole
import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 권한 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "group_permission", uniqueConstraints = [UniqueConstraint(columnNames = ["node_group_id", "member_group_id", "group_permission"])])
@NamedQuery(name = "GroupPermission.findAll", query = "SELECT g FROM GroupPermission g")
class GroupPermission : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_permission_id", unique = true, nullable = false)
    private val groupPermissionId: Long? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assign_date", nullable = false)
    private val assignDate: Date? = null

    @Column(name = "group_permission", nullable = false)
    private val groupPermission: GroupRole? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_group_id", nullable = false)
    private val nodeGroup: NodeGroup? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_group_id", nullable = false)
    private val memberGroup: MemberGroup? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}