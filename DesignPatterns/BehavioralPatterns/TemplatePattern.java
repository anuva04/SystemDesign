/**
 * This pattern is used when we have multiple code workflows with almost similar logic and only minor differences.
 * Instead of writing redundant logic for each workflow, we can segragate the code into multiple methods.
 * Code which is similar for all workflows can be implemented in base class, and dissimilar code can be implemented in child classes.
 * In this example, in a CI/CD pipeline, stages code checkout and compliance check logic are same for all pipelines so they are implemented in the abstract class.
 * Build, Test and Deploy logic are different so they are implemented in the extended classes.
 */

class TemplatePattern {
    public static void main(String[] args) {
        FunctionAppPipeline functionAppPipeline = new FunctionAppPipeline();
        functionAppPipeline.runPipeline();

        WebappPipeline webappPipeline = new WebappPipeline();
        webappPipeline.runPipeline();
    }
}

abstract class CICDPipeline {
    public void runPipeline() {
        checkout();
        build();
        runTests();
        runComplianceChecks();
        deploy();
    }
    protected void checkout() {
        System.out.println("Checking out code...");
        System.out.println("Checked out code.");
    }

    protected abstract void build();
    protected abstract void runTests();

    protected void runComplianceChecks() {
        System.out.println("Running compliance checks...");
        System.out.println("Compliance checked cleared.");
    }

    protected abstract void deploy();
}

class FunctionAppPipeline extends CICDPipeline {
    @Override
    protected void build() {
        System.out.println("Building functionapp code...");
        System.out.println("Functionapp code built.");
    }

    @Override
    protected void runTests() {
        System.out.println("Running functionapp tests...");
        System.out.println("Tests cleared.");
    }

    @Override
    protected void deploy() {
        System.out.println("Deploying code to functionapp slot...");
        System.out.println("Deployed code to functionapp slot.");
    }
}

class WebappPipeline extends CICDPipeline {
    @Override
    protected void build() {
        System.out.println("Building webapp code...");
        System.out.println("Webapp code built.");
    }

    @Override
    protected void runTests() {
        System.out.println("Running webapp tests...");
        System.out.println("Tests cleared.");
    }

    @Override
    protected void deploy() {
        System.out.println("Deploying code to webapp slot...");
        System.out.println("Deployed code to webapp slot.");
    }
}