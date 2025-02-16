package com.meokq.api.answer.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tb_answer")
class AnswerEntity {
    @Id
    var answerId: String? = null
    var content: String? = null
}

