package spring.homework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class Role {

    private String name;
    private List<String> permissions;

    public List<String> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return  getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission))
                .collect(Collectors.toSet());
    }
}
