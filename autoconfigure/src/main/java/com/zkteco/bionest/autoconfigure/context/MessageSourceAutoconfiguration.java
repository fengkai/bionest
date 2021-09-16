package com.zkteco.bionest.autoconfigure.context;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

/**
 * Autoconfigure for message source.
 *
 * @author Tyler Feng
 */
@Configuration
public class MessageSourceAutoconfiguration {

	private static final Resource[] NO_RESOURCES = {};

	@Bean
	@ConfigurationProperties(prefix = "spring.messages")
	public MessageSourceProperties messageSourceProperties() {
		return new MessageSourceProperties();
	}

	@Bean
	public MessageSource messageSource(MessageSourceProperties properties) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

		Set<String> localNames = new HashSet<>();
		// All business module we can find.
		Resource[] resources = getResources(this.getClass().getClassLoader());
		for (Resource resource : resources) {
			String[] split = resource.getFilename().split("\\.");
			localNames.add(split[0].split("_")[0]);
		}

		for (String localName : localNames) {
			// Should start with i18n/
			properties.setBasename(properties.getBasename()  + ",i18n/" + localName);
		}

		messageSource.setBasenames(StringUtils
				.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(properties.getBasename())));

		if (StringUtils.hasText(properties.getBasename())) {
			messageSource.setBasenames(StringUtils
					.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(properties.getBasename())));
		}
		if (properties.getEncoding() != null) {
			messageSource.setDefaultEncoding(properties.getEncoding().name());
		}
		messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
		Duration cacheDuration = properties.getCacheDuration();
		if (cacheDuration != null) {
			messageSource.setCacheMillis(cacheDuration.toMillis());
		}
		messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
		messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
		return messageSource;
	}

	private Resource[] getResources(ClassLoader classLoader) {
		try {
			return new PathMatchingResourcePatternResolver(classLoader)
					.getResources("classpath*:i18n/*.properties");
		}
		catch (Exception ex) {
			return NO_RESOURCES;
		}
	}

}
