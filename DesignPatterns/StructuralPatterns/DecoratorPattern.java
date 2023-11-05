/**
 * This pattern is used to attach additional behaviors/characteristics to elements.
 * When we have a situation where new responsibility asks arise of an object regularly, it is not a good idea to keep exntending the class.
 * This is because there can a huge number of combinations of various responsibilities and creating child classes for each of these is not feasible.
 * Instead we wrap the original class in various decorator classes to provide it with the required responsibilties.
 * This can be done statically or at runtime.
 * In this example, we have the original object Venue, and we keep decorating it with added charateristics like Balloons and Lights.
 */

class DecoratorPattern {
    public static void main(String[] args) {
        Venue partyVenue = new PartyVenue();
        partyVenue = new Balloons(partyVenue);
        partyVenue = new Lights(partyVenue);
        partyVenue = new Sequins(partyVenue);

        System.out.println(partyVenue.decorate());
    }
}

interface Venue {
    String decorate();
}

class PartyVenue implements Venue {
    @Override
    public String decorate() {
        return "Party Venue";
    }
}

abstract class VenueDecorator implements Venue {
    private Venue venue;

    public VenueDecorator(Venue venue) {
        this.venue = venue;
    }
    @Override
    public String decorate() {
        return venue.decorate();
    }
}

class Balloons extends VenueDecorator {
    public Balloons(Venue venue) {
        super(venue);
    }

    public String decorate() {
        return super.decorate() + balloonDecorator();
    }

    private String balloonDecorator() {
        return " with balloons";
    }
}

class Sequins extends VenueDecorator {
    public Sequins(Venue venue) {
        super(venue);
    }

    public String decorate() {
        return super.decorate() + sequinDecorator();
    }

    private String sequinDecorator() {
        return " with sequins";
    }
}

class Lights extends VenueDecorator {
    public Lights(Venue venue) {
        super(venue);
    }

    public String decorate() {
        return super.decorate() + lightsDecorator();
    }

    private String lightsDecorator() {
        return " with lights";
    }
}