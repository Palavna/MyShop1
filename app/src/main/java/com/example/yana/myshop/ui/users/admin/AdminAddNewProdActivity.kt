package com.example.yana.myshop.ui.users.admin

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.yana.myshop.R
import com.example.yana.myshop.databinding.ActivityAdminAddNewProdBinding
import com.example.yana.myshop.databinding.ActivityMainBinding
import com.example.yana.myshop.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.MONTH
import java.util.Calendar.getInstance
import kotlin.collections.HashMap

class AdminAddNewProdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminAddNewProdBinding
    private var categoryName: String? = null
    private var imageUri: Uri? = null
    private var saveCurrentDate: String? = null
    private var saveCurrentTime: String? = null
    private var prodRandomKey: String? = null
    private lateinit var prodImageRef: StorageReference
    private var downloadImageU: String? = null
    private lateinit var productsRef: DatabaseReference
    private lateinit var loadingBar: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddNewProdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingBar = ProgressDialog(this)
        setupListeners()

        prodImageRef = FirebaseStorage.getInstance().reference.child("Product Image")
        prodImageRef = FirebaseStorage.getInstance().reference.child("Products")
        categoryName = intent.extras?.get("category").toString()
    }

    private fun setupListeners() {
        binding.selectProdImage.setOnClickListener {
            openGallery()
        }

        binding.btnAddNewProd.setOnClickListener {
            validateProdData()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            binding.selectProdImage.setImageURI(imageUri)
        }
    }

    private fun openGallery() {
        val intent = Intent.getIntent("")
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    private fun validateProdData() {
        binding.productDescription.text.toString()
        binding.productPrice.text.toString()
        binding.productName.text.toString()

        if (imageUri == null) {
            Toast.makeText(this, "Добавьте изображение товара", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(binding.productDescription.toString())) {
            Toast.makeText(this, "Добавьте описание товара", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(binding.productPrice.toString())) {
            Toast.makeText(this, "Добавьте стоимость товара", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(binding.productName.toString())) {
            Toast.makeText(this, "Добавьте название товара", Toast.LENGTH_SHORT).show()
        } else {
            storeProductInformation()
        }
    }

    private fun storeProductInformation() {
        val calendar = getInstance()
        val sdfDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        saveCurrentDate = sdfDate.format(calendar.time)
        val sdfTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        saveCurrentTime = sdfTime.format(calendar.time)

        prodRandomKey = saveCurrentDate + saveCurrentTime
        val filePath: StorageReference =
            prodImageRef.child(imageUri?.lastPathSegment + prodRandomKey + ".jpg")
        val uploadTask: UploadTask = filePath.putFile(imageUri!!)
        uploadTask.addOnFailureListener {
            val message = it.toString()
            Toast.makeText(this, "Ошибка $message", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            Toast.makeText(this, "Изображение успешно загружено", Toast.LENGTH_SHORT).show()
            val uriTask: Task<Uri> = uploadTask.continueWithTask {
                if (!it.isSuccessful) {
                    it.exception
                }
                downloadImageU = filePath.downloadUrl.toString()
                return@continueWithTask filePath.downloadUrl
            }.addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Фото сохранено", Toast.LENGTH_SHORT).show()
                    saveProductInfoToDatabase()
                }
            }
        }
    }

    private fun saveProductInfoToDatabase() {
        val productMap: HashMap<String, Any?> = HashMap()
        productMap.put("productId", prodRandomKey)
        productMap.put("date", saveCurrentDate)
        productMap.put("time", saveCurrentTime)
        productMap.put("image", downloadImageU)
        productMap.put("category", categoryName)
        productMap.put("description", binding.productDescription.toString())
        productMap.put("price", binding.productPrice.toString())
        productMap.put("name", binding.productName.toString())
        productsRef.child(prodRandomKey.toString()).updateChildren(productMap)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Товар добавлен", Toast.LENGTH_SHORT).show()
                } else{
                    val message = it.exception.toString()
                    Toast.makeText(this, "Ошибка$message", Toast.LENGTH_SHORT).show()
                }
            }
    }
}