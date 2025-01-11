package com.meokq.api.banner.repository

import com.meokq.api.banner.BannerUpdateRequest
import com.meokq.api.banner.QBannerEntity.bannerEntity
import com.meokq.api.banner.dto.BannerItemResponse
import com.meokq.api.banner.dto.BannerSearchRequest
import com.meokq.api.file.response.ImageResp
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class BannerDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : BannerDslRepository {

    override fun findAllBySearchRequest(
        searchRequest: BannerSearchRequest,
        pageable: PageRequest
    ): PageImpl<BannerItemResponse> {

        // 동적 쿼리 생성
        val whereClause = BooleanBuilder()

        searchRequest.titleLike?.let {
            whereClause.and(bannerEntity.title.containsIgnoreCase(it))
        }

        searchRequest.descriptionLike?.let {
            whereClause.and(bannerEntity.description.containsIgnoreCase(it))
        }

        searchRequest.activeYn?.let {
            whereClause.and(bannerEntity.activeYn.eq(it))
        }

        // 페이징 처리와 결과 조회
        val results = queryFactory
            .selectFrom(bannerEntity)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        // 총 개수 조회
        val total = queryFactory
            .select(bannerEntity.count())
            .from(bannerEntity)
            .where(whereClause)
            .fetchOne() ?: 0

        // 결과를 BannerItemResponse로 변환
        val bannerResponses = results.map { banner ->
            BannerItemResponse(
                id = banner.id,
                title = banner.title,
                description = banner.description,
                image = banner.image?.let { ImageResp(it) },
                activeYn = banner.activeYn,
            )
        }

        return PageImpl(bannerResponses, pageable, total)
    }

    override fun updateByBannerId(request: BannerUpdateRequest, bannerId: Long): Int {
        val updateClause = queryFactory.update(bannerEntity)

        request.title?.let { updateClause.set(bannerEntity.title, it) }
        request.description?.let { updateClause.set(bannerEntity.description, it) }
        request.activeYn?.let { updateClause.set(bannerEntity.activeYn, it) }

        val affectedRows = updateClause
            .where(bannerEntity.id.eq(bannerId))
            .execute()

        return affectedRows.toInt()
    }
}