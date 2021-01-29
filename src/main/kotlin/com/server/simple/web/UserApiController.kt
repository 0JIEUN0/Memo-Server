package com.server.simple.web

import com.server.simple.service.UserService
import com.server.simple.web.dto.UserResponseDto
import com.server.simple.web.dto.UserSaveRequestDto
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*

@RequiredArgsConstructor
@RestController
class UserApiController (val userService: UserService){
    @PostMapping("/api/v1/saveUser")
    fun save(@RequestBody userSaveRequestDto: UserSaveRequestDto) : Long {
        return userService.save(userSaveRequestDto)
    }

    @GetMapping("/api/v1/findUser/{userName}")
    fun findUserByName(@PathVariable userName: String) : UserResponseDto {
        return userService.findUserByName(userName)
    }

}