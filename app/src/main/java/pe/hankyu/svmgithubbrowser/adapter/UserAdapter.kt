package pe.hankyu.svmgithubbrowser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pe.hankyu.svmgithubbrowser.R
import pe.hankyu.svmgithubbrowser.UserDetailsActivity
import pe.hankyu.svmgithubbrowser.model.UserListModel

class UserAdapter(var items: MutableList<UserListModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_recycler_view, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val userViewHolder = holder as UserViewHolder

        userViewHolder.userIdTextView.text = items[position].userId.toString()
        userViewHolder.userNameTextView.text = items[position].userName
        Picasso.get().load(items[position].avatarUrl).error(R.drawable.ic_launcher_foreground).into(userViewHolder.photo)

        userViewHolder.itemView.setOnClickListener{v ->
            val context = v.context
            val intent = Intent(context, UserDetailsActivity::class.java)

            intent.putExtra("username", userViewHolder.userNameTextView.text.toString())
            context.startActivity(intent)
        }
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val photo = itemView.findViewById<ImageView>(R.id.userPhoto)
        val userIdTextView = itemView.findViewById<TextView>(R.id.userId)
        val userNameTextView = itemView.findViewById<TextView>(R.id.userName)
    }

}