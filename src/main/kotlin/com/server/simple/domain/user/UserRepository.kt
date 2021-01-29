package com.server.simple.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {

    @Query("FROM User WHERE name= ?1")
    fun findByName(userName: String): User
}