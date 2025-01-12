package com.meokq.api.banner.repository

import com.meokq.api.banner.BannerEntity
import com.meokq.api.banner.BannerUpdateRequest
import com.meokq.api.banner.dto.BannerSearchRequest
import com.meokq.api.core.enums.TypeYN
import com.meokq.api.file.enums.ImageType
import com.meokq.api.file.model.Image
import com.meokq.api.file.repository.ImageRepository
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@ActiveProfiles("local")
@SpringBootTest
internal class BannerDslRepositoryImplTest {

    @Autowired
    lateinit var bannerRepository: BannerRepository
    @Autowired
    lateinit var imageRepository: ImageRepository
    @Autowired
    lateinit var entityManager: EntityManager

    @DisplayName("조건에 맞는 배너 리스트를 조회한다.")
    @Test
    fun findAllBySearchRequest() {
        // given
        val banner1 = createBannerEntity("my-title", "my-desc", TypeYN.Y)
        val banner2 = createBannerEntity("my-aaaaa", "my-desc", TypeYN.Y)
        val banner3 = createBannerEntity("my-aaaaa", "my-bbbb", TypeYN.Y)
        val banner4 = createBannerEntity("my-title", "my-desc", TypeYN.N)
        val bannerReq = BannerSearchRequest(
            titleLike = "title",
            descriptionLike = "desc",
            activeYn = TypeYN.Y
        )
        val pageable = PageRequest.of(0, 10)

        imageRepository.save(banner1.image!!)
        imageRepository.save(banner2.image!!)
        imageRepository.save(banner3.image!!)
        imageRepository.save(banner4.image!!)
        bannerRepository.save(banner1)
        bannerRepository.save(banner2)
        bannerRepository.save(banner3)
        bannerRepository.save(banner4)

        // when
        val lists = bannerRepository.findAllBySearchRequest(bannerReq, pageable)

        // then
        assertThat(lists)
            .hasSize(1)
            .extracting("title", "description", "activeYn")
            .contains(Tuple.tuple("my-title", "my-desc", TypeYN.Y))
    }

    @DisplayName("id를 전달받아서 배너의 정보를 수정한다.")
    @Test
    fun updateById(){
        // given
        val banner = createBannerEntity("title", "desc", TypeYN.Y)
        imageRepository.save(banner.image!!)
        val savedBanner = bannerRepository.save(banner)
        val bannerId: Long = savedBanner.id!!

        val request = BannerUpdateRequest(
            title = "title-updated",
            activeYn = TypeYN.N,
        )

        // when
        bannerRepository.updateByBannerId(request, bannerId)
        bannerRepository.flush() // 변경 내용 DB에 반영
        entityManager.clear() // 영속성 컨텍스트 초기화

        // then
        val findBanner = bannerRepository.findById(bannerId)
        assertThat(findBanner).isNotNull
        assertThat(findBanner.get())
            .extracting("title", "description", "activeYn")
            .contains("title-updated", "desc", TypeYN.N)
    }

    fun createBannerEntity(title: String, description: String, activeYn: TypeYN): BannerEntity {
        val fileId = "sample${UUID.randomUUID()}"
        val image = Image(
            fileId = fileId,
            type = ImageType.BANNER_IMAGE
        )

        return BannerEntity(
            title = title,
            image = image,
            activeYn = activeYn,
            description = description,
        )
    }
}