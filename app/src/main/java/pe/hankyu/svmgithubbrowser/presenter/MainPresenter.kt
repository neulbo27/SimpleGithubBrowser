package pe.hankyu.svmgithubbrowser.presenter

import pe.hankyu.svmgithubbrowser.model.UserListModel

interface MainPresenter {
    fun loadItem(since: Int)

    interface View {
        fun updateView(items: List<UserListModel>)
        fun makeToast(message: String)
    }
}