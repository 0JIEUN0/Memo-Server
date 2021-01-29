package com.server.simple.web

import com.server.simple.domain.post.Post
import com.server.simple.domain.post.PostRepository
import com.server.simple.service.PostService
import com.server.simple.web.dto.PostResponseDto
import com.server.simple.web.dto.PostSaveRequestDto
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.HttpStatus


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var postRepository: PostRepository

    @After
    fun clearUp() = postRepository.deleteAll()

    @Test
    fun testSavePost() {
        val userName = "user1"
        val title = "title"
        val content = "content"
        val time = "time"

        val requestDto = PostSaveRequestDto (
            userName = userName,
            title = title,
            content = content,
            time = time

        )
        val url = "http://localhost:$port/api/v1/post"

        //api request
        val responseEntity : ResponseEntity<Long> = restTemplate.postForEntity(url, requestDto, Long::class.java)

        val result: Post = postRepository.findAll().get(0)

        //Assert
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).isGreaterThan(0L)
        assertThat(result.userName).isEqualTo(userName)
        assertThat(result.title).isEqualTo(title)
        assertThat(result.content).isEqualTo(content)
        assertThat(result.time).isEqualTo(time)
    }


    @Test
    fun testFindByName() {
        val num = 3
        val userName = "user1"
        for(i in 1..num){
            postRepository.save(Post(userName = userName, title = "title", content = "content", time = "time"))
        }
        postRepository.save(Post(userName = "user2", title = "t", content = "ct", time = "ti"))

        val url = "http://localhost:$port/api/v1/findPosts/$userName"
        var responseEntity : ResponseEntity<Array<PostResponseDto>> = restTemplate.getForEntity(url, Array<PostResponseDto>::class.java)

        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body.size).isEqualTo(num)


        val results : Array<PostResponseDto> = responseEntity.body

        results.forEach{
            assertThat(it.userName).isEqualTo(userName)
            assertThat(it.title).isEqualTo("title")
            assertThat(it.content).isEqualTo("content")
            assertThat(it.time).isEqualTo("time")
        }

    }
}