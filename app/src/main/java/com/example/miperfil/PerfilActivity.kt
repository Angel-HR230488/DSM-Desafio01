package com.example.miperfil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val lblDatos: TextView = findViewById(R.id.lblDatos)
        val btnInicio: Button = findViewById(R.id.btnInicio)
        val btnNuevo: Button = findViewById(R.id.btnNuevo)

        val nombre = intent.getStringExtra("nombre")
        val correo = intent.getStringExtra("correo")
        val telefono = intent.getStringExtra("telefono")
        val fecha = intent.getStringExtra("fecha")
        val direccion = intent.getStringExtra("direccion")

        lblDatos.text = """
            Nombre: $nombre
            Correo: $correo
            Teléfono: $telefono
            Fecha Nacimiento: $fecha
            Dirección: $direccion
        """.trimIndent()

        btnInicio.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnNuevo.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}
