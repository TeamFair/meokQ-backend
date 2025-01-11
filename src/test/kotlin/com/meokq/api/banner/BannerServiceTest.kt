package com.meokq.api.banner

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.banner.dto.BannerCreateRequestV2
import com.meokq.api.banner.dto.BannerSearchRequest
import com.meokq.api.banner.repository.BannerRepository
import com.meokq.api.core.enums.TypeYN
import com.meokq.api.file.enums.ImageType
import com.meokq.api.file.model.Image
import com.meokq.api.file.repository.ImageRepository
import com.meokq.api.file.service.ImgStorageService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.Commit
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional
@ActiveProfiles("local")
@SpringBootTest
internal class BannerServiceTest{

    @Autowired
    lateinit var bannerService: BannerService

    @Autowired
    lateinit var bannerRepository: BannerRepository

    @Autowired
    lateinit var imageRepository: ImageRepository

    @Autowired
    lateinit var storageService: ImgStorageService

    @Autowired
    lateinit var entityManager: EntityManager

    @BeforeEach
    fun setup() {
        // clear banner table
        bannerRepository.deleteAll()
    }

    @DisplayName("배너 데이터와 이미지 아이디를 받아서 저장한다.")
    @Test
    fun createBanner() {
        // given
        val fileId = "sample${UUID.randomUUID()}"
        val image = Image(
            fileId = fileId,
            type = ImageType.BANNER_IMAGE
        )
        val request = BannerCreateRequestV2(
            title = "my-title",
            description = "my-description",
            imageId = fileId
        )

        val saveImage = imageRepository.save(image)

        // when
        val result = bannerService.createBanner(request)

        // then
        assertThat(result.bannerId).isNotNull
        assertThat(result.image)
            .extracting("imageId", "location")
            .contains(fileId, "/open/images/$fileId")
    }

    @DisplayName("유효하지 않은 이미지 아이디를 사용하면, 배너를 저장할수 없다.")
    @Test
    fun createBannerWithWrongUrl() {
        // given
        val request = BannerCreateRequestV2(
            title = "my-title",
            description = "my-description",
            imageId = "wrong image ID"
        )

        // when
        // then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            bannerService.createBanner(request)
        }
    }

    @DisplayName("이미 사용된 이미지를 재사용하면, 배너를 저장할수 없다.")
    @Test
    fun createBannerWithDuplicatedImage() {
        // given
        val fileId = "sample${UUID.randomUUID()}"
        val image = Image(
            fileId = fileId,
            type = ImageType.BANNER_IMAGE
        )
        val request = BannerCreateRequestV2(
            title = "my-title",
            description = "my-description",
            imageId = fileId
        )

        val saveImage = imageRepository.save(image)

        // when // then
        bannerService.createBanner(request)
        assertThatThrownBy {
            bannerService.createBanner(request)
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("같은 아이디로 이미 등록된 배너가 있습니다.")
    }

    @DisplayName("모든 배너를 조회한다.")
    @Test
    fun selectAllBanners() {
        // given
        val banner1 = createBannerEntity(title = "activeBanner")
        val banner2 = createBannerEntity(title = "inactiveBanner")
        imageRepository.save(banner1.image!!)
        imageRepository.save(banner2.image!!)
        bannerRepository.save(banner1)
        bannerRepository.save(banner2)
        val of = PageRequest.of(0, 10)

        // when
        val responses = bannerService.findAll(BannerSearchRequest(), of)

        // then
        assertThat(responses)
            .hasSize(2)
            .extracting("title")
            .contains(
                "activeBanner",
                "inactiveBanner"
            )
    }

    @DisplayName("게시할 수 있는 배너를 조회한다.")
    @Test
    fun selectActiveBanners() {
        // given
        val banner1 = createBannerEntity("activeBanner", TypeYN.Y)
        val banner2 = createBannerEntity("inactiveBanner", TypeYN.N)
        imageRepository.save(banner1.image!!)
        imageRepository.save(banner2.image!!)
        bannerRepository.save(banner1)
        bannerRepository.save(banner2)

        val request = BannerSearchRequest(activeYn = TypeYN.Y)
        val of = PageRequest.of(0, 10)

        // when
        val responses = bannerService.findAll(request, of)

        // then
        assertThat(responses)
            .hasSize(1)
            .extracting("title")
            .contains("activeBanner")
    }

    @DisplayName("배너 아이디를 받아서, 배너를 비활성화처리한다.")
    @Test
    fun inactiveBanner() {
        // given
        val banner = createBannerEntity(activeYn = TypeYN.Y)
        imageRepository.save(banner.image!!)
        bannerRepository.save(banner)
        val bannerId: Long = banner.id!!

        // when
        bannerService.update(BannerUpdateRequest(activeYn = TypeYN.N), bannerId)
        entityManager.flush()
        entityManager.clear()

        // then
        val findBanner = bannerRepository.findById(bannerId)
        assertThat(findBanner).isNotNull
        assertThat(findBanner.get())
            .extracting("id", "activeYn")
            .contains(bannerId, TypeYN.N)
    }

    @DisplayName("배너아이디를 받아서, 배너를 비활성화처리한다.")
    @Test
    fun activeBanner() {
        // given
        val banner = createBannerEntity(activeYn = TypeYN.N)
        imageRepository.save(banner.image!!)
        bannerRepository.save(banner)
        val bannerId: Long = banner.id!!

        // when
        bannerService.update(BannerUpdateRequest(activeYn = TypeYN.Y), bannerId)
        entityManager.flush()
        entityManager.clear()

        // then
        val findBanner = bannerRepository.findById(bannerId)
        assertThat(findBanner).isNotNull
        assertThat(findBanner.get())
            .extracting("id", "activeYn")
            .contains(bannerId, TypeYN.Y)
    }

    @DisplayName("배너를 제거하면 연관된 이미지가 함께 제거된다.")
    @Test
    @Commit
    fun deleteBanner() {
        // given
        val banner = createBannerEntity()
        imageRepository.save(banner.image!!)
        bannerRepository.save(banner)
        val bannerId: Long = banner.id!!
        val fileId = banner.image!!.fileId!!
        val authReq = AuthReq(userType = UserType.ADMIN, userId = "admin")

        // when
        bannerService.deleteById(bannerId, authReq)

        // then
        val findBanner = bannerRepository.findById(bannerId)
        assertThat(findBanner.isPresent).isFalse

        val findImage = imageRepository.findById(fileId)
        assertThat(findImage.isPresent).isFalse

        val exist = storageService.exist(fileId)
        assertThat(exist).isFalse
    }

    @DisplayName("등록되지 않은 배너 아이디로 삭제할수 없다.")
    @Test
    fun deleteBannerWithWrongBannerId() {
        // given
        val bannerId: Long = -1
        val authReq = AuthReq(userType = UserType.ADMIN, userId = "admin")

        // when // then
        assertThrows<IllegalArgumentException> {
            bannerService.deleteById(bannerId, authReq)
        }
    }

    fun createBannerEntity(title: String = "title", activeYn: TypeYN = TypeYN.Y): BannerEntity {
        val fileId = "sample${UUID.randomUUID()}"
        val image = Image(
            fileId = fileId,
            type = ImageType.BANNER_IMAGE
        )

        return BannerEntity(
            title = title,
            image = image,
            activeYn = activeYn
        )
    }
}