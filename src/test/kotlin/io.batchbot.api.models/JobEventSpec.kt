package io.batchbot.api.models

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.batchbot.api.models.job.JobEvent
import io.batchbot.api.models.job.JobStatus
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringReader
import java.io.StringWriter
import java.time.Instant
import javax.xml.bind.JAXBContext
import kotlin.test.assertEquals

object JobEventSpec : Spek({

    val creationDate = Instant.now()
    val jobEvent = JobEvent("4711", "TestJob", creationDate.toString(), JobStatus.STARTED, null)
    val json =
            """{"id":"4711","name":"TestJob","creationDate":"$creationDate","status":"STARTED","error":null}"""
    val xml =
            """<?xml version="1.0" encoding="UTF-8" standalone="yes"?><jobEvent id="4711"><name>TestJob</name><creationDate>$creationDate</creationDate><status>STARTED</status></jobEvent>"""

    describe("marshalling job start") {
        describe("to xml") {
            it("should create xml output") {
                val jaxbContext = JAXBContext.newInstance(JobEvent::class.java)
                val marshaller = jaxbContext.createMarshaller()

                val stringWriter = StringWriter()
                stringWriter.use { marshaller.marshal(jobEvent, stringWriter) }

                val result = stringWriter.toString()
                assertEquals(xml, result)
            }
        }

        describe("to json") {
            it("should create json output") {
                val result = jacksonObjectMapper().writeValueAsString(jobEvent)
                assertEquals(json, result)
            }
        }
    }

    describe("unmarshalling job start from json") {
        describe("from xml") {
            it("should create job event") {
                val jaxbContext = JAXBContext.newInstance(JobEvent::class.java)
                val unmarshaller = jaxbContext.createUnmarshaller()

                val result = unmarshaller.unmarshal(StringReader(xml))
                assertEquals(jobEvent, result)
            }
        }

        describe("from json") {
            it("should create job event") {
                val result = jacksonObjectMapper().readValue(json, JobEvent::class.java)
                assertEquals(jobEvent, result)
            }
        }
    }
})