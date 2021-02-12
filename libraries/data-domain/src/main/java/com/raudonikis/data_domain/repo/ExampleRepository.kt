package com.raudonikis.data_domain.repo

import com.raudonikis.data.database.daos.ExampleDao
import com.raudonikis.network.igdb.IgdbApi
import javax.inject.Inject

class ExampleRepository @Inject constructor(
    private val exampleDao: ExampleDao,
    private val exampleApi: IgdbApi
) {

    /*fun getAllExamples(): Flow<List<ExampleModel>> {
        return exampleDao.getAllExamples().map { ExampleModelMapper.fromExampleEntityList(it) }
    }*/

    /*suspend fun fetchNewExample(): ExampleModel {
        return ExampleModelMapper.fromExampleResponse(exampleApi.example(name = "name"))
    }*/
}