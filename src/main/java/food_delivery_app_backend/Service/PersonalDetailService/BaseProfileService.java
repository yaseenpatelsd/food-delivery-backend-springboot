package food_delivery_app_backend.Service.PersonalDetailService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.w3c.dom.Entity;

import java.util.List;
@Service
public interface BaseProfileService<REQ, ENTITY, RES> {

//    RES createProfile(REQ request);

    RES updateProfile( REQ request, Authentication authentication);

    RES getProfile(Authentication authentication);

    void deleteProfile(Authentication authentication);
}

