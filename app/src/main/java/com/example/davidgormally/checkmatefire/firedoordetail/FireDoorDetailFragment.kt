package com.example.davidgormally.checkmatefire.firedoordetail

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

import com.example.davidgormally.checkmatefire.R
import com.example.davidgormally.checkmatefire.ServiceLocator
import com.example.davidgormally.checkmatefire.databinding.FragmentFireDoorDetailBinding
import com.example.davidgormally.checkmatefire.util.hideKeyBoard
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FireDoorDetailFragment : Fragment(), AdapterView.OnItemSelectedListener {

    /*
      View Model
    */
    private val viewModel by viewModels<FireDoorDetailViewModel> {
        FireDoorDetailViewModelFactory(ServiceLocator.provideRepository(requireContext()))
    }

    private lateinit var binding: FragmentFireDoorDetailBinding

    // Arguments received from the FireDoorListFragment
    private val args: FireDoorDetailFragmentArgs by navArgs()

    // Media List Adapter
    private lateinit var fireDoorDetailAdapter: FireDoorDetailAdapter

    // Photo Path
    private var photoPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFireDoorDetailBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner

            /*
              Spinner adapter
            */
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.doors_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                doorLocationSpinner.adapter = adapter
            }

            doorDetailRoot.setOnClickListener {
                hideKeyBoard()
            }

            toolbar.setNavigationOnClickListener { view ->
                viewModel.update()
                view.findNavController().navigateUp()
            }

            toolbar.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_delete -> {
                        MaterialAlertDialogBuilder(context)
                            .setTitle(resources.getString(R.string.delete_dialog_title))
                            .setMessage(resources.getString(R.string.delete_dialog_supporting_text))
                            .setNeutralButton(resources.getString(R.string.cancel)) { _, _ ->
                            }
                            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                                viewModel.deleteFireDoor()
                                requireView().findNavController().navigateUp()
                            }
                            .show()

                        true
                    }
                    R.id.action_photo -> {
                        dispatchPictureIntent()

                        true
                    }
                    else -> false
                }
            }
        }

        /*
           set the listener for the spinner
        */
        binding.doorLocationSpinner.onItemSelectedListener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadFireDoorDetails(args.fireDoorId)

        /*
          Load previously saved door location position into spinner
        */
        viewModel.doorLocation.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.doorLocationSpinner.setSelection(it)
        })

        viewModel.loadMediaItems()

        setupMediaListAdapter()
    }

    override fun onPause() {
        super.onPause()
        viewModel.update()
    }

    /*
      Called after returning from the camera
    */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            viewModel.createMedia(photoPath!!)
        }
    }

    private fun setupMediaListAdapter() {
        val viewModel = binding.viewmodel

        if (viewModel != null) {
            fireDoorDetailAdapter = FireDoorDetailAdapter(viewModel)
            binding.mediaList.adapter = fireDoorDetailAdapter

        } else {
            Timber.w("ViewModel not initialised when setting up adapter in FireDoorDetailFragment")
        }
    }

    private fun dispatchPictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {

                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            photoPath = absolutePath
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    /*
      Responding to user selections from the door location spinner
    */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        viewModel.updateDoorLocationPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}
