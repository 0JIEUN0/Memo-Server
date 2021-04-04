package com.server.simple.web

import com.server.simple.domain.post.Post
import com.server.simple.domain.post.PostRepository
import com.server.simple.web.dto.PostResponseDto
import com.server.simple.web.dto.PostSaveRequestDto
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
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.junit.Before
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.WebApplicationContext
import java.net.URI
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private lateinit var mockMvc : MockMvc

    @Before
    fun init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @After
    fun clearUp() = postRepository.deleteAll()

    @Test
    fun testSavePost() {
        val userName = "user1"
        val title = "title"
        val content = "content"
        val time = Date()

        val requestDto = PostSaveRequestDto (
            userName = userName,
            title = title,
            content = content,
            time = time
        )
        val url = "http://localhost:$port/api/v1/posts"

        //api request
        val responseEntity : ResponseEntity<Long> = restTemplate.postForEntity(url, requestDto, Long::class.java)

        val result: Post = postRepository.findByName(userName)[0]

        //Assert
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).isGreaterThan(0L)
        assertThat(result.userName).isEqualTo(userName)
        assertThat(result.title).isEqualTo(title)
        assertThat(result.content).isEqualTo(content)
    }


    @Test
    fun testFindByName() {
        val num = 3
        val userName = "user1"
        for(i in 1..num){
            postRepository.save(Post(userName = userName, title = "title", content = "content", time = Date()))
        }
        postRepository.save(Post(userName = "user2", title = "t", content = "ct", time = Date()))

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

    @Test
    fun testFindAll() {
        postRepository.save(Post(userName = "user2", title = "t", content = "ct", time = Date()))
        postRepository.save(Post(userName = "user2", title = "t", content = "ct", time = Date()))

        val url = "http://localhost:$port/api/v1/posts"
        var responseEntity : ResponseEntity<Array<PostResponseDto>> = restTemplate.getForEntity(url, Array<PostResponseDto>::class.java)

        /*
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/posts")
        ).andExpect{ status(HttpStatus.OK) }
            .andDo(MockMvcResultHandlers.print())
            .andDo{
                assertThat(it.response.status).isEqualTo(HttpStatus.OK.value())
                assertThat(it.response.to)
            }

         */

        assertThat(responseEntity.body.size).isEqualTo(2)


        val results : Array<PostResponseDto> = responseEntity.body

        assertThat(results.size).isEqualTo(2)
        results.forEach{
            assertThat(it.userName).isEqualTo("user2")
        }

    }

}