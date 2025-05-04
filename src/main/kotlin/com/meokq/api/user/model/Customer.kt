package com.meokq.api.user.model

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.core.model.BaseModel
import com.meokq.api.title.model.TitleHistory
import com.meokq.api.user.enums.UserStatus
import com.meokq.api.xp.model.Xp
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity(name = "tb_customer")
data class Customer(
    @Id
    @UuidGenerator
    var customerId: String? = null,
    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.ACTIVE,
    @NotNull
    @Column(unique = true)
    var email: String? = null,
    var nicknameSeq: Long? = null,
    var nickname: String? = null,
    @Enumerated(EnumType.STRING)
    var channel: AuthChannel? = null,
    @Column(name = "withdraw_at")
    var withdrawAt: LocalDateTime? = null,
    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], orphanRemoval = true)
    var xp: MutableList<Xp> = mutableListOf(),
    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], orphanRemoval = true)
    var titles: MutableList<TitleHistory> = mutableListOf(),
    var profileImageId: String? = null,
    @Column(name = "title_id")
    var titleHistoryId: String? = null,

    ) : BaseModel() {
    constructor(request: LoginReq) : this(
        email = request.email,
        channel = request.channel,
    )

    fun totalXp(): Long {
        return xp.sumOf { it.xpPoint } ?: 0L
    }

    fun addXp(xp: Xp) {
        this.xp.add(xp)
        xp.customer = this
    }

    fun deleteProfileImage() {
        this.profileImageId = null
    }

    fun updateProfileImage(imageId: String) {
        this.profileImageId = imageId
    }

    fun addTitle(titleId: String) {
        titles.findLast { it.titleId == titleId } ?: run {
            titles.add(TitleHistory(
                customer = this,
                titleId = titleId,
            ))
        }
    }

    fun getTitleId(): String? {
        return this.titleHistoryId?.let {
                this.titles.findLast { value -> value.id == this.titleHistoryId }
            }?.titleId
    }

    fun updateTitle(titleHistoryId: String?) {
        if (titleHistoryId.isNullOrBlank()) {
            this.titleHistoryId = null
        } else {
            this.titleHistoryId = titleHistoryId
        }
    }

}
