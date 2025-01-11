package com.meokq.api.banner

import com.meokq.api.banner.dto.BannerCreateRequestV2
import com.meokq.api.core.enums.TypeYN
import com.meokq.api.file.model.Image
import jakarta.persistence.*
import jakarta.validation.constraints.Max

@Entity(name = "tb_banner")
class BannerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    val id: Long? = null,

    @Max(200)
    var title: String? = null,

    @Max(1000)
    var description: String? = null,

    @OneToOne
    @JoinColumn(name = "image_file_id")
    var image: Image? = null,

    var activeYn: TypeYN = TypeYN.N
){

    companion object {
        fun create(request: BannerCreateRequestV2, findImage: Image): BannerEntity {
            return BannerEntity(
                title = request.title,
                description = request.description,
                image = findImage
            )
        }
    }
}