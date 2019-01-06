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
import java.text.SimpleDateFormat
import java.util.*

class UserDetailsAdapter(var items: MutableList<UserDetailsModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val responseDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.KOREA)
    val usingDateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.user_details_recycler_view, parent, false)
                UserDetailsViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.user_repository_recycler_view, parent, false)
                UserRepositoryViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> 0
            else -> 1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(position) {
            0 -> {
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

            else -> {
                val viewHolder = holder as UserRepositoryViewHolder

                val createdAtDate = responseDateFormat.parse(items[position].createdAt)
                val formedCreatedAtString = usingDateFormat.format(createdAtDate)

                viewHolder.run {
                    repoNameTextView.text = items[position].name
                    repoDescriptionTextView.text = items[position].description
                    repoStarTextView.text = String.format("Star : %d", items[position].star)
                    repoWatcherTextView.text = String.format("Watcher : %d", items[position].watcher)
                    repoCreatedAtTextView.text = formedCreatedAtString
                }
            }
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

    class UserRepositoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val repoNameTextView = itemView.findViewById<TextView>(R.id.user_repository_name)
        val repoDescriptionTextView = itemView.findViewById<TextView>(R.id.user_repository_description)
        val repoStarTextView = itemView.findViewById<TextView>(R.id.user_repository_star)
        val repoWatcherTextView = itemView.findViewById<TextView>(R.id.user_repository_watcher)
        val repoCreatedAtTextView = itemView.findViewById<TextView>(R.id.user_repository_created_at)
    }

}