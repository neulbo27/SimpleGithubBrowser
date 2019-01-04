package pe.hankyu.svmgithubbrowser

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import pe.hankyu.svmgithubbrowser.adapter.UserAdapter
import pe.hankyu.svmgithubbrowser.model.GithubUserModel

class MainActivity : AppCompatActivity() {
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var userLayoutManager: LinearLayoutManager
    lateinit var userAdapter: UserAdapter
    var lastItemPos: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userLayoutManager = LinearLayoutManager(this)
        user_recyclerview.run {
            setHasFixedSize(true)
            layoutManager = userLayoutManager
        }

        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(GithubApi.getUserList("0")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe ({ response: List<GithubUserModel> ->
                lastItemPos = response.last().userId

                userAdapter = UserAdapter(response)
                user_recyclerview.run {
                    adapter = userAdapter
                    addOnScrollListener(createInflateScrollListener())
                }
            }, { error: Throwable ->
                Log.d("MainActivity", error.localizedMessage)
                Toast.makeText(this, "네트워크 오류입니다.", Toast.LENGTH_SHORT).show()
            }))

        user_swipelayout.setOnRefreshListener(this::onRefresh)
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
                Toast.makeText(this, "네트워크 오류입니다.", Toast.LENGTH_SHORT).show()

                user_swipelayout.isRefreshing = false
            }))
    }

    private fun createInflateScrollListener(): InfiniteScrollListener {
        return object: InfiniteScrollListener(1, userLayoutManager) {
            override fun onScrolledToEnd(firstVisibleItemPosition: Int) {
                compositeDisposable = CompositeDisposable()

                compositeDisposable.add(GithubApi.getUserList(lastItemPos.toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe ({ response: List<GithubUserModel> ->
                        userAdapter.items.plus(response)
                        refreshView(user_recyclerview, userAdapter, firstVisibleItemPosition)
                    }, { error: Throwable ->
                        Log.d("MainActivity", error.localizedMessage)
                        Toast.makeText(this@MainActivity, "네트워크 오류입니다.", Toast.LENGTH_SHORT).show()
                    }))
            }
        }
    }
}
