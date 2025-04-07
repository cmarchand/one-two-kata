package com.oxiane.katas.one.two;

import io.cucumber.core.options.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("/com/oxiane/katas/one/two")
@ConfigurationParameter(
    key = Constants.GLUE_PROPERTY_NAME,
    value = "com.oxiane.katas.one.two.glues"
)
@ConfigurationParameter(
    key = Constants.PLUGIN_PROPERTY_NAME,
    value = "pretty, html:target/one-two.html"
)
public class OneTwoTests {
}
