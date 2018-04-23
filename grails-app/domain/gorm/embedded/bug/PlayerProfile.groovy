package gorm.embedded.bug

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString(includeNames = true)
class PlayerProfile {
    String nickname
    int level = 0;
    static hasMany = [roles: PlayerRole]
}
