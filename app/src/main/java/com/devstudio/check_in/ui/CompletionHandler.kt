package com.devstudio.check_in.ui

interface CompletionHandler {
    fun onFailure(message:String)
    fun onSuccess(message: String)
}