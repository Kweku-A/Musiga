package com.kweku.armah.network.client

import io.ktor.client.HttpClient

interface KtorClient {
    fun getKtorClient(): HttpClient
}