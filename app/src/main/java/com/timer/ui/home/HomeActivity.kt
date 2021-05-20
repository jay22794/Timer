package com.timer.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.timer.R
import com.timer.app.BaseActivity
import com.timer.models.Timer
import com.timer.ui.home.adapter.HomeAdapter
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : BaseActivity(), View.OnClickListener {

    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Init()
    }

    private fun Init() {

        setListeners()

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = HomeAdapter(this)
        recyclerView.adapter = adapter
        adapter.addItems(getItems())

    }

    private fun setListeners() {
        img_add.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.img_add -> {

                adapter.addItem(Timer())
                Toast.makeText(this, getString(R.string.item_added), Toast.LENGTH_SHORT).show();

            }

        }

    }

    private fun getItems(): ArrayList<Timer> {

        val items = ArrayList<Timer>()

        for (i in 0 until 15) {
            items.add(Timer())
        }

        return items

    }


}