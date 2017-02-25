package school.journal.repository.factory;

import school.journal.entity.*;
import school.journal.repository.IRepository;
import school.journal.repository.RepositoryAbstractClass;
import school.journal.repository.impl.*;

public class RepositoryFactory {
    private static RepositoryFactory instance = new RepositoryFactory();
    private IRepository<Role> roleRepository = RoleRepository.getInstance();


    public static RepositoryFactory getInstance(){
        return instance;
    }

    public IRepository<Role> getRoleRepository(){
        return roleRepository;
    }

}
