package com.meokq.api.answer.model

import com.meokq.api.quiz.model.QuizEntity
import jakarta.persistence.*

@Entity
@Table(name = "tb_answer_history")
class AnswerHistoryEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var id : Long? = null,

    @JoinColumn(name = "quiz_id")
    @ManyToOne
    var quiz: QuizEntity? = null,

    var content: String? = null,
) {
}