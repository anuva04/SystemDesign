/**
 * This pattern lets you pass requests along a chain of processors until it reaches an appropriate processor which can handle it.
 * User doesn't need to know about all the available processors.
 * Only the head of the chain needs to be known to the users, and each processor can know its succeeding one.
 * In this example, user is provided the head of the chain that is Leave Request processor.
 * If the request provided by user is a leave request, it is handled immediately, else it is passed down the chain until an appropriate processor is found.
 * If no processor can process it, an appropriate message is returned to the user.
 * If new processors come in, they can be attached to the chain without any change required on the user side.
 */

class ChainOfResponsibilityPattern {
    public static void main(String[] args) {
        RequestProcessor requestProcessor = getRequestProcessor();

        Request request = new LeaveRequest("123", "test leave request", "01/01/1970", "02/01/1970");
        System.out.println("Got request: " + request.description);
        requestProcessor.handle(request);

        Request request1 = new DatabaseAccessRequest("123", "test db request", "test_db", "read");
        System.out.println("Got request: " + request1.description);
        requestProcessor.handle(request1);

        Request request2 = new OtherRequest("123", "test other request");
        System.out.println("Got request: " + request2.description);
        requestProcessor.handle(request2);
    }

    public static RequestProcessor getRequestProcessor() {
        LeaveRequestProcessor leaveRequestProcessor = new LeaveRequestProcessor();
        RelocationRequestProcessor relocationRequestProcessor = new RelocationRequestProcessor();
        DatabaseAccessRequestProcessor databaseAccessRequestProcessor = new DatabaseAccessRequestProcessor();

        leaveRequestProcessor.setNextProcessor(relocationRequestProcessor);
        relocationRequestProcessor.setNextProcessor(databaseAccessRequestProcessor);
        databaseAccessRequestProcessor.setNextProcessor(null);

        return leaveRequestProcessor;
    }
}

abstract class RequestProcessor {
    protected RequestProcessor next;

    public void setNextProcessor(RequestProcessor next) {
        this.next = next;
    }

    public abstract void handle(Request request);
}

public class LeaveRequestProcessor extends RequestProcessor {
    @Override
    public void handle(Request request) {
        if(request instanceof LeaveRequest) {
            System.out.println("[LeaveRequestProcessor] Processing request...");
        } else {
            System.out.println("[LeaveRequestProcessor] Can't process request, handing over to next processor.");
            if(this.next != null) {
                this.next.handle(request);
            } else {
                System.out.println("[LeaveRequestProcessor] No available processor to handle the request.");
            }
        }
    }
}

public class RelocationRequestProcessor extends RequestProcessor {
    @Override
    public void handle(Request request) {
        if(request instanceof RelocationRequest) {
            System.out.println("[RelocationRequestProcessor] Processing request...");
        } else {
            System.out.println("[RelocationRequestProcessor] Can't process request, handing over to next processor.");
            if(this.next != null) {
                this.next.handle(request);
            } else {
                System.out.println("[RelocationRequestProcessor] No available processor to handle the request.");
            }
        }
    }
}

public class DatabaseAccessRequestProcessor extends RequestProcessor {
    @Override
    public void handle(Request request) {
        if(request instanceof DatabaseAccessRequest) {
            System.out.println("[DatabaseAccessRequestProcessor] Processing request...");
        } else {
            System.out.println("[DatabaseAccessRequestProcessor] Can't process request, handing over to next processor.");
            if(this.next != null) {
                this.next.handle(request);
            } else {
                System.out.println("[DatabaseAccessRequestProcessor] No available processor to handle the request.");
            }
        }
    }
}

class Request {
    String employeeId;
    String description;

    public Request(String employeeId, String description) {
        this.employeeId = employeeId;
        this.description = description;
    }
}

class LeaveRequest extends Request {
    public String startdate;
    public String endDate;

    public LeaveRequest(String employeeId, String description, String startdate, String endDate) {
        super(employeeId, description);
        this.startdate = startdate;
        this.endDate = endDate;
    }
}

class RelocationRequest extends Request {
    public String location;
    public String startDate;

    public RelocationRequest(String employeeId, String description, String location, String startDate) {
        super(employeeId, description);
        this.location = location;
        this.startDate = startDate;
    }
}

class DatabaseAccessRequest extends Request {
    public String dbName;
    public String accessType;

    public DatabaseAccessRequest(String employeeId, String description, String dbName, String accessType) {
        super(employeeId, description);
        this.dbName = dbName;
        this.accessType = accessType;
    }
}

class OtherRequest extends Request {
    public OtherRequest(String employeeId, String description) {
        super(employeeId, description);
    }
}