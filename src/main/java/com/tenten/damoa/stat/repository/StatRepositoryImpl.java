package com.tenten.damoa.stat.repository;

import static com.tenten.damoa.interaction.domain.QInteractionHistory.interactionHistory;
import static com.tenten.damoa.hashtag.domain.QHashtag.hashtag;
import static com.tenten.damoa.post.domain.QPost.post;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import com.tenten.damoa.stat.controller.dto.HashtagStatRes;
import com.tenten.damoa.interaction.domain.InteractionCategory;
import com.tenten.damoa.stat.domain.MetricsType;
import com.tenten.damoa.stat.domain.TimeUnit;
import com.tenten.damoa.stat.service.command.HashtagStatCommand;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatRepositoryImpl implements StatRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<HashtagStatRes> getMetricsCountForPeriod(HashtagStatCommand command) {
        DateTemplate<String> date = getDateTemplateByTimeUnit(command.unit(), interactionHistory.createdAt);

        return queryFactory
                .select(date, interactionHistory.count())
                .from(interactionHistory)
                .join(hashtag).on(interactionHistory.post.id.eq(hashtag.post.id))
                .where(
                        hashtagEq(command.hashtag()),
                        categoryEq(command.metric()),
                        interactionHistory.createdAt.goe(command.period().getStart()),
                        interactionHistory.createdAt.loe(command.period().getEnd())
                )
                .groupBy(date)
                .orderBy(date.asc())
                .fetch()
                .stream()
                .filter(Objects::nonNull)
                .map(tuple -> HashtagStatRes.builder()
                        .date(LocalDateTime.parse(tuple.get(date), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .value(Objects.requireNonNull(tuple.get(interactionHistory.count())).intValue())
                        .build())
                .toList();
    }

    @Override
    public List<HashtagStatRes> getPostCountForPeriod(HashtagStatCommand command) {
        DateTemplate<String> date = getDateTemplateByTimeUnit(command.unit(), post.createdAt);

        return queryFactory
                .select(date, post.id.countDistinct())
                .from(post)
                .join(hashtag).on(post.id.eq(hashtag.post.id))
                .where(
                        hashtagEq(command.hashtag()),
                        post.createdAt.goe(command.period().getStart()),
                        post.createdAt.loe(command.period().getEnd())
                )
                .groupBy(date)
                .orderBy(date.asc())
                .fetch()
                .stream()
                .filter(Objects::nonNull)
                .map(tuple -> HashtagStatRes.builder()
                        .date(LocalDateTime.parse(tuple.get(date), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .value(Objects.requireNonNull(tuple.get(post.id.countDistinct())).intValue())
                        .build())
                .toList();
    }

    private DateTemplate<String> getDateTemplateByTimeUnit(TimeUnit unit, DateTimePath<LocalDateTime> path) {
        switch (unit) {
            case DATE -> {
                return Expressions.dateTemplate(
                        String.class,
                        "DATE_FORMAT({0}, '%Y-%m-%d %00:00:00')",
                        path);
            }
            case HOUR -> {
                return Expressions.dateTemplate(
                        String.class,
                        "DATE_FORMAT({0}, '%Y-%m-%d %H:00:00')",
                        path);
            }
            default -> throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
    }

    private BooleanExpression hashtagEq(String tag) {
        return hashtag.tag.eq(tag);
    }

    private BooleanExpression categoryEq(MetricsType type) {
        return switch (type) {
            case VIEW_COUNT -> interactionHistory.category.eq(InteractionCategory.VIEW);
            case LIKE_COUNT -> interactionHistory.category.eq(InteractionCategory.LIKE);
            case SHARE_COUNT -> interactionHistory.category.eq(InteractionCategory.SHARE);
            default -> throw new BusinessException(ErrorCode.BAD_REQUEST);
        };
    }
}
