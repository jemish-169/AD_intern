package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var todoList = mutableListOf(
            Todo("Follow android devs.", false),
            Todo("walk 1000 steps", false),
            Todo("Build 1 new connection", false),
            Todo("Follow Your Passion Daily", true),
            Todo("Learn 1 new Concept Daily", true),
            Todo("Follow daily Routine time-table", false),
            Todo("Read min. 20 pages of Self development Book", true),
        )
        val adapter = TodoAdapter(todoList)
        rvTodo.adapter = adapter
        rvTodo.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val title = etTodo.text.toString()
            val todo = Todo(title,false)
            todoList.add(todo)
            adapter.notifyItemInserted(todoList.size-1)
        }
    }
}