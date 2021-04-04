package com.server.simple.web

import com.server.simple.service.PostService
import com.server.simple.web.dto.PostResponseDto
import com.server.simple.web.dto.PostSaveRequestDto
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
class PostApiController (val postService: PostService){

    @PostMapping("/api/v1/posts")
    fun save(@RequestBody postSaveRequestDto: PostSaveRequestDto) : ResponseEntity<Long>  {
        return postService.save(postSaveRequestDto)
    }

    @GetMapping("/api/v1/posts")
    fun findAllPostsDesc() : ResponseEntity<List<PostResponseDto>> {
        return postService.findAllByTimeDesc()
    }

    @GetMapping("/api/v1/findPosts/{userName}")
    fun findPostByUser(@PathVariable userName: String) : List<PostResponseDto> {
        return postService.findByUser(userName)
    }

    @GetMapping("/api/v2/posts")
    fun findAllPosts() : ResponseEntity<List<PostResponseDto>> {
        return postService.findAllPosts()
    }
}