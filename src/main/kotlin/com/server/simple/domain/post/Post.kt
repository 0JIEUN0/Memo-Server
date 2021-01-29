package com.server.simple.domain.post

import javax.persistence.*

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = Long.MAX_VALUE,

    @Column(length = 100, nullable = false)
    val userName: String,

    @Column(length = 100, nullable = false)
    var title : String,

    @Column(length = 500, nullable = false)
    var content : String,

    var time: String

            ){
}