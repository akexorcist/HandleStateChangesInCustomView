package com.akexorcist.example.handlestatechangesincustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonLoadData.setOnClickListener {
            simplePostView.setTitle(getString(R.string.sample_title))
            simplePostView.setDescription(getString(R.string.sample_description))
            simplePostView.setDividerColor(R.color.colorAccent)

            inheritedPostView.setTitle(getString(R.string.sample_title))
            inheritedPostView.setDescription(getString(R.string.sample_description))
            inheritedPostView.setDividerColor(R.color.colorAccent)
        }
    }
}
