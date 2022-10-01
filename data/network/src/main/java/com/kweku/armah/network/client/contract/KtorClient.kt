package com.kweku.armah.network.client.contract

import io.ktor.client.HttpClient

interface KtorClient {
    fun getKtorClient(): HttpClient
}
