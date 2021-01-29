package com.server.simple.domain.post

import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.assertThat

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostRepositoryTest {
    @Autowired
    private lateinit var postRepository: PostRepository

    @After
    fun cleanUp() = postRepository.deleteAll()

    @Test
    fun postRepoTest(){
        var userName = "name"
        var title : String = "title"
        var content : String = "content"
        var time: String = "2021"

        postRepository.save(Post(userName = userName, title = title, content = content ,time = time))


        var result: Post = postRepository.findAll().get(0)
        assertThat(result.userName).isEqualTo(userName)
        assertThat(result.title).isEqualTo(title)
        assertThat(result.content).isEqualTo(content)
        assertThat(result.time).isEqualTo(time)

    }
}