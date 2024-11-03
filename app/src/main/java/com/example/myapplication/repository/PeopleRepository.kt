package com.example.myapplication.repository

import androidx.room.PrimaryKey
import com.example.myapplication.database.PeopleDao
import com.example.myapplication.database.PeopleEntity
import com.example.myapplication.datastore.QueryDatastore
import com.example.myapplication.network.ApiService
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(
    private val peopleDao: PeopleDao,
    private val apiService : ApiService,
    private val queryDatastore: QueryDatastore
) {
    fun getPeople(query: String) : Flow<List<PeopleEntity>> = peopleDao.getPeople("%$query%")

    val query : Flow<String?> = queryDatastore.query

    suspend fun queryPeople(query: String) {
        runCatching {
            queryDatastore.updateQuery(query)
            apiService.retrievePeople(query)
        }.fold(
            onSuccess = {response ->
                if (response.result?.isNotEmpty() == true) {
                    peopleDao.upsert(
                        *(response.result.map {people ->
                            PeopleEntity(
                                uid = people.uid.toInt(),
                                name = people.properties.name,
                                mass = people.properties.mass,
                                height = people.properties.height,
                                birthYear = people.properties.birthYear
                            )
                        }.toTypedArray())
                    )
                }
            },
            onFailure = {
                Timber.e("queryPeople failed")
            }
        )
    }
}