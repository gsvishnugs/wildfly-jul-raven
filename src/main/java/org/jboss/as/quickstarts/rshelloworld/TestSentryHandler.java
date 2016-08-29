package org.jboss.as.quickstarts.rshelloworld;

import java.util.Collections;
import java.util.Map;
import java.util.logging.LogRecord;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getsentry.raven.DefaultRavenFactory;
import com.getsentry.raven.Raven;
import com.getsentry.raven.RavenFactory;
import com.getsentry.raven.event.Event;
import com.getsentry.raven.jul.SentryHandler;
import com.google.common.base.Splitter;

/**
 * A class to address <a
 * href="https://github.com/getsentry/raven-java/issues/140">this issue</a>.
 * Based on <a href="https://github.com/getsentry/raven-java/pull/174/">this
 * pull request</a>. Thanks, <a href="https://github.com/giilby">giilby</a>.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public class TestSentryHandler extends SentryHandler {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(TestSentryHandler.class);

    public TestSentryHandler() {

    }

    public TestSentryHandler(Raven raven) {
	super(raven);
    }

    public void setDsn(String dsn) {
	super.dsn = dsn;
    }

    public void setTags(String tags) {
	super.tags = this.parseTags(tags);
    }

    public void setRavenFactory(String ravenFactory) {
	super.ravenFactory = ravenFactory;
    }

    private Map<String, String> parseTags(String tagsProperty) {
	return tagsProperty == null ? Collections.<String, String> emptyMap()
		: Splitter.on(",").withKeyValueSeparator(":")
			.split(tagsProperty);
    }

    @PostConstruct
    @Override
    protected void initRaven() {
	RavenFactory.registerFactory(new DefaultRavenFactory());
	super.initRaven();
	LOGGER.info("[initRaven] ************************* Raven initialized *************************");
    }

    @Override
    protected Event buildEvent(LogRecord record) {
	LOGGER.debug("[buildEvent] Preparing event");
	return super.buildEvent(record);
    }
}
