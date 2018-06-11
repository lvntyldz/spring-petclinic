package com.samples.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.samples.dao.OwnerRepository;
import com.samples.model.Owner;

@Repository("ownerRepository")
public class OwnerRepositoryJdbcImpl implements OwnerRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private RowMapper<Owner> rowMapper = new RowMapper<Owner>() {

		@Override
		public Owner mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Owner owner = new Owner();
			owner.setId(resultSet.getLong("id"));
			owner.setFirstName(resultSet.getString("first_name"));
			owner.setLastName(resultSet.getString("last_name"));

			return owner;
		}
	};

	@Override
	public List<Owner> findAll() {
		String sql = "SELECT id,first_name,last_name FROM t_owner";
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public Owner findById(Long id) {
		String sql = "SELECT id,first_name,last_name FROM t_owner WHERE id=?";
		return DataAccessUtils.singleResult(jdbcTemplate.query(sql, rowMapper, id));
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		String sql = "SELECT id,first_name,last_name FROM t_owner WHERE last_name LIKE ?";
		return jdbcTemplate.query(sql, rowMapper, "%" + lastName + "%");
	}

	@Override
	public void create(Owner owner) {
		String sql = "INSERT INTO t_owner(id,first_name,last_name) VALUES (?,?,?)";
		jdbcTemplate.update(sql, owner.getId(), owner.getFirstName(), owner.getLastName());
	}

	@Override
	public Owner update(Owner owner) {

		String sql = "UPDATE t_owner SET last_name=? WHERE id=? ";
		jdbcTemplate.update(sql, owner.getLastName(), owner.getId());

		String selectSql = "SELECT id,first_name,last_name FROM t_owner WHERE id=?";

		return DataAccessUtils.singleResult(jdbcTemplate.query(selectSql, rowMapper, owner.getId()));
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
