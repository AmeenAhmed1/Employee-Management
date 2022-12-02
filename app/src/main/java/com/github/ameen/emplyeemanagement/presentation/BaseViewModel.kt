package com.github.ameen.emplyeemanagement.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {
    private val coroutineJob: Job = Job()
    private val coroutineDispatcher = Dispatchers.IO
    protected val coroutineContext: CoroutineContext = coroutineJob + coroutineDispatcher
}