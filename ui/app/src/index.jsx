import FileUploadService from '@eazyportal/document-store-service-ui-lib'

import React from 'react'
import ReactDOM from 'react-dom/client'

import '@eazyportal/core-ui-lib/dist/index.css'
import '@eazyportal/document-store-service-ui-lib/dist/index.css'

ReactDOM.createRoot(document.getElementById('document-store-service-root'))
    .render(<FileUploadService.App />)
