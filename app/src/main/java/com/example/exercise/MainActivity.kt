package com.example.exercise

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.exercise.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val rotateOpen :Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open) }
    private val rotateClose :Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close) }
    private val rotateFrom :Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom) }
    private val rotateTo :Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom) }
    private var click =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.shareFab.setOnClickListener {
            shareFile()
            Toast(this).apply {
                this.duration = Toast.LENGTH_LONG
                view = layoutInflater.inflate(R.layout.toast_layout,findViewById(R.id.toastMain))

            }.show()

        }


    }
    private fun shareFile() {
        setAnimation(click)
        setVisibility(click)
        setClickable(click)
        click = !click
    }

    private fun setClickable(click: Boolean) {
        if (!click){
            binding.copyFab.isClickable=true
            binding.mailFab.isClickable=true
            binding.messageFab.isClickable=true
        }else{
            binding.copyFab.isClickable=false
            binding.mailFab.isClickable=false
            binding.messageFab.isClickable=false
        }

    }

    private fun setVisibility(click:Boolean) {
        if (!click){
            binding.copyFab.visibility = View.VISIBLE
            binding.mailFab.visibility=View.VISIBLE
            binding.messageFab.visibility=View.VISIBLE

        }else{
            binding.copyFab.visibility = View.INVISIBLE
            binding.mailFab.visibility=View.INVISIBLE
            binding.messageFab.visibility=View.INVISIBLE
        }

    }
    private fun setAnimation(click:Boolean) {
        if (!click){
            binding.shareFab.startAnimation(rotateOpen)
            binding.copyFab.startAnimation(rotateFrom)
            binding.mailFab.startAnimation(rotateFrom)
            binding.messageFab.startAnimation(rotateFrom)
        }else{
            binding.shareFab.startAnimation(rotateClose)
            binding.copyFab.startAnimation(rotateTo)
            binding.mailFab.startAnimation(rotateTo)
            binding.messageFab.startAnimation(rotateTo)
        }
    }




}