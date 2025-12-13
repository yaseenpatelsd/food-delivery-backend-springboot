package food_delivery_app_backend.Controller;


import food_delivery_app_backend.Dto.BusinessPartnerFlow.BusinessDetailsEditDto;
import food_delivery_app_backend.Dto.BusinessPartnerFlow.BusinessPartnerRequestDto;
import food_delivery_app_backend.Enum.VegNonVeg;
import food_delivery_app_backend.Service.BusinessPartnerFlow.BusinessPartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessPartnerController {
    private final BusinessPartnerService businessPartnerService;

    public BusinessPartnerController(BusinessPartnerService businessPartnerService) {
        this.businessPartnerService = businessPartnerService;
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/list")
    public ResponseEntity<BusinessPartnerRequestDto> addBusiness(@RequestBody BusinessPartnerRequestDto dto,Authentication authentication){
        BusinessPartnerRequestDto businessPartnerRequestDto=businessPartnerService.addBusiness(dto,authentication);
        return ResponseEntity.ok(businessPartnerRequestDto);
    }

    @GetMapping("/get")
    public ResponseEntity<List<BusinessPartnerRequestDto>> getAll(@RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) VegNonVeg vegCategory,
                                                                  @RequestParam(required = false) String city){
       List<BusinessPartnerRequestDto> requestDtos= businessPartnerService.getBusiness(name, vegCategory, city);
       return ResponseEntity.ok(requestDtos);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/edit")
    public ResponseEntity<BusinessPartnerRequestDto> editBusiness(Authentication authentication, @RequestBody BusinessDetailsEditDto dto){
        BusinessPartnerRequestDto businessPartnerRequestDto=businessPartnerService.editBusiness(authentication,dto);
        return ResponseEntity.ok(businessPartnerRequestDto);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBusiness(Authentication authentication){
        businessPartnerService.deleteBusiness(authentication);
        return ResponseEntity.noContent().build();
    }

}
