package com.server.simple.domain.user

import javax.persistence.*

@Entity
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = Long.MAX_VALUE,

    @Column(length = 100, nullable = false)
    val name: String,

    var postNum: Int)

{
    fun update(newNum: Int) = this.postNum++
}