package com.example.firebase

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

@Suppress("UNREACHABLE_CODE", "NAME_SHADOWING")
class Adapter (val mCtx: Context, val layoutResId: Int, val list: List<Users> )
    : ArrayAdapter<Users>(mCtx,layoutResId,list) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textNama = view.findViewById<TextView>(R.id.textNama)
        val textStatus = view.findViewById<TextView>(R.id.textStatus)
        val textHarga = view.findViewById<TextView>(R.id.textHarga)
        val textJenis = view.findViewById<TextView>(R.id.textJenis)

        val textUpdate = view.findViewById<TextView>(R.id.TextUpdate)
        val textDelete = view.findViewById<TextView>(R.id.TextDelete)



        val user = list[position]

        textNama.text = user.nama
        textStatus.text = user.status
        textHarga.text = user.harga
        textJenis.text = user.jenis


        textUpdate.setOnClickListener {
            showUpdateDialog(user)
        }
        textDelete.setOnClickListener {
            Deleteinfo(user)
        }


        return view
    }

    private fun Deleteinfo(user: Users) {
        val progressDialog = ProgressDialog(
            context,
            R.style.Theme_MaterialComponents_Light_Dialog
        )
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("USERS")
        mydatabase.child(user.id).removeValue()
        Toast.makeText(mCtx, "Deleted!!", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, show::class.java)
        context.startActivity(intent)
    }

    private fun showUpdateDialog(user: Users) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update, null)

        val textNama = view.findViewById<EditText>(R.id.inputNama)
        val textStatus = view.findViewById<EditText>(R.id.inputStatus)
        val textHarga = view.findViewById<EditText>(R.id.inputHarga)
        val textJenis = view.findViewById<EditText>(R.id.inputJenis)

        textNama.setText(user.nama)
        textStatus.setText(user.status)
        textHarga.setText(user.harga)
        textJenis.setText(user.jenis)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("USERS")

            val nama = textNama.text.toString().trim()

            val status = textStatus.text.toString().trim()

            val harga = textHarga.text.toString().trim()

            val jenis = textJenis.text.toString().trim()

            if (nama.isEmpty()) {
                textNama.error = "please enter merk"
                textNama.requestFocus()
                return@setPositiveButton
            }

            if (status.isEmpty()) {
                textStatus.error = "please enter stok"
                textStatus.requestFocus()
                return@setPositiveButton
            }

            if (status.isEmpty()) {
                textHarga.error = "please enter harga"
                textHarga.requestFocus()
                return@setPositiveButton
            }

            if (status.isEmpty()) {
                textJenis.error = "please enter jenis"
                textJenis.requestFocus()
                return@setPositiveButton
            }

            val user = Users(user.id, nama, status, harga, jenis )

            dbUsers.child(user.id).setValue(user).addOnCompleteListener {
                Toast.makeText(mCtx, "Updated", Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()

    }}