package com.example.miperfil

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

class RegistroActivity : AppCompatActivity() {

    private val REQUEST_CAMERA_PERMISSION = 100

    private lateinit var txtNombre: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtTelefono: EditText
    private lateinit var txtFecha: EditText
    private lateinit var txtDireccion: EditText
    private lateinit var btnFoto: Button
    private lateinit var lblPermiso: TextView
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        txtNombre = findViewById(R.id.txtNombre)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtTelefono = findViewById(R.id.txtTelefono)
        txtFecha = findViewById(R.id.txtFecha)
        txtDireccion = findViewById(R.id.txtDireccion)
        btnFoto = findViewById(R.id.btnFoto)
        lblPermiso = findViewById(R.id.lblPermiso)
        btnGuardar = findViewById(R.id.btnGuardar)

        txtFecha.setOnClickListener { showDatePickerDialog() }
        btnFoto.setOnClickListener { checkCameraPermission() }
        btnGuardar.setOnClickListener {
            if (validateForm()) {
                sendDataToPerfilActivity()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                txtFecha.setText(format.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        } else {
            lblPermiso.text = "Permiso de cámara concedido"
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            lblPermiso.text = if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                "Permiso de cámara concedido"
            } else {
                "Permiso de cámara denegado"
            }
        }
    }

    private fun validateForm(): Boolean {
        val nombre = txtNombre.text.toString().trim()
        val correo = txtCorreo.text.toString().trim()
        val telefono = txtTelefono.text.toString().trim()
        val fecha = txtFecha.text.toString().trim()
        val direccion = txtDireccion.text.toString().trim()

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || fecha.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Correo electrónico inválido.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!telefono.matches(Regex("^[0-9]{8,15}$"))) {
            Toast.makeText(this, "Teléfono inválido.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!fecha.matches(Regex("^\\d{2}/\\d{2}/\\d{4}$"))) {
            Toast.makeText(this, "Formato de fecha inválido (DD/MM/AAAA).", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun sendDataToPerfilActivity() {
        val intent = Intent(this, PerfilActivity::class.java)
        intent.putExtra("nombre", txtNombre.text.toString().trim())
        intent.putExtra("correo", txtCorreo.text.toString().trim())
        intent.putExtra("telefono", txtTelefono.text.toString().trim())
        intent.putExtra("fecha", txtFecha.text.toString().trim())
        intent.putExtra("direccion", txtDireccion.text.toString().trim())
        startActivity(intent)
    }
}
