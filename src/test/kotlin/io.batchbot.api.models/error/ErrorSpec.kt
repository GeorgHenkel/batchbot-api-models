package io.batchbot.api.models.error

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXBContext
import kotlin.test.assertEquals

object ErrorSpec : Spek({

    describe("marshalling error") {
        val error = Error("4711", "Error During Processing", "java.lang.ArrayIndexOutOfBoundsException: 3\n" +
                "  at example.common.TestTry.execute(TestTry.java:17)\n" +
                "  at example.common.TestTry.main(TestTry.java:11)")

        describe("to xml") {
            it("should create xml output") {
                val jaxbContext = JAXBContext.newInstance(Error::class.java)
                val marshaller = jaxbContext.createMarshaller()

                val stringWriter = StringWriter()
                stringWriter.use { marshaller.marshal(error, stringWriter) }

                val result = stringWriter.toString()
                val expected =
                        """<?xml version="1.0" encoding="UTF-8" standalone="yes"?><error><code>4711</code><message>Error During Processing</message><stackTrace>java.lang.ArrayIndexOutOfBoundsException: 3
  at example.common.TestTry.execute(TestTry.java:17)
  at example.common.TestTry.main(TestTry.java:11)</stackTrace></error>"""

                assertEquals(expected, result)
            }
        }

        describe("to json") {
            it("should create json output") {
                val mapper = jacksonObjectMapper()

                val result = mapper.writeValueAsString(error)
                val expected =
                        """{"code":"4711","message":"Error During Processing","stackTrace":"java.lang.ArrayIndexOutOfBoundsException: 3\n  at example.common.TestTry.execute(TestTry.java:17)\n  at example.common.TestTry.main(TestTry.java:11)"}"""

                assertEquals(expected, result)
            }
        }
    }

    describe("unmarshalling error") {
        val error = Error("4711", "Error During Processing", "java.lang.ArrayIndexOutOfBoundsException: 3\n" +
                "  at example.common.TestTry.execute(TestTry.java:17)\n" +
                "  at example.common.TestTry.main(TestTry.java:11)")

        describe("from xml") {
            it("should create error") {
                val xml =
                        """<?xml version="1.0" encoding="UTF-8" standalone="yes"?><error><code>4711</code><message>Error During Processing</message><stackTrace>java.lang.ArrayIndexOutOfBoundsException: 3
  at example.common.TestTry.execute(TestTry.java:17)
  at example.common.TestTry.main(TestTry.java:11)</stackTrace></error>"""

                val jaxbContext = JAXBContext.newInstance(Error::class.java)
                val unmarshaller = jaxbContext.createUnmarshaller()
                val result = xml.reader().use { unmarshaller.unmarshal(it) }

                assertEquals(error, result)
            }
        }

        describe("from json") {
            it("should create error") {
                val json =
                        """{"code":"4711","message":"Error During Processing","stackTrace":"java.lang.ArrayIndexOutOfBoundsException: 3\n  at example.common.TestTry.execute(TestTry.java:17)\n  at example.common.TestTry.main(TestTry.java:11)"}"""

                val mapper = jacksonObjectMapper()
                val result = mapper.readValue(json, Error::class.java)

                assertEquals(error, result)

            }
        }
    }
})