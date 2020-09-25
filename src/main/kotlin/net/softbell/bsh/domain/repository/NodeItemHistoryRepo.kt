package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 기록 리포지토리 인터페이스
 */
@Repository
interface NodeItemHistoryRepo : JpaRepository<NodeItemHistory?, Long?> {
    fun findByNodeItem(nodeItem: NodeItem?, page: Pageable?): List<NodeItemHistory?>?
    fun findFirstByNodeItemOrderByItemHistoryIdDesc(nodeItem: NodeItem?): NodeItemHistory?
    fun findByNodeItemOrderByItemHistoryIdDesc(nodeItem: NodeItem?, page: Pageable?): Page<NodeItemHistory?>?

    @Query("SELECT AVG(nih.itemStatus) FROM NodeItemHistory nih WHERE nih.nodeItem = ?1 AND receiveDate BETWEEN ?2 AND NOW() ORDER BY nih.itemHistoryId DESC")
    fun avgByNodeItem(nodeItem: NodeItem?, date: Date?): Double?

    @Query("SELECT MIN(nih.itemStatus) FROM NodeItemHistory nih WHERE nih.nodeItem = ?1 AND receiveDate BETWEEN ?2 AND NOW() ORDER BY nih.itemHistoryId DESC")
    fun minByNodeItem(nodeItem: NodeItem?, date: Date?): Double?

    @Query("SELECT MAX(nih.itemStatus) FROM NodeItemHistory nih WHERE nih.nodeItem = ?1 AND receiveDate BETWEEN ?2 AND NOW() ORDER BY nih.itemHistoryId DESC")
    fun maxByNodeItem(nodeItem: NodeItem?, date: Date?): Double?
}