package com.csahmed.deardiary.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "node")
data class Node(
    @PrimaryKey @ColumnInfo(name = "nodeId") var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "session_id") var sessionId: String = "123",
)



