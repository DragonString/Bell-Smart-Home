package net.softbell.bsh.dto.view.advance;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Node;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 수정뷰 정보 카드정보 DTO
 */
@Getter
@Setter
public class NodeInfoCardDto
{
	private Long nodeId;
	private EnableStatusRule enableStatus;
	private String nodeName;
	private String alias;
	private String uid;
	private String token;
	private Byte controlMode;
	private String version;
	private Date registerDate;
	private Date approvalDate;
	
	public NodeInfoCardDto(Node entity)
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
		this.version = entity.getVersion();
		this.registerDate = entity.getRegisterDate();
		this.approvalDate = entity.getApprovalDate();
	}
}