package com.alishaikh.wagchallenge.vo

/**
 * Created by alishaikh on 3/5/18.
 */
data class User(
        val accountId: Int,
        val displayName: String,
        val profileImage: String,
        val badgeCounts: List<Badges>,
        val reputation: Long
)

data class Badges(
        val gold: Int,
        val silver: Int,
        val bronze: Int
)