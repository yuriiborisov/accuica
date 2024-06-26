package code.impl.accessor.service;

import code.accessor.core.code.Privilege4Access;
import code.accessor.core.code.dto.PrivilegeEntityMatrix;
import code.accessor.core.code.dto.response.Role4AccessResponse;
import code.accessor.core.code.service.PrivilegeService4Access;
import code.accessor.core.code.service.RoleService4Access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Role4AccessBaseAbstract implements RoleService4Access {
    private final PrivilegeService4Access privilegeService4Access;

    protected Role4AccessBaseAbstract(PrivilegeService4Access privilegeService4Access) {
        this.privilegeService4Access = privilegeService4Access;
    }

    public List<PrivilegeEntityMatrix> getMatrix(String id){
        return getMatrix(id, privilegeService4Access.getAll());
    }

    public List<PrivilegeEntityMatrix> getMatrixOfParents(String id){
        return getMatrixOfParents(id, privilegeService4Access.getAll());
    }

    public List<PrivilegeEntityMatrix> getMatrixOfParents(String id, List<Privilege4Access> allPrivileges){
        List<PrivilegeEntityMatrix> result = new ArrayList<>();
        Map<Privilege4Access, List<String>> map = new HashMap<>();
        Role4AccessResponse role4AccessResponse = getById(id).getParent();

//        if(role4AccessResponse != null){
            calcParents(role4AccessResponse, map);
//        }

        map.entrySet().forEach(item -> {
            result.add(new PrivilegeEntityMatrix(item.getKey(), item.getValue().stream().distinct().collect(Collectors.toList())));
        });
        return allPrivileges.stream().map(p ->
                new PrivilegeEntityMatrix(p, !map.containsKey(p) ? new ArrayList<>() : map.get(p).stream().distinct().collect(Collectors.toList()))
        ).collect(Collectors.toList());
    }

    private void calcParents(Role4AccessResponse role4AccessResponse , Map<Privilege4Access, List<String>> map){
//        if(role4AccessResponse != null){
            role4AccessResponse.getGrantAccesses().forEach(ga -> {
                ga.getPrivileges().forEach(p-> {
                    List<String> entities = map.get(p) == null ? new ArrayList<>() : map.get(p);
                    entities.add(ga.getEntityType());
                    map.put(p,entities);
                });
//            });

        });
            if(role4AccessResponse.getParent() != null){
                calcParents(role4AccessResponse.getParent(), map);
            }
    }
    private List<PrivilegeEntityMatrix> getMatrix(String id, List<Privilege4Access> allPrivileges){
        List<PrivilegeEntityMatrix> result = new ArrayList<>();
        Map<Privilege4Access, List<String>> map = new HashMap<>();
        Role4AccessResponse role4AccessResponse = getById(id);

        role4AccessResponse.getGrantAccesses().forEach(ga -> {
            ga.getPrivileges().forEach(p-> {
                List<String> entities = map.get(p) == null ? new ArrayList<>() : map.get(p);
               if(!entities.contains(p.getId())){
                   entities.add(ga.getEntityType());
               }
                map.put(p,entities);
            });
        });

        map.entrySet().forEach(item -> {
            result.add(new PrivilegeEntityMatrix(item.getKey(), item.getValue().stream().distinct().collect(Collectors.toList())));
        });
        return allPrivileges.stream().map(p ->
                new PrivilegeEntityMatrix(p, !map.containsKey(p) ? new ArrayList<>() : map.get(p).stream().distinct().collect(Collectors.toList()))
        ).collect(Collectors.toList());
    }
}
