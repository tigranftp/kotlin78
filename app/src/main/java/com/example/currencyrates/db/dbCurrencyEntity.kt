package com.example.currencyrates.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(primaryKeys = ["date","Full"])
class DBCurrencyEntity (
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "Full") var Full: Int,
    @ColumnInfo(name = "JSON") var JSON: String
)