package myapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String userName;
    private String password;

    protected User() {}

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

		public String getUserName(){
			return this.userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return this.password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, userName='%s', password='%s']",
                id, userName, password);
    }

}
