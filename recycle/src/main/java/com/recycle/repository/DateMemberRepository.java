package com.recycle.repository;

import com.recycle.domain.MemberGameDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DateMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    public DateMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 데이터베이스 연결을 위한 DataSource를 사용하여 Connection을 가져옴
            conn = dataSource.getConnection();

            String sql = "INSERT INTO Member (id, email, password, joinDate) VALUES (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);

            // 이거는 회원가입에서 받은 정보로 할거임
            pstmt.setInt(1, 1);
            pstmt.setString(2, "1234");
            pstmt.setString(3, "test@test.com");
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            // 쿼리 실행
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public MemberGameDAO getMemberGameInfo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT M.email, M.joinDate, G.gameScore " +
                    "FROM member M INNER JOIN game G ON M.id = G.id " +
                    "WHERE M.id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                MemberGameDAO mg = new MemberGameDAO();
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
    public List<MemberGameDAO> getAllGameScore() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<MemberGameDAO> memberGameInfos = new ArrayList<>();

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT M.email, M.joinDate, G.gameScore " +
                    "FROM member M INNER JOIN game G ON M.id = G.id";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                MemberGameDAO mg = new MemberGameDAO();
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
