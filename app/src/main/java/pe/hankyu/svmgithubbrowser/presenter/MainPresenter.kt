package pe.hankyu.svmgithubbrowser.presenter

import pe.hankyu.svmgithubbrowser.model.UserListModel

interface MainPresenter {
    fun loadItem(since: Int)
    fun loadItem()
    fun destroy()

    interface View {
        fun updateView(items: List<UserListModel>)
        fun makeToast(message: String)
        fun addView(items: List<UserListModel>)
    }
}