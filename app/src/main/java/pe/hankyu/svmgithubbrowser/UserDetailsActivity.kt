package pe.hankyu.svmgithubbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class UserDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        Toast.makeText(this, intent.extras?.getString("username"), Toast.LENGTH_SHORT).show()
    }
}
