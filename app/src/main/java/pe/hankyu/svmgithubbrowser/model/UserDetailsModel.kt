package pe.hankyu.svmgithubbrowser.model

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
}