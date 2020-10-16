package com.example.davidgormally.checkmatefire.firedoordetail

import androidx.lifecycle.*
import com.example.davidgormally.checkmatefire.database.entity.FireDoor
import com.example.davidgormally.checkmatefire.database.entity.Media
import com.example.davidgormally.checkmatefire.repository.Repository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class FireDoorDetailViewModel(private val repository: Repository) : ViewModel() {

    /*
      id of Fire Door
    */
    private var fireDoorId: String? = null

    /*
      Two-way data-binding, exposing MutableLiveData
    */
    val doorNumber = MutableLiveData<String>() // No

    // spinner => should set the position (int) of the value selected
    private val _doorLocation = MutableLiveData<Int>()
    val doorLocation: LiveData<Int>
        get() = _doorLocation

    val doorLabelled = MutableLiveData<Boolean>() // Labelled?

    // 4mm Gap?
    val topMax = MutableLiveData<String>()
    val bottomMax = MutableLiveData<String>()
    val leftMax = MutableLiveData<String>()
    val rightMax = MutableLiveData<String>()

    val doorInstrumescentSeal = MutableLiveData<Boolean>() // Instrumescent Seal?
    val doorHinges = MutableLiveData<Boolean>() // 3 Hinges?
    val doorClosuresWork = MutableLiveData<Boolean>() // Door Closures Work?
    val doorGlazingAndGlass = MutableLiveData<Boolean>() // Glazing & Glass
    val doorLeafOk = MutableLiveData<Boolean>() // Door Leaf ok?
    val doorFrameOk = MutableLiveData<Boolean>() // Door Frame ok?
    val doorOperationOk = MutableLiveData<Boolean>() // Door Operation ok?
    val comments = MutableLiveData<String>() // Comments
    val actionRequired = MutableLiveData<String>() // Action Req Y/N?

    // date
    private val dateCreated = MutableLiveData<String>()

    /*
      List of photos associated with this fire door document
    */
    var mediaItems: LiveData<List<Media>>? = null

    /*
      Load FireDoor information
    */
    fun loadFireDoorDetails(id: String) {
        this.fireDoorId = id
        viewModelScope.launch {
            onFireDoorDetailsLoaded(repository.getFireDoorById(id))
        }
    }

    private fun onFireDoorDetailsLoaded(fireDoor: FireDoor) {
        doorNumber.value = fireDoor.no
        _doorLocation.value = fireDoor.location
        doorLabelled.value = fireDoor.labelled

        topMax.value = fireDoor.topMax
        bottomMax.value = fireDoor.bottomMax
        leftMax.value = fireDoor.leftMax
        rightMax.value = fireDoor.rightMax

        doorInstrumescentSeal.value = fireDoor.instrumescentSeal
        doorHinges.value = fireDoor.threeHinges
        doorClosuresWork.value = fireDoor.doorClosuresWork
        doorGlazingAndGlass.value = fireDoor.glazingAndGlass
        doorLeafOk.value = fireDoor.doorLeafOk
        doorFrameOk.value = fireDoor.doorFrameOk
        doorOperationOk.value = fireDoor.doorOperationOk
        comments.value = fireDoor.comments
        actionRequired.value = fireDoor.actionRequired
        dateCreated.value = fireDoor.dateCreated.toString()
    }

    /*
      Update the details of the Fire Door
    */
    fun update() {
        val currentDoorNumber = doorNumber.value
        val currentDoorLocation = _doorLocation.value
        val currentDoorLabelled = doorLabelled.value

        val currentTopMax = topMax.value
        val currentBottomMax = bottomMax.value
        val currentLeftMax = leftMax.value
        val currentRightMax = rightMax.value

        val currentDoorInstrumescentSeal = doorInstrumescentSeal.value
        val currentDoorHinges = doorHinges.value
        val currentDoorClosuresWork = doorClosuresWork.value
        val currentDoorGlazingAndGlass = doorGlazingAndGlass.value
        val currentDoorLeafOk = doorLeafOk.value
        val currentDoorFrameOk = doorFrameOk.value
        val currentDoorOperationOk = doorOperationOk.value
        val currentComments = comments.value
        val currentActionRequired = actionRequired.value

        // keep previous date when item was created
        val currentDateCreated = dateCreated.value
        val simpleDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.UK)
        val dateCreated = simpleDateFormat.parse(currentDateCreated!!)

        val fireDoor = FireDoor(
            fireDoorId!!,
            currentDoorNumber!!,
            currentDoorLocation!!,
            currentDoorLabelled!!,
            currentTopMax!!,
            currentBottomMax!!,
            currentLeftMax!!,
            currentRightMax!!,
            currentDoorInstrumescentSeal!!,
            currentDoorHinges!!,
            currentDoorClosuresWork!!,
            currentDoorGlazingAndGlass!!,
            currentDoorLeafOk!!,
            currentDoorFrameOk!!,
            currentDoorOperationOk!!,
            currentComments!!,
            currentActionRequired!!,
            dateCreated!!
        )

        updateFireDoor(fireDoor)
    }

    private fun updateFireDoor(fireDoor: FireDoor) = viewModelScope.launch {
        repository.updateFireDoor(fireDoor)
    }

    /*
      Permanently delete the fire door information
    */
    fun deleteFireDoor() = viewModelScope.launch {
        repository.deleteFireDoorById(fireDoorId!!)
    }

    fun updateDoorLocationPosition(pos: Int) {
        _doorLocation.value = pos
    }

    /*
      Loading, creating and saving photo(s)
    */

    fun loadMediaItems() {
        viewModelScope.launch {
            onMediaItemsLoaded(repository.getAllMediaByFireDoorId(fireDoorId!!))
        }
    }

    private fun onMediaItemsLoaded(mediaValues: LiveData<List<Media>>) {
        mediaItems = mediaValues
    }

    fun createMedia(photoPath: String) {
        val media = Media(fireDoorId!!, photoPath)
        saveMedia(media)
    }

    private fun saveMedia(media: Media) = viewModelScope.launch {
        repository.insertMedia(media)
    }
}

@Suppress("UNCHECKED_CAST")
class FireDoorDetailViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (FireDoorDetailViewModel(repository) as T)
}