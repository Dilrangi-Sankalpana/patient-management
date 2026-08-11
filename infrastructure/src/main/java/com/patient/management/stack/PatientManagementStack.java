package com.patient.management.stack;

import software.amazon.awscdk.*;

public class PatientManagementStack extends Stack {

    public PatientManagementStack(final App scope, final String id, final StackProps props) {
        super(scope, id, props);
    }

    public static void main(final String[] args) {

        App app = new App(AppProps.builder()
                .outdir("./cdk.out")
                .build()
        );

        StackProps props = StackProps.builder()
                .synthesizer(new BootstraplessSynthesizer())
                .build();

        new PatientManagementStack(app, "localstack", props);
        app.synth();

    }
}
