package com.example.a25a_hw1.model

import com.example.a25a_hw1.interfaces.CollisionCallback

data class Obstacle(
    var resID: Int,
    var isVisible: Boolean,
    var type: String,
    var collisionCallback: CollisionCallback
)
