package com.example.practica1danielhidalgo.recyclerview
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.danielhidalgopractica1.R
import com.example.danielhidalgopractica1.model.Factura

class FacturaAdapter(
    private val facturaClickListener: OnFacturaListener
) : ListAdapter<Factura,FacturaViewHolder>(DiffUtil()) {

    interface OnFacturaListener {
        fun onFacturaClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FacturaViewHolder(layoutInflater.inflate(R.layout.item_factura, parent, false), this.facturaClickListener)
    }

    override fun onBindViewHolder(holder: FacturaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}