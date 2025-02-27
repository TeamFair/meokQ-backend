package com.meokq.api.answer.model

import com.meokq.api.quiz.model.QuizEntity
import jakarta.persistence.*

@Entity
@Table(name = "tb_answer")
class AnswerEntity (
    @Id
    var answerId: String? = null,
    var content: String? = null,
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    var quiz: QuizEntity,
)

