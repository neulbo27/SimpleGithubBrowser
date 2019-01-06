package pe.hankyu.svmgithubbrowser

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import pe.hankyu.svmgithubbrowser.adapter.UserAdapter
import pe.hankyu.svmgithubbrowser.model.UserListModel
import pe.hankyu.svmgithubbrowser.presenter.MainPresenter
import pe.hankyu.svmgithubbrowser.presenter.MainPresenterImpl
import pe.hankyu.svmgithubbrowser.utils.EndlessRecyclerViewScrollListener

class MainActivity : AppCompatActivity(), MainPresenter.View {
    lateinit var userLayoutManager: LinearLayoutManager
    private var userAdapter = UserAdapter(mutableListOf())
    private var lastItemPos = 0

    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userLayoutManager = LinearLayoutManager(this)
        user_recyclerview.run {
            setHasFixedSize(true)
            layoutManager = userLayoutManager
            setOnScrollListener(object: EndlessRecyclerViewScrollListener(userLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    mainPresenter.loadItem(lastItemPos)
                }
            })
        }

        mainPresenter = MainPresenterImpl(this)

        mainPresenter.loadItem()

        user_swipelayout.setOnRefreshListener(this::onRefresh)
    }

    private fun onRefresh() {
        userAdapter = UserAdapter(mutableListOf())
        mainPresenter.loadItem()
    }

    override fun updateView(items: List<UserListModel>) {
        for(item in items) {
            Log.d("MainActivity", item.userId.toString() + " " + item.userName + " " + item.avatarUrl)
        }

        lastItemPos = items.last().userId

        userAdapter.items.addAll(items)
        user_recyclerview.adapter = userAdapter

        if(user_swipelayout.isRefreshing) {
            user_swipelayout.isRefreshing = false
        }

        userAdapter.notifyDataSetChanged()
    }

    override fun addView(items: List<UserListModel>) {
        for(item in items) {
            Log.d("MainActivity", item.userId.toString() + " " + item.userName + " " + item.avatarUrl)
        }

        lastItemPos = items.last().userId

        userAdapter.items.addAll(items)

        if(user_swipelayout.isRefreshing) {
            user_swipelayout.isRefreshing = false
        }

        val curSize = userAdapter.itemCount
        userAdapter.notifyItemRangeInserted(curSize, items.size - 1)
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.destroy()
    }
}
