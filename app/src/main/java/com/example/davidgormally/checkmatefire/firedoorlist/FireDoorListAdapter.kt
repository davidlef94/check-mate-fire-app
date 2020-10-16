package com.example.davidgormally.checkmatefire.firedoorlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.davidgormally.checkmatefire.database.entity.FireDoor
import com.example.davidgormally.checkmatefire.databinding.FireDoorItemBinding
import com.example.davidgormally.checkmatefire.firedoorlist.FireDoorListAdapter.ViewHolder

class FireDoorListAdapter(private val viewModel: FireDoorListViewModel) : ListAdapter<FireDoor, ViewHolder>(FireDoorListCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    class ViewHolder private constructor(private val binding: FireDoorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.fireDoor?.let { fireDoor ->
                    navigateToFireDoorDetailFragment(fireDoor, it)
                }
            }
        }

        private fun navigateToFireDoorDetailFragment(fireDoor: FireDoor, view: View) {
            val direction = FireDoorListFragmentDirections.actionFireDoorListFragmentToFireDoorDetailFragment(fireDoor.id)
            view.findNavController().navigate(direction)
        }

        fun bind(viewModel: FireDoorListViewModel, item: FireDoor) {
            binding.viewmodel = viewModel
            binding.fireDoor = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FireDoorItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class FireDoorListCallback : DiffUtil.ItemCallback<FireDoor>() {
    override fun areItemsTheSame(oldItem: FireDoor, newItem: FireDoor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FireDoor, newItem: FireDoor): Boolean {
        return oldItem == newItem
    }
}