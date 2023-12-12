package com.meokq.api.coupon.repository

import com.meokq.api.coupon.model.Coupon
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, String> {
    fun findAll(spec: Specification<Coupon>, pageable: Pageable?) : Page<Coupon>
}