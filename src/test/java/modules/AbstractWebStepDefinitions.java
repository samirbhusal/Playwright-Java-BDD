package modules;

public abstract class AbstractWebStepDefinitions {

    public PageObjectFactory pageFactory;

    public AbstractWebStepDefinitions() {
        pageFactory = new PageObjectFactory();
    }
}
