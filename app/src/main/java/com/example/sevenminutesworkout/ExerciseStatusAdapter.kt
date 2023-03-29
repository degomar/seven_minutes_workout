package com.example.sevenminutesworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sevenminutesworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val itensList: ArrayList<ExerciseModel>) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolderExercise>(){

    class ViewHolderExercise(binding : ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root){
        val tvItem = binding.tvitem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderExercise {
        return ViewHolderExercise(ItemExerciseStatusBinding.inflate(LayoutInflater.from(
            parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return itensList.size
    }

    override fun onBindViewHolder(holder: ViewHolderExercise, position: Int) {
        var model : ExerciseModel = itensList[position]
        holder.tvItem.text = model.getId().toString()
    }
}