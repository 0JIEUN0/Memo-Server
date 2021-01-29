package com.server.simple.service

import com.server.simple.domain.post.Post
import com.server.simple.domain.user.User
import com.server.simple.domain.user.UserRepository
import com.server.simple.web.dto.PostSaveRequestDto
import com.server.simple.web.dto.UserSaveRequestDto
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@RequiredArgsConstructor
@Service
class UserService (val userRepository: UserRepository ){

    @Transactional
    fun save(userSaveRequestDto: UserSaveRequestDto) : Long {
        return userRepository.save(userSaveRequestDto.toEntity()).id
    }

    @Transactional
    fun findUserByName(userName: String) : User {
        return userRepository.findByName(userName)
    }

}