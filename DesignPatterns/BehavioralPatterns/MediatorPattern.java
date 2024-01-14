/**
 * Mediator pattern is used when we want to reduce chaotic communication between various objects in a system.
 * In such a situation, we want all communication to be handled by a central object called the mediator; no object communicates directly to each other.
 * This helps remove dependencies and enables reuse of objects.
 * In this example, the submit button and loginOrRegister checkbox don't communicate with each other, and neither do they communicate with the title.
 * All state changes of any object are notified to the mediator which in turn changes states of other objects as required.
 * By doing this, we didn't have to write code of updating title in loginOrRegister checkbox, which means this checkbox can be reused somewhere else which doesn't need title related logic.
 */

class MediatorPattern {
    public static void main(String[] args) {
        LoginForm form = new LoginForm();
        form.loginOrRegister.check();
        form.submit.click();
        form.loginOrRegister.check();
        form.submit.click();
    }
}

interface Mediator {
    public void notify(Component component, Event event);
}

class LoginForm implements Mediator {
    public String title;
    public Checkbox loginOrRegister;
    public Textbox loginUsername, loginPassword;
    public Textbox registerUsername, registerPassword;
    public Button submit, cancel;

    public LoginForm() {
        loginOrRegister = new Checkbox(this);
        loginUsername = new Textbox(this);
        loginPassword = new Textbox(this);
        registerUsername = new Textbox(this);
        registerPassword = new Textbox(this);
        submit = new Button(this);
        cancel = new Button(this);
    }

    @Override
    public void notify(Component component, Event event) {
        if(component == loginOrRegister && event == Event.CHECK) {
            if(loginOrRegister.checked) title = "Login";
            else title = "Register";
            System.out.println("Title: " + title);
        }
        if(component == submit && event == Event.CLICK) {
            if(loginOrRegister.checked) {
                System.out.println("Logging in...");
            } else System.out.println("Registering...");
        }
    }
}

class Component {
    protected Mediator mediator;

    public Component(Mediator mediator) {
        this.mediator = mediator;
    }

    public void click() {
        mediator.notify(this, Event.CLICK);
    }

    public void keypress() {
        mediator.notify(this, Event.KEYPRESS);
    }
}

class Button extends Component {
    public Button(Mediator mediator) {
        super(mediator);
    }
}

class Checkbox extends Component {
    public boolean checked = false;

    public Checkbox(Mediator mediator) {
        super(mediator);
    }
    public void check() {
        if(checked) checked = false;
        else checked = true;
        mediator.notify(this, Event.CHECK);
    }
}

class Textbox extends Component {
    public Textbox(Mediator mediator) {
        super(mediator);
    }

    public String input;
}

enum Event {
    CLICK,
    KEYPRESS,
    CHECK
}