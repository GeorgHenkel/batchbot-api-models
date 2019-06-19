package io.batchbot.api.models

import io.batchbot.api.models.error.Error

abstract class Event {
    abstract val id: String?
    abstract val name: String
    abstract val creationDate: String

    abstract val error: Error?
}