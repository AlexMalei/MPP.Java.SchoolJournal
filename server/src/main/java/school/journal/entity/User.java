package school.journal.entity;

public class User {
    private int userId;
    private String username;
    private String passHash;
    private byte locked;
    private String email;
    private ApiToken apiTokenByUserId;
    private Pupil pupilByUserId;
    private Teacher teacherByUserId;
    private Role roleByRoleId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public byte getLocked() {
        return locked;
    }

    public void setLocked(byte locked) {
        this.locked = locked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (locked != user.locked) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (passHash != null ? !passHash.equals(user.passHash) : user.passHash != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (passHash != null ? passHash.hashCode() : 0);
        result = 31 * result + (int) locked;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public ApiToken getApiTokenByUserId() {
        return apiTokenByUserId;
    }

    public void setApiTokenByUserId(ApiToken apiTokenByUserId) {
        this.apiTokenByUserId = apiTokenByUserId;
    }

    public Pupil getPupilByUserId() {
        return pupilByUserId;
    }

    public void setPupilByUserId(Pupil pupilByUserId) {
        this.pupilByUserId = pupilByUserId;
    }

    public Teacher getTeacherByUserId() {
        return teacherByUserId;
    }

    public void setTeacherByUserId(Teacher teacherByUserId) {
        this.teacherByUserId = teacherByUserId;
    }

    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }
}