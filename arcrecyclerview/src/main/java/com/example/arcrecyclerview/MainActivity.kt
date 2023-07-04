package com.example.arcrecyclerview

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.marginBottom
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.arcrecyclerview.ViewPagerLayoutManager.OnPageChangeListener
import com.example.arcrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val circleScaleLayoutManager = CircleScaleLayoutManager(this)

    private val dataAdapter = DataAdapter { view ->

        circleScaleLayoutManager.onClickItem(view)

        ScrollHelper.smoothScrollToTargetView(binding.rvCircle, view)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        circleScaleLayoutManager.apply {
            radius = 1200
            angleInterval = 7
            centerScale = 1f
            moveSpeed = 0.1f
            circleScaleLayoutManager.enableBringCenterToFront = false
        }

        binding.rvCircle.layoutManager = circleScaleLayoutManager

        binding.rvCircle.adapter = dataAdapter
    }
}