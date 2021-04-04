package com.server.simple.web.dto

import com.server.simple.domain.post.Post
import java.util.*

class PostResponseDto (
    var id: Long = 0,
    var userName: String,
    var title: String,
    var content: String,
    var time: Date
){
    constructor(entity: Post) : this (
        id = entity.id,
        userName = entity.userName,
        title = entity.title,
        content = entity.content,
        time = entity.time
    )

    fun toEntity() : Post {
        return Post (
            id = this.id,
            userName = this.userName,
            title = this.title,
            content = this.content,
            time = this.time
        )
    }


}