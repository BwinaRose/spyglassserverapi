package the_thundercats.spyglassserverapi.security.services;

import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceCreationException;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceUpdateException;
import the_thundercats.spyglassserverapi.domain.dtos.UserCreateRequest;
import the_thundercats.spyglassserverapi.domain.models.User;

public interface FirebaseUserMgrService {
    String createFireBaseUser(UserCreateRequest userDetail) throws ResourceCreationException;
    void updateFireBaseUser(User userDetail) throws ResourceUpdateException;
    void deleteFireBaseUser(String id) throws ResourceUpdateException;
}
