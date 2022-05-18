package com.example.danielhidalgopractica1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.danielhidalgopractica1.databinding.ActivityMainBinding
import com.example.danielhidalgopractica1.model.Factura
//import com.example.practica1danielhidalgo.getRetrofit
//import com.example.practica1danielhidalgo.databinding.ActivityMainBinding
import com.example.danielhidalgopractica1.model.WrapperFiltro
import com.example.danielhidalgopractica1.model.RespuestaFactura
import com.example.practica1danielhidalgo.getRetrofit
import com.example.practica1danielhidalgo.network.FacturaApi
//import com.example.danielhidalgopractica1.network.getRetrofit
import com.example.practica1danielhidalgo.recyclerview.FacturaAdapter
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.format.DateTimeFormat
import retrofit2.Response


class MainActivity : AppCompatActivity(), FacturaAdapter.OnFacturaListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: FacturaAdapter

    private var _facturas = mutableListOf<Factura>()


    private var importeMaximo: Double = 0.0

    private var wrapperFiltro: WrapperFiltro = WrapperFiltro()


    private val filtrosLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                wrapperFiltro = Gson().fromJson(
                    activityResult.data?.getStringExtra(Constants.wrapperFiltro),
                    WrapperFiltro::class.java
                )
                getFacturas()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        getFacturas()
        initRecyclerView()
        prepararYBindeoActionBar()

    }

    private fun prepararYBindeoActionBar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (wrapperFiltro.getImporteMaximo() == 0) {
            wrapperFiltro.setImporteMaximo(importeMaximo.toInt())
        }

        if (item.itemId == R.id.icon) {

            val intent = Intent(this, FiltroActivity::class.java)
            intent.putExtra(Constants.wrapper, Gson().toJson(wrapperFiltro))
            filtrosLauncher.launch(intent)
        }

        return super.onOptionsItemSelected(item)
    }


    private fun initRecyclerView() {
        adapter = FacturaAdapter(this)
        binding.recyclerFacturas.layoutManager = LinearLayoutManager(this)
        binding.recyclerFacturas.adapter = adapter
        binding.recyclerFacturas.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        adapter.submitList(_facturas)

    }


    //Método GET para obtener las fácturas de nuestra API el cual se realiza en un hilo secundario, IO,
    //mediante una corutina
    @SuppressLint("NotifyDataSetChanged")
    private fun getFacturas() {
        binding.recyclerFacturas.visibility = View.VISIBLE
        binding.textViewFacturas.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {

            val call: Response<RespuestaFactura> =
                getRetrofit().create(FacturaApi::class.java).getFacturas()
            runOnUiThread {

                //Si la llamada es exitosa se almacenan las facturas recibidas de la API en una variable local
                if (call.isSuccessful) {
                    val callBody = call.body()

                    //si callBody es nullo facturas = lista vacía
                    val facturas = callBody?.facturas ?: emptyList()

                    //Se limpia las posibles facturas que haya en _facturas y se añaden las nuevas facturas filtradas
                    _facturas.clear()

                    _facturas.addAll(
                        facturas.filter {
                            filtrar(it)
                        }
                    )

                    if (_facturas.isNotEmpty()) {
                        //Se calcula el importe máximo de estas
                        importeMaximo =
                            _facturas.maxByOrNull { it.importeOrdenacion }!!.importeOrdenacion

                    } else {
                        binding.recyclerFacturas.visibility = View.GONE
                        binding.textViewFacturas.visibility = View.VISIBLE
                    }
                }
                adapter.notifyDataSetChanged()

                mostrarLoader()

            }

        }
    }


    //Lógica que se realiza al realizar click en el icono de cada factura
    override fun onFacturaClick() {

        //Se instancia el constructor del dialog y se setea el título y el mensaje
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.titutloDialogFactura))
        builder.setMessage(getString(R.string.cuerpoDialogFactura))
        builder.setNeutralButton(getString(R.string.neutralButtonDialogFactura)) { _, _ ->
            run {}
        }

        //Se muestra el dialog
        builder.show()
    }


    private fun mostrarLoader() {
        binding.layoutvertical.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }


    private fun filtrar(factura: Factura): Boolean {
        var flag: Boolean

        flag = filtrarFechas(factura.fecha)

        //Si se ha tocado el parámetro de importe en filtro y el valor de la factura es mayor o igual a este
        if (wrapperFiltro.importeFiltro > 0 && wrapperFiltro.importeFiltro.toDouble() < factura.importeOrdenacion) {
            flag = false
        }

        if (wrapperFiltro.getEstadosFiltro().isNotEmpty() && !(wrapperFiltro.getEstadosFiltro()
                .contains(factura.descEstado))
        ) {
            flag = false
        }



        return flag
    }


    private fun filtrarFechas(fecha: String): Boolean {
        var flag = true
        val fechaDesdeWrapper = wrapperFiltro.getFecha_desde()
        val fechaHastaWrapper = wrapperFiltro.getFecha_hasta()
        val format = DateTimeFormat.forPattern(Constants.defaultPatternFecha)
        val fecha2 = format.parseDateTime(fecha)


        if (fechaDesdeWrapper.isNotEmpty() && fechaHastaWrapper.isNotEmpty()) {

            val fechaDesdeWrapper2 = format.parseDateTime(fechaDesdeWrapper)
            val fechaHastaWrapper2 = format.parseDateTime(fechaHastaWrapper)
            flag = fecha2.isAfter(fechaDesdeWrapper2) && fecha2.isBefore(fechaHastaWrapper2)

        } else if (fechaDesdeWrapper.isNotEmpty()) {

            val fechaDesdeWrapper2 = format.parseDateTime(fechaDesdeWrapper)
            flag = fecha2.isAfter(fechaDesdeWrapper2)

        } else if (fechaHastaWrapper.isNotEmpty()) {

            val fechaHastaWrapper2 = format.parseDateTime(fechaHastaWrapper)
            flag = fecha2.isBefore(fechaHastaWrapper2)

        }

        return flag
    }
}