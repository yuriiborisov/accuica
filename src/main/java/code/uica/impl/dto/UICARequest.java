package code.uica.impl.dto;

import lombok.Data;

import java.util.List;

//UICA - Uni-graphic Interface Component Access
@Data
public class UICARequest {

	private String formId;
	private List<String> componentIds;
	private Boolean createState;
}
