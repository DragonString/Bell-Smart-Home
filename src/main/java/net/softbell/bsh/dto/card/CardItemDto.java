package net.softbell.bsh.dto.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 카드 아이템 DTO
 */
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CardItemDto {
	public enum ItemType
	{
		PRIMARY,
		SECONDARY,
		SUCCESS,
		DANGER,
		WARNING,
		INFO,
		LIGHT,
		DARK;
	}
	
	@NonNull
	private String cardKey;
	@NonNull
	private String cardValue;
	@Builder.Default
	private ItemType cardType = ItemType.INFO;
}
