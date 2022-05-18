package com.example.practica1danielhidalgo.recyclerview
import androidx.recyclerview.widget.DiffUtil
import com.example.danielhidalgopractica1.model.Factura

class DiffUtil :DiffUtil.ItemCallback<Factura>() {


    override fun areContentsTheSame(oldItem: Factura, newItem: Factura): Boolean {
        return (oldItem==newItem)
    }


    override fun areItemsTheSame(oldItem: Factura, newItem: Factura): Boolean {
        return (oldItem.fecha==newItem.fecha)
    }
}