package com.example.locamail

data class EmailModel(
    val id: Int,
    val sender: String,
    val receive: String,
    val subject: String,
    val preview: String,
    val content: String,
    val markup: String,
    val important: Boolean,
    val deleted: Boolean,
    val spam: Boolean,
    val sended: Boolean,
    val received: Boolean,
    val date: String,
    val read: Boolean
)
