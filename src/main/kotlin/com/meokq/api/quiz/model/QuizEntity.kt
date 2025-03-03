package com.meokq.api.quiz.model

import com.meokq.api.answer.model.AnswerEntity
import com.meokq.api.core.model.BaseModel
import com.meokq.api.quest.model.Mission
import com.meokq.api.quiz.request.QuizReq
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "tb_quiz")
class QuizEntity (
    var question : String,
    var hint: String,
    @OneToMany(mappedBy = "quiz", cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, orphanRemoval = true)
    var answers: MutableList<AnswerEntity> = mutableListOf(),
    @ManyToOne
    @JoinColumn(name = "mission_id")
    var mission: Mission,
    @Id
    @UuidGenerator
    var quizId: String? = null,
): BaseModel() {
    constructor(req: QuizReq, mission: Mission) : this(
        question = req.question,
        hint = req.hint,
        mission = mission,
    ) {
        this.answers = req.answers.map { it.toEntity(this) }.toMutableList()
    }
}


