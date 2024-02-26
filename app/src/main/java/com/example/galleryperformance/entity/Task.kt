package com.example.galleryperformance.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo var unknown: Int,
    @ColumnInfo var skim: Int,
    @ColumnInfo var search: Int,
    @ColumnInfo var know: Int,
    @ColumnInfo var startTime: Date,
    @ColumnInfo var nextTime: Date,

    /**
     * [TaskStatus]
     */
    @ColumnInfo var status: Int?,
    @ColumnInfo var wordId: Long?,
    @ColumnInfo var courseId: Long?,
    @Ignore var isSkim: Boolean = false,
    @Ignore var isDefinitionShown: Boolean = false,
) {
    constructor(nextTime: Date) : this(null, 0, 0,0, 0, Date(), nextTime, 0, 1, 1)

    /**
     * Task is used as a key, its hashcode should keep the same, hence, exclude some fields which
     * may change due to user interaction.
     */
    override fun hashCode(): Int {
        return id.hashCode() + wordId.hashCode() + startTime.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Task) return false
        return id == other.id && wordId == other.wordId && startTime == other.startTime
    }


}
