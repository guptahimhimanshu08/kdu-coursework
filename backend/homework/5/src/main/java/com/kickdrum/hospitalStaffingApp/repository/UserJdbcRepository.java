package com.kickdrum.hospitalStaffingApp.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kickdrum.hospitalStaffingApp.model.User;

@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user) {
        String sql = """
            INSERT INTO users (id, username, logged_in, timezone, tenant_id)
            VALUES (?, ?, ?, ?, ?)
        """;
        
        jdbcTemplate.update(sql,
                user.getId(),
                user.getUsername(),
                user.isLoggedIn(),
                user.getTimezone(),
                user.getTenantId());
    }

    public List<User> findByTenantId(UUID tenantId, int limit, int offset, String sortDir) {
        String sql = """
            SELECT * FROM users
            WHERE tenant_id = ?
            ORDER BY username %s
            LIMIT ? OFFSET ?
        """.formatted(sortDir);

        return jdbcTemplate.query(sql, userRowMapper(), tenantId, limit, offset);
    }

    public int updateUser(UUID userId, String username, String timezone) {
        return jdbcTemplate.update("""
            UPDATE users
            SET username = ?, timezone = ?
            WHERE id = ?
        """, username, timezone, userId);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User u = new User();
            u.setId(UUID.fromString(rs.getString("id")));
            u.setUsername(rs.getString("username"));
            u.setLoggedIn(rs.getBoolean("logged_in"));
            u.setTimezone(rs.getString("timezone"));
            u.setTenantId(UUID.fromString(rs.getString("tenant_id")));
            return u;
        };
    }
}
