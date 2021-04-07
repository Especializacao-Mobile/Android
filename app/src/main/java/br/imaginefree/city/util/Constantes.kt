package br.imaginefree.city

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import br.imaginefree.city.bg.DownloadImageWorker
import br.imaginefree.city.bg.DownloadWorker
import br.imaginefree.city.bg.UnzipWorker

const val notificationWarning = "Worker Status"

val constraints = Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .build()

val unzipWorker = OneTimeWorkRequest.Builder(UnzipWorker::class.java)
    .setConstraints(constraints)
    .build()

val download = OneTimeWorkRequest
    .Builder(DownloadWorker::class.java)
    .setConstraints(constraints)
    .setInputData(
        Data.Builder().putString("URL", "https://raw.githubusercontent.com/haldny/imagens/main/pacotes.json").build()
    )
    .build()

val downloadImages = OneTimeWorkRequest
    .Builder(DownloadImageWorker::class.java)
    .setConstraints(constraints)
    .setInputData(
        Data.Builder().putString("URL", "https://github.com/haldny/imagens/raw/main/cidades.zip").build()
    )
    .build()