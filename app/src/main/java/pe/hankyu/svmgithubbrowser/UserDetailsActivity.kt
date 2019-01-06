package pe.hankyu.svmgithubbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_user_details.*
import pe.hankyu.svmgithubbrowser.adapter.UserDetailsAdapter
import pe.hankyu.svmgithubbrowser.model.UserDetailsModel
import pe.hankyu.svmgithubbrowser.presenter.UserDetailsPresenter
import pe.hankyu.svmgithubbrowser.presenter.UserDetailsPresenterImpl
import pe.hankyu.svmgithubbrowser.utils.EndlessRecyclerViewScrollListener

class UserDetailsActivity : AppCompatActivity(), UserDetailsPresenter.View {
    lateinit var detailsLayoutManager: LinearLayoutManager
    lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener
    lateinit var nickName: String
    private var detailsAdapter = UserDetailsAdapter(mutableListOf())

    private lateinit var detailsPresenter: UserDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        detailsLayoutManager = LinearLayoutManager(this)

        intent.extras?.let {
            nickName = it.getString("username", "")
        }

        detailsPresenter = UserDetailsPresenterImpl(this)
        detailsPresenter.loadUserDetails(nickName)
        detailsPresenter.loadUserRepos(nickName)

        endlessRecyclerViewScrollListener = object: EndlessRecyclerViewScrollListener(detailsLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if(page != 1) {
                    detailsPresenter.loadUserRepos(nickName, page)
                }
            }
        }

        user_details_recyclerview.run {
            setHasFixedSize(true)
            layoutManager = detailsLayoutManager
            setOnScrollListener(endlessRecyclerViewScrollListener)
        }

        user_details_swipelayout.setOnRefreshListener(this::onRefresh)
    }

    private fun onRefresh() {
        endlessRecyclerViewScrollListener.resetState()

        detailsPresenter.loadUserDetails(nickName)
        detailsPresenter.loadUserRepos(nickName)
    }

    override fun updateItem(items: UserDetailsModel) {
        detailsAdapter.items.add(items)
        user_details_recyclerview.adapter = detailsAdapter
    }
/*override fun updateItem(items: List<UserDetailsModel>) {
        detailsAdapter.items.addAll(items)*/

    override fun updateItem(items: List<UserDetailsModel>) {
        detailsAdapter.items.addAll(items)

        if(user_details_swipelayout.isRefreshing) {
            user_details_swipelayout.isRefreshing = false
        }
        detailsAdapter.notifyDataSetChanged()
    }

    override fun addItem(items: List<UserDetailsModel>) {
        for(item in items) {
            Log.d("UserDetailsActivity", item.name + " " + item.description)
        }
        detailsAdapter.items.addAll(items)

        val curSize = detailsAdapter.itemCount
        detailsAdapter.notifyItemRangeInserted(curSize, items.size)
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsPresenter.onDestroy()
    }
}
