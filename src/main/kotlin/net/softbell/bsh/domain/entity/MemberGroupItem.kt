package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 아이템 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member_group_item", uniqueConstraints = [UniqueConstraint(columnNames = ["member_group_id", "member_id"])])
@NamedQuery(name = "MemberGroupItem.findAll", query = "SELECT m FROM MemberGroupItem m")
class MemberGroupItem : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_item_id", unique = true, nullable = false)
    private val groupItemId: Long? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assign_date", nullable = false)
    private val assignDate: Date? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_group_id", nullable = false)
    private val memberGroup: MemberGroup? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private val member: Member? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}