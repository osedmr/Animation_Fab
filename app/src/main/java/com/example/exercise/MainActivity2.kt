package com.example.exercise

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.exercise.databinding.ActivityMain2Binding


//burada kullanıcıdan izin istemek için bir fonksiyon yazdık
class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private var imageUri: Uri? = null //Uniform Resource Identifier (URI)
    private var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.button.setOnClickListener {
            requestPermission()

        }
    }


    private fun readImagePermission():Boolean{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
        }else{
            ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }


    }

    private fun requestPermission(){
        val permissionList = mutableListOf<String>()
        if (!readImagePermission()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
               permissionList.add(Manifest.permission.READ_MEDIA_IMAGES)
            }else{
                permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            val galeryIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeryIntent,1)
        }

        if (permissionList.isNotEmpty()){
            ActivityCompat.requestPermissions(this,permissionList.toTypedArray(),0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater :MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.camera -> { cameraRequestPermission() }
            R.id.profile -> {Toast.makeText(this,"profile",Toast.LENGTH_SHORT).show()}
            R.id.setting -> {Toast.makeText(this,"setting",Toast.LENGTH_SHORT).show()}
        }
        return super.onOptionsItemSelected(item)
    }


    private fun cameraPermission():Boolean{
        return ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
    private fun cameraRequestPermission(){
        val permissionList = mutableListOf<String>()
        if (!cameraPermission()){
            permissionList.add(Manifest.permission.CAMERA)
        }else{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent,1)
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
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("osman","${permissions[0]}izin verildi")
                val galeryIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeryIntent,1)
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent,1)
            }else{
                Log.d("osman","${permissions[0]}izin verilmedi")

            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1 && data != null){

            // BURADA DİREK KAMERADAN FOTO ÇEKİP ALDIGIMIZ YONTEM
            val imageBitmap = data.extras?.get("data") as? Bitmap
            binding.imageView2.setImageBitmap(imageBitmap)


            /*
            BURADA GALERİYE ERİŞİP ORDAN FOTOGRAF ALMAK İÇİN KULLANILAN YÖNTEM
            imageUri = data.data
            // bunu Bitmap dönüştürücez ve bunun için 2 yöntem var sdk>28 iseİmageDecoder yoksa MediaStore
            if (Build.VERSION.SDK_INT >= 28){
                val source = ImageDecoder.createSource(this.contentResolver,imageUri!!)
                imageBitmap = ImageDecoder.decodeBitmap(source)
                binding.imageView2.setImageBitmap(imageBitmap)
            }else{
                imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,imageUri)
                binding.imageView2.setImageBitmap(imageBitmap)
            } */
        }
    }
}