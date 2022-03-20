package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "The Material"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        ref = FirebaseDatabase.getInstance().getReference("USERS")

        btnSave.setOnClickListener {
            savedata()
            val intent = Intent (this, show::class.java)
            startActivity(intent)
        }
    }

    private fun savedata() {
        val nama = inputNama.text.toString()
        val status = inputStatus.text.toString()
        val harga = inputHarga.text.toString()
        val jenis = inputJenis.text.toString()

        val userId = ref.push().key.toString()
        val user = Users(userId,nama,status,harga,jenis)

        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            inputNama.setText("")
            inputStatus.setText("")
            inputHarga.setText("")
            inputJenis.setText("")
        }
    }
}