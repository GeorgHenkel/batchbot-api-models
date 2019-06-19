package io.batchbot.api.models.job

import io.batchbot.api.models.Event
import io.batchbot.api.models.error.Error
import javax.xml.bind.annotation.*

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class JobEvent(
        @XmlAttribute
        override val id: String?,
        @XmlElement
        override val name: String,
        @XmlElement
        override val creationDate: String,
        @XmlElement
        val status: JobStatus,
        @XmlElement
        override val error: Error?
) : Event()