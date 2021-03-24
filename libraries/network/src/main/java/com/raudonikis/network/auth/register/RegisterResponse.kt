package com.raudonikis.network.auth.register

import com.raudonikis.network.auth.UserResponse
import com.squareup.moshi.Json

data class RegisterResponse(
    @Json(name = "user")
    val user: UserResponse,
    @Json(name = "accessToken")
    val accessToken: String,
)

/**
 * "user": {
"name": "pipsas",
"email": "n.nevada@gmail.com",
"uuid": "93078c76-b1e4-436b-8cef-13ebe171af93",
"updated_at": "2021-03-24T17:18:26.000000Z",
"created_at": "2021-03-24T17:18:26.000000Z"
},
"accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiYmUxMjBiYWY4YzM1N2Q4YjM0YTg4ZmQ1ZGNmNjdkM2ZhZDkzZWM4ZWM1NGM2MmVhMmMwOWI3ODU4ZDRhOTAxZTQxN2RmZWQ4YmZkNTU5YzEiLCJpYXQiOjE2MTY2MDYzMDYsIm5iZiI6MTYxNjYwNjMwNiwiZXhwIjoxNjMyNTAzOTA2LCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.YuFX53yqlEc_9dtlMqm2SZPXE770Wh-AgzgGhyoZv0EGRVyn4-oaKAF45_wt0cMk_gle-AwUVc7CAcWdYXtiQh3FnGQ_4j6q_-hVgSC3lZI1Hs0Hi_XvR-Y8bcPSy2RTpMklTxWLHGCVXlju_l4J3xZMrIQT-zQbfteatgdZPvwr2GdEs0TVLbf6xAmmIBFzPcxgv4b4MqCufPBDLfCfcKI_A1ijaJYZGOK7vxuxYTP7NheXmS1cH0cHZMGf9dJpTHcyTBSoFZlGnyYC2tg4EluGJeWMYpY_lt8miDreZGZ5xnl3Lkel8NeHxSV5N4ojMIgSi1_zW2qNw2tB4DgJYzlftufwDRQP31tWDgx_s-IMyFbNTDCnwWXavcJUf3p5ffJBAVUQclegUHglA79hXuV1XJzsBB6vdVyEgSVqK4roSkEa9Bp9Uw94zdOHnW6yetCFg45s_XcN6yYTvR8ejl3drC8Ac5TiQe3-0OQeJY_h2FwibcSUZwH2LSfFKjhX1EWh1sdy8djaB714Szbyqd3kvIHgB3qQP9BhiLwMN_u8umujzSYT8Y2G4V7h8x5i687Ko-CsF52ZmqmPS7hGmyLmLSM2u0Pdv_ytenZxbZtKk8V3_sDq9dlUaqz6PjS2XEFIQkL5Cs3zJ1aKXr-VUJqTeneTZYMUUHuxf0s7JnU"
}
 */
