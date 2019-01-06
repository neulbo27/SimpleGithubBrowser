package pe.hankyu.svmgithubbrowser.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pe.hankyu.svmgithubbrowser.GithubApi
import pe.hankyu.svmgithubbrowser.model.UserListModel

class MainPresenterImpl(val view: MainPresenter.View): MainPresenter {
    override fun loadItem(since: Int) {
        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
            GithubApi.getUserList(since.toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ response: List<UserListModel> ->
                    view.updateView(response)
                }, { error: Throwable ->
                    Log.d("MainActivity", error.localizedMessage)
                    view.makeToast("네트워크 연결을 확인하세요.")
                }))
    }
}