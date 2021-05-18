# REST API

This is the spring-boot backend server for the woven planet assignment. It supports four different types of commands, uploading/downloading files deleting and listing files on the server.
Currently, there is one authenticated user 'wovendb' which connects to a user db (seperated per user, ideally it depends how you envision if this is shared between users)

The user is authenticated using BCrypt, but in using some BCrypt (and other SpringBoot API security settings) it caused issues in usage with both the CLI and the frontend.
There's some irony buried in there, but in truth it's my first time building some means of program security through API requests from scratch.

Functionally, the API itself should be feature complete. Having mis-read the assignment spec, I also implemented a download file endpoint, which so far can only be successfully accessed via Postman.

There is some light testing of the API, I had hoped to return to test it some more via JUnit but for now my inspection will have to suffice.


# Database
The backend itself is a personal MariaDb server running on a remote NAS machine. I'm using JPA to abstract some of my db interactions but I think improvements can be made in how the data is modelled.
Currently, there is one super-table keeping track of UserData and such. I had attempted to break this apart into seperated User, File and File meta-data tables in hopes to make the schema slightly more nice, but the increase in the query complexity and debugging made it somewhat infeasible in such a short time.

Obviously, the username/db stuff is store in plaintext in the application.properties (which is something I'd never do to be clear) and ideally would be abstracted in some sort of production keypass store.

## Usage
<!-- usage -->
```sh-session
$ mvn spring-boot:run
```

## Get list of Files on Server

### Request

`GET /listFiles`

    curl -i -H 'Accept: application/json' http://localhost:8083/listFiles/

### Response

      summary: [
    {
      documentId: 5,
      fileName: '3_test3.md',
      documentType: 'test3',
      documentFormat: 'text/markdown',
      uploadDir: null,
      size: '42',
      userId: 3
    },
    {
      documentId: 6,
      fileName: '3_test2.md',
      documentType: 'test2',
      documentFormat: 'text/markdown',
      uploadDir: null,
      size: '42',
      userId: 3
    }]

### Request

`GET /deleteFile`
`PARAMS: userId, DocType`

    curl -i -H 'Accept: application/json' http://localhost:8083/deleteFile/

### Response

      200 OK

### Request

`GET /uploadFile`
`PARAMS: filePath, userId, DocType`

    curl -i -H 'Accept: application/json' http://localhost:8083/uploadFiles/

### Response

      200 OK
