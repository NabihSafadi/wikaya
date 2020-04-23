package com.nabih.wikaya;

public class Places {

    private String string_place_id;
    private String string_place_name;
    private String string_place_address;
    private String string_location_type;
    private String string_residential_type;
    private String string_section;
    private String string_property_number;


    public Places(String input_place_id, String input_place_name,
                 String input_place_address, String input_location_type,
                 String input_residential_type, String input_section, String input_property_number) {
        set_place_id(input_place_id);
        set_place_name(input_place_name);
        set_place_address(input_place_address);
        set_location_type(input_location_type);
        set_residential_type(input_residential_type);
        set_section(input_section);
        set_property_number(input_property_number);


    }

    public void set_place_id(String place_id) {
        this.string_place_id = place_id;
    }
    public void set_place_name(String place_name) {
        this.string_place_name = place_name;
    }
    public void set_place_address(String place_address) {
        this.string_place_address = place_address;
    }
    public void set_location_type(String location_type) {
        this.string_location_type = location_type;
    }
    public void set_residential_type(String residential_type) {
        this.string_residential_type = residential_type;
    }

    public void set_section(String section) {
        this.string_section = section;
    }
    public void set_property_number(String property_number) {
        this.string_property_number = property_number;
    }


    public String get_place_id() {
        return this.string_place_id;
    }

    public String get_place_name() {
        return this.string_place_name;
    }

    public String get_place_address() {
        return this.string_place_address;
    }

    public String get_location_type() {
        return this.string_location_type;
    }

    public String get_residential_type() {
        return this.string_residential_type;
    }
    public String get_section() {
        return this.string_section;
    }


    public String get_property_number() {
        return this.string_property_number;
    }


}
