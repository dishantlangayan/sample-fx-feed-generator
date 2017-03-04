/**
 * Copyright 2017 Dishant Langayan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dishant.demo.fxfeed;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for the Sample FX Feed Generator.
 * <p/>
 * Reads a value from src/main/resources/application.properties first but would
 * also read:
 * <p/>
 * java -Dconfiguration.projectName=.. export
 * <p/>
 * CONFIGURATION_PROJECTNAME=.
 * 
 * @author Dishant Langayan
 */
@Component
@ConfigurationProperties("fxfeed")
public class FxFeedConfigProperties {

    private String topicPrefix;
    private long publishInterval;
    private long feedPollingInterval;

    public String getTopicPrefix() {
        return topicPrefix;
    }

    public void setTopicPrefix(String topicPrefix) {
        this.topicPrefix = topicPrefix;
    }

    public long getPublishInterval() {
        return publishInterval;
    }

    public void setPublishInterval(long publishInterval) {
        this.publishInterval = publishInterval;
    }

    public long getFeedPollingInterval() {
        return feedPollingInterval;
    }

    public void setFeedPollingInterval(long feedPollingInterval) {
        this.feedPollingInterval = feedPollingInterval;
    }
}
