package sample;

public class UserProfile {
    private String name;
    private String email;
    private String keystrokeString ;

    public UserProfile(String name, String email, String keystrokeString){
        this.name = name;
        this.email = email;
        this.keystrokeString = keystrokeString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKeystrokeString() {
        return keystrokeString;
    }

    public void setKeystrokeString(String keystrokeString) {
        this.keystrokeString = keystrokeString;
    }
}
