package pe.hankyu.svmgithubbrowser.presenter

import pe.hankyu.svmgithubbrowser.model.UserDetailsModel

interface UserDetailsPresenter {
    fun loadItem(userName: String)
    fun onDestroy()

    interface View {
        fun updateItem(response: UserDetailsModel)
        fun makeToast(message: String)
    }
}