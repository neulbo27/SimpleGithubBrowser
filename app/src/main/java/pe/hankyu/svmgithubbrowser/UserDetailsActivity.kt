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
    private var detailsAdapter = UserDetailsAdapter(mutableListOf())

    lateinit var detailsPresenter: UserDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        detailsLayoutManager = LinearLayoutManager(this)
        user_details_recyclerview.run {
            setHasFixedSize(true)
            layoutManager = detailsLayoutManager
            setOnScrollListener(object: EndlessRecyclerViewScrollListener(detailsLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {

                }
            })
        }

        var nickName = ""
        intent.extras?.let {
            nickName = it.getString("username", "")
        }

        detailsPresenter = UserDetailsPresenterImpl(this)
        detailsPresenter.loadUserDetails(nickName)
        detailsPresenter.loadUserRepos(nickName)

        detailsAdapter.notifyDataSetChanged()
    }

    override fun updateItem(response: UserDetailsModel) {
        detailsAdapter.items.add(response)
        user_details_recyclerview.adapter = detailsAdapter
    }

    override fun updateItem(response: List<UserDetailsModel>) {
        for(item in response) {
            Log.d("UserDetailsActivity", item.name + " " + item.description)
        }
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsPresenter.onDestroy()
    }
}
