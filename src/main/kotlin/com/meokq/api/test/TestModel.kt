package com.meokq.api.test

import com.meokq.api.core.model.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

@Entity(name = "tb_test_1")
open class TestModel() : BaseModel(){

    @Id
    @UuidGenerator
    open lateinit var id : String

    @UuidGenerator
    var param1 : String? = null

    var date : LocalDateTime = LocalDateTime.now()

    var date1 : LocalDateTime? = null
}