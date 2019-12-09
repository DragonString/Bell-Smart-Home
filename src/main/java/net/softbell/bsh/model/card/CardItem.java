package net.softbell.bsh.model.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CardItem {
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
