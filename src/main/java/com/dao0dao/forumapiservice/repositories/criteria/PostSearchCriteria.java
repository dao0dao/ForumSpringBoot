package com.dao0dao.forumapiservice.repositories.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.dao0dao.forumapiservice.model.entities.PostEntity;
import com.dao0dao.forumapiservice.model.entities.PostEntity_;
import com.dao0dao.forumapiservice.model.entities.UserEntity;
import com.dao0dao.forumapiservice.model.entities.UserEntity_;
import com.dao0dao.forumapiservice.model.request.post.PostSearchFilter;
import com.dao0dao.forumapiservice.model.request.post.PostStatusFilter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostSearchCriteria implements Specification<PostEntity> {
    private final PostSearchFilter request;
    private final boolean includeDeleted;

    @Override
    @Nullable
    public Predicate toPredicate(
            @NonNull Root<PostEntity> root,
            @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (!includeDeleted) {
            predicates.add(criteriaBuilder.isFalse(root.get(PostEntity_.deleted)));
        }

        if (request.getStatus() == PostStatusFilter.ACTIVE) {
            predicates.add(criteriaBuilder.isFalse(root.get(PostEntity_.deleted)));
        } else if (request.getStatus() == PostStatusFilter.DELETED) {
            predicates.add(criteriaBuilder.isTrue(root.get(PostEntity_.deleted)));
        }

        Predicate keywordPredicate = this.getKeywordPredicate(root, criteriaBuilder);
        if (keywordPredicate != null) {
            predicates.add(keywordPredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    @Nullable
    private Predicate getKeywordPredicate(Root<PostEntity> root, CriteriaBuilder criteriaBuilder) {
        boolean hasKeywords = Objects.nonNull(request.getKeywords()) && !request.getKeywords().isBlank();

        if (!hasKeywords) {
            return null;
        }

        var searchEverywhere = Boolean.FALSE.equals(request.getTitle()) && Boolean.FALSE.equals(request.getContent())
                && Boolean.FALSE.equals(request.getAuthorName());

        List<Predicate> keywordPredicates = new ArrayList<>();
        String likePattern = "%" + request.getKeywords() + "%";

        if (Boolean.TRUE.equals(request.getTitle()) || searchEverywhere) {
            keywordPredicates.add(criteriaBuilder.like(root.get(PostEntity_.title), likePattern));
        }

        if (Boolean.TRUE.equals(request.getContent()) || searchEverywhere) {
            keywordPredicates.add(criteriaBuilder.like(root.get(PostEntity_.content), likePattern));
        }

        if (Boolean.TRUE.equals(request.getAuthorName()) || searchEverywhere) {
            Join<PostEntity, UserEntity> userJoin = root.join(PostEntity_.user);
            keywordPredicates.add(criteriaBuilder.like(userJoin.get(UserEntity_.username), likePattern));
        }

        if (keywordPredicates.isEmpty()) {
            return null;
        }

        return criteriaBuilder.or(keywordPredicates.toArray(new Predicate[0]));
    }
}
