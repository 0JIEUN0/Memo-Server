package com.server.simple.web

import com.server.simple.service.PostService
import com.server.simple.web.dto.PostResponseDto
import com.server.simple.web.dto.PostSaveRequestDto
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*

@RequiredArgsConstructor
@RestController
class PostApiController (val postService: PostService){

    @PostMapping("/api/v1/post")
    fun save(@RequestBody postSaveRequestDto: PostSaveRequestDto) : Long  {
        return postService.save(postSaveRequestDto)
    }

    @GetMapping("/api/v1/findPosts/{userName}")
    fun findPostByUser(@PathVariable userName: String) : List<PostResponseDto> {
        return postService.findByUser(userName)
    }

}