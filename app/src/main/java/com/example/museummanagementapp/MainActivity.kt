package com.example.museummanagementapp

import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        val etId = findViewById<EditText>(R.id.etId)
        val etName = findViewById<EditText>(R.id.etName)
        val etCategory = findViewById<EditText>(R.id.etCategory)
        val etPrice = findViewById<EditText>(R.id.etPrice)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnView = findViewById<Button>(R.id.btnView)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            val category = etCategory.text.toString()
            val price = etPrice.text.toString()

            if (name.isNotEmpty() && category.isNotEmpty() && price.isNotEmpty()) {
                val success = dbHelper.insertExhibit(name, category, price)
                if (success) {
                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnView.setOnClickListener {
            val cursor: Cursor = dbHelper.getAllExhibits()
            if (cursor.count == 0) {
                showMessage("Error", "No records found")
                return@setOnClickListener
            }
            val buffer = StringBuffer()
            while (cursor.moveToNext()) {
                buffer.append("ID: ${cursor.getString(0)}\n")
                buffer.append("Name: ${cursor.getString(1)}\n")
                buffer.append("Category: ${cursor.getString(2)}\n")
                buffer.append("Price: ${cursor.getString(3)}\n\n")
            }
            showMessage("Exhibits", buffer.toString())
        }

        btnUpdate.setOnClickListener {
            val id = etId.text.toString()
            val name = etName.text.toString()
            val category = etCategory.text.toString()
            val price = etPrice.text.toString()

            if (id.isNotEmpty() && name.isNotEmpty()) {
                val isUpdated = dbHelper.updateExhibit(id, name, category, price)
                if (isUpdated) {
                    Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Enter ID and fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            val id = etId.text.toString()
            if (id.isNotEmpty()) {
                val deletedRows = dbHelper.deleteExhibit(id)
                if (deletedRows > 0) {
                    Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Enter ID to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showMessage(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }
}