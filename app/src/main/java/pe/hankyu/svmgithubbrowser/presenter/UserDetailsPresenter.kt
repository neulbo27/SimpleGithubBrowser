package pe.hankyu.svmgithubbrowser.presenter

import pe.hankyu.svmgithubbrowser.model.UserDetailsModel

interface UserDetailsPresenter {
    fun loadUserDetails(userName: String)
    fun loadUserRepos(userName: String)
    fun loadUserRepos(userName: String, page: Int)
    fun onDestroy()

    interface View {
        fun updateItem(items: UserDetailsModel)
        fun updateItem(items: List<UserDetailsModel>)
        fun addItem(items: List<UserDetailsModel>)
        fun makeToast(message: String)
    }
}