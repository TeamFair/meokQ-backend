package com.meokq.api.quiz.model

import com.meokq.api.answer.model.AnswerEntity
import com.meokq.api.quest.model.Mission
import jakarta.persistence.*

@Entity
@Table(name = "tb_quiz")
class QuizEntity (
    @Id
    var quizId: String? = null,
    var question : String,
    @OneToMany(mappedBy = "quiz", cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
    var answers: MutableList<AnswerEntity> = mutableListOf(),
    @ManyToOne
    @JoinColumn(name = "mission_id")
    var mission: Mission,
)

