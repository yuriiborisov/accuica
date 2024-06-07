package code.uica.code.service;

import code.uica.code.Config4UICA;
import code.uica.code.Holder4UICA;
import code.uica.code.dto.UicaComponentInfoRequest;
import code.uica.code.dto.UicaInfoRequest;
import code.uica.code.exception.UICACreationException;
import code.uica.code.exception.UICANotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface HolderService4UICA {
    Holder4UICA getById(String id);

    List<Holder4UICA> getAll();
    Holder4UICA save(Holder4UICA holder4UICA);

    void delete(String id);


    default Holder4UICA updateComponentsStates(UicaInfoRequest request){
        Holder4UICA holder4UICA = getById(request.getFormId());
        if(holder4UICA == null){
            throw new UICANotFoundException(request.getFormId());
        }
        Config4UICA config4UICA = holder4UICA.getConfig() == null ? new Config4UICA() : holder4UICA.getConfig();
        List<Config4UICA.UICAHolderItem> holderItems = config4UICA.getComponentAccess() == null ? new ArrayList<>() : config4UICA.getComponentAccess();
        holderItems.forEach(e -> {
            if(request.getComponentId().equals(e.getComponentId())){
                e.setStates(request.getStates());
            }
        });
        return save(holder4UICA);
    }

//    default List<Holder4UICA> update(UicaRequest request){
//        Holder4UICA holder4UICA = getById(request.getFormId());
//        if(holder4UICA == null){
//            throw new UICANotFoundException(request.getFormId());
//        }
//        Config4UICA config4UICA = holder4UICA.getConfig() == null ? new Config4UICA() : holder4UICA.getConfig();
//        checkEntity(request);
//        config4UICA.setEntity(request.getEntity());
//        List<Config4UICA.UICAHolderItem> holderItems = config4UICA.getComponentAccess() == null ? new ArrayList<>() : config4UICA.getComponentAccess();
//        holderItems.forEach(e -> {
//            if(request.getComponentId().equals(e.getComponentId())){
//                e.setStates(request.getStates());
//            }
//        });
//        save(holder4UICA);
//        return getAll();
//    }

    default Holder4UICA getExisted(String id){
        Holder4UICA holder4UICA = getById(id);
        if(holder4UICA == null){
            throw new UICANotFoundException(id);
        }
        return holder4UICA;
    }
    default Holder4UICA updateComponentInfo(UicaComponentInfoRequest request){
        checkComponentId(request.getComponentId());
        Holder4UICA holder4UICA = getExisted(request.getFormId());
        Config4UICA config4UICA = holder4UICA.getConfig() == null ? new Config4UICA() : holder4UICA.getConfig();
        List<Config4UICA.UICAHolderItem> holderItems = config4UICA.getComponentAccess() == null ? new ArrayList<>() : config4UICA.getComponentAccess();
        holderItems.forEach(e -> {
            if(request.getComponentId().equals(e.getComponentId())){
                if(request.getComponentIdToRename() != null){
                    e.setComponentId(request.getComponentIdToRename());
                }
            }
        });
        return save(holder4UICA);
    }
    default Holder4UICA updateFormInfo(UicaInfoRequest request){
        Holder4UICA holder4UICA = getExisted(request.getFormId());
        Config4UICA config4UICA = holder4UICA.getConfig() == null ? new Config4UICA() : holder4UICA.getConfig();
        checkEntity(request);
        config4UICA.setEntity(request.getEntity());
        holder4UICA.setDescription(request.getDescription());
        return save(holder4UICA);
    }

    default Holder4UICA deleteComponent(UicaInfoRequest request){
        Holder4UICA holder4UICA = getExisted(request.getFormId());
        Config4UICA config4UICA = holder4UICA.getConfig() == null ? new Config4UICA() : holder4UICA.getConfig();
        List<Config4UICA.UICAHolderItem> holderItems = config4UICA.getComponentAccess() == null ? new ArrayList<>() : config4UICA.getComponentAccess();
        List<Config4UICA.UICAHolderItem> newHolderItems = holderItems.stream().filter(e -> !request.getComponentId().equals(e.getComponentId())).collect(Collectors.toList());
        config4UICA.setComponentAccess(newHolderItems);
        return save(holder4UICA);
    }

    private void checkEntity(UicaInfoRequest request){
        if(request.getEntity() == null){
            throw new UICACreationException("Entity cannot be null while update UICA");
        }
    }

    private void checkComponentId(String id){
        if(id == null){
            throw new UICACreationException("ComponentId cannot be null in UICA configuration");
        }
    }

}
