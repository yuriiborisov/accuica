package code.uica.code;


import code.common.PrivilegeApplicable;
import lombok.Data;

@Data
public class Privilege4UICA {
	private String id;
	private boolean calculated;
	private String statementScript;
	private String description;
	private boolean uica;
	private PrivilegeApplicable applicable;
}
