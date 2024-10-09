package com.example.exercise

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.exercise.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


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



        binding.shareFab.setOnClickListener {
            shareFile()
            Toast(this).apply {
                this.duration = Toast.LENGTH_LONG
                view = layoutInflater.inflate(R.layout.toast_layout,findViewById(R.id.toastMain))

            }.show()

        }
        binding.messageFab.setOnClickListener {
            Snackbar.make(it,"silmek istedinize eminmisiniz",Snackbar.LENGTH_INDEFINITE).apply {
                this.setBackgroundTint(resources.getColor(R.color.black))
                this.setAction("He aq") {


                }
            }.show()
        }
        binding.alertDialog.setOnClickListener {
            alertDialog()
        }
        binding.alertDialog2.setOnClickListener {
            setItemDialog()
        }
        binding.singleChoiceDialog.setOnClickListener {
            singleChoiceDialog()
        }
        binding.alertDialog4.setOnClickListener {
            multiConfirmDialog()
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
    private fun alertDialog(){
        val alertDialog = MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
            .setTitle("Alert Dialog")
            .setMessage("Silmek işlemini onaylıyor musunuz")
            .setCancelable(false)
            .setIcon(R.drawable.fav_icon_foreground)
            .setPositiveButton("Evet"){_,_ ->
                Toast(this).apply {
                    this.duration=Toast.LENGTH_SHORT
                    view=layoutInflater.inflate(R.layout.toast_layout,findViewById(R.id.toastMain))
                    this.show()
                }
            }
            .setNegativeButton("Hayır"){_,_ ->
                Toast.makeText(this,"Silinmedi",Toast.LENGTH_LONG).show()
            }
            .create()
        alertDialog.show()

    }
    val list= arrayOf("item1","item2","item3","item3")

    private fun setItemDialog(){
        val alertDialog=MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
            .setTitle("Başlık Kısmı")
            .setItems(list){_,which->
                Toast.makeText(this,list[which],Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Tamam"){_,_->
                Toast.makeText(this,"Tamamlandı",Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("İptal"){_,_->
                Toast.makeText(this,"İptal Edildi",Toast.LENGTH_SHORT).show()
            }

            .create()
        alertDialog.show()
    }

    private fun singleChoiceDialog(){
        val alertDialog = MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
            .setTitle("Başlık Kısmı")
            .setSingleChoiceItems(list,0){_,which->
                Toast.makeText(this,list[which],Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Tamam"){_,_->
                Toast.makeText(this,"Tamamlandı",Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("İptal"){_,_->
                Toast.makeText(this,"İptal Edildi",Toast.LENGTH_SHORT).show()
            }
            .create()
            alertDialog.show()
    }
    private fun multiConfirmDialog(){
        val alertDialog=MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
            .setTitle("Başlık Kısmı")
            .setMultiChoiceItems(list,booleanArrayOf(false,false,false,false)){_,which,isChecked->
                Toast.makeText(this,list[which],Toast.LENGTH_SHORT).show()

            }
            .setPositiveButton("Tamam"){_,_->
                Toast.makeText(this,"Tamamlandı",Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("İptal"){_,_->
                Toast.makeText(this,"İptal Edildi",Toast.LENGTH_SHORT).show()
            }
            .create()
        alertDialog.show()
    }
}