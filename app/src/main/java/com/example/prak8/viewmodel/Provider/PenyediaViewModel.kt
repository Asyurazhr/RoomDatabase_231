package com.example.prak8.viewmodel.provider

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.prak8.repositori.OfflineRepositoriSiswa
import com.example.prak8.repositori.RepositoriSiswa
import com.example.prak8.room.DatabaseSiswa
import com.example.prak8.viewmodel.EntryViewModel
import com.example.prak8.viewmodel.HomeViewModel

object PenyediaViewModel {

    // dipakai di: viewModel(factory = PenyediaViewModel.Factory)
    val Factory: ViewModelProvider.Factory = viewModelFactory {

        // Home
        initializer {
            HomeViewModel(
                repositoriSiswa = siswaRepository()
            )
        }

        // Entry / tambah data
        initializer {
            EntryViewModel(
                repositoriSiswa = siswaRepository()
            )
        }
    }
}

/**
 * Extension untuk mengambil RepositoriSiswa langsung dari DatabaseSiswa,
 * TANPA AplikasiSiswa / Container.
 */
fun CreationExtras.siswaRepository(): RepositoriSiswa {
    val app = this[AndroidViewModelFactory.APPLICATION_KEY] as Application
    val db = DatabaseSiswa.getDatabase(app)
    return OfflineRepositoriSiswa(db.siswaDao())
}
