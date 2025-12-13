package food_delivery_app_backend.Specification;


import food_delivery_app_backend.Entity.Business_Partner_Entity.BusinessPartnerEntity;
import food_delivery_app_backend.Enum.VegNonVeg;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BusinessPartner {

    public static Specification<BusinessPartnerEntity> findByName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(
                    cb.lower(root.get("name")),"%"+name.toLowerCase()+"%"
            );
        };
    }

    public static Specification<BusinessPartnerEntity> findByVegCategory(VegNonVeg vegType) {
        return (root, query, cb) -> {
            if (vegType == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("vegType"), vegType);
        };
    }
    public static Specification<BusinessPartnerEntity> findByCity( String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("city")),"%"+city.toLowerCase()+"%"
            );
        };
    }
}
