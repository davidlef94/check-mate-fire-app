package com.example.davidgormally.checkmatefire.media

import androidx.lifecycle.*
import com.example.davidgormally.checkmatefire.database.entity.Media
import com.example.davidgormally.checkmatefire.repository.Repository
import kotlinx.coroutines.launch

class MediaViewModel(private val repository: Repository) : ViewModel() {

    private var mediaId: String? = null

    private val _photoPath = MutableLiveData<String>()
    val photoPath: LiveData<String> = _photoPath

    fun loadMedia(id: String) {
        this.mediaId = id
        viewModelScope.launch {
            onMediaLoaded(repository.getMediaById(id))
        }
    }

    private fun onMediaLoaded(media: Media) {
        _photoPath.value = media.fileName
    }

    fun deletePhoto() = viewModelScope.launch {
        repository.deleteMediaById(mediaId!!)
    }
}

@Suppress("UNCHECKED_CAST")
class MediaViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (MediaViewModel(repository) as T)
}