package com.meokq.api.test

import com.meokq.api.core.controller.CheckAuthCallback
import com.meokq.api.core.converter.BaseConverterV2
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.core.service.BaseServiceV2
import com.meokq.api.core.specification.BaseSpecificationV2
import org.springframework.stereotype.Service

@Service
class TestService(
    repository: TestRepository
) : BaseServiceV2<TestModel, String>{
    override var _repository: BaseRepository<TestModel, String> = repository
    override var _specification: BaseSpecificationV2<TestModel> = TestSpecification
    override var checkAuthCallback: CheckAuthCallback = object : CheckAuthCallback {}
    override val modelConverter: BaseConverterV2<*> = BaseConverterV2(TestModel::class)
}