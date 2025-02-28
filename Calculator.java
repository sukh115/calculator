import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculator {


    private final List<Double> results;
    private char operator;
    private String firstInput;
    private String secondInput;


    public Calculator() {
        results = new ArrayList<>();
    }



    // 입력값 검증 메서드
    public boolean validateInput(String input) {
        if (input.trim().isEmpty()) { // 공백 입력 확인
            System.out.println("아무 값도 입력하지 않았습니다. 값을 다시 입력하세요.");
            return false;
        }
        return true;
    }


    // 종료 처리 메서드
    public static boolean exit(String input) {
        return input.equalsIgnoreCase("exit");
    }

    // 첫 번째 숫자 입력 메서드
    public void setFirstInput(Scanner sc) {
        while (true) {
            System.out.println("첫 번째 숫자를 입력하세요. 'exit' 입력 시 종료, 'remove' 입력시 첫번 째 결과 값 삭제 : ");
            String input = sc.nextLine();

            // 입력 유효성 검사
            if (!validateInput(input)) {
                continue;
            }

            // 종료 처리
            if (exit(input)) {
                firstInput = "exit"; // 종료
                return;
            }

            // remove 처리
            if (input.equalsIgnoreCase("remove")) {
                removeList();
                System.out.println("삭제 후 현재 값들 : " + getResults());
                continue; // remove 처리 후 다시 입력
            }

            firstInput = input; // 유효한 입력을 설정
            return;
        }
    }

    // 첫 번째 입력값 반환
    public String getFirstInput() {
        return firstInput;
    }

    // 연산자 입력 메서드
    public void setOperator(Scanner sc) {
        while (true) {
            System.out.println("연산자를 입력하세요 (+,-,*,/) 또는 'exit' 입력 시 종료:");
            String input = sc.nextLine();

            // 입력 유효성 검사
            if (!validateInput(input)) {
                continue;
            }

            // 종료 처리
            if (exit(input)) {
                operator = 'e'; // 종료를 알리는 특수 문자
                return;
            }

            operator = input.charAt(0); // 연산자 설정
            return;
        }
    }

    // 연산자 반환
    public char getOperator() {
        return operator;
    }

    // 두 번째 숫자 입력 메서드
    public void setSecondInput(Scanner sc) {
        while (true) {
            System.out.println("두 번째 숫자를 입력하세요 또는 'exit' 입력 시 종료:");
            String input = sc.nextLine();

            // 입력 유효성 검사
            if (!validateInput(input)) {
                continue;
            }

            // 종료 처리
            if (exit(input)) {
                secondInput = "exit"; // 종료
                return;
            }

            secondInput = input; // 유효한 입력을 설정
            return;
        }
    }

    // 두 번째 입력값 반환
    public String getSecondInput() {
        return secondInput;
    }

    // 연산 수행 메서드
    public void calculate(double num1, char operator, double num2) {
        double newResult;

        switch (operator) {
            case '+':
                newResult = add(num1, num2);
                break;
            case '-':
                newResult = subtract(num1, num2);
                break;
            case '*':
                newResult = multiply(num1, num2);
                break;
            case '/':
                try {
                    newResult = divide(num1, num2);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                break;
            default:
                System.out.println("잘못된 연산자입니다.");
                return;
        }

        results.add(newResult); // 결과 저장
    }

    // 덧셈
    private double add(double num1, double num2) {
        return num1 + num2;
    }

    // 뺄셈
    private double subtract(double num1, double num2) {
        return num1 - num2;
    }

    // 곱셈
    private double multiply(double num1, double num2) {
        return num1 * num2;
    }

    // 나눗셈
    private double divide(double num1, double num2) {
        if (num2 == 0) {
            throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
        }
        return num1 / num2;
    }

    // 최신 결과 반환
    public double getLastResult() {
        if (results.isEmpty()) {
            return 0; // 결과가 없으면 0 반환
        }
        return results.get(results.size() - 1);
    }

    // 결과 리스트 반환
    public List<Double> getResults() {
        return new ArrayList<>(results); // 원본 보호를 위해 복사본 반환
    }

    // 첫 번째 결과 삭제
    public void removeList() {
        if (!results.isEmpty()) {
            results.remove(0); // 첫 번째 결과 삭제
        }
    }
}