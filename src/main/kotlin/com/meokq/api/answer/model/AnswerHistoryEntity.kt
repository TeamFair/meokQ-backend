package com.meokq.api.answer.model

import com.meokq.api.quest.model.Mission
import com.meokq.api.quiz.model.QuizEntity
import jakarta.persistence.*

@Entity
@Table(name = "tb_answer_history")
class AnswerHistoryEntity(
    @Id
    var id : Long? = null,

    @ManyToOne
    @JoinColumn(name = "mission_id")
    var mission: Mission? = null,

    @JoinColumn(name = "quiz_id")
    @ManyToOne
    var quiz: QuizEntity? = null,

    var content: String? = null,
) {
}