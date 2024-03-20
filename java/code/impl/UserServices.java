//package code.impl;
//
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import ru.rvd.ask.access.code.accessor.orgstructure.EmployeeAccessor;
//import ru.rvd.ask.access.configuration.proxy.Entities;
//import ru.rvd.ask.access.service.SPG2RoleService;
//import ru.rvd.ask.domain.entity.classifier.Classifier;
//import ru.rvd.ask.domain.entity.classifier.ClassifierElement;
//import ru.rvd.ask.domain.entity.orgstructure.Employee;
//import ru.rvd.ask.domain.entity.orgstructure.Organization;
//import ru.rvd.ask.domain.repository.orgstructure.EmployeesRepository;
//import ru.rvd.ask.domain.repository.orgstructure.OrganizationRepository;
//import ru.rvd.ask.domain.specification.Specs;
//import ru.rvd.ask.exception.CustomExceptions;
//import ru.rvd.ask.exception.orgstructure.EmployeeDuplicationException;
//import ru.rvd.ask.exception.orgstructure.EmployeeNotFoundException;
//import ru.rvd.ask.mapper.orgstructure.EmployeeMapper;
//import ru.rvd.ask.service.UtilityService;
//import ru.rvd.ask.service.classifier.ClassifierService;
//import ru.rvd.ask.service.security.AuthProvider;
//import ru.rvd.ask.web.ErrorResponse;
//import ru.rvd.ask.web.dto.common.*;
//import ru.rvd.ask.web.dto.dictionary.response.BulkResponseDto;
//import ru.rvd.ask.web.dto.orgstructure.ListOfEmployeeId;
//import ru.rvd.ask.web.dto.orgstructure.request.EmployeeRequestByCodeDto;
//import ru.rvd.ask.web.dto.orgstructure.response.EmployeeResponseDto;
//import ru.rvd.ask.web.dto.orgstructure.response.EmployeeResponseFullDto;
//import ru.rvd.ask.web.dto.orgstructure.response.EmployeeResponseMinimalDto;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * Предполагаемая централь по обработке всего, что касается пользователя-
// * получение контексттов, ролей, принадлежности к организации и т.д. и т.п.
// *
// */
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class UserServices {
//
//    private final static String ROLE="ROLE_";//Префикс, который Spring вставляет перед ролями всегда.
//    private final static String ANONYMOUS = "anonymousUser";// Имя анонима - может оказаться удобнее на ui
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Value("${application.security.adminRole}")
//    private String adminRole;
//    private final EmployeesRepository repository;
//    private final OrganizationRepository organizationRepository;
//    private final SPG2RoleService roleService;
//    private final AuthProvider authProvider;
//    private final EmployeeMapper mapper;
//    private final EmployeeAccessor code.accessor;
//    private final ClassifierService classifierService;
//    private final UtilityService utilityService;
//
//
//    public Authentication getAuthentication() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
//
//    public String getCurrentUserName() {
//        Authentication authentication = getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            String currentUserName = authentication.getName();
//            return currentUserName.toLowerCase();
//        }else{
//            return ANONYMOUS;
//        }
//    }
//
//    public boolean isAllowed(Employee employee){
//        return employee != null && employee.getDismissalDate() == null ? true : false;
//    }
//
//    public Employee getEmployeeByUserLogin(String userLogin){
//        //Проверок досупа не ставить!!!
//        return repository.findByUserLoginCustom(userLogin).orElseThrow(() -> new EmployeeNotFoundException(" User login:"+userLogin));
//    }
//
//    public Employee getCurrentEmployee(){
//        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
//            return getEmployeeByUserLogin(getCurrentUserName());
//        }else{
//            log.info("User is not authenticated");
//            return null;
//        }
//    }
//
//    public String getCurrentUserFullName(){
//        var result = "";
//        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
//            var empl = getCurrentEmployee();
//            if (empl!= null){
//                    return empl.getFullName();
//            }
//            return result;
//        }else{
//            log.info("User is not authenticated");
//            return ANONYMOUS;
//        }
//    }
//
//    public String getCurrentUserOrganizationName() {
//        var result = "";
//        //Collection<? extends GrantedAuthority> userData = getAuthentication().getAuthorities(); это если хотели бы привязать организацию в группе АД
//        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
//            Employee employee = getEmployeeByUserLogin(getCurrentUserName());
//            if (employee != null) {
//                Organization organization = employee.getOrganization();
//                if (organization != null) {
//                    return organization.getTitle();
//                }
//                log.warn("Can not find user organisation User id: {}", employee.getId());
//            }
//            return result;
//        } else {
//            log.info("User is not authenticated");
//            return "";
//        }
//    }
//
//
//    /**
//     * из Authority получает список ролей без префиксов
//     * @param roles Raw коллекция ролей из Spring Security
//     * @return List&lt;String&gt; Нормализированный список ролей пользователя
//     */
//    private List<String> getNormalizedAuthorities(Collection<? extends GrantedAuthority> roles){
//        return roles
//                .stream()
//                .map(auth -> StringUtils.removeStart(auth.getAuthority(), ROLE))
//                .collect(Collectors.toList());
//    }
//
//    public List<String> getCurrentUserRoles(){
//        return getNormalizedAuthorities(getAuthentication().getAuthorities());
//    }
//
//    private Boolean isRoleInCurrentUser(@NonNull String role){
//        List<String> roles = getNormalizedAuthorities(getAuthentication().getAuthorities());
//        return roles.stream().anyMatch(item -> role.equalsIgnoreCase(item));
//    }
//
//    public EmployeeResponseDto getInfoByLogin(String login){
//        var empl = getEmployeeByUserLogin(login);
//        return mapper.toDto(empl);
//    }
//
//    /**
//     * По текущему пользователю отдать инфу
//     * @return
//     */
//    public EmployeeResponseDto getCurrentUserContextInfo(){
//        return getInfoByLogin(getCurrentUserName());
//    }
//    /**
//     * Есть ли у пользователя право смотреть всё и вся.
//     * @return
//     */
//    public Boolean isCurrentUserGod(){
//        return isRoleInCurrentUser(adminRole);
//    }
//
//    public List<EmployeeResponseDto> getEmployeesByAllRoles(List<String> roleIds){
//        String rolesString = roleIds.stream().collect(Collectors.joining(","));
//        Query query = entityManager.createNativeQuery(
//                "SELECT * FROM employees e where jsonb_exists_all(e.roles,'{" + rolesString + "}')", "EmployeeMapping");
//        return (List<EmployeeResponseDto>) query.getResultList().stream().map(e -> mapper.toDto((Employee)e)).collect(Collectors.toList());
//    }
//
//    public List<Employee> getEmployeesByAnyOfRoles(List<String> roleIds){
//        String rolesString = roleIds.stream().collect(Collectors.joining(","));
//        Query query  = entityManager.createNativeQuery(
//                "SELECT * FROM employees e where jsonb_exists_any(e.roles,'{" + rolesString + "}')", Employee.class);
//        return query.getResultList();
//    }
//
//    public Stream<Employee> getEmployeesByRoleIds(Set<String> roleIds, int start, int count){
//        return repository.findEmployeeByRolesIn(roleIds, pageRequest(start, count)).stream();
//    }
//    public long getEmployeesCountByRoleIds(Set<String> roleIds){
//        return repository.countEmployeeByRolesIn(roleIds);
//    }
//
//    public Stream<Employee> getAllEmployees(int start, int count){
//        return repository.findAll(pageRequest(start,count)).stream();
//    }
//    public long getAllEmployeesCount(){
//        return repository.count();
//    }
//
//    public Stream<Employee> getEmployeesByLastName(String lastName, int start, int count){
//        return repository.findEmployeeByLastName(lastName,pageRequest(start,count)).stream();
//    }
//    public long getEmployeesCountByLastName(String lastName){
//        return repository.countEmployeeByLastName(lastName);
//    }
//
//    public Stream<Employee> getEmployeesByLastNameLike(String lastNameLike, int start, int count){
//        return repository.findEmployeeByLastNameLike(lastNameLike,pageRequest(start,count)).stream();
//    }
//    public long getEmployeesCountByLastNameLike(String lastNameLike){
//        return repository.countEmployeeByLastNameLike(lastNameLike);
//    }
//
//    public Stream<Employee> getEmployeesByFirstName(String firstName, int start, int count){
//        return repository.findEmployeeByFirstName(firstName,pageRequest(start,count)).stream();
//    }
//    public long getEmployeesCountByFirstName(String firstName){
//        return repository.countEmployeeByFirstName(firstName);
//    }
//
//    public Stream<Employee> getEmployeesByFirstNameLike(String firstNameLike, int start, int count){
//        return repository.findEmployeeByFirstNameLike(firstNameLike,pageRequest(start,count)).stream();
//    }
//    public long getEmployeesCountByFirstNameLike(String firstNameLike){
//        return repository.countEmployeeByFirstNameLike(firstNameLike);
//    }
//
//    public Stream<Employee> getEmployeesByEmail(String email, int start, int count){
//        return repository.findEmployeeByUserEmail(email,pageRequest(start,count)).stream();
//    }
//    public long getEmployeesCountByEmail(String email){
//        return repository.countEmployeeByUserEmail(email);
//    }
//
//    public Stream<Employee> getEmployeesByEmailLike(String emailLike, int start, int count){
//        return repository.findEmployeeByUserEmailLike(emailLike,pageRequest(start,count)).stream();
//    }
//    public long getEmployeesCountByEmailLike(String emailLike){
//        return repository.countEmployeeByUserEmailLike(emailLike);
//    }
//
//    private static PageRequest pageRequest(int start, int count){
//        return PageRequest.of(start,count, Sort.by(Employee.FIELD_LAST_NAME, Employee.FIELD_FIRST_NAME).ascending());
//    }
//
//    public  List<Employee> getEmployeeEntities (long orgId, String lettersFromName){
//        return getEmployeeEntities(orgId,lettersFromName,false);
//
//    }
//
//    public Employee getEmployeeEntitie(long userId){
//        return repository.findByDismissalDateIsNullAndId(userId).orElseThrow(() -> new EmployeeNotFoundException(" User login:"+userId));
//
//    }
//
//    public  List<Employee> getEmployeeEntities (long orgId, String lettersFromName,Boolean forReviewOnly){
//        List<Employee> empls;
//        if(StringUtils.isEmpty(lettersFromName)){
//            empls = forReviewOnly ?
//                    repository.findByDismissalDateIsNullAndReviewQueryParticipateIsTrueAndOrganization_IdEquals(orgId) :
//                    repository.customFindEmplByOrg(orgId);
//        }else{
//            String template =  '%'+lettersFromName.toLowerCase()+'%';
//            empls = forReviewOnly ?
//                    repository.customFindEmplReviewByOrgAndLettersFromName(orgId,template) :
//                    repository.customFindEmplByOrgAndLettersFromName(orgId,template);
//        }
//        return empls;
//    }
//
//    public List<EmployeeResponseMinimalDto> getEmployeesForOrganizationMinimal(Long orgId, String lettersFromName) {
//        List<Employee> empls = getEmployeeEntities(orgId,lettersFromName);
//        return mapper.toMinimalDtos(empls);
//    }
//
//    public List<EmployeeResponseDto> getEmployeesForOrganization(@NonNull  Long orgId,String lettersFromName){
//        List<Employee> empls = getEmployeeEntities(orgId,lettersFromName);
//        return mapper.toDtos(empls);
//    }
//
//    protected EmployeeResponseDto innerCreate(Employee entity){
//        if(repository.findByUserLoginCustom(entity.getUserLogin()).isPresent()){
//            throw new EmployeeDuplicationException(entity.getUserLogin());
//        }
//        entity.setCreatedAt(LocalDateTime.now());
//        entity.setCreatedBy(getCurrentUserName());
//        var resp = repository.save(entity);
//        return mapper.toDto(resp);
//    }
//
//    public EmployeeResponseDto create(EmployeeRequestByCodeDto requestDto) throws Throwable{
//        code.accessor.create();
//        var entity = mapper.fromDto(requestDto);
//        return innerCreate(entity);
//    }
//
//    public EmployeeResponseDto update(@NonNull EmployeeRequestByCodeDto dto) throws Throwable {
//        code.accessor.edit();
//        if(dto.getId()==null){
//            throw new CustomExceptions("Id can not be null");
//        }
//        final String login = dto.getUserLogin();
//        Employee entity = repository.findByUserLoginCustom(dto.getUserLogin()).orElseThrow(() ->  new EmployeeNotFoundException("Employee not found by login: '{}': ", login)) ;
//        mapper.updateFromDtoById(dto, entity);
//        var savedEntity = saveAndUpdateInfo(entity);
//        return mapper.toDto(savedEntity);
//    }
//
//    /**
//     * Переключение флага "Участвует в согласовании"
//     * @param login
//     * @return
//     */
//    public EmployeeResponseDto toggleUserReviewQueryParticipate(@NonNull final String login) {
//        code.accessor.edit();
//        Employee entity = repository.findByUserLoginCustom(login).orElseThrow(() ->  new EmployeeNotFoundException("Employee not found by login: '{}': ", login)) ;
//        Boolean reviewQueryParticipate = entity.getReviewQueryParticipate() == null ? false : entity.getReviewQueryParticipate();
//        if(authProvider.isUserExist(login)){
//            entity.setReviewQueryParticipate(!reviewQueryParticipate);
//            Employee savedEntity = saveAndUpdateInfo(entity);
//            return mapper.toDto(savedEntity);
//        }
//        throw new EmployeeNotFoundException("Employee with id '{}' not found in authorization provider service '{}'", login, authProvider.getProviderName());
//    }
//
//    public void delete(String login, boolean hard) {
//        code.accessor.delete();
//        Employee entity = repository.findByUserLoginCustom(login).orElseThrow(() ->  new EmployeeNotFoundException("Employee not found by login: '{}': ", login));
//
//        if(!hard){
//            repository.delete(entity);
//        }else{
//            entity.setActive(false);
//            repository.save(entity);
//        }
//    }
//
//    /**
//     * Обновление полей при обновлении записи.
//     * @param entity
//     * @return
//     */
//    protected Employee saveAndUpdateInfo(Employee entity){
//        var currentUserName = getCurrentUserName();
//        entity.setUpdatedBy(currentUserName);
//        var currentDt = LocalDateTime.now();
//        entity.setUpdatedAt(currentDt);
//
//        return repository.save(entity);
//    }
//
//    public List<EmployeeResponseDto> getEmployeesByUserLoginIn(List<String> logins){
//        List<Employee> entities = repository.findEmployeeByUserLoginIgnoreCaseIsIn(logins);
//        return entities.stream().map(mapper::toDto).collect(Collectors.toList());
//    }
//
//    public List<EmployeeResponseDto> getEmployeesByUserEmailIn(List<String> mails){
//        List<Employee> entities = repository.findEmployeeByUserEmailIsIn(mails);
//        return entities.stream().map(mapper::toDto).collect(Collectors.toList());
//    }
//
//    public BulkResponseDto<EmployeeResponseDto> create_bulk(List<EmployeeRequestByCodeDto> listRequestDto) {
//        BulkResponseDto res = new BulkResponseDto();
//        listRequestDto.forEach(reqDTO -> {
//            try {
//                var responseDto = this.create(reqDTO);
//                res.getSuccess().add(responseDto);
//            }catch(Throwable e){
//                String uuid = UUID.randomUUID().toString();
//                log.error("create_bulk BUG={}", uuid, e);
//                ErrorResponse errorResponse = ErrorResponse.builder()
//                        .rquid(uuid)
//                        .message(e.getMessage())
//                        .stackTrace(String.format("%s:\n%s", e.getClass().getSimpleName(), ExceptionUtils.getStackTrace(e)))
//                        .build();
//                res.getErrors().add(errorResponse);
//            }
//        });
//        return  res;
//    }
//
//    /**
//     * Получает пользователей по списку id.
//     * Получение идет только тех пользователей, которые находятся в той же организации, что и текущий.
//     * @param inputEmpls
//     * @return
//     */
//    public List<String> getEmployeeNamesByIds(ListOfEmployeeId inputEmpls) {
//        var empl = getEmployeeByUserLogin(getCurrentUserName());
//        Organization org =  empl.getOrganization();
//        Long orgId = org==null? -1:org.getId();
//        var empls=  repository.findByDismissalDateIsNullAndIdInAndOrganization_IdEquals(inputEmpls.getEmployeeIds(),orgId);
//        if(!CollectionUtils.isEmpty(empls)){
//            return empls.stream().map(employee -> employee.getUserLogin().toLowerCase()).collect(Collectors.toList());
//        }else{
//            return Collections.EMPTY_LIST;
//        }
//    }
//
//    public ResponsePageable<List<EmployeeResponseFullDto>> employeeByParams(UIListParams params) {
//
//        UIListParams uiListParams = new UIListParams();
//        uiListParams.setPagination(new UIPagination(-1,-1,0L,0));
//        uiListParams.setSort(List.of(new UISort("created", Sort.Direction.DESC, 0)));
//        uiListParams.setFilters(List.of(new UIFilter()));
//
//        Specs<Employee> specs = new Specs<>(params,10);
//        PageRequest pageParams = specs.getPageParamsFromUIParams();
//        Specification<Employee> filter = specs.getSpecificationFromFilters(params.getFilters());
//
//
//        Page<Employee> temporaryPage = repository.findAll( pageParams);
//
//        //После фильтра текущая страница может стать за пределы - выбрали фильтр на последней
//        // и страниц стало резко меньше. Иду на первую
//        if (temporaryPage.getTotalPages() < specs.calcCurrentPage() + 1 && temporaryPage.getTotalElements() != 0) {
//            params.getPagination().setCurrent(0);
//            return employeeByParams(params);
//        }
//
//        UIPagination pagination = UIPagination.builder()
//                .pageSize(params.getPagination().getPageSize())
//                .totalPages(temporaryPage.getTotalPages())
//                .current(params.getPagination().getCurrent())
//                .total(temporaryPage.getTotalElements())
//                .build();
//
//
//        ResponsePageable<List<EmployeeResponseFullDto>> pgRes= new ResponsePageable();
//        List<EmployeeResponseFullDto> listEmpl = mapper.toFullDtos(temporaryPage.getContent());
//        pgRes.setResult(listEmpl);
//        pgRes.setPagination(pagination);
//        return pgRes;
//    }
//
//    public List<EmployeeResponseMinimalDto> getEmployeesForReviewMinimal(Long orgId, String lettersFromName) {
//        List<Employee> empls = getEmployeeEntities(orgId,lettersFromName,true);
//        return mapper.toMinimalDtos(empls);
//    }
//
//    public List<EmployeeResponseMinimalDto> getEmployeesByReviewIsTrueAndOrganization(Long orgId) {
//        List<Employee> employees = repository.findEmployeeByReviewQueryParticipateIsTrueAndOrganization_IdEquals(orgId);
//        return mapper.toMinimalDtos(employees);
//    }
//
//    public long getActiveEmployeesCount() {
//        return repository.countEmployeeByActiveIsTrueOrActiveIsNull();
//    }
//
//    public long getFiredEmployeesCount() {
//        return repository.countEmployeeByDismissalDateIsNotNull();
//    }
//
//    public ClassifierElement getNexApprovalFunction(String login) {
//        Employee empl = getEmployeeByUserLogin(login);
//        ClassifierElement func = empl.getFunction();
//        Classifier classifier= classifierService.findClassifierByTypeField(ClassifierService.FUNCTION);
//        ClassifierElement next =  classifier.getElements().stream()
//                .sorted((o1, o2) -> Long.compare(o1.getOrder(),o2.getOrder()))
//                .filter(elem -> elem.getOrder()>func.getOrder() ).findFirst().orElseThrow(() -> new CustomExceptions("Тhe user does not have rights to start the process. User name:{} User function {} ",login,func.getDefault()));
//        return next;
//    }
//
//    public ClassifierElement getNextApprovalFunctionBySubject(String login, Entities entityType, Long entityId) {
//        Employee employee = getEmployeeByUserLogin(login);
//        ClassifierElement nextFunction = utilityService.getNextApprovalFunctionBySubject(employee, entityType, entityId);
//        return nextFunction;
//    }
//}
