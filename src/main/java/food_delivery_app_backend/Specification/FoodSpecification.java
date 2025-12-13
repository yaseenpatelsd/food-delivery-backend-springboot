package food_delivery_app_backend.Specification;

import food_delivery_app_backend.Entity.Business_Partner_Entity.FoodEntity;
import food_delivery_app_backend.Enum.VegNonVeg;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FoodSpecification {


    public static Specification<FoodEntity> findByName(String name){
        return (root, query, criteriaBuilder) -> {
            if (name==null || name.isEmpty()){
                return criteriaBuilder.conjunction();
            }
        return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")),"%"+name.toLowerCase()+"%"
        );
        };
    }

    public static Specification<FoodEntity> findByPrice(Integer minAmount,Integer maxAmount){
        return (root, query, criteriaBuilder) -> {
            if (minAmount==null && maxAmount==null ){
                return criteriaBuilder.conjunction();
            }
            if (minAmount!=null && maxAmount==null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("amount"),minAmount);
            }
            if (minAmount==null&& maxAmount!=null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("amount"),maxAmount);
            }

                return criteriaBuilder.between(root.get("amount"),minAmount,maxAmount);
        };
    }

    public static  Specification<FoodEntity> findByRating(Double minRating ,Double maxRating){
        return (root, query, criteriaBuilder) -> {
            if (minRating==null && maxRating==null){
                return criteriaBuilder.conjunction();
            }
            if (minRating!=null && maxRating==null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("averageRating"),minRating);
            }
            if (minRating==null && maxRating!=null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("averageRating"),maxRating);
            }
            return criteriaBuilder.between(root.get("averageRating"),minRating,maxRating);
        };
    }

    public static Specification<FoodEntity> findByVegNonVed(VegNonVeg vegType){
        return (root, query, criteriaBuilder) -> {
            if (vegType==null ){
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("type"),vegType);
        };
    }

    public static Specification<FoodEntity> filterIsAvailable(Boolean isAvailable){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("isAvailable"),isAvailable);
        };
    }

}
