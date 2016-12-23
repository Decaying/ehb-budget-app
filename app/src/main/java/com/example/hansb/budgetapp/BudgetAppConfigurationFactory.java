package com.example.hansb.budgetapp;

import android.support.annotation.NonNull;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.net.URI;

/**
 * Created by HansB on 23/12/2016.
 */
public class BudgetAppConfigurationFactory extends ConfigurationFactory {

    static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
        builder.setConfigurationName(name);
        builder.setStatusLevel(Level.ERROR);

        builder.add(createThresholdFilter(builder));
        builder.add(createConsoleAppender(builder));
        builder.add(createStdoutLogger(builder));

        builder.add(builder.newRootLogger(Level.ERROR)
                .add(builder.newAppenderRef("ConsoleAppender")));
        return builder.build();
    }

    private static LoggerComponentBuilder createStdoutLogger(ConfigurationBuilder<BuiltConfiguration> builder) {
        return builder.newLogger("org.apache.logging.log4j", Level.DEBUG).
                add(builder.newAppenderRef("ConsoleAppender")).
                addAttribute("additivity", false);
    }

    private static FilterComponentBuilder createThresholdFilter(ConfigurationBuilder<BuiltConfiguration> builder) {
        return builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL).
                addAttribute("level", Level.DEBUG);
    }

    @NonNull
    private static AppenderComponentBuilder createConsoleAppender(ConfigurationBuilder<BuiltConfiguration> builder) {
        AppenderComponentBuilder appenderBuilder = builder.newAppender("ConsoleAppender", "CONSOLE").
                addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        appenderBuilder.add(builder.newLayout("PatternLayout").
                addAttribute("pattern", "%d [%t : %c{1}.%M()] %-5level: %msg%n%throwable"));
        appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY,
                Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"));
        return appenderBuilder;
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[]{"*"};
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }

}
