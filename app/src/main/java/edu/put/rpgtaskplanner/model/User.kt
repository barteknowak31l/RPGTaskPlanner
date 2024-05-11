package edu.put.rpgtaskplanner.model

import java.util.Date

class User {

    var email: String = ""
    var character_id: String = ""
    var easy_task_done: Int = 0
    var medium_task_done: Int = 0
    var hard_task_done: Int = 0
    lateinit var next_shop_refresh: Date
}