package net.softbell.bsh.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 권한 정보 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeGroupPermissionDto
{
    private Long memberGid;
    private Integer permission;
}