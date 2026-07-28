package com.terminalit.di

import com.terminalit.data.ExtraKeyStore
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ExtraKeyStoreEntryPoint {
    fun extraKeyStore(): ExtraKeyStore
}
