package net.softbell.bsh.dto.view.admin;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Node;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 관리 수정뷰 정보 카드정보 DTO
 */
@Getter
@Setter
public class NodeManageInfoCardDto
{
	private long nodeId;
	private EnableStatusRule enableStatus;
	private String nodeName;
	private String alias;
	private String uid;
	private String token;
	private byte controlMode;
	private Date registerDate;
	private Date approvalDate;
	
	public NodeManageInfoCardDto(Node entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.nodeId = entity.getNodeId();
		this.enableStatus = entity.getEnableStatus();
		this.nodeName = entity.getNodeName();
		this.alias = entity.getAlias();
		this.uid = entity.getUid();
		this.token = entity.getToken();
		this.controlMode = entity.getControlMode();
		this.registerDate = entity.getRegisterDate();
		this.approvalDate = entity.getApprovalDate();
	}
}