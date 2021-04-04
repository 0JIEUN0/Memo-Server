package com.server.simple.service

import com.server.simple.domain.post.Post
import com.server.simple.domain.post.PostRepository
import com.server.simple.web.dto.PostResponseDto
import com.server.simple.web.dto.PostSaveRequestDto
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.stream.Collector
import java.util.stream.Collectors
import javax.transaction.Transactional

@RequiredArgsConstructor
@Service
class PostService (val postRepository: PostRepository){


    @Transactional
    fun save(fileSaveRequestDto: PostSaveRequestDto) : ResponseEntity<Long> {
        val result = postRepository.save(fileSaveRequestDto.toEntity()).id
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result)
    }

    @Transactional
    fun findByUser(userName: String) : List<PostResponseDto> {
        return postRepository.findByName(userName).stream()
            .map { PostResponseDto(it) }
            .collect(Collectors.toList())
    }

    @Transactional
    fun findAllPosts() : ResponseEntity<List<PostResponseDto>> {
        val result = postRepository.findAll()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                result.stream()
                    .map { PostResponseDto(it) }
                    .collect(Collectors.toList()))
    }

    @Transactional
    fun findAllByTimeAsc() : ResponseEntity<List<PostResponseDto>> {
        val result = postRepository.findAllByOrderByTimeAsc()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                result.stream()
                    .map { PostResponseDto(it) }
                    .collect(Collectors.toList()))
    }


    @Transactional
    fun findAllByTimeDesc() : ResponseEntity<List<PostResponseDto>> {
        val result = postRepository.findAllByOrderByTimeDesc()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                result.stream()
                    .map { PostResponseDto(it) }
                    .collect(Collectors.toList()))
    }

}