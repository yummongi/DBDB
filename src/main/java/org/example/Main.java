package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //y = 아이디 입력 후 아이디가 있다면 로그인 프로세스로 이동
        //아이디 없으면 신규가입


        //n = 신규 가입
        //아이디, 패스워드,이름 입력받고 디비 저장 후 로그인 프로세스로 이동

        // 로그인 프로세스)
        //디비에 아이디 패스워드가 있는지 확인해서 있으면 게임 시작
        //없으면 다시 로그인

        //한번 게임을 하고 게임 데이터를 저장할 것인가?
        //y = 저장 후 게임 종료
        //n = 게임 종료
        //다시 게임을 실행하면 자신에 아이디에 따라 구슬 갯수 정보 출력

        DBConn.login();

    }
}