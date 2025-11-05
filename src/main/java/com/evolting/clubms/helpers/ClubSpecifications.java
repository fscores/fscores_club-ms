package com.evolting.clubms.helpers;

import com.evolting.clubms.dtos.request.ClubSearchDto;
import com.evolting.clubms.entities.Club;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ClubSpecifications {

    public static Specification<Club> buildFromDto(ClubSearchDto dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getNameContains() != null && !dto.getNameContains().isEmpty()) {
                String search = "%" + dto.getNameContains().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("name")), search));
            }

            // Combine all predicates with AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

