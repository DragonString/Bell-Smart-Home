package net.softbell.bsh.dto.navbar;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NavBar {
	public enum NavType
	{
		LINK,
		BUTTON,
		DROPDOWN,
		DIVIDER,
		DISABLED;
	}
	
	@NonNull
	private String navName;
	@NonNull
	private String navURI;
	@NonNull
	private NavType navType;
	@Builder.Default
	private boolean navActive = false;
	private List<NavBar> navItems;
}
