use admin
db.createUser(
    {
        user: "devroot",
        pwd: "devroot",
        roles:[
            {
                role: "readWrite",
                db:   "mydatabase"
            }
        ]
    }
);