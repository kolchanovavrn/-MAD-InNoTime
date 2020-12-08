package com.example.innotime.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "timers_table")
data class TimerDbModel (
    @PrimaryKey @ColumnInfo(name="id") val id: Int,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="duration") val duration: Long,
    @ColumnInfo(name = "description") val description: String
): Parcelable