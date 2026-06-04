package com.example.take_note_app_2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.take_note_app_2.data.Note
import com.example.take_note_app_2.data.NoteDatabase
import com.example.take_note_app_2.data.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.ExperimentalCoroutinesApi

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    val searchQuery = MutableStateFlow("")
    val selectedTag = MutableStateFlow<String?>(null)

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val notes: StateFlow<List<Note>> = combine(searchQuery, selectedTag) { query, tag ->
        query to tag
    }.flatMapLatest { (query, tag) ->
        val flow = if (query.isEmpty()) {
            repository.allNotes
        } else {
            repository.searchNotes(query)
        }
        
        flow.map { list ->
            if (tag == null) list
            else list.filter { note -> 
                note.tags.split(",").map { t -> t.trim() }.contains(tag) 
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allTags: StateFlow<List<String>> = repository.allNotes.map { notesList ->
        notesList.flatMap { it.tags.split(",") }
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
            .sorted()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
}
