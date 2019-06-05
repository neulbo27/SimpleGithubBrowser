package pe.hankyu.svmgithubbrowser.repo.model

import com.google.gson.annotations.SerializedName

class UserListModel {
    @SerializedName("id")
    val userId: Int = 0

    @SerializedName("avatar_url")
    val avatarUrl: String = ""

    @SerializedName("login")
    val userName: String = ""
}