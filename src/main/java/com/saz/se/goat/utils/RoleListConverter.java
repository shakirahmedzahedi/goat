package com.saz.se.goat.utils;

import com.saz.se.goat.model.Role;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class RoleListConverter implements AttributeConverter<List<Role>, String> {

    // Convert List<Role> to a comma-separated String
    @Override
    public String convertToDatabaseColumn(List<Role> roleList) {
        if (roleList == null || roleList.isEmpty()) {
            return "";
        }
        return roleList.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    // Convert a comma-separated String to List<Role>
    @Override
    public List<Role> convertToEntityAttribute(String roleString) {
        if (roleString == null || roleString.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(roleString.split(","))
                .map(Role::valueOf)
                .collect(Collectors.toList());
    }
}
