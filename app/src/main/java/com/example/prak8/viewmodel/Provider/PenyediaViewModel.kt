package com.example.prak8.viewmodel.provider   // <- sudah lowercase

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.prak8.repositori.AplikasiSiswa
import com.example.prak8.viewmodel.EntryViewModel
import com.example.prak8.viewmodel.HomeViewModel

object PenyediaViewModel {

    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiSiswa().container.repositoriSiswa)
        }
        initializer {
            EntryViewModel(aplikasiSiswa().container.repositoriSiswa)
        }
    }
}

// Extension utk ambil AplikasiSiswa dari CreationExtras
fun CreationExtras.aplikasiSiswa(): AplikasiSiswa =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiSiswa
