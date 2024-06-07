package code.uica.code;

import lombok.Data;

import java.util.List;
//UICA - Uni-graphic Interface Component Access
@Data
public class Config4UICA {
	private String entity;
	private List<UICAHolderItem> componentAccess;

	@Data
	public static class UICAHolderItem {
		private String componentId;
		private List<UIState> states;
	}

	@Data
	public static class UIState {
		private UICAState state;
		private List<String> privileges;
		private String entity;
	}
}
