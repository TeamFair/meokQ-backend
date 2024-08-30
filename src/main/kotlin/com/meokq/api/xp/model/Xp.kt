package com.meokq.api.xp.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.user.model.Customer
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_xp")
class Xp (
    @Id
    @UuidGenerator
    var xpId: String? = null,

    var xpPoint: Long = 0,

    @Enumerated(EnumType.STRING)
    var xpType: XpType? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null
): BaseModel(){
    fun gain(xpPoint: Long){
        this.xpPoint += xpPoint
    }
    fun withdraw(xpPoint: Long){
        this.xpPoint -= xpPoint
    }

}