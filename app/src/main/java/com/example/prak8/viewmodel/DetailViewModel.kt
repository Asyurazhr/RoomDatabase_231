package com.example.prak8.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prak8.repositori.RepositoriSiswa      // <- perhatikan: repositori, bukan repository
import com.example.prak8.view.route.DestinasiDetailSiswa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriSiswa: RepositoriSiswa
) : ViewModel() {

    // id siswa diambil dari argumen nav: "idSiswa"
    private val idSiswa: Int =
        checkNotNull(savedStateHandle[DestinasiDetailSiswa.itemIdArg])

    // Flow -> UI state untuk layar detail
    val uiDetailState: StateFlow<DetailSiswaUiState> =
        repositoriSiswa.getSiswaStream(idSiswa)
            .filterNotNull()
            .map { siswa ->
                // toDetailSiswa() diasumsikan adalah extension function
                DetailSiswaUiState(detailSiswa = siswa.toDetailSiswa())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DetailSiswaUiState()
            )

    suspend fun deleteSiswa() {
        // toSiswa() juga diasumsikan extension function dari DetailSiswa ke entity Siswa
        repositoriSiswa.deleteSiswa(uiDetailState.value.detailSiswa.toSiswa())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state untuk layar Detail
 */
data class DetailSiswaUiState(
    val detailSiswa: DetailSiswa = DetailSiswa()
)
