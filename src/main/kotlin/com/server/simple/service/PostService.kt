package com.server.simple.service

import com.server.simple.domain.post.Post
import com.server.simple.domain.post.PostRepository
import com.server.simple.web.dto.PostResponseDto
import com.server.simple.web.dto.PostSaveRequestDto
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.util.stream.Collector
import java.util.stream.Collectors
import javax.transaction.Transactional

@RequiredArgsConstructor
@Service
class PostService (val postRepository: PostRepository){


    @Transactional
    fun save(fileSaveRequestDto: PostSaveRequestDto) : Long {
        return postRepository.save(fileSaveRequestDto.toEntity()).id
    }

    @Transactional
    fun findByUser(userName: String) : List<PostResponseDto> {
        return postRepository.findByName(userName).stream()
            .map { PostResponseDto(it) }
            .collect(Collectors.toList())
    }

}