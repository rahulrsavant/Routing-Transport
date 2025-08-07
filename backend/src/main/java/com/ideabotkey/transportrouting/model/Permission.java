package com.ideabotkey.transportrouting.model;

public enum Permission {
    VIEW_COMPANY(1, "VIEW_COMPANY", "View companies"),
    CREATE_COMPANY(2, "CREATE_COMPANY", "Create companies"),
    UPDATE_COMPANY(3, "UPDATE_COMPANY", "Update companies"),
    DELETE_COMPANY(4, "DELETE_COMPANY", "Delete companies"),

    VIEW_CONTRACT(5, "VIEW_CONTRACT", "View contracts"),
    CREATE_CONTRACT(6, "CREATE_CONTRACT", "Create contracts"),
    UPDATE_CONTRACT(7, "UPDATE_CONTRACT", "Update contracts"),
    DELETE_CONTRACT(8, "DELETE_CONTRACT", "Delete contracts"),

    VIEW_DRIVER(9, "VIEW_DRIVER", "View drivers"),
    CREATE_DRIVER(10, "CREATE_DRIVER", "Create drivers"),
    UPDATE_DRIVER(11, "UPDATE_DRIVER", "Update drivers"),
    DELETE_DRIVER(12, "DELETE_DRIVER", "Delete drivers"),

    VIEW_EMPLOYEE(13, "VIEW_EMPLOYEE", "View employees"),
    CREATE_EMPLOYEE(14, "CREATE_EMPLOYEE", "Create employees"),
    UPDATE_EMPLOYEE(15, "UPDATE_EMPLOYEE", "Update employees"),
    DELETE_EMPLOYEE(16, "DELETE_EMPLOYEE", "Delete employees"),

    VIEW_OWNER(17, "VIEW_OWNER", "View owners"),
    CREATE_OWNER(18, "CREATE_OWNER", "Create owners"),
    UPDATE_OWNER(19, "UPDATE_OWNER", "Update owners"),
    DELETE_OWNER(20, "DELETE_OWNER", "Delete owners"),

    VIEW_PERMISSION(21, "VIEW_PERMISSION", "View permissions"),
    CREATE_PERMISSION(22, "CREATE_PERMISSION", "Create permissions"),
    UPDATE_PERMISSION(23, "UPDATE_PERMISSION", "Update permissions"),
    DELETE_PERMISSION(24, "DELETE_PERMISSION", "Delete permissions"),

    VIEW_ROLE(25, "VIEW_ROLE", "View roles"),
    CREATE_ROLE(26, "CREATE_ROLE", "Create roles"),
    UPDATE_ROLE(27, "UPDATE_ROLE", "Update roles"),
    DELETE_ROLE(28, "DELETE_ROLE", "Delete roles"),

    VIEW_ROUTE(29, "VIEW_ROUTE", "View routes"),
    CREATE_ROUTE(30, "CREATE_ROUTE", "Create routes"),
    UPDATE_ROUTE(31, "UPDATE_ROUTE", "Update routes"),
    DELETE_ROUTE(32, "DELETE_ROUTE", "Delete routes"),

    VIEW_SEAT(33, "VIEW_SEAT", "View seats"),
    BOOK_SEAT(34, "BOOK_SEAT", "Book seat"),
    CANCEL_SEAT(35, "CANCEL_SEAT", "Cancel seat"),

    VIEW_TRIP(36, "VIEW_TRIP", "View trips"),
    CREATE_TRIP(37, "CREATE_TRIP", "Create trips"),
    UPDATE_TRIP(38, "UPDATE_TRIP", "Update trips"),
    DELETE_TRIP(39, "DELETE_TRIP", "Delete trips"),

    VIEW_TRIP_EMPLOYEE(40, "VIEW_TRIP_EMPLOYEE", "View trip employees"),
    CREATE_TRIP_EMPLOYEE(41, "CREATE_TRIP_EMPLOYEE", "Create trip employees"),
    UPDATE_TRIP_EMPLOYEE(42, "UPDATE_TRIP_EMPLOYEE", "Update trip employees"),
    DELETE_TRIP_EMPLOYEE(43, "DELETE_TRIP_EMPLOYEE", "Delete trip employees"),

    VIEW_USER(44, "VIEW_USER", "View users"),
    CREATE_USER(45, "CREATE_USER", "Create users"),
    UPDATE_USER(46, "UPDATE_USER", "Update users"),
    DELETE_USER(47, "DELETE_USER", "Delete users"),

    VIEW_PROFILE(48, "VIEW_PROFILE", "View profile"),
    UPDATE_PROFILE(49, "UPDATE_PROFILE", "Update profile"),

    VIEW_VEHICLE(50, "VIEW_VEHICLE", "View vehicles"),
    CREATE_VEHICLE(51, "CREATE_VEHICLE", "Create vehicles"),
    UPDATE_VEHICLE(52, "UPDATE_VEHICLE", "Update vehicles"),
    DELETE_VEHICLE(53, "DELETE_VEHICLE", "Delete vehicles");

    private final long id;
    private final String code;
    private final String description;

    Permission(long id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Permission fromCode(String code) {
        for (Permission p : values()) {
            if (p.code.equalsIgnoreCase(code)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown permission code: " + code);
    }

    public static Permission fromId(long id) {
        for (Permission p : values()) {
            if (p.id == id) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown permission id: " + id);
    }
}
