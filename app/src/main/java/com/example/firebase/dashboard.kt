package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val intentButton : Button = findViewById(R.id.lihat)
        intentButton.setOnClickListener { viewlihat() }

        val intentButton2 : Button = findViewById(R.id.tambah)
        intentButton2.setOnClickListener { viewtambah() }
    }

    private fun viewtambah() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun viewlihat() {
        val intent = Intent(this, show::class.java)
        startActivity(intent)
    }
}