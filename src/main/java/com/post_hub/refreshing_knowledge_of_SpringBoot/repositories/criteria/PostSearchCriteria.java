package com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.PostEntity;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.UserEntity;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.post.PostSearchFilter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostSearchCriteria implements Specification<PostEntity> {
    private final PostSearchFilter request;

    @Override
    @Nullable
    public Predicate toPredicate(
            @NonNull Root<PostEntity> root,
            @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {

        boolean hasKeywords = Objects.nonNull(request.getKeywords()) && !request.getKeywords().isBlank();

        if (!hasKeywords) {
            return criteriaBuilder.conjunction();
        }

        var searchEverywhere = Boolean.FALSE.equals(request.getTitle()) && Boolean.FALSE.equals(request.getContent())
                && Boolean.FALSE.equals(request.getAuthorName());

        List<Predicate> keywordPredicates = new ArrayList<>();
        String likePattern = "%" + request.getKeywords() + "%";

        if (Boolean.TRUE.equals(request.getTitle()) || searchEverywhere) {
            keywordPredicates.add(criteriaBuilder.like(root.get(PostEntity.TITLE_FIELD), likePattern));
        }

        if (Boolean.TRUE.equals(request.getContent()) || searchEverywhere) {
            keywordPredicates.add(criteriaBuilder.like(root.get(PostEntity.CONTENT_FIElD), likePattern));
        }

        if (Boolean.TRUE.equals(request.getAuthorName()) || searchEverywhere) {
            Join<PostEntity, UserEntity> userJoin = root.join("user");
            keywordPredicates.add(criteriaBuilder.like(userJoin.get("username"), likePattern));
        }

        if (keywordPredicates.isEmpty()) {
            return criteriaBuilder.conjunction();
        }

        return criteriaBuilder.or(keywordPredicates.toArray(new Predicate[0]));
    }
}