package pe.hankyu.svmgithubbrowser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import pe.hankyu.svmgithubbrowser.adapter.UserAdapter
import pe.hankyu.svmgithubbrowser.model.GithubUserModel

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
                val userAdapter = UserAdapter(response)
                user_recyclerview.adapter = userAdapter
            }, { error: Throwable ->
                Log.d("MainActivity", error.localizedMessage)
                Toast.makeText(this, "네트워크 오류입니다.", Toast.LENGTH_SHORT).show();
            }))

        user_swipelayout.setOnRefreshListener(this::onRefresh)
    }

    override fun onResume() {
        super.onResume()

        val mLayoutManager = LinearLayoutManager(this)
        user_recyclerview.layoutManager = mLayoutManager


    }

    private fun onRefresh() {
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(GithubApi.getUserList("46")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe ({ response: List<GithubUserModel> ->
                val userAdapter = UserAdapter(response)
                user_recyclerview.adapter = userAdapter

                user_swipelayout.isRefreshing = false
            }, { error: Throwable ->
                Log.d("MainActivity", error.localizedMessage)
                Toast.makeText(this, "네트워크 오류입니다.", Toast.LENGTH_SHORT).show();

                user_swipelayout.isRefreshing = false
            }))
    }
}
