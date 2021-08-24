package cn.widealpha.train.domain;

public class Role {
    public static final String SYSTEM = "ROLE_SYSTEM";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String SERVER = "ROLE_SERVER";
    public static final String COMMON = "ROLE_COMMON";

    private Integer id;

    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}