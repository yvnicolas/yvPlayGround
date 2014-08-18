package jsonRpcMinimal;

public class Person {
    private String first;
    private String last;

    
    
    public Person(String first, String last) {
        super();
        this.first = first;
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

}
