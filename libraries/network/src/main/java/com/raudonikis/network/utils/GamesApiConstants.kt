package com.raudonikis.network.utils

object GamesApiConstants {

    const val BASE_URL = "http://45.9.191.248/"

    object Authorization {
        object Endpoints {
            const val GET_TOKEN = "https://id.twitch.tv/oauth2/token"
        }

        object Query {
            const val KEY_CLIENT_ID = "client_id"
            const val VALUE_CLIENT_ID = "0mblh64pr5sgqtcv3k3kuruuk4nsdm"
            const val KEY_CLIENT_SECRET = "client_secret"
            const val VALUE_CLIENT_SECRET = "5zc682mu39dujy4hmv14mpf1ji3iab"
            const val KEY_GRANT_TYPE = "grant_type"
            const val VALUE_GRANT_TYPE = "client_credentials"
        }

        object Header {
            const val KEY_CLIENT_ID = "Client-ID"
            const val VALUE_CLIENT_ID = "0mblh64pr5sgqtcv3k3kuruuk4nsdm"
            const val KEY_AUTHORIZATION = "Authorization"
            const val VALUE_AUTHORIZATION = "Bearer "
        }
    }
}