package com.server.simple.web

import com.server.simple.domain.user.User
import com.server.simple.domain.user.UserRepository
import com.server.simple.service.UserService
import com.server.simple.web.dto.UserResponseDto
import com.server.simple.web.dto.UserSaveRequestDto
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
class UserApiControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var userRepository: UserRepository

    @After
    fun cleanup() = userRepository.deleteAll()

    @Test
    fun testSave() {
        val name = "name"
        val postNum = 3

        val requestDto = UserSaveRequestDto(name = name, postNum = postNum)
        var url = "http://localhost:$port/api/v1/saveUser"

        val responseEntity: ResponseEntity<Long> = restTemplate.postForEntity(url, requestDto, Long::class.java)

        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).isGreaterThan(0L)

        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo(name)
        assertThat(result.postNum).isEqualTo(postNum)
    }

    @Test
    fun testFindUser() {
        val name = "name"
        val postNum = 3

        userRepository.save(User(name = name, postNum = postNum))
        userRepository.save(User(name = "name2", postNum = postNum))
        userRepository.save(User(name = "name3", postNum = postNum))

        var url = "http://localhost:$port/api/v1/findUser/$name"

        val responseEntity = restTemplate.getForEntity(url, UserResponseDto::class.java)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)

        val result = responseEntity.body
        assertThat(result.name).isEqualTo(name)
        assertThat(result.postNum).isEqualTo(postNum)


    }
}