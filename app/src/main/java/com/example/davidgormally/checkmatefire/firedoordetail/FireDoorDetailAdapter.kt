package com.example.davidgormally.checkmatefire.firedoordetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.davidgormally.checkmatefire.database.entity.Media
import com.example.davidgormally.checkmatefire.databinding.MediaItemBinding
import com.example.davidgormally.checkmatefire.firedoordetail.FireDoorDetailAdapter.ViewHolder

class FireDoorDetailAdapter(private val viewModel: FireDoorDetailViewModel) : ListAdapter<Media, ViewHolder>(MediaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    class ViewHolder private constructor(private val binding: MediaItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.media?.let { media ->
                    navigateToMediaFragment(media, it)
                }
            }
        }

        private fun navigateToMediaFragment(media: Media, view: View) {
            val direction = FireDoorDetailFragmentDirections.actionFireDoorDetailFragmentToMediaFragment(media.id)
            view.findNavController().navigate(direction)
        }

        fun bind(viewModel: FireDoorDetailViewModel, item: Media) {
            binding.viewmodel = viewModel
            binding.media = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MediaItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class MediaDiffCallback : DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
        return oldItem == newItem
    }
}