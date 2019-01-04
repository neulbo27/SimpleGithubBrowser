package pe.hankyu.svmgithubbrowser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(GithubApi.getUserList("0")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe ({ response: List<GithubUserModel> ->
                for(item in response) {
                    Log.d("MainActivity", item.userId.toString() + "," + item.userName + ", " + item.avatarUrl)
                }
            }, { error: Throwable ->
                Log.d("MainActivity", error.localizedMessage)
        }))
    }
}
