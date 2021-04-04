package com.server.simple.service

import com.server.simple.domain.post.Post
import com.server.simple.domain.post.PostRepository
import com.server.simple.web.dto.PostResponseDto
import com.server.simple.web.dto.PostSaveRequestDto
import org.junit.After
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.ResponseEntity
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostServiceTest {
    @Autowired
    private lateinit var postRepository: PostRepository
    @Autowired
    private lateinit var postService: PostService

    @After
    fun clearUp() = postRepository.deleteAll()

    @Test
    fun testSave(){
        val userName = "user1"
        val title = "title"
        val content = "content"
        val time = Date()

        val id = postService.save(PostSaveRequestDto(userName = userName, title = title, content = content, time = time)).body

        val result: Post = postRepository.findById(id).get()

        //Assert
        assertThat(result.userName).isEqualTo(userName)
        assertThat(result.title).isEqualTo(title)
        assertThat(result.content).isEqualTo(content)
        assertThat(result.time).isEqualTo(time)

    }

    @Test
    fun testFindByName() {
        val num = 3
        val userName = "user1"
        var ids = mutableListOf<Long>()
        for(i in 1..num){
             val id = postService.save(PostSaveRequestDto(userName = userName, title = "title", content = "content", time = Date())).body
            ids.add(id)
        }
        postService.save(PostSaveRequestDto(userName = "user2", title = "t", content = "ct", time = Date()))

        val results : List<PostResponseDto> = postService.findByUser(userName)

        for(i in 1..results.size){
            assertThat(results[i-1].userName).isEqualTo(userName)
            assertThat(results[i-1].title).isEqualTo("title")
        }
    }

    @Test
    fun testFindAll() {
        postService.save(PostSaveRequestDto(userName = "user2", title = "t", content = "ct", time = Date()))
        postService.save(PostSaveRequestDto(userName = "user2", title = "t", content = "ct", time = Date()))

        val results: ResponseEntity<List<PostResponseDto>> = postService.findAllPosts()
        assertThat(results.body.size).isEqualTo(2)
        for(i in 1..results.body.size){
            assertThat(results.body[i-1].userName).isEqualTo("user2")
        }

    }

    @Test
    fun testFindTimeAsc() {
        postService.save(PostSaveRequestDto(userName = "user2", title = "first", content = "ct", time = Date()))
        postService.save(PostSaveRequestDto(userName = "user2", title = "second", content = "ct", time = Date()))
        postService.save(PostSaveRequestDto(userName = "user2", title = "third", content = "ct", time = Date()))

        val results: List<PostResponseDto> = postService.findAllByTimeAsc().body
        assertThat(results.size).isEqualTo(3)
        assertThat(results[0].title).isEqualTo("first")
        assertThat(results[1].title).isEqualTo("second")
        assertThat(results[2].title).isEqualTo("third")
    }

    @Test
    fun testFindTimeDesc() {
        postService.save(PostSaveRequestDto(userName = "user2", title = "first", content = "ct", time = Date()))
        postService.save(PostSaveRequestDto(userName = "user2", title = "second", content = "ct", time = Date()))
        postService.save(PostSaveRequestDto(userName = "user2", title = "third", content = "ct", time = Date()))

        val results: List<PostResponseDto> = postService.findAllByTimeDesc().body
        assertThat(results.size).isEqualTo(3)
        assertThat(results[0].title).isEqualTo("third")
        assertThat(results[1].title).isEqualTo("second")
        assertThat(results[2].title).isEqualTo("first")
    }

}