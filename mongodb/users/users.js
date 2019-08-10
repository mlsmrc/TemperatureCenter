db.createUser(
    {
        user: "test",
        pwd: "test",
        roles:[
            {
                role: "root",
                db:   "admin"
            }
        ]
    }
);

db.getUsers( {
   showCredentials: true
} );