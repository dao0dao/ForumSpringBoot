package com.post_hub.iam_service.repositories.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.post_hub.iam_service.model.entities.Post;
import com.post_hub.iam_service.model.request.post.PostSearchRequest;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class PostSearchCriteria implements Specification<Post> {
    private final PostSearchRequest request;

    @Override
    @Nullable
    public Predicate toPredicate(
            @NonNull Root<Post> root,
            @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getTitle())) {
            predicates.add(criteriaBuilder.like(root.get(Post.TITLE_FIELD), "%" + request.getTitle() + "%"));
        }

        if (Objects.nonNull(request.getContent())) {
            predicates.add(criteriaBuilder.like(root.get(Post.CONTENT_FIElD), "%" + request.getContent() + "%"));
        }

        if (Objects.nonNull(request.getLikes())) {
            predicates.add(criteriaBuilder.equal(root.get(Post.LIKES_FIELD), request.getLikes()));
        }

        if (Objects.nonNull(request.getDeleted())) {
            predicates.add(criteriaBuilder.equal(root.get(Post.DELETED_FIELD), request.getDeleted()));
        }

        if (Objects.nonNull(request.getKeywords())) {
            Predicate keywordsPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get(Post.TITLE_FIELD), "%" + request.getKeywords() + "%"),
                    criteriaBuilder.like(root.get(Post.CONTENT_FIElD), "%" + request.getKeywords() + "%"));
            predicates.add(keywordsPredicate);
        }
        
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
