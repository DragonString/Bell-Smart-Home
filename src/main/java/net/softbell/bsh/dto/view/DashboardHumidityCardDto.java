package net.softbell.bsh.dto.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 대시보드 습도 정보 카드정보 DTO
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class DashboardHumidityCardDto
{
	private String alias;
	private Double avgStatus;
}