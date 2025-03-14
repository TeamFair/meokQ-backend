package com.meokq.api.answer.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.quest.model.Mission
import com.meokq.api.quiz.model.QuizEntity
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "tb_answer")
class AnswerEntity (
    @Id
    @UuidGenerator
    var answerId: String? = null,
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    var quiz: QuizEntity,
) : BaseModel()

