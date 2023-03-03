db = db.getSiblingDB("documentStore")
db.createUser(
    {
        user: "documentStore",
        pwd: "DocumentStoreAdmin",
        roles: [
            {
                role: "readWrite",
                db: "documentStore"
            }
        ]
    }
)
