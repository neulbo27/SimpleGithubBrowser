package pe.hankyu.svmgithubbrowser.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import pe.hankyu.svmgithubbrowser.model.GithubUserModel

class UserAdapter(val items: List<GithubUserModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val 
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
    }

}