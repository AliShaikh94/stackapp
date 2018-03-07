package com.alishaikh.wagchallenge.vo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by alishaikh on 3/5/18.
 */
data class User(
        @SerializedName("account_id")
        val accountId: Long,
        @SerializedName("display_name")
        val displayName: String,
        @SerializedName("profile_image")
        val profileImage: String,
        @SerializedName("location")
        val location: String,
        @SerializedName("badge_counts")
        val badgeCounts: Badges,
        val reputation: Long
) : Serializable

data class Badges(
        val gold: Int,
        val silver: Int,
        val bronze: Int
) : Serializable