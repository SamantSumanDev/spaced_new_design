package com.example.stack1trail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class MyAdapter(private val items: ArrayList<Item>, private val viewPager: ViewPager2) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var selectedItem = 0
    private var selectedTextSize: Float = 140f
    private var unSelectedTextSize: Float = 70f // Default text size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: AppCompatTextView = itemView.findViewById(R.id.textCh)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sc_txt_vp, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.textView.text = item.text

        // Set the text size and color based on the selected item
        val context = holder.itemView.context
        if (position == selectedItem) {
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedTextSize)
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.selectedTextColor))
            holder.textView.alpha = 1.0f // Make sure alpha is set to 1 for the selected page
        } else {
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, unSelectedTextSize)
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.defaultTextColor))
            holder.textView.alpha = 0.5f // Set alpha to a lower value for non-selected pages
        }

// Apply fade animation to the textView
        val animatorSet = AnimatorSet()
        val fadeAnimation = ObjectAnimator.ofFloat(holder.textView, "alpha", holder.textView.alpha)
        fadeAnimation.duration = 500 // Set the duration of the animation (in milliseconds)
        animatorSet.play(fadeAnimation)
        animatorSet.start()


        holder.itemView.setOnClickListener {
            viewPager.currentItem = position
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setSelectedItem(position: Int) {
        // Update the selected item
        selectedItem = position

        // Notify adapter of the data change
        Handler(Looper.getMainLooper()).post {
            notifyDataSetChanged()
        }
    }


    fun setTextSize(size: Float) {
        unSelectedTextSize = size
        notifyDataSetChanged()
    }

    fun setSelectedTextSize(textSize: Float) {
        selectedTextSize = textSize
        notifyDataSetChanged()
    }



    data class Item(val text: String)
}
