package entity;

public class Employers {
    private int userId;
    private String name;
    private String position;
    private String companyName;
    private String location;

    public Employers () {}

    public Employers (int userId, String name, String position, String companyName, String location) {
        this.userId = userId;
        this.name = name;
        this.position = position;
        this.companyName = companyName;
        this.location = location;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    @Override
    public String toString() {
        return "Employer{" + "userId=" + userId + ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", companyName='" + companyName + '\'' +
                ", location='" + location + '\'' + '}';
    }
}
