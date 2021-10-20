package com.dev_studio.check_in.ui

interface CompletionHandler {
    fun onFailure(message:String)
    fun onSuccess(message: String)
}