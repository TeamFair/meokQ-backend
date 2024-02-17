package com.meokq.api.core.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.controller.CheckAuthCallback
import com.meokq.api.core.converter.BaseConverterV2
import com.meokq.api.core.exception.DataException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.core.specification.BaseSpecificationV2
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

@Deprecated("JpaService를 사용하는 것을 권장")
interface BaseServiceV2<MODEL, ID> {
    val _repository : BaseRepository<MODEL, ID>
    val _specification : BaseSpecificationV2<MODEL>
    val modelConverter : BaseConverterV2<*>
    val checkAuthCallback : CheckAuthCallback

    /**
     * for create
     */
    fun <DTO> save(
        reqSave : DTO,
        authReq : AuthReq? = null,
        needAuthCheck : Boolean = false,
    ) : MODEL {
        if (needAuthCheck){
            checkNotNullData(authReq, "권한정보가 없습니다.")
            checkAuthCallback.beforeSave<DTO>(
                authReq = authReq!!,
                req = reqSave,
            )
        }

        val reqModel : MODEL = convertToModel(reqSave)
        return _repository.save(reqModel as (MODEL & Any))
    }

    fun <DTO>saveAll(
        reqSaves : List<DTO>,
        authReq : AuthReq? = null,
        needAuthCheck : Boolean = false,
    ) : List<MODEL> {
        if (needAuthCheck){
            checkNotNullData(authReq, "권한정보가 없습니다.")
            checkAuthCallback.beforeSave(
                authReq = authReq!!,
                req = reqSaves,
            )
        }

        val models: List<MODEL> = reqSaves.map { convertToModel(reqSaves)}
        return _repository.saveAll(models)
    }

    /**
     * for update
     */
    fun <DTO_SAVE, DTO_SEARCH>update(
        reqSave: DTO_SAVE,
        reqSearch : DTO_SEARCH,
        modelForSave: MODEL = convertToModel(reqSave), //TODO : 추후삭제,
        authReq : AuthReq? = null,
        needAuthCheck : Boolean = false,
    ) : MODEL {
        if (needAuthCheck){
            checkNotNullData(authReq, "권한정보가 없습니다.")
            checkAuthCallback.beforeUpdate<DTO_SAVE, DTO_SEARCH>(
                authReq = authReq!!,
                saveReq = reqSave,
                searchReq = reqSearch
            )
        }

        val targets = findAll(
            reqSearch = reqSearch
        ).map {
            modelConverter.overWrite(
                fromSource = reqSave,
                toSource = it as Nothing,
            )
        }

        return _repository.save(targets as (MODEL & Any))
    }

    /**
     * for read
     */
    fun findById(
        id : ID,
        authReq : AuthReq? = null,
        needAuthCheck : Boolean = false,
    ) : MODEL {
        if (needAuthCheck){
            checkNotNullData(authReq, "권한정보가 없습니다.")
            checkAuthCallback.beforeSelect<ID>(
                authReq = authReq!!,
                req = id,
            )
        }

        checkNotNullData(id, "id 값이 없습니다.")
        return _repository.findById(id!!).orElseThrow { NotFoundException("data is not found by id : $id") }
    }

    fun <DTO>findAll(
        reqSearch: DTO,
        pageable: Pageable,
        authReq : AuthReq? = null,
        needAuthCheck : Boolean = false,
    ) : Page<MODEL> {
        if (needAuthCheck){
            checkNotNullData(authReq, "권한정보가 없습니다.")
            checkAuthCallback.beforeSelect<DTO>(
                authReq = authReq!!,
                req = reqSearch,
            )
        }

        val pageableWithSorting = PageRequest.of(
            pageable.pageNumber, pageable.pageSize, Sort.by("createDate").descending()
        )

        checkNotNullData(reqSearch, "검색조건을 구성하는 데 문제가 생겼습니다.")
        val spec = _specification.bySearchDto(reqSearch)
        return _repository.findAll(spec, pageableWithSorting)
    }

    fun <DTO>findAll(
        reqSearch: DTO,
        authReq : AuthReq? = null,
        needAuthCheck : Boolean = false,
    ) : List<MODEL> {
        if (needAuthCheck){
            checkNotNullData(authReq, "권한정보가 없습니다.")
            checkAuthCallback.beforeSelect<DTO>(
                authReq = authReq!!,
                req = reqSearch,
            )
        }

        val spec = _specification.bySearchDto(reqSearch)
        return _repository.findAll(spec)
    }

    fun <DTO>exists(
        reqSearch: DTO,
        authReq : AuthReq? = null,
        needAuthCheck : Boolean = false,
    ) : Boolean {
        if (needAuthCheck){
            checkNotNullData(authReq, "권한정보가 없습니다.")
            checkAuthCallback.beforeSelect<DTO>(
                authReq = authReq!!,
                req = reqSearch,
            )
        }

        val spec = _specification.bySearchDto(reqSearch)
        return _repository.exists(spec)
    }

    /**
     * for-delete
     */
    fun <DTO>delete(
        reqSearch : DTO,
        authReq : AuthReq? = null,
        needAuthCheck : Boolean = false,
    ) : Long {
        if (needAuthCheck){
            checkNotNullData(authReq, "권한정보가 없습니다.")
            checkAuthCallback.beforeDelete<DTO>(
                authReq = authReq!!,
                req = reqSearch,
            )
        }

        val spec = _specification.bySearchDto(reqSearch)
        val cnt = _repository.delete(spec)
        if (cnt > 0) return cnt
        else throw NotFoundException("조건에 맞는 데이터가 없습니다.")
    }

    fun deleteById(
        id : ID,
        authReq : AuthReq? = null,
        needAuthCheck : Boolean = false,
    ) {
        if (needAuthCheck){
            checkNotNullData(authReq, "권한정보가 없습니다.")
            checkAuthCallback.beforeDelete<Any>(
                authReq = authReq!!,
            )
        }

        checkNotNullData(id, "id 값이 없습니다.")
        _repository.deleteById(id!!)
    }

    /**
     * check validation
     */
    fun checkNotNullData(value : Any?, message : String){
        try{ requireNotNull(value) }
        catch (e : IllegalArgumentException){
            throw DataException("DataException : $message")
        }
    }

    fun <DTO>convertToModel(fromSource : DTO) : MODEL {
        return modelConverter.convertTo(fromSource) as MODEL
    }
}