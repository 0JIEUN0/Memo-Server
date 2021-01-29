package com.server.simple.domain.user

import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.assertThat

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRepositoryTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @After
    fun cleanUp() = userRepository.deleteAll()

    @Test
    fun userRepoTest(){
        val name = "testName"
        val num = 2

        userRepository.save(User(name = name, postNum = num))

        var result: User = userRepository.findAll().get(0)
        assertThat(result.name).isEqualTo(name)
        assertThat(result.postNum).isEqualTo(num)
    }
}