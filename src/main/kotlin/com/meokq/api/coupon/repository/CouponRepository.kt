package com.meokq.api.coupon.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.coupon.model.Coupon

interface CouponRepository : BaseRepository<Coupon, String> {
    //fun findAll(spec: Specification<Coupon>, pageable: Pageable?) : Page<Coupon>
}