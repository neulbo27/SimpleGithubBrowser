package pe.hankyu.svmgithubbrowser.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pe.hankyu.svmgithubbrowser.GithubApi
import pe.hankyu.svmgithubbrowser.model.UserDetailsModel

class UserDetailsPresenterImpl(val view: UserDetailsPresenter.View): UserDetailsPresenter {
    private val compositeDisposable = CompositeDisposable()

    override fun loadUserDetails(userName: String) {
        compositeDisposable.add(
                GithubApi.getUserDetails(userName)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe ({ response: UserDetailsModel ->
                            view.updateItem(response)
                        }, { error: Throwable ->
                            Log.d("MainActivity", error.localizedMessage)
                            view.makeToast("네트워크 연결을 확인하세요.")
                        }))
    }

    override fun loadUserRepos(userName: String) {
        compositeDisposable.add(
                GithubApi.getUserRepos(userName)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe ({ response: List<UserDetailsModel> ->
                            view.updateItem(response)
                        }, { error: Throwable ->
                            Log.d("MainActivity", error.localizedMessage)
                            view.makeToast("네트워크 연결을 확인하세요.")
                        }))
    }

    override fun loadUserRepos(userName: String, page: Int) {
        compositeDisposable.add(
                GithubApi.getUserRepos(userName, page)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe ({ response: List<UserDetailsModel> ->
                            view.addItem(response)
                        }, { error: Throwable ->
                            Log.d("MainActivity", error.localizedMessage)
                            view.makeToast("네트워크 연결을 확인하세요.")
                        }))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}