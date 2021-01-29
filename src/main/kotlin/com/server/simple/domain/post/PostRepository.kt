package com.server.simple.domain.post

import com.server.simple.web.dto.PostResponseDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostRepository : JpaRepository<Post, Long>{
    @Query("FROM Post where userName= ?1")
    fun findByName(userName: String) : List<Post>
}