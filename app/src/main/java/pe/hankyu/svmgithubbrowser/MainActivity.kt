package pe.hankyu.svmgithubbrowser

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pe.hankyu.svmgithubbrowser.adapter.UserAdapter
import pe.hankyu.svmgithubbrowser.model.UserListModel
import pe.hankyu.svmgithubbrowser.presenter.MainPresenter
import pe.hankyu.svmgithubbrowser.presenter.MainPresenterImpl

class MainActivity : AppCompatActivity(), MainPresenter.View {
    lateinit var userLayoutManager: LinearLayoutManager
    private lateinit var userAdapter: UserAdapter
    var lastItemPos: Int = 0

    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userLayoutManager = LinearLayoutManager(this)
        user_recyclerview.run {
            setHasFixedSize(true)
            layoutManager = userLayoutManager
        }

        mainPresenter = MainPresenterImpl(this)

        mainPresenter.loadItem(0)
        user_swipelayout.setOnRefreshListener(this::onRefresh)
    }

    private fun onRefresh() {
        mainPresenter.loadItem(0)
    }

    override fun updateView(items: List<UserListModel>) {
        lastItemPos = items.last().userId
        for(item in items) {
            Log.d("MainActivity", item.userId.toString() + " " + item.userName + " " + item.avatarUrl)
        }

        userAdapter = UserAdapter(items)
        user_recyclerview.adapter = userAdapter
        userAdapter.notifyDataSetChanged()

        if(user_swipelayout.isRefreshing) {
            user_swipelayout.isRefreshing = false
        }
    }
}
