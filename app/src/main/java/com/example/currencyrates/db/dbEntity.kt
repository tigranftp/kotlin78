package com.example.currencyrates.db

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.*
import com.example.currencyrates.retrofit.Currency
import com.example.currencyrates.retrofit.CurrencyAPI
import com.example.currencyrates.retrofit.CurrencyByTime
import com.google.gson.Gson
import java.time.LocalDate
import java.util.*


class RoomRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrencyRates(): Currency? {
        val ins = DBInstance.dao.loadNotFull(LocalDate.now().toString()) ?: return null
        return Gson().fromJson(
            ins.JSON,
            Currency::class.java
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun writeCurrencyRates(curRate: Currency) {
        DBInstance.dao.insert(DBCurrencyEntity(LocalDate.now().toString(), 0, Gson().toJson(curRate)))

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrencyRatesTimes(): CurrencyByTime? {
        val ins = DBInstance.dao.loadFull(LocalDate.now().toString()) ?: return null
        return Gson().fromJson(
            ins.JSON,
            CurrencyByTime::class.java
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun writeCurrencyRatesTimes(curRate: CurrencyByTime) {
        DBInstance.dao.insert(DBCurrencyEntity(LocalDate.now().toString(), 1, Gson().toJson(curRate)))

    }
}


fun initRoomRepo(ctx: Context) {
    DBInstance = DBInstanceClass(ctx)
}


lateinit var DBInstance: DBInstanceClass

class DBInstanceClass(ctx: Context) {
    private val db = Room.databaseBuilder(
        ctx,
        AppDatabase::class.java, "listDataBaseTest2"
    ).allowMainThreadQueries().build()

    val dao = db.currencyDao()
}

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM DBCurrencyEntity WHERE date = (:date) and Full = 0")
    fun loadNotFull(date: String): DBCurrencyEntity?

    @Query("SELECT * FROM DBCurrencyEntity WHERE date = (:date) and Full = 1")
    fun loadFull(date: String): DBCurrencyEntity?

    @Insert
    fun insert(DBcur: DBCurrencyEntity): Long
}

@Database(entities = [DBCurrencyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}
