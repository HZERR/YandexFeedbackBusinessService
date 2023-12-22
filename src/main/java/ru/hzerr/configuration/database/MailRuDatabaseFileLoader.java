package ru.hzerr.configuration.database;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.context.annotation.Bean;
import ru.hzerr.configuration.IExtendedStructureConfiguration;
import ru.hzerr.fx.engine.core.LoadException;
import ru.hzerr.fx.engine.core.Loader;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;

@Registered
public class MailRuDatabaseFileLoader implements Loader<PropertiesConfiguration> {

    private FileBasedConfigurationBuilder<PropertiesConfiguration> builder;
    private String location;

    @Include
    public MailRuDatabaseFileLoader(IExtendedStructureConfiguration configuration) {
        builder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                .configure(new Parameters().properties()
                        .setFile(configuration.getMailRuDatabaseFile().asIOFile())
                        .setThrowExceptionOnMissing(true)
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(';'))
                        .setIncludesAllowed(true));

        this.location = configuration.getMailRuDatabaseFile().getLocation();
    }

    @Override
    @Bean("mailRuDatabaseFile")
    public PropertiesConfiguration load() throws LoadException {
        builder.setAutoSave(true);
        try {
            return builder.getConfiguration();
        } catch (ConfigurationException ce) {
            throw new LoadException("Unable to load a configuration on the path " + location, ce);
        }
    }
}
