package com.app.cineverse.ui.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.app.cineverse.R
import com.app.cineverse.data.models.Genre

class GenresAdapter(context: Context, private val items: List<Genre>) : ArrayAdapter<Genre>(context, android.R.layout.simple_spinner_item, items) {
    private val inflater = LayoutInflater.from(context)
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.genre_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.genre_title)
        textView.text = items[position].name
        return view
    }
}