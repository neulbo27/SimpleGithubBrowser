package pe.hankyu.svmgithubbrowser.presenter

import pe.hankyu.svmgithubbrowser.model.UserDetailsModel

interface UserDetailsPresenter {
    fun loadUserDetails(userName: String)
    fun loadUserRepos(userName: String)
    fun onDestroy()

    interface View {
        fun updateItem(response: UserDetailsModel)
        fun updateItem(response: List<UserDetailsModel>)
        fun makeToast(message: String)
    }
}