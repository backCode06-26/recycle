package com.team.recycle.repository;

import com.team.recycle.domain.Member;
import com.team.recycle.domain.UserDAO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataMemberRepository implements MemberRepository {

    private final DataSource dataSource;

    public DataMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Member member) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            // id_seq.nextval 값을 먼저 가져옴
            String sqlSeq = "SELECT id_seq.nextval FROM dual";
            pstmt = conn.prepareStatement(sqlSeq);
            rs = pstmt.executeQuery();
            long nextId = 0;
            if (rs.next()) {
                nextId = rs.getLong(1);
            }

            // member 테이블에 삽입
            String sqlMember = "INSERT INTO member (id, email, password, join_date) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sqlMember);
            pstmt.setLong(1, nextId);
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();

            // game 테이블에 삽입
            String sqlGame = "INSERT INTO game (id, game_score) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sqlGame);
            pstmt.setLong(1, nextId);
            pstmt.setInt(2, 0);
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }




    // 게임 점수 올리기
    @Override
    public int scoreUp(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        UserDAO ud = getUserInfo(email);

        try {
            conn = dataSource.getConnection();

            String sql = "update game set game_score = ? where email = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, ud.getGameScore()+1);
            pstmt.setString(2, email);

            // 쿼리 실행
            pstmt.executeUpdate();

            return 0;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Member findByEmail(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            Member member = new Member();
            String sql = "select * from member where email = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, email);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                member.setId(rs.getLong("id"));
                member.setEmail(rs.getString("email"));
                member.setPassword(rs.getString("password"));
                member.setJoinDate(rs.getTimestamp("join_date"));
            }
            return member;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public UserDAO getUserInfo(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT M.email, M.join_date, G.game_score " +
                    "FROM member M INNER JOIN game G ON M.id = G.id " +
                    "WHERE M.email = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                UserDAO mg = new UserDAO();
                mg.setEmail(rs.getString("email"));
                mg.setJoinDate(rs.getTimestamp("join_date"));
                mg.setGameScore(rs.getInt("game_score"));
                return mg;
            } else {
                throw new SQLException("조회 실패");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<UserDAO> getAllUserInfo(int start, int page) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<UserDAO> memberGameInfos = new ArrayList<>();

        try {
            conn = dataSource.getConnection();

            // 페이징을 적용한 SQL 쿼리
            String sql = "SELECT * FROM ( " +
                    "    SELECT M.email, M.join_date, M.game_score, ROWNUM AS row_num " +
                    "    FROM (SELECT M.email, M.join_date, G.game_score " +
                    "          FROM member M INNER JOIN game G ON M.id = G.id) M " +
                    "    WHERE ROWNUM <= ?" +
                    ") WHERE row_num > ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, start * page);
            pstmt.setInt(2, (start-1)*page);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserDAO mg = new UserDAO();
                mg.setEmail(rs.getString("email"));
                mg.setJoinDate(rs.getTimestamp("join_date"));
                mg.setGameScore(rs.getInt("game_score"));
                memberGameInfos.add(mg);
            }
            return memberGameInfos;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }


    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
