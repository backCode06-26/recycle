package org.recycle.repository;

import org.recycle.domain.Member;
import org.recycle.domain.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataMemberRepository implements MemberRepository {

    @Autowired
    private final DataSource dataSource;

    public DataMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        System.out.println("DB 연결에 성공했습니다.");
    }

    /*
    CREATE TABLE member (
        id number(5) AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(10) NOT NULL,
        password VARCHAR(10) NOT NULL,
        joinDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    */


    @Override
    public void save(Member member) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 데이터베이스 연결을 위한 DataSource를 사용하여 Connection을 가져옴
            conn = dataSource.getConnection();

            // MEMBER_SEQ_ID 시퀀스에서 다음 값을 가져옴
            String sqlGetId = "SELECT MEMBER_SEQ.NEXTVAL FROM DUAL";
            pstmt = conn.prepareStatement(sqlGetId);
            rs = pstmt.executeQuery();

            long generatedId;
            if (rs.next()) {
                generatedId = rs.getLong(1);
            } else {
                throw new SQLException("ID 조회 실패");
            }

            // Member 객체의 ID를 설정
            member.setId(generatedId);

            // MEMBER 테이블에 멤버 정보를 삽입
            String sqlInsertMember = "INSERT INTO MEMBER (id, email, password, join_date) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sqlInsertMember);
            pstmt.setLong(1, member.getId());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();

            // GAME 테이블에도 데이터 추가
            String sqlInsertGame = "INSERT INTO GAME (id, game_score) VALUES (?, ?)";
            pstmt.close(); // 이전 PreparedStatement 닫기
            pstmt = conn.prepareStatement(sqlInsertGame);
            pstmt.setLong(1, member.getId()); // 앞서 생성된 멤버의 ID를 사용
            pstmt.setInt(2, 0);
            pstmt.executeUpdate();

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
            String sql = "select * from MEMBER where email = ?";
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


    //
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
            pstmt.setLong(1, ud.getGameScore() + 1);
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
    public UserDAO getUserInfo(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT M.email, M.joinDate, G.gameScore " +
                    "FROM member M INNER JOIN game G ON M.id = G.id " +
                    "WHERE M.email = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                UserDAO mg = new UserDAO();
                mg.setEmail(rs.getString("email"));
                mg.setJoinDate(rs.getTimestamp("joinDate"));
                mg.setGameScore(rs.getInt("gameScore"));
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
                    "    SELECT M.email, M.joinDate, G.gameScore, ROWNUM AS row_num " +
                    "    FROM (SELECT M.email, M.joinDate, G.gameScore " +
                    "          FROM member M INNER JOIN game G ON M.id = G.id) M " +
                    "    WHERE ROWNUM <= ?" +
                    ") WHERE row_num > ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, start + page);
            pstmt.setInt(2, start);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserDAO mg = new UserDAO();
                mg.setEmail(rs.getString("email"));
                mg.setJoinDate(rs.getTimestamp("joinDate"));
                mg.setGameScore(rs.getInt("gameScore"));
                memberGameInfos.add(mg);
            }
            return memberGameInfos;
        } catch (SQLException e) {
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
