package com.server.simple.web.dto

import com.server.simple.domain.user.User

class UserSaveRequestDto (
    var id: Long = 0,
    var name: String,
    var postNum: Int
) {

    constructor(entity: User) : this (
        id = entity.id,
        name = entity.name,
        postNum = entity.postNum
    )

    fun toEntity() : User {
        return User(
            id = this.id,
            name = this.name,
            postNum = this.postNum
        )
    }
}