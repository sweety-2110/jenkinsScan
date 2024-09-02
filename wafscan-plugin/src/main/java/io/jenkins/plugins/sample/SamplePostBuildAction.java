package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import java.io.IOException;
import jenkins.tasks.SimpleBuildStep;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class SamplePostBuildAction extends Notifier implements SimpleBuildStep {

    private String accessKey;

    @DataBoundConstructor
    public SamplePostBuildAction(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    @DataBoundSetter
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener)
            throws InterruptedException, IOException {
        // Your logic here
        /* }

        @Override
        public boolean perform(Run<?, ?> run,AbstractBuild<?, ?> build, hudson.Launcher launcher, BuildListener listener)
                throws InterruptedException, IOException {
            */
        // Check if the build was successful
        if (run.getResult() == hudson.model.Result.SUCCESS) {
            listener.getLogger().println("Build was successful! Executing post-build action...");
            listener.getLogger().println("Access Key: " + accessKey);
            ScanApiLaunch sc = new ScanApiLaunch();
            sc.startScan(listener, this.accessKey);
        } else {
            listener.getLogger().println("Build was not successful. Skipping post-build action.");
        }
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        public DescriptorImpl() {
            super(SamplePostBuildAction.class);
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Waf Scan Action-Post build";
        }
    }
}
