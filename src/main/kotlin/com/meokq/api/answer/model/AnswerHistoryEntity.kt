package com.meokq.api.answer.model

import com.meokq.api.quest.model.Mission
import jakarta.persistence.*

@Entity
@Table(name = "tb_answer_history")
class AnswerHistoryEntity(
    @Id
    var id : Long? = null,
    @OneToOne
    @JoinColumn(name = "mission_id")
    var mission: Mission? = null,
    var content: String? = null,
) {
}