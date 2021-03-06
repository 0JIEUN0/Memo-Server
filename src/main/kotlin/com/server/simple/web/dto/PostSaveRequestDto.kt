package com.server.simple.web.dto

import com.server.simple.domain.post.Post
import java.util.*

class PostSaveRequestDto (
    var id: Long = 0,
    var userName: String,
    var title: String,
    var content: String,
    var time: Date
){

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