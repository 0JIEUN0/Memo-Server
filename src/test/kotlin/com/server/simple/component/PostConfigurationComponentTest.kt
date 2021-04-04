package com.server.simple.component

import com.server.simple.domain.post.PostRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.assertThat

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostConfigurationComponentTest {

    @Autowired
    lateinit var postRepository: PostRepository

    @Test
    fun testInit() {
        //initial data size = 100
        assertThat(postRepository.findAll().size).isEqualTo(100)
    }
}