package com.mitch.core

sealed class ProgressBarState {

    object Loading : ProgressBarState()
    object Idle : ProgressBarState()
}