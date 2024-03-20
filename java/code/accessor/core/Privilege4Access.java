package code.accessor.core;

import code.accessor.implementations.dto.PrivilegeApplicable;

public interface Privilege4Access {
    boolean isFilter();

    String getId();
    Object getFilterValue();

    String getStatementScript();

    Integer getPriorityOrder();

    boolean isCalculated();

    PrivilegeApplicable getApplicable();
}
