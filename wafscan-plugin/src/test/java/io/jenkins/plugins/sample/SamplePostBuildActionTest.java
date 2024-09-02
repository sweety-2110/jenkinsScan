package io.jenkins.plugins.sample;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class SamplePostBuildActionTest {

    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    @Test
    public void testPerform() throws Exception {
        /* FreeStyleProject project = jenkins.createFreeStyleProject();
             project.getPublishersList().add(new SamplePostBuildAction("Hello"));
             FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
             jenkins.assertLogContains("SamplePostBuildAction: Hello", build);
        */ }
}
