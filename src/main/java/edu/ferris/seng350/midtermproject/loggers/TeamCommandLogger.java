package edu.ferris.seng350.midtermproject.loggers;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TeamCommandLogger {

    public static final Logger log = LoggerFactory.getLogger(TeamCommandLogger.class);

    @AfterReturning(value = "execution (void edu.ferris.seng350.midtermproject.commands.SportsTeam.addTeam(..)) && args(teamName)", argNames = "teamName")
    public void logAddTeam(String teamName) {
        log.info("Team {} has been added.", teamName);
    }
}
