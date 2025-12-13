package food_delivery_app_backend.Service.PersonalDetailService;

import food_delivery_app_backend.Exeption.ResouceNotFoundException;
import food_delivery_app_backend.Exeption.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public abstract class AbstractionProfileService<REQ,ENTITY ,RES>
                implements BaseProfileService<REQ,ENTITY,RES>{

    protected final JpaRepository<ENTITY,Long>  repository;
    protected AbstractionProfileService(JpaRepository<ENTITY, Long> repository) {
        this.repository = repository;
    }

    protected abstract ENTITY mapToEntity(REQ req);
    protected abstract RES mapToDto(ENTITY entity);
    protected abstract void mapTOExistingEntity(ENTITY entity, REQ req);

    protected abstract Optional<ENTITY> findEntityByUsername(String username);
    protected abstract void detachProfileFromUser(String username);



//    @Override
//    public RES createProfile(REQ req){
//        ENTITY entity=mapToEntity(req);
//        ENTITY saved=repository.save(entity);
//        return mapToDto(saved);
//    }

    @Override
    public RES updateProfile(REQ req,Authentication authentication){
        ENTITY entity=findEntityByUsername(authentication.getName())
                .orElseThrow(()-> new ResouceNotFoundException("Cant find a profile make with this account"));

        mapTOExistingEntity(entity,req);
        ENTITY update=repository.save(entity);
        return mapToDto(update);
    }

    @Override
    public RES getProfile(Authentication authentication){
        ENTITY entity=findEntityByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFoundException("Cant find a profile with this account"));

        return mapToDto(entity);
    }

    @Override
    public void deleteProfile(Authentication authentication){
        ENTITY entity=findEntityByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFoundException("Account does not found with the id provided"));


        detachProfileFromUser(authentication.getName());

        repository.delete(entity);
    }

}
