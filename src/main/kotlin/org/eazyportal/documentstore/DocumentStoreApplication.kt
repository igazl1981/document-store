package org.eazyportal.documentstore

import org.eazyportal.core.extension.runEazyPortalApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DocumentStoreApplication

fun main() {
    runEazyPortalApplication<DocumentStoreApplication>()
}
