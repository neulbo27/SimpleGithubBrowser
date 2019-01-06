package pe.hankyu.svmgithubbrowser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pe.hankyu.svmgithubbrowser.R
import pe.hankyu.svmgithubbrowser.model.UserDetailsModel

class UserDetailsAdapter(var items: MutableList<UserDetailsModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_details_recycler_view, parent, false)
        return UserDetailsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as UserDetailsViewHolder

        viewHolder.run {
            userNickTextView.text = items[position].nickName
            userNameTextView.text = items[position].name
            userLocationTextView.text = items[position].location
            userCompanyTextView.text = items[position].company
            user_details_followers.text = String.format("followers : %d", items[position].followers)
            user_details_following.text = String.format("following : %d", items[position].following)

            Picasso.get().load(items[position].avatarUrl).error(R.drawable.ic_launcher_foreground).into(viewHolder.photo)
        }
    }

    class UserDetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val photo = itemView.findViewById<ImageView>(R.id.user_details_photo)
        val userNickTextView = itemView.findViewById<TextView>(R.id.user_details_nickname)
        val userNameTextView = itemView.findViewById<TextView>(R.id.user_details_realname)
        val userLocationTextView = itemView.findViewById<TextView>(R.id.user_details_location)
        val userCompanyTextView = itemView.findViewById<TextView>(R.id.user_details_company)
        val user_details_followers = itemView.findViewById<TextView>(R.id.user_details_followers)
        val user_details_following = itemView.findViewById<TextView>(R.id.user_details_following)
    }

}