package com.server.simple.component

import com.server.simple.domain.post.Post
import com.server.simple.domain.post.PostRepository
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.*
import javax.annotation.PostConstruct

@Component
class PostConfigurationComponent {

    @Autowired
    private lateinit var postRepository: PostRepository

    @PostConstruct
    fun initPosts() {
        var restTemplate: RestTemplate = RestTemplate()
        val response = restTemplate.getForEntity(URI("https://jsonplaceholder.typicode.com/posts/"), String::class.java)
        //println(response.body)

        val parser: JSONParser = JSONParser()
        val result: JSONArray = parser.parse(response.body) as JSONArray
        println(result.size)

        result.forEach {
            val jsonObject = it as JSONObject
            postRepository.save(
                Post(
                    userName = jsonObject.get("userId").toString(),
                    title = jsonObject.get("title").toString(),
                    content = jsonObject.get("body").toString(),
                    time = Date()
                )
            )
        }
    }

}