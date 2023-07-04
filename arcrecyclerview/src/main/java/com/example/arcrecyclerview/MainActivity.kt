package com.example.arcrecyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.arcrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val dataAdapter = DataAdapter { view ->
        ScrollHelper.smoothScrollToTargetView(binding.rvCircle, view)
    }

    private val circleScaleLayoutManager = CircleScaleLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        circleScaleLayoutManager.apply {
            radius = 525
            angleInterval = 20
            centerScale = 1f
            moveSpeed = 0.1f
        }


        binding.rvCircle.layoutManager = circleScaleLayoutManager

        binding.rvCircle.adapter = dataAdapter
    }
}