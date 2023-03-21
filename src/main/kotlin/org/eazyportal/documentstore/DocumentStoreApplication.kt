package org.eazyportal.documentstore

import org.eazyportal.core.extension.runEazyPortalApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients(
    basePackages = [
        "org.eazyportal.core",
        "org.eazyportal.documentstore"
    ]
)
@SpringBootApplication
class DocumentStoreApplication

fun main() {
    runEazyPortalApplication<DocumentStoreApplication>()
}
