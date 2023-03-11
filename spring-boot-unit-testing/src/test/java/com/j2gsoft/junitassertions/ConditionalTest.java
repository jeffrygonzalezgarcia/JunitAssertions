package com.j2gsoft.junitassertions;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

class ConditionalTest {

     @Test
     @Disabled("Dont run unit jira #123 is resolved")
     void basic(){
        // execute method and perform asserts
     }

    @Test
    @EnabledOnOs(OS.MAC)
    void forMacOnly(){
        // execute method and perform asserts
    }

    @Test
    @EnabledOnOs({OS.WINDOWS,OS.LINUX})
    void forWindowsAndLinuxOnly(){
        // execute method and perform asserts
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void forJava17Only(){
        // execute method and perform asserts
    }

    @Test
    @EnabledOnJre(JRE.JAVA_13)
    void forJava13Only(){
        // execute method and perform asserts
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_12, max = JRE.JAVA_13)
    void forJavaRange(){
        // execute method and perform asserts
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_12)
    void forJavaRangeMin(){
        // execute method and perform asserts
    }

    @Test
     @EnabledIfSystemProperty(named = "J2GSOFT_SYS_PROP", matches = "CI_CD_DEPLOY")
    void onlyForSystemProperty(){
        // execute method and perform asserts
        //We just need to modify run configuration of the test and add the system properties same for environment properties
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "J2GSOFT_ENV", matches = "DEV")
    void onlyForDevEnvironment(){
        // execute method and perform asserts
    }

}
