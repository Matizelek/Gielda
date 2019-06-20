package user;

import java.util.Objects;

public class User {
    private final String hashedPassword;
    private final String username;
    private Long id;

    public User(String hashedPassword, String username, Long id) {
        this.hashedPassword = hashedPassword;
        this.username = username;
        this.id = id;
    }

    public String getNick() {
    	String[] parts = username.split("@");
    	return parts[0];
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    
    public Long getId() {
		return id;
	}
    
    public void setId(Long id) {
		this.id = id;
	}


	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
