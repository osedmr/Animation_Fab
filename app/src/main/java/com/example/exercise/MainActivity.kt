package com.example.exercise

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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


        binding.toActivity2.setOnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }

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


        binding.permission.setOnClickListener {
            requestPermission()
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


    private fun readImagePermission():Boolean{
        return ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
    }
    private fun coarseLocationPermission():Boolean{
        return ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    private fun fineLocationPermission():Boolean{
       return ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        var permissionList = mutableListOf<String>()
        if (!readImagePermission()){
            permissionList.add(Manifest.permission.READ_MEDIA_IMAGES)
        }
        if (!coarseLocationPermission()){
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!fineLocationPermission()){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (permissionList.isNotEmpty()){
            ActivityCompat.requestPermissions(this,permissionList.toTypedArray(),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"${permissions[i]} izin verildi",Toast.LENGTH_SHORT).show()
                    Log.d("osman","${permissions[i]} izin verildi")
                }else{
                    Toast.makeText(this,"${permissions[i]} izin verilmedi",Toast.LENGTH_SHORT).show()
                    Log.d("osman","${permissions[i]} izin verilmedi")
                }
            }
        }
    }
}