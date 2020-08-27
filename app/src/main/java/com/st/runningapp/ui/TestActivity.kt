package com.st.runningapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.st.runningapp.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_test)

        button.setOnClickListener {
            Toast.makeText(this, "bottom button", Toast.LENGTH_SHORT).show()
        }

        status_bar_button.setOnClickListener {
            Toast.makeText(this, "status bar button", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
//        View.SYSTEM_UI_FLAG_LOW_PROFILE   for dimming status / navigation bar colors
//        View.SYSTEM_UI_FLAG_LAYOUT_STABLE You may also need to use to help your app maintain a stable layout.
//        View.SYSTEM_UI_FLAG_FULLSCREEN   hiding status bar
//        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN   On Android 4.1 and higher, you can set your application's content to appear behind the status bar, so that the content doesn't resize as the status bar hides and shows.
//        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION   hiding navigation bar https://developer.android.com/training/system-ui/navigation#40
//        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION   Make Content Appear Behind the Navigation Bar https://developer.android.com/training/system-ui/navigation#behind

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
}