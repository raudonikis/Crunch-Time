package com.raudonikis.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raudonikis.data.database.daos.ExampleDao
import com.raudonikis.data.database.entities.ExampleEntity

@Database(entities = [ExampleEntity::class], version = 1)
abstract class ExampleDatabase : RoomDatabase() {

    abstract fun exampleDao(): ExampleDao

    companion object {
        const val DATABASE_NAME = "example_db.db"
    }
}