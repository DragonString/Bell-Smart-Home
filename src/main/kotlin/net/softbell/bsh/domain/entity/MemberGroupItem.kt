package net.softbell.bsh.domain.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 아이템 엔티티
 */
@Entity
@Table(name = "member_group_item", uniqueConstraints = [UniqueConstraint(columnNames = ["member_group_id", "member_id"])])
@NamedQuery(name = "MemberGroupItem.findAll", query = "SELECT m FROM MemberGroupItem m")
class MemberGroupItem(
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "assign_date", nullable = false)
        var assignDate: Date,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_group_id", nullable = false)
        var memberGroup: MemberGroup,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id", nullable = false)
        var member: Member
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_item_id", unique = true, nullable = false)
    var groupItemId: Long = 0

    companion object {
        private const val serialVersionUID = 1L
    }
}