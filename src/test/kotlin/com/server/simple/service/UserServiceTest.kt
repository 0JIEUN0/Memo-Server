package com.server.simple.service

import com.server.simple.domain.user.UserRepository
import com.server.simple.web.dto.UserSaveRequestDto
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.assertThat

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var userService: UserService

    @After
    fun cleanup() = userRepository.deleteAll()

    @Test
    fun testSave() {
        val name = "name"
        val postNum = 3

        val id = userService.save(UserSaveRequestDto(name = name, postNum = postNum))

        val result = userRepository.findAll()[0]

        assertThat(result.name).isEqualTo(name)
        assertThat(result.postNum).isEqualTo(postNum)

    }

    @Test
    fun testFindUser() {
        val name = "name"
        val postNum = 3

        val id1 = userService.save(UserSaveRequestDto(name = name, postNum = postNum))
        val id2 = userService.save(UserSaveRequestDto(name = "name2", postNum = postNum))
        val id3 = userService.save(UserSaveRequestDto(name = "name3", postNum = postNum))

        val result = userService.findUserByName(name)
        assertThat(result.name).isEqualTo(name)
        assertThat(result.postNum).isEqualTo(postNum)


    }
}