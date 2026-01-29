package com.dta.Dating_App.specification;

import com.dta.Dating_App.DTO.UserFilterRequest;
import com.dta.Dating_App.entitys.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecifications {

    public static Specification<User> filterUsers(UserFilterRequest req) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            //  Search (name or displayName)
            if (req.getSearch() != null && !req.getSearch().isEmpty()) {
                String like = "%" + req.getSearch().toLowerCase() + "%";
                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("name")), like),
                                cb.like(cb.lower(root.get("displayName")), like)
                        )
                );
            }

            //  Age
            if (req.getMinAge() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("age"), req.getMinAge()));
            }
            if (req.getMaxAge() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("age"), req.getMaxAge()));
            }

            //  Height
            if (req.getMinHeight() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("height"), req.getMinHeight()));
            }
            if (req.getMaxHeight() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("height"), req.getMaxHeight()));
            }

            //  Multi select filters
            if (req.getBodyType() != null && !req.getBodyType().isEmpty()) {
                predicates.add(root.get("bodyType").in(req.getBodyType()));
            }
            if (req.getAppearance() != null && !req.getAppearance().isEmpty()) {
                predicates.add(root.get("appearance").in(req.getAppearance()));
            }
            if (req.getLanguage() != null && !req.getLanguage().isEmpty()) {
                predicates.add(root.get("language").in(req.getLanguage()));
            }
            if (req.getEnglishLevel() != null && !req.getEnglishLevel().isEmpty()) {
                predicates.add(root.get("englishLevel").in(req.getEnglishLevel()));
            }
            if (req.getEthnicity() != null && !req.getEthnicity().isEmpty()) {
                predicates.add(root.get("ethnicity").in(req.getEthnicity()));
            }
            if (req.getLookingFor() != null && !req.getLookingFor().isEmpty()) {
                predicates.add(root.get("lookingFor").in(req.getLookingFor()));
            }
            if (req.getGender() != null && !req.getGender().isEmpty()) {
                predicates.add(root.get("gender").in(req.getGender()));
            }

            //  smoke/drink
            if (req.getSmoke() != null) {
                predicates.add(cb.equal(root.get("smoke"), req.getSmoke()));
            }
            if (req.getDrink() != null) {
                predicates.add(cb.equal(root.get("drink"), req.getDrink()));
            }

            //  online status
            if (req.getOnlyOnline() != null && req.getOnlyOnline()) {
                predicates.add(cb.equal(root.get("online"), true));
            }

            //  exclude self
            if (req.getUserId() != null) {
                predicates.add(cb.notEqual(root.get("id"), req.getUserId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
