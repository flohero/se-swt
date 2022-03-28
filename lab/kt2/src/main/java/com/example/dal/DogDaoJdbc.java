package com.example.dal;

import com.example.domain.Dog;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DogDaoJdbc implements DogDao {

    private final JdbcTemplate jdbcTemplate;

    public DogDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class DogMapper implements RowMapper<Dog> {

        @Override
        public Dog mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Dog(rs.getLong(1), rs.getString(2));
        }
    }

    @Override
    public Optional<Dog> findById(Long id) {
        final String sql = "select ID, NAME from Dog where id=?";
        var dog = jdbcTemplate.query(sql, new DogMapper(), id);
        if(dog.isEmpty()) {
            return Optional.empty();
        } else if(dog.size() == 1) {
            return Optional.of(dog.get(0));
        } else {
            throw new IncorrectResultSizeDataAccessException(1, dog.size());
        }
    }

    @Override
    public List<Dog> findAll() {
        return jdbcTemplate.query("select id, name from dog", new DogMapper());
    }

    @Override
    public void remove(Dog dog) {

    }

    @Override
    public void insert(Dog dog) {
        final String sql =
                "insert into DOG (NAME) "
                        + "values ( ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, dog.getName());
            return ps;
        }, keyHolder);
        dog.setId(keyHolder.getKey().longValue());
    }
}
