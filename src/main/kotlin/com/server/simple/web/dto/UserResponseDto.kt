package com.server.simple.web.dto

import com.server.simple.domain.user.User

class UserResponseDto(
    var id: Long = 0,
    var name: String,
    var postNum: Int
) {

    fun toEntity() : User {
        return User(
            id = this.id,
            name = this.name,
            postNum = this.postNum
        )
    }
}