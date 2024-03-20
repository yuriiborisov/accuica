package code.uica.code;

import java.util.List;

//UICA - Uni-graphic Interface Component Access
public class Config4UICA {
	private String entity;
	private List<UICAHolderItem> componentAccess;

	public Config4UICA() {
	}

	public String getEntityType() {
		return this.entity;
	}

	public List<UICAHolderItem> getComponentAccess() {
		return this.componentAccess;
	}

	public void setEntityType(String entity) {
		this.entity = entity;
	}

	public void setComponentAccess(List<UICAHolderItem> componentAccess) {
		this.componentAccess = componentAccess;
	}

	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof Config4UICA)) return false;
		final Config4UICA other = (Config4UICA) o;
		if (!other.canEqual((Object) this)) return false;
		final String this$entity = this.getEntityType();
		final String other$entity = other.getEntityType();
		if (this$entity == null ? other$entity != null : !this$entity.equals(other$entity)) return false;
		final Object this$componentAccess = this.getComponentAccess();
		final Object other$componentAccess = other.getComponentAccess();
		if (this$componentAccess == null ? other$componentAccess != null : !this$componentAccess.equals(other$componentAccess))
			return false;
		return true;
	}

	protected boolean canEqual(final Object other) {
		return other instanceof Config4UICA;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final String $entity = this.getEntityType();
		result = result * PRIME + ($entity == null ? 43 : $entity.hashCode());
		final Object $componentAccess = this.getComponentAccess();
		result = result * PRIME + ($componentAccess == null ? 43 : $componentAccess.hashCode());
		return result;
	}

	public String toString() {
		return "UICAConfig(code.entity=" + this.getEntityType() + ", componentAccess=" + this.getComponentAccess() + ")";
	}

	public static class UICAHolderItem {
		private String componentId;
		private List<UIState> states;

		public UICAHolderItem() {
		}

		public String getComponentId() {
			return this.componentId;
		}

		public List<UIState> getStates() {
			return this.states;
		}

		public void setComponentId(String componentId) {
			this.componentId = componentId;
		}

		public void setStates(List<UIState> states) {
			this.states = states;
		}

		public boolean equals(final Object o) {
			if (o == this) return true;
			if (!(o instanceof UICAHolderItem)) return false;
			final UICAHolderItem other = (UICAHolderItem) o;
			if (!other.canEqual((Object) this)) return false;
			final Object this$componentId = this.getComponentId();
			final Object other$componentId = other.getComponentId();
			if (this$componentId == null ? other$componentId != null : !this$componentId.equals(other$componentId))
				return false;
			final Object this$states = this.getStates();
			final Object other$states = other.getStates();
			if (this$states == null ? other$states != null : !this$states.equals(other$states)) return false;
			return true;
		}

		protected boolean canEqual(final Object other) {
			return other instanceof UICAHolderItem;
		}

		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			final Object $componentId = this.getComponentId();
			result = result * PRIME + ($componentId == null ? 43 : $componentId.hashCode());
			final Object $states = this.getStates();
			result = result * PRIME + ($states == null ? 43 : $states.hashCode());
			return result;
		}

		public String toString() {
			return "UICAConfig.UICAHolderItem(componentId=" + this.getComponentId() + ", states=" + this.getStates() + ")";
		}
	}

	public static class UIState {
		private UICAState state;
		private List<String> privileges;
		private String entity;

		public UIState() {
		}

		public UICAState getState() {
			return this.state;
		}

		public List<String> getPrivileges() {
			return this.privileges;
		}

		public String getEntity() {
			return this.entity;
		}

		public void setState(UICAState state) {
			this.state = state;
		}

		public void setPrivileges(List<String> privileges) {
			this.privileges = privileges;
		}

		public void setEntity(String entity) {
			this.entity = entity;
		}

		public boolean equals(final Object o) {
			if (o == this) return true;
			if (!(o instanceof UIState)) return false;
			final UIState other = (UIState) o;
			if (!other.canEqual((Object) this)) return false;
			final Object this$state = this.getState();
			final Object other$state = other.getState();
			if (this$state == null ? other$state != null : !this$state.equals(other$state)) return false;
			final Object this$privileges = this.getPrivileges();
			final Object other$privileges = other.getPrivileges();
			if (this$privileges == null ? other$privileges != null : !this$privileges.equals(other$privileges))
				return false;
			final Object this$entity = this.getEntity();
			final Object other$entity = other.getEntity();
			if (this$entity == null ? other$entity != null : !this$entity.equals(other$entity)) return false;
			return true;
		}

		protected boolean canEqual(final Object other) {
			return other instanceof UIState;
		}

		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			final Object $state = this.getState();
			result = result * PRIME + ($state == null ? 43 : $state.hashCode());
			final Object $privileges = this.getPrivileges();
			result = result * PRIME + ($privileges == null ? 43 : $privileges.hashCode());
			final Object $entity = this.getEntity();
			result = result * PRIME + ($entity == null ? 43 : $entity.hashCode());
			return result;
		}

		public String toString() {
			return "UICAConfig.UIState(state=" + this.getState() + ", privileges=" + this.getPrivileges() + ", code.entity=" + this.getEntity() + ")";
		}
	}
}
