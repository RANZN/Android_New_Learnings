package dev.ranjan.androidnewlearnings.designpatterns.structural_pattern

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ranjan.androidnewlearnings.R

/*
We use adapter to communicate between two different types as here communicating with data and the view.
 */

class DummyAdapter(private val list: List<String>) : RecyclerView.Adapter<DummyAdapter.DummyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.activity_main, parent, false)
        return DummyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DummyViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size


    inner class DummyViewHolder(private val view:View):RecyclerView.ViewHolder(view){
        fun bindData(s: String) {

        }
    }
}