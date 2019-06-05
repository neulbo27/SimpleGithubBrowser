package pe.hankyu.svmgithubbrowser.repo.model

import com.google.gson.annotations.SerializedName

class UserDetailsModel {
    @SerializedName("avatar_url")
    val avatarUrl: String = ""

    @SerializedName("login")
    val nickName: String = ""

    @SerializedName("name")
    val name: String = ""

    @SerializedName("location")
    val location: String = ""

    @SerializedName("company")
    val company: String = ""

    @SerializedName("followers")
    val followers: Int = 0

    @SerializedName("following")
    val following: Int = 0

    @SerializedName("description")
    val description: String = ""

    @SerializedName("stargazers_count")
    val star: Int = 0

    @SerializedName("watchers_count")
    val watcher: Int = 0

    @SerializedName("created_at")
    val createdAt: String = ""
}