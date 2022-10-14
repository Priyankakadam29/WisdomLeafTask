package com.wisdomleaf.myassignment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var list: ArrayList<ModalClass>
    var GET_URL = "https://picsum.photos/v2/list?page=2&limit=20"

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.container)
        recyclerView = findViewById(R.id.recyclerView)

        list = ArrayList<ModalClass>()
        list.clear()
        recyclerAdapter = RecyclerAdapter(list, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter


        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        try {
            progressDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)


        val request = StringRequest(Request.Method.GET, GET_URL, { response ->

            val jsonArrayList = JSONArray(response)

            for (index in 0 until jsonArrayList.length()) {
                val item = jsonArrayList.getJSONObject(index)
                list.add(
                    ModalClass(
                        item.getString("id"),
                        item.getString("author"),
                        item.getString("download_url")
                    )
                )
            }

            recyclerAdapter = RecyclerAdapter(list, this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = recyclerAdapter

            try {
                progressDialog.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }, { error ->
            progressDialog.dismiss()
            Log.e("TAG", "RESPONSE IS $error")
            Toast.makeText(this@MainActivity, "Fail to get response", Toast.LENGTH_SHORT)
                .show()
        })
        queue.add(request)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            recyclerAdapter.notifyDataSetChanged()
        }
    }
}
