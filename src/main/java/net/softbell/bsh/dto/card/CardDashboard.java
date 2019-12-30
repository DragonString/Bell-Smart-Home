package net.softbell.bsh.dto.card;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
@AllArgsConstructor
public class CardDashboard {
	@NonNull
	private String cardTopic;
	@NonNull
	private String cardLast;
	
	@NonNull
	private List<CardItem> cardItems;
}
