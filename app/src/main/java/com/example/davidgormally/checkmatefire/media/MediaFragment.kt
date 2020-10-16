package com.example.davidgormally.checkmatefire.media

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

import com.example.davidgormally.checkmatefire.R
import com.example.davidgormally.checkmatefire.ServiceLocator
import com.example.davidgormally.checkmatefire.databinding.FragmentMediaDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A simple [Fragment] subclass.
 */
class MediaFragment : Fragment() {

    private val viewModel by viewModels<MediaViewModel> {
        MediaViewModelFactory(ServiceLocator.provideRepository(requireContext()))
    }

    private lateinit var binding: FragmentMediaDetailBinding

    private val args: MediaFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMediaDetailBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner

            // navigating back
            navigateBackButton.setOnClickListener {
                it.findNavController().navigateUp()
            }

            deletePhotoButton.setOnClickListener {
                MaterialAlertDialogBuilder(context)
                    .setTitle(resources.getString(R.string.delete_dialog_title))
                    .setMessage(resources.getString(R.string.delete_dialog_photo))
                    .setNeutralButton(resources.getString(R.string.cancel)) { _, _ ->
                    }
                    .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                        viewModel.deletePhoto()
                        requireView().findNavController().navigateUp()
                    }
                    .show()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadMedia(args.mediaId)
        setupImage()
    }

    private fun setupImage() {
        viewModel.photoPath.observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext()).load(it).centerCrop().into(binding.photoImageView)
        })
    }
}
