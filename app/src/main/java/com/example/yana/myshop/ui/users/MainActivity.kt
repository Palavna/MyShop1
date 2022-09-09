package com.example.yana.myshop.ui.users

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yana.myshop.R
import com.example.yana.myshop.databinding.ActivityMainBinding
import com.example.yana.myshop.model.Prevalent
import com.example.yana.myshop.model.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.paperdb.Paper

class MainActivity : AppCompatActivity(), ValueEventListener {

    private lateinit var binding: ActivityMainBinding

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("message")
    private var userPhoneKey: String? = null
    private var userPasswordKey: String? = null
    private var phone: String? = null
    private var password: String? = null
    private lateinit var loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadingBar = ProgressDialog(this)

        Paper.init(this)

        binding.mainLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.mainJoinButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

         userPhoneKey = Paper.book().read<String>(Prevalent::userPhoneKey.toString())
         userPasswordKey = Paper.book().read<String>(Prevalent::userPasswordKey.toString())

        if (userPhoneKey != "" && userPasswordKey != "") {
            if (!TextUtils.isEmpty(userPhoneKey.toString()) &&
                !TextUtils.isEmpty(userPasswordKey.toString())
            ) {
                validateUsers(userPhoneKey.toString(), userPasswordKey.toString())

                loadingBar.setTitle("Вход в приложение")
                loadingBar.setMessage("Пожалуйста подождите")
                loadingBar.setCanceledOnTouchOutside(false)
                loadingBar.show()
            }
        }
    }

    private fun validateUsers(userPhoneK: String, userPasswordK: String) {

        myRef.addListenerForSingleValueEvent(this)
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.child("User").child(phone.toString()).exists()) {
            val usersData = snapshot.child("User").child(phone.toString())
                .getValue(Users::class.java)
            if (usersData?.phone.equals(userPhoneKey)) {
                if (usersData?.password.equals(userPasswordKey)) {
                    loadingBar.dismiss()
                    Toast.makeText(this, "Успешный вход", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
//                    loadingBar.dismiss()
                }
            }
        } else {
            loadingBar.dismiss()
            Toast.makeText(this, "Аккаунт с номером" + phone + "не существует", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            Log.d("sadsadasdsa", "sdasdasdasdsadasd")
        }
    }


    override fun onCancelled(error: DatabaseError) {
        Log.d("sadsadasdsa", "sdasdasdasdsadasd")
    }
}
