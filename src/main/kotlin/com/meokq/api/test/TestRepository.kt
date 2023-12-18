package com.meokq.api.test

import com.meokq.api.core.repository.BaseRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
interface TestRepository : BaseRepository<TestModel,String> {
}