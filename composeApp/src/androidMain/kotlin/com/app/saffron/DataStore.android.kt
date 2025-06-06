package com.app.saffron
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.app.saffron.PrefsDataStore
import com.app.saffron.createDataStore
import com.app.saffron.dataStoreFileName

@Composable
actual fun rememberDataStore() : PrefsDataStore {
    val context = LocalContext.current
    return remember {
        createDataStore(
            producePath = {
                context.filesDir.resolve(dataStoreFileName).absolutePath
            },
        )
    }
}