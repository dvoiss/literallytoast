package com.dvoiss.literallytoastdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dvoiss.literallytoast.LitToast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.show_toast_button)
                .setOnClickListener {
                    LitToast.create(this, R.string.toast_demo_text, 1000)
                            .setPlayToasterSound(true)
                            .show()
                }
    }

}
