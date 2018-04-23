This repository tries to replicate using Grails and GORM the hibernate implementation (which works) 
available in the following repository: https://github.com/Leward/hibernate-embedded-with-relation

## Run

Run the project with the following command: 

```bash
$ ./grasilw
| Starting interactive mode...
| Enter a command name to run. Use TAB for completion:
grails> run

```

The application is be available at http://localhost:8080/

## Domain model

```groovy
class User {
    String name
    PlayerProfile playerProfile = new PlayerProfile()
    static embedded = ['playerProfile']
}

class PlayerProfile {
    String nickname
    int level = 0;
    static hasMany = [roles: PlayerRole]
}

enum PlayerRole {
    SUPPORT,
    SLAYER,
    FLEX,
    FLANK,
    SHOTCALLER
}
```

## The problem

When letting hibernate generate the schema, the following tables are generated: 

* `USER`
* `PLAYER_PROFILE`
* `PLAYER_PROFILE_ROLE`

`playerProfile` is embedded and should no generate a `PLAYER_PROFILE`

You can view the generated table by browsing: http://localhost:8080/dbconsole/ 

Also there is no easy way to control the name of the embedded column. 
For example, I would like `user.nickname` but instead get `user.player_profile_nickname`.


## Other weird behavior

Running the project directly with `./grailsw run` does not work and seems to have a different behavior and throws the following exception:

```
org.springframework.dao.DataIntegrityViolationException: Hibernate operation: could not execute statement; SQL [n/a]; Referential integrity constraint violation: "FK2875CYJQHU8ODLU4WVLB819GJ: PUBLIC.PLAYER_PROFILE_ROLES FOREIGN KEY(PLAYER_PROFILE_ID) REFERENCES PUBLIC.PLAYER_PROFILE(ID) (1)"; SQL statement:
insert into player_profile_roles (player_profile_id, player_role) values (?, ?) [23506-197]; nested exception is org.h2.jdbc.JdbcSQLException: Referential integrity constraint violation: "FK2875CYJQHU8ODLU4WVLB819GJ: PUBLIC.PLAYER_PROFILE_ROLES FOREIGN KEY(PLAYER_PROFILE_ID) REFERENCES PUBLIC.PLAYER_PROFILE(ID) (1)"; SQL statement:
insert into player_profile_roles (player_profile_id, player_role) values (?, ?) [23506-197]

```


Doing `/.grailsw` and then typing `run` works. 
However, if we hadd the following to the `User` entity we get the same exception as above: `static mapping = {}`

