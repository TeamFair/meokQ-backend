package com.meokq.api.title.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.user.model.Customer
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "tb_title_history")
class TitleHistory (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    var customer: Customer,
    var titleId : String,
    var readYn: Boolean = false,
    @Id
    @UuidGenerator
    var id: String? = null,
): BaseModel() {
}


