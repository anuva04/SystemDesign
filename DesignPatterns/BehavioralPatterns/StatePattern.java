/**
 * This pattern is used when we need to change an object's behavior when its state changes.
 * Instead of accomplising this by maintaining a state variable and adding a lot of conditional statements,
 * we can store a different class instance (called state) inside our concerned class.
 * All information and logic required for object's behavior will be stored in the state object.
 * In this example, state stores the logic for phone ring/vibration/silence.
 * PhoneRinger stores an instance of state.
 * Whenever state changes, the state object is also changed inside PhoneRinger.
 */
class StatePattern {
    public static void main(String[] args) {
        PhoneRinger ringer = new PhoneRinger("arial");
        ringer.ring();

        ringer.changeState(new SilentState(ringer));
        ringer.ring();

        ringer.changeState(new VibrationState(ringer));
        ringer.ring();
    }
}

class PhoneRinger {
    private State state;
    public String ringtone;

    public PhoneRinger(String ringtone) {
        state = new RingState(this);
        this.ringtone = ringtone;
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void ring() {
        this.state.ring();
    }
}

abstract class State {
    protected PhoneRinger phoneRinger;

    public State(PhoneRinger phoneRinger) {
        this.phoneRinger = phoneRinger;
    }

    abstract void ring();
}

class RingState extends State {
    public RingState(PhoneRinger phoneRinger) {
        super(phoneRinger);
    }
    @Override
    public void ring() {
        System.out.println("Received call: Playing ringtone " + this.phoneRinger.ringtone);
    }
}

class VibrationState extends State {
    public VibrationState(PhoneRinger phoneRinger) {
        super(phoneRinger);
    }
    @Override
    public void ring() {
        System.out.println("Received call: Phone is vibrating");
    }
}

class SilentState extends State {
    public SilentState(PhoneRinger phoneRinger) {
        super(phoneRinger);
    }
    @Override
    public void ring() {
        System.out.println("Received call: Phone is silent");
    }
}