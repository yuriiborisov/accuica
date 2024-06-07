package code.accessor.core.code;

import code.common.PrivilegeApplicable;

public interface Privilege4Access {
    boolean isFilter();

    String getId();

    String getAlt();
    Object getFilterValue();

    String getDescription();

    Boolean getCalculated();

    String getStatementScript();

    Integer getPriorityOrder();

    Boolean getUica();

    PrivilegeApplicable getApplicable();
}
