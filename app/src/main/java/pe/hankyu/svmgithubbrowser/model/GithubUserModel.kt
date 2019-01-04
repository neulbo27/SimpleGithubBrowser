package pe.hankyu.svmgithubbrowser.model

import com.google.gson.annotations.SerializedName

class GithubUserModel {
    @SerializedName("id")
    val userId: Long = 0

    @SerializedName("avatar_url")
    val avatarUrl: String = ""

    @SerializedName("login")
    val userName: String = ""
}