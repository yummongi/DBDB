package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConn {

    public static Connection dbConnect() {

        Connection conn = null;

        final String driver = "org.mariadb.jdbc.Driver"; //패키지
        final String DB_IP = "localhost"; //접속 아이피
        final String DB_PORT = "3306"; //포트
        final String DB_NAME = "dbdb"; //DB 이름
        final String DB_URL = //DB 연결 방식
                "jdbc:mariadb://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(DB_URL, "root", "1234");
            if (conn != null) {
                System.out.println("DB 접속 성공");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB 접속 실패");
            e.printStackTrace();
        }
        return conn;
    }

    public static void dbView() {
        Connection conn = null;
        conn = dbConnect();


        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM `game`";

            //쿼리 준비
            pstmt = conn.prepareStatement(sql);

            //rs 변수에 데이터 삽입
            rs = pstmt.executeQuery();

            String num = null;
            String user_id = null;
            String user_pw = null;
            String user_name = null;
            String marble = null;
            String create_at = null;
            String update_at = null;

            //rs.next에 데이터가 들어오면 해당 반복문 실행
            while (rs.next()) {
                //rs.getString("컬럼명")
                num = rs.getString("user_id");
                user_id = rs.getString("user_id");
                user_pw = rs.getString("user_pw");
                user_name = rs.getString("user_name");
                marble = rs.getString("marble");
                create_at = rs.getString("create_at");
                update_at = rs.getString("update_at");

                System.out.println(num);
                System.out.println(user_id);
                System.out.println(user_pw);
                System.out.println(user_name);
                System.out.println(marble);
                System.out.println(create_at);
                System.out.println(update_at);
                System.out.println();
            }


        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally { //데이터 베이스를 연결했으면 항상 닫아야함 (안하면 풀 현상)
            try {
                //쿼리 닫기
                if (rs != null) {
                    rs.close();
                }
                //데이터 닫기
                if (pstmt != null) {
                    pstmt.close();
                }
                //sql 닫기
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //회원가입
    public static void register() {
        dbConnect();
        PreparedStatement pstmt = null;
        Connection conn = dbConnect();;
        ResultSet rs = null;

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("가입할 아이디를 입력하세요.");
            String user_id = scanner.next();
            System.out.print("가입할 비밀번호를 입력하세요.");
            String user_pw = scanner.next();
            System.out.print("가입할 이름을 입력하세요.");
            String user_name = scanner.next();


            // Insert문 ?부분은 아래의 입력값이 자동으로 변환이 됩니다.
            String sql = "INSERT INTO `game` (`user_id`, `user_pw`, `user_name`) VALUES (?, ?, ?)";

            //쿼리 준비
            pstmt = conn.prepareStatement(sql);

            // Insert 데이터값
            int index = 1;

            //메소드의 매게변수에 입력 받은 데이터를 넣으면 된다.
            pstmt.setString(index++, user_id);
            pstmt.setString(index++, user_pw);
            pstmt.setString(index++, user_name);

            //SQL 실행
            int result = pstmt.executeUpdate();
            if (result == 0) {
                System.out.println("회원가입에 실패하였습니다.");
            } else {
                System.out.println("회원가입에 성공하였습니다.");
                login();
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally { //데이터 베이스를 연결했으면 항상 닫아야함 (안하면 풀 현상)
            try {
                //데이터 닫기
                if (pstmt != null) {
                    pstmt.close();
                }
                //sql 닫기
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //로그인
    public static void login() {
        dbConnect();
        PreparedStatement pstmt = null;
        Connection conn = dbConnect();;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM `game`";
            //String sql_name = "SELECT user_name FROM `game` WHERE user_id = 'user_id'";

            //쿼리 준비
            pstmt = conn.prepareStatement(sql);


            //rs 변수에 데이터 삽입
            rs = pstmt.executeQuery();

            //DB 변수
            String num = null;
            String user_id = null;
            String user_pw = null;
            String user_name = null;
            String marble = null;
            String create_at = null;
            String update_at = null;

            Scanner scanner = new Scanner(System.in);
            //Scanner 입력 변수
            String answer = null;
            String id = null;
            String pw = null;
            String name = null;
            System.out.println("오징어 게임에 오신 것을 환영합니다.");
            System.out.print("아이디를 가지고 계신가요? y/n: ");
            answer = scanner.next(); //아이디가 맞다면

//            while (rs.next()) {
//                user_pw = rs.getString("user_pw");
//                System.out.println(user_pw); //디버깅
//                user_id = rs.getString("user_id");
//                System.out.println(user_id); //디버깅
//                System.out.println();
//            }


            if (answer.equalsIgnoreCase("y")) { //y라면
                System.out.print("아이디 입력: ");
                id = scanner.next();
                System.out.print("패스워드 입력: ");
                pw = scanner.next();

                while (rs.next()) {
                    user_id = rs.getString("user_id");
                    user_pw = rs.getString("user_pw");
                    user_name = rs.getString("user_name");

                    if (id.equalsIgnoreCase(user_id)) {
                        break;
                    }
                    if (pw.equalsIgnoreCase(user_pw)) {
                        break;
                    }
                }


                if (id.equalsIgnoreCase(user_id)) { //아이디가 맞다면
                    System.out.println("아이디가 있습니다. 성공");
                    if (id.equalsIgnoreCase(user_id) && pw.equalsIgnoreCase(user_pw)) {
                        System.out.println("로그인 성공!");
                        System.out.println("==================");
                        System.out.println("당신의 아이디: " + user_id);
                        System.out.println("당신의 패스워드: " + user_pw);
                        System.out.println("당신의 이름: " + user_name);
                    } else {
                        System.out.println("비밀번호가 틀렸습니다!");
                    }
                } else { //회원가입
                    System.out.println("아이디가 없습니다. 회원가입으로 이동합니다.");
                    register();
                }
            } else if (answer.equalsIgnoreCase("n")) {
                System.out.println("아이디가 없으므로, 회원가입으로 이동합니다.");
                register();
            } else {
                System.out.println("로그인 실패!");
            }


        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally { //데이터 베이스를 연결했으면 항상 닫아야함 (안하면 풀 현상)
            try {
                //쿼리 닫기
                if (rs != null) {
                    rs.close();
                }
                //데이터 닫기
                if (pstmt != null) {
                    pstmt.close();
                }
                //sql 닫기
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}