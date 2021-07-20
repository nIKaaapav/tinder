package intity;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String gender;
    private String urlImg;
    private String whom_liked;
    private String position;
    private Date date;
    private String timeDif;

    public User(){}

    public User(int id, String name, String surname, String email, String gender, String urlImg) {
        this.id=id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
        this.urlImg = urlImg;
    }

    public User(int id, String name, String surname, String email, String gender, String urlImg, String whom_liked) {
        this.id=id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
        this.urlImg = urlImg;
        this.whom_liked=whom_liked;

    }
    public User(int id, String name, String urlImg) {
        this.id=id;
        this.name = name;
        this.urlImg = urlImg;
    }

    public User(int id, String name, String urlImg, String position, java.sql.Date date) {
        this.id = id;
        this.name = name;
        this.urlImg = urlImg;
        this.position = position;
        this.date = date;
    }

    public User( String name, String urlImg, String position, Date date) {
        this.name = name;
        this.urlImg = urlImg;
        this.position=position;
        this.date=date;
    }

    public User(String name, String surname, String email, String password, String gender, String urlImg) {
    }


    public String getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getWhom_liked(){return whom_liked;}

    public String getUrlImg() {
        return urlImg;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", urlImg='" + urlImg + '\'' +
                ", whom_liked='" + whom_liked + '\'' +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeDif() {
        return timeDif;
    }

    public void setTimeDif(String timeDif) {
        this.timeDif = timeDif;
    }


}
